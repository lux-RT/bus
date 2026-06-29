<template>
  <div class="violation-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="title">违章记录</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        录入违章
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchForm.keyword"
        placeholder="司机姓名或车牌号"
        clearable
        style="width: 180px"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-select
        v-model="searchForm.violationTypeId"
        placeholder="违章类型"
        clearable
        style="width: 150px"
      >
        <el-option
          v-for="item in violationTypes"
          :key="item.typeId"
          :label="item.typeName"
          :value="item.typeId"
        />
      </el-select>
      <el-select
        v-model="searchForm.lineId"
        placeholder="线路"
        clearable
        style="width: 110px"
      >
        <el-option
          v-for="item in lineOptions"
          :key="item.lineId"
          :label="item.lineName"
          :value="item.lineId"
        />
      </el-select>
      <el-date-picker
        v-model="searchForm.dateRange"
        type="datetimerange"
        range-separator="至"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        style="width: 340px"
        value-format="YYYY-MM-DD HH:mm:ss"
      />
      <el-button type="primary" @click="handleSearch">
        <el-icon><Search /></el-icon>
        查询
      </el-button>
      <el-button @click="handleReset">
        <el-icon><Refresh /></el-icon>
        重置
      </el-button>
    </div>

    <!-- 数据表格 -->
    <div class="card">
      <el-table
        v-loading="loading"
        :data="tableData"
        stripe
        style="width: 100%"
      >
        <el-table-column prop="recordId" label="ID" width="65" sortable />
        <el-table-column prop="driverName" label="司机" width="75" />
        <el-table-column prop="licensePlate" label="车牌号" width="105">
          <template #default="{ row }">
            <el-tag effect="plain" type="info" size="small">{{ row.licensePlate }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lineName" label="线路" width="80" />
        <el-table-column prop="stationName" label="站点" width="100" show-overflow-tooltip />
        <el-table-column prop="typeName" label="违章类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getViolationTagType(row.typeName)" size="small" effect="dark">
              {{ row.typeName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="violationTime" label="违章时间" width="140" sortable />
        <el-table-column prop="description" label="详细描述" min-width="140" show-overflow-tooltip />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button link type="danger" @click="handleDelete(row)">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      destroy-on-close
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="违章司机" prop="driverId">
              <el-select
                v-model="form.driverId"
                placeholder="请选择司机"
                filterable
                style="width: 100%"
                @change="handleDriverChange"
              >
                <el-option
                  v-for="item in driverOptions"
                  :key="item.driverId"
                  :label="item.name"
                  :value="item.driverId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="涉事车辆" prop="vehicleId">
              <el-select
                v-model="form.vehicleId"
                placeholder="请选择车辆"
                filterable
                style="width: 100%"
              >
                <el-option
                  v-for="item in vehicleOptions"
                  :key="item.vehicleId"
                  :label="item.licensePlate"
                  :value="item.vehicleId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="违章线路" prop="lineId">
              <el-select
                v-model="form.lineId"
                placeholder="请选择线路"
                style="width: 100%"
              >
                <el-option
                  v-for="item in lineOptions"
                  :key="item.lineId"
                  :label="item.lineName"
                  :value="item.lineId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="违章站点" prop="stationId">
              <el-select
                v-model="form.stationId"
                placeholder="请选择站点"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="item in stationOptions"
                  :key="item.stationId"
                  :label="item.stationName"
                  :value="item.stationId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="违章类型" prop="violationTypeId">
              <el-select
                v-model="form.violationTypeId"
                placeholder="请选择违章类型"
                style="width: 100%"
              >
                <el-option
                  v-for="item in violationTypes"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="违章时间" prop="violationTime">
              <el-date-picker
                v-model="form.violationTime"
                type="datetime"
                placeholder="请选择违章时间"
                style="width: 100%"
                value-format="YYYY-MM-DD HH:mm:ss"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="详细描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="3"
            placeholder="请输入违章详细描述"
          />
        </el-form-item>
        <el-form-item label="录入人" prop="inputterId">
          <el-select
            v-model="form.inputterId"
            placeholder="请选择录入人"
            style="width: 100%"
          >
            <el-option
              v-for="item in captainOptions"
              :key="item.driverId"
              :label="item.name"
              :value="item.driverId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, Refresh, Edit, Delete } from '@element-plus/icons-vue'
import { getViolationList, addViolation, updateViolation, deleteViolation, getViolationTypes, searchViolations } from '@/api/violation'
import { getAllLines } from '@/api/line'
import { getAllDrivers } from '@/api/driver'
import { getAllVehicles } from '@/api/vehicle'
import { getAllStations } from '@/api/station'
import { getAllFleets } from '@/api/fleet'

// 搜索表单
const searchForm = reactive({
  keyword: '',
  violationTypeId: '',
  lineId: '',
  dateRange: []
})

// 表格数据
const loading = ref(false)
const tableData = ref([])

// 分页
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 选项数据
const violationTypes = ref([])

const lineOptions = ref([])
const driverOptions = ref([])
const vehicleOptions = ref([])
const stationOptions = ref([])
const captainOptions = ref([])

const fetchLineOptions = async () => {
  try {
    const res = await getAllLines()
    lineOptions.value = res.data || []
  } catch (e) { console.error('获取线路列表失败', e) }
}

const fetchDriverOptions = async () => {
  try {
    const res = await getAllDrivers()
    const drivers = res.data || []
    driverOptions.value = drivers
    captainOptions.value = drivers.filter(d => d.isCaptain || d.isRoadCaptain)
  } catch (e) { console.error('获取司机列表失败', e) }
}

const fetchVehicleOptions = async () => {
  try {
    const res = await getAllVehicles()
    vehicleOptions.value = res.data || []
  } catch (e) { console.error('获取车辆列表失败', e) }
}

const fetchStationOptions = async () => {
  try {
    const res = await getAllStations()
    stationOptions.value = res.data || []
  } catch (e) { console.error('获取站点列表失败', e) }
}

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const formRef = ref(null)
const submitLoading = ref(false)

// 表单
const form = reactive({
  recordId: '',
  driverId: '',
  vehicleId: '',
  lineId: '',
  stationId: '',
  violationTypeId: '',
  violationTime: '',
  description: '',
  inputterId: ''
})

// 表单规则
const rules = {
  driverId: [
    { required: true, message: '请选择违章司机', trigger: 'change' }
  ],
  vehicleId: [
    { required: true, message: '请选择涉事车辆', trigger: 'change' }
  ],
  lineId: [
    { required: true, message: '请选择违章线路', trigger: 'change' }
  ],
  violationTypeId: [
    { required: true, message: '请选择违章类型', trigger: 'change' }
  ],
  violationTime: [
    { required: true, message: '请选择违章时间', trigger: 'change' }
  ],
  inputterId: [
    { required: true, message: '请选择录入人', trigger: 'change' }
  ]
}

// 获取违章标签类型
const getViolationTagType = (type) => {
  const typeMap = {
    '闯红灯': 'danger',
    '压线': 'warning',
    '未礼让斑马线': 'info',
    '违章停车': 'info',
    '超速': 'danger'
  }
  return typeMap[type] || 'info'
}

// 司机变化时自动填充线路
const handleDriverChange = (driverId) => {
  const driver = driverOptions.value.find(d => d.driverId === driverId)
  if (driver && driver.lineId) {
    form.lineId = driver.lineId
  }
}

// 获取违章类型列表
const fetchViolationTypes = async () => {
  try {
    const res = await getViolationTypes()
    violationTypes.value = res.data || []
  } catch (error) {
    console.error('获取违章类型失败', error)
  }
}

// 获取列表数据（服务端分页+搜索）
const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      keyword: searchForm.keyword || undefined,
      lineId: searchForm.lineId || undefined,
      violationTypeId: searchForm.violationTypeId || undefined
    }
    if (searchForm.dateRange && searchForm.dateRange.length === 2) {
      params.startTime = searchForm.dateRange[0]
      params.endTime = searchForm.dateRange[1]
    }
    const res = await searchViolations(params)
    tableData.value = res.data?.records || []
    pagination.total = res.data?.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  fetchData()
}

// 重置
const handleReset = () => {
  searchForm.keyword = ''
  searchForm.violationTypeId = ''
  searchForm.lineId = ''
  searchForm.dateRange = []
  handleSearch()
}

// 分页大小变化
const handleSizeChange = (val) => {
  pagination.pageSize = val
  pagination.page = 1
  fetchData()
}

// 页码变化
const handleCurrentChange = (val) => {
  pagination.page = val
  fetchData()
}

// 添加
const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '录入违章'
  Object.assign(form, {
    recordId: '',
    driverId: '',
    vehicleId: '',
    lineId: '',
    stationId: '',
    violationTypeId: '',
    violationTime: '',
    description: '',
    inputterId: ''
  })
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑违章记录'
  Object.assign(form, {
    recordId: row.recordId,
    driverId: row.driverId,
    vehicleId: row.vehicleId,
    lineId: row.lineId,
    stationId: row.stationId,
    violationTypeId: row.violationTypeId,
    violationTime: row.violationTime,
    description: row.description,
    inputterId: row.inputterId
  })
  dialogVisible.value = true
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除这条违章记录吗？`,
      '提示',
      { type: 'warning' }
    )
    await deleteViolation(row.recordId)
    ElMessage.success('删除成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败', error)
    }
  }
}

// 提交
const handleSubmit = async () => {
  formRef.value.validate(async valid => {
    if (valid) {
      submitLoading.value = true
      try {
        const submitData = { ...form }
        // 将空字符串转为 null，避免后端 Integer 反序列化失败
        if (submitData.stationId === '' || submitData.stationId === undefined) submitData.stationId = null
        if (isEdit.value) {
          await updateViolation(submitData.recordId, submitData)
        } else {
          await addViolation(submitData)
        }
        ElMessage.success(isEdit.value ? '编辑成功' : '录入成功')
        dialogVisible.value = false
        fetchData()
      } catch (error) {
        console.error('提交失败', error)
      }
      submitLoading.value = false
    }
  })
}

onMounted(() => {
  fetchViolationTypes()
  fetchLineOptions()
  fetchDriverOptions()
  fetchVehicleOptions()
  fetchStationOptions()
  fetchData()
})
</script>

<style lang="scss" scoped>
.violation-container {
  .search-bar {
    .el-input, .el-select, .el-date-picker {
      margin-right: 12px;
    }
  }
}
</style>
