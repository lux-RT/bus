<template>
  <div class="line-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="title">线路管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        添加线路
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchForm.keyword"
        placeholder="请输入线路名称"
        clearable
        style="width: 200px"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-select
        v-model="searchForm.fleetId"
        placeholder="选择车队"
        clearable
        style="width: 160px"
      >
        <el-option
          v-for="item in fleetOptions"
          :key="item.fleetId"
          :label="item.fleetName"
          :value="item.fleetId"
        />
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
        <el-table-column prop="lineId" label="ID" width="70" sortable />
        <el-table-column prop="lineName" label="线路名称" min-width="100" show-overflow-tooltip />
        <el-table-column prop="fleetName" label="所属车队" width="100" />
        <el-table-column prop="totalStations" label="站点数" width="70" sortable />
        <el-table-column prop="roadCaptainName" label="路队长" width="80">
          <template #default="{ row }">
            {{ row.roadCaptainName || '-' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleViewStations(row)">
              <el-icon><View /></el-icon>
              站点
            </el-button>
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
        <el-form-item v-if="isEdit" label="线路ID" prop="lineId">
          <el-input v-model="form.lineId" disabled />
        </el-form-item>
        <el-form-item label="线路名称" prop="lineName">
          <el-input v-model="form.lineName" placeholder="请输入线路名称" />
        </el-form-item>
        <el-form-item label="所属车队" prop="fleetId">
          <el-select
            v-model="form.fleetId"
            placeholder="请选择所属车队"
            style="width: 100%"
          >
            <el-option
              v-for="item in fleetOptions"
              :key="item.fleetId"
              :label="item.fleetName"
              :value="item.fleetId"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-if="isEdit" label="路队长" prop="roadCaptainId">
          <el-select
            v-model="form.roadCaptainId"
            placeholder="请选择路队长"
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="item in roadCaptainOptions"
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

    <!-- 站点管理对话框 -->
    <el-dialog
      v-model="stationDialogVisible"
      title="线路站点管理"
      width="700px"
      destroy-on-close
    >
      <div class="station-header">
        <span class="line-info">{{ currentLine?.lineName }}</span>
      </div>
      <div style="display: flex; gap: 12px; margin: 16px 0;">
        <el-select
          v-model="selectedStationId"
          placeholder="选择要添加的站点"
          filterable
          style="flex: 1"
        >
          <el-option
            v-for="s in availableStations"
            :key="s.stationId"
            :label="`${s.stationName}（${s.address || ''}）`"
            :value="s.stationId"
          />
        </el-select>
        <el-input-number
          v-model="stationSequence"
          :min="1"
          placeholder="顺序"
          style="width: 100px"
        />
        <el-button type="primary" @click="handleAddStation">
          <el-icon><Plus /></el-icon>
          添加
        </el-button>
      </div>
      <el-table :data="stationList" stripe style="width: 100%;" v-loading="stationLoading">
        <el-table-column prop="sequence" label="顺序" width="80" />
        <el-table-column prop="stationId" label="站点ID" width="100" />
        <el-table-column prop="stationName" label="站点名称" min-width="150" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="danger" @click="handleRemoveStation(row)">
              <el-icon><Delete /></el-icon>
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, Refresh, Edit, Delete, View } from '@element-plus/icons-vue'
import { getLineList, addLine, updateLine, deleteLine, getLineStations, addLineStation, removeLineStation, setRoadCaptain } from '@/api/line'
import { getAllFleets } from '@/api/fleet'
import { getAllStations } from '@/api/station'
import { getAllDrivers } from '@/api/driver'

// 搜索表单
const searchForm = reactive({
  keyword: '',
  fleetId: ''
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

// 车队选项
const fleetOptions = ref([])

const fetchFleetOptions = async () => {
  try {
    const res = await getAllFleets()
    fleetOptions.value = res.data || []
  } catch (e) {
    console.error('获取车队列表失败', e)
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
  lineId: null,
  lineName: '',
  fleetId: '',
  roadCaptainId: null
})

// 表单规则
const rules = {
  lineName: [
    { required: true, message: '请输入线路名称', trigger: 'blur' }
  ],
  fleetId: [
    { required: true, message: '请选择所属车队', trigger: 'change' }
  ]
}

// 路队长选项
const roadCaptainOptions = ref([])

const fetchRoadCaptainOptions = async () => {
  try {
    const res = await getAllDrivers()
    // 排除队长（队长不能同时当路队长），所有普通司机和现任路队长都可选
    roadCaptainOptions.value = (res.data || []).filter(d => !d.isCaptain)
  } catch (e) { console.error('获取路队长选项失败', e) }
}

// 站点管理
const stationDialogVisible = ref(false)
const stationLoading = ref(false)
const currentLine = ref(null)
const stationList = ref([])
const selectedStationId = ref(null)
const stationSequence = ref(1)
const availableStations = ref([])
const allStationsCache = ref([])

// 加载所有站点（用于下拉选择）
const fetchAllStations = async () => {
  try {
    const res = await getAllStations()
    allStationsCache.value = res.data || []
  } catch (e) { console.error('获取站点列表失败', e) }
}

// 获取列表数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      lineName: searchForm.keyword || undefined,
      fleetId: searchForm.fleetId || undefined
    }
    const res = await getLineList(params)
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
  searchForm.fleetId = ''
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
  dialogTitle.value = '添加线路'
  Object.assign(form, {
    lineId: null,
    lineName: '',
    fleetId: '',
    roadCaptainId: null
  })
  dialogVisible.value = true
}

// 编辑
const handleEdit = async (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑线路'
  Object.assign(form, {
    lineId: row.lineId,
    lineName: row.lineName,
    fleetId: row.fleetId,
    roadCaptainId: null
  })
  await fetchRoadCaptainOptions()
  dialogVisible.value = true
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确定要删除线路 "${row.lineName}" 吗？`, '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteLine(row.lineId)
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
        if (submitData.fleetId === '' || submitData.fleetId === undefined) submitData.fleetId = null
        if (isEdit.value) {
          await updateLine(submitData.lineId, submitData)
          if (submitData.roadCaptainId) {
            await setRoadCaptain(submitData.lineId, submitData.roadCaptainId)
          }
        } else {
          await addLine(submitData)
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

// 查看站点
const handleViewStations = async (row) => {
  currentLine.value = row
  selectedStationId.value = null
  stationSequence.value = (stationList.value.length || 0) + 1
  stationLoading.value = true
  try {
    const res = await getLineStations(row.lineId)
    const lineStations = res.data || []
    // 合并站点名称信息
    if (!allStationsCache.value.length) await fetchAllStations()
    const stationMap = {}
    allStationsCache.value.forEach(s => { stationMap[s.stationId] = s.stationName })
    stationList.value = lineStations.map(ls => ({
      ...ls,
      stationName: stationMap[ls.stationId] || `站点${ls.stationId}`
    }))
    // 过滤出未在该线路中的站点
    const existingIds = new Set(lineStations.map(ls => ls.stationId))
    availableStations.value = allStationsCache.value.filter(s => !existingIds.has(s.stationId))
  } catch (e) {
    console.error('获取线路站点失败', e)
  } finally {
    stationLoading.value = false
  }
  stationDialogVisible.value = true
}

// 添加站点
const handleAddStation = async () => {
  if (!selectedStationId.value || !currentLine.value) return
  try {
    await addLineStation(currentLine.value.lineId, {
      stationId: selectedStationId.value,
      sequence: stationSequence.value
    })
    ElMessage.success('站点添加成功')
    // 刷新站点列表和线路表格
    await handleViewStations(currentLine.value)
    fetchData()
    // 重置选择
    selectedStationId.value = null
    stationSequence.value += 1
  } catch (e) {
    console.error('添加站点失败', e)
  }
}

// 移除站点
const handleRemoveStation = async (row) => {
  if (!currentLine.value) return
  try {
    await removeLineStation(currentLine.value.lineId, row.stationId)
    ElMessage.success('站点移除成功')
    handleViewStations(currentLine.value)
    fetchData()
  } catch (e) {
    console.error('移除站点失败', e)
  }
}

onMounted(() => {
  fetchData()
  fetchFleetOptions()
  fetchAllStations()
})
</script>

<style lang="scss" scoped>
.line-container {
  .search-bar {
    .el-input, .el-select {
      margin-right: 12px;
    }
  }

  .station-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .line-info {
      font-size: 16px;
      font-weight: 600;
      color: var(--text-primary);
    }
  }
}
</style>
