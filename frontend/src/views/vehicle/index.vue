<template>
  <div class="vehicle-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="title">车辆管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        添加车辆
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchForm.keyword"
        placeholder="请输入车牌号或车型"
        clearable
        style="width: 220px"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-select
        v-model="searchForm.lineId"
        placeholder="选择线路"
        clearable
        style="width: 150px"
      >
        <el-option
          v-for="item in lineOptions"
          :key="item.lineId"
          :label="item.lineName"
          :value="item.lineId"
        />
      </el-select>
      <el-select
        v-model="searchForm.status"
        placeholder="车辆状态"
        clearable
        style="width: 130px"
      >
        <el-option label="正常" value="正常" />
        <el-option label="维修" value="维修" />
        <el-option label="报废" value="报废" />
      </el-select>
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
        <el-table-column prop="vehicleId" label="编号" width="75" sortable />
        <el-table-column prop="licensePlate" label="车牌号" width="110">
          <template #default="{ row }">
            <el-tag effect="plain" type="info" size="small">{{ row.licensePlate }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lineName" label="所属线路" width="100">
          <template #default="{ row }">
            {{ row.lineName || '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="model" label="车型" min-width="130" show-overflow-tooltip />
        <el-table-column prop="purchaseTime" label="购买时间" width="110" sortable />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)" size="small">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
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
      width="500px"
      destroy-on-close
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item v-if="isEdit" label="车辆编号" prop="vehicleId">
          <el-input v-model="form.vehicleId" disabled />
        </el-form-item>
        <el-form-item label="车牌号" prop="licensePlate">
          <el-input v-model="form.licensePlate" placeholder="请输入车牌号" />
        </el-form-item>
        <el-form-item label="所属线路" prop="lineId">
          <el-select
            v-model="form.lineId"
            placeholder="请选择所属线路"
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
        <el-form-item label="车型" prop="model">
          <el-input v-model="form.model" placeholder="请输入车型" />
        </el-form-item>
        <el-form-item label="购买时间" prop="purchaseTime">
          <el-date-picker
            v-model="form.purchaseTime"
            type="date"
            placeholder="请选择购买时间"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="车辆状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="正常">正常</el-radio>
            <el-radio label="维修">维修</el-radio>
            <el-radio label="报废">报废</el-radio>
          </el-radio-group>
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
import { getVehicleList, addVehicle, updateVehicle, deleteVehicle } from '@/api/vehicle'
import { getAllLines } from '@/api/line'

// 搜索表单
const searchForm = reactive({
  keyword: '',
  lineId: '',
  status: ''
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

// 线路选项
const lineOptions = ref([])

const fetchLineOptions = async () => {
  try {
    const res = await getAllLines()
    lineOptions.value = res.data || []
  } catch (e) {
    console.error('获取线路列表失败', e)
  }
}

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const formRef = ref(null)
const submitLoading = ref(false)

// 表单
const form = reactive({
  vehicleId: '',
  licensePlate: '',
  lineId: '',
  model: '',
  purchaseTime: '',
  status: '正常'
})

// 表单规则
const rules = {
  licensePlate: [
    { required: true, message: '请输入车牌号', trigger: 'blur' },
    { pattern: /^[一-龥][A-Z][·]?[A-Z0-9]{4,6}$/, message: '请输入正确的车牌号格式', trigger: 'blur' }
  ],
  lineId: [
    { required: true, message: '请选择所属线路', trigger: 'change' }
  ],
  model: [
    { required: true, message: '请输入车型', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择车辆状态', trigger: 'change' }
  ]
}

// 获取状态标签类型
const getStatusType = (status) => {
  const typeMap = {
    '正常': 'success',
    '维修': 'warning',
    '报废': 'danger'
  }
  return typeMap[status] || 'info'
}

// 获取列表数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      keyword: searchForm.keyword,
      lineId: searchForm.lineId || null,
      status: searchForm.status || null
    }
    const res = await getVehicleList(params)
    const { total, records } = res.data || { total: 0, records: [] }
    tableData.value = records
    pagination.total = total
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
  searchForm.lineId = ''
  searchForm.status = ''
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
  dialogTitle.value = '添加车辆'
  Object.assign(form, {
    vehicleId: null,
    licensePlate: '',
    lineId: '',
    model: '',
    purchaseTime: '',
    status: '正常'
  })
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑车辆'
  Object.assign(form, {
    vehicleId: row.vehicleId,
    licensePlate: row.licensePlate,
    lineId: row.lineId,
    model: row.model,
    purchaseTime: row.purchaseTime,
    status: row.status
  })
  dialogVisible.value = true
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确定要删除车辆 "${row.licensePlate}" 吗？`, '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteVehicle(row.vehicleId)
    ElMessage.success('删除成功')
    fetchData()
  } catch (e) {
    if (e !== 'cancel') console.error(e)
  }
}

// 提交
const handleSubmit = () => {
  formRef.value.validate(async valid => {
    if (valid) {
      submitLoading.value = true
      try {
        const submitData = { ...form }
        if (submitData.lineId === '' || submitData.lineId === undefined) submitData.lineId = null
        if (isEdit.value) {
          await updateVehicle(submitData.vehicleId, submitData)
        } else {
          await addVehicle(submitData)
        }
        ElMessage.success(isEdit.value ? '编辑成功' : '添加成功')
        dialogVisible.value = false
        fetchData()
      } catch (e) {
        console.error(e)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

onMounted(() => {
  fetchData()
  fetchLineOptions()
})
</script>

<style lang="scss" scoped>
.vehicle-container {
  .search-bar {
    .el-input, .el-select {
      margin-right: 12px;
    }
  }
}
</style>
