<template>
  <div class="violation-query-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="title">违章查询</h2>
    </div>

    <!-- 高级搜索区 -->
    <div class="card search-card">
      <h3 class="section-title">
        <el-icon><Filter /></el-icon>
        高级查询条件
      </h3>
      <el-form :model="searchForm" label-width="90px" class="advanced-search-form">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="司机姓名">
              <el-input
                v-model="searchForm.driverName"
                placeholder="支持模糊查询"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="车牌号">
              <el-input
                v-model="searchForm.licensePlate"
                placeholder="支持模糊查询"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="所属车队">
              <el-select
                v-model="searchForm.fleetId"
                placeholder="请选择车队"
                clearable
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
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="所属线路">
              <el-select
                v-model="searchForm.lineId"
                placeholder="请选择线路"
                clearable
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
        </el-row>
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="违章类型">
              <el-select
                v-model="searchForm.violationTypeId"
                placeholder="请选择类型"
                clearable
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
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="违章站点">
              <el-select
                v-model="searchForm.stationId"
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
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="时间范围">
              <el-date-picker
                v-model="searchForm.dateRange"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                style="width: 100%"
                value-format="YYYY-MM-DD HH:mm:ss"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="search-buttons">
            <el-button type="primary" size="large" @click="handleSearch">
              <el-icon><Search /></el-icon>
              查询
            </el-button>
            <el-button size="large" @click="handleReset">
              <el-icon><Refresh /></el-icon>
              重置
            </el-button>
            <el-button type="success" size="large" @click="handleExport">
              <el-icon><Download /></el-icon>
              导出结果
            </el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 查询结果 -->
    <div class="card">
      <div class="table-header">
        <h3 class="section-title">
          <el-icon><List /></el-icon>
          查询结果
          <el-tag type="info" size="small" class="count-tag">共 {{ pagination.total }} 条</el-tag>
        </h3>
      </div>

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
        <el-table-column prop="description" label="详细描述" min-width="150" show-overflow-tooltip />
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh, Download, Filter, List } from '@element-plus/icons-vue'
import { searchViolations, getViolationTypes } from '@/api/violation'
import { getAllFleets } from '@/api/fleet'
import { getAllLines } from '@/api/line'
import { getAllStations } from '@/api/station'

// 搜索表单
const searchForm = reactive({
  driverName: '',
  licensePlate: '',
  fleetId: '',
  lineId: '',
  violationTypeId: '',
  stationId: '',
  dateRange: []
})

// 选项数据
const fleetOptions = ref([])
const lineOptions = ref([])
const violationTypes = ref([])
const stationOptions = ref([])

const fetchFleetOptions = async () => {
  try {
    const res = await getAllFleets()
    fleetOptions.value = res.data || []
  } catch (e) { console.error('获取车队列表失败', e) }
}

const fetchLineOptions = async () => {
  try {
    const res = await getAllLines()
    lineOptions.value = res.data || []
  } catch (e) { console.error('获取线路列表失败', e) }
}

const fetchViolationTypes = async () => {
  try {
    const res = await getViolationTypes()
    violationTypes.value = res.data || []
  } catch (e) { console.error('获取违章类型失败', e) }
}

const fetchStationOptions = async () => {
  try {
    const res = await getAllStations()
    stationOptions.value = res.data || []
  } catch (e) { console.error('获取站点列表失败', e) }
}

// 表格数据
const loading = ref(false)
const tableData = ref([])

// 分页
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

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

// 获取列表数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.driverName) params.keyword = searchForm.driverName
    else if (searchForm.licensePlate) params.keyword = searchForm.licensePlate
    if (searchForm.fleetId) params.fleetId = searchForm.fleetId
    if (searchForm.lineId) params.lineId = searchForm.lineId
    if (searchForm.violationTypeId) params.violationTypeId = searchForm.violationTypeId
    if (searchForm.stationId) params.stationId = searchForm.stationId
    if (searchForm.dateRange && searchForm.dateRange.length === 2) {
      params.startTime = searchForm.dateRange[0]
      params.endTime = searchForm.dateRange[1]
    }
    Object.assign(params, { page: pagination.page, pageSize: pagination.pageSize })
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
  searchForm.driverName = ''
  searchForm.licensePlate = ''
  searchForm.fleetId = ''
  searchForm.lineId = ''
  searchForm.violationTypeId = ''
  searchForm.stationId = ''
  searchForm.dateRange = []
  handleSearch()
}

// 导出
const handleExport = () => {
  ElMessage.success('正在导出数据...')
  // 实际项目中这里会调用导出API
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

onMounted(() => {
  fetchFleetOptions()
  fetchLineOptions()
  fetchViolationTypes()
  fetchStationOptions()
  fetchData()
})
</script>

<style lang="scss" scoped>
.violation-query-container {
  .search-card {
    margin-bottom: 20px;

    .section-title {
      font-size: 16px;
      font-weight: 600;
      color: var(--text-primary);
      display: flex;
      align-items: center;
      gap: 8px;
      margin: 0 0 20px 0;
      padding-bottom: 16px;
      border-bottom: 1px solid var(--border-color);
    }

    .advanced-search-form {
      .search-buttons {
        display: flex;
        justify-content: center;
        gap: 12px;
        margin-top: 20px;
        padding-top: 20px;
        border-top: 1px dashed var(--border-color);
      }
    }
  }

  .table-header {
    margin-bottom: 16px;

    .section-title {
      font-size: 16px;
      font-weight: 600;
      color: var(--text-primary);
      display: flex;
      align-items: center;
      gap: 8px;
      margin: 0;

      .count-tag {
        margin-left: 8px;
      }
    }
  }
}
</style>
