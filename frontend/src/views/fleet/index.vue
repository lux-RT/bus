<template>
  <div class="fleet-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="title">车队管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        添加车队
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchForm.keyword"
        placeholder="请输入车队名称"
        clearable
        style="width: 280px"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-date-picker
        v-model="searchForm.dateRange"
        type="daterange"
        range-separator="至"
        start-placeholder="成立开始日期"
        end-placeholder="成立结束日期"
        style="width: 280px"
        value-format="YYYY-MM-DD"
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
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="fleetId" label="车队ID" width="100" sortable />
        <el-table-column prop="fleetName" label="车队名称" min-width="150" show-overflow-tooltip />
        <el-table-column prop="captainName" label="队长" width="100">
          <template #default="{ row }">
            {{ row.captainName || '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="成立时间" width="120" sortable>
          <template #default="{ row }">
            {{ formatDate(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
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
        <el-form-item v-if="isEdit" label="车队ID" prop="fleetId">
          <el-input v-model="form.fleetId" disabled />
        </el-form-item>
        <el-form-item label="车队名称" prop="fleetName">
          <el-input v-model="form.fleetName" placeholder="请输入车队名称" />
        </el-form-item>
        <el-form-item label="队长" prop="captainId">
          <el-select
            v-model="form.captainId"
            placeholder="请选择队长"
            clearable
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
        <el-form-item label="成立时间" prop="createTime">
          <el-date-picker
            v-model="form.createTime"
            type="date"
            placeholder="请选择成立时间"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
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
import { getFleetList, addFleet, updateFleet, deleteFleet } from '@/api/fleet'
import { getAllDrivers } from '@/api/driver'

// 搜索表单
const searchForm = reactive({
  keyword: '',
  dateRange: []
})

// 表格数据
const loading = ref(false)
const tableData = ref([])
const selectedRows = ref([])

// 分页
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const formRef = ref(null)
const submitLoading = ref(false)

// 表单
const form = reactive({
  fleetId: '',
  fleetName: '',
  captainId: null,
  createTime: ''
})

// 表单规则
const rules = {
  fleetName: [
    { required: true, message: '请输入车队名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  createTime: [
    { required: true, message: '请选择成立时间', trigger: 'change' }
  ]
}

// 队长选项
const captainOptions = ref([])

const fetchCaptainOptions = async () => {
  try {
    const res = await getAllDrivers()
    const drivers = res.data || []
    captainOptions.value = drivers.filter(d => d.isCaptain)
  } catch (e) {
    console.error('获取队长列表失败', e)
  }
}

// 格式化日期
const formatDate = (date) => {
  if (!date) return '-'
  return new Date(date).toLocaleDateString('zh-CN')
}

// 获取列表数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      fleetName: searchForm.keyword,
      startTime: searchForm.dateRange && searchForm.dateRange[0] ? searchForm.dateRange[0] : null,
      endTime: searchForm.dateRange && searchForm.dateRange[1] ? searchForm.dateRange[1] : null
    }
    const res = await getFleetList(params)
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
  searchForm.dateRange = []
  handleSearch()
}

// 选择变化
const handleSelectionChange = (val) => {
  selectedRows.value = val
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
  dialogTitle.value = '添加车队'
  Object.assign(form, {
    fleetId: null,
    fleetName: '',
    captainId: null,
    createTime: ''
  })
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑车队'
  Object.assign(form, {
    fleetId: row.fleetId,
    fleetName: row.fleetName,
    captainId: row.captainId,
    createTime: row.createTime
  })
  dialogVisible.value = true
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确定要删除车队 "${row.fleetName}" 吗？`, '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteFleet(row.fleetId)
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
        if (submitData.captainId === '' || submitData.captainId === undefined) submitData.captainId = null
        if (submitData.captainId === '' || submitData.captainId === undefined) submitData.captainId = null
        if (isEdit.value) {
          await updateFleet(submitData.fleetId, submitData)
        } else {
          await addFleet(submitData)
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
  fetchCaptainOptions()
})
</script>

<style lang="scss" scoped>
.fleet-container {
  .search-bar {
    .el-input, .el-date-picker {
      margin-right: 12px;
    }
  }
}
</style>
