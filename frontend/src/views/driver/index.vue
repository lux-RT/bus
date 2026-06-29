<template>
  <div class="driver-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="title">司机管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        添加司机
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchForm.keyword"
        placeholder="请输入姓名或工号"
        clearable
        style="width: 200px"
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
        v-model="searchForm.fleetId"
        placeholder="选择车队"
        clearable
        style="width: 150px"
      >
        <el-option
          v-for="item in fleetOptions"
          :key="item.fleetId"
          :label="item.fleetName"
          :value="item.fleetId"
        />
      </el-select>
      <el-select
        v-model="searchForm.gender"
        placeholder="性别"
        clearable
        style="width: 100px"
      >
        <el-option label="男" value="男" />
        <el-option label="女" value="女" />
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
        <el-table-column prop="driverId" label="工号" width="80" sortable />
        <el-table-column prop="name" label="姓名" min-width="80" />
        <el-table-column prop="gender" label="性别" width="60">
          <template #default="{ row }">
            <el-tag :type="row.gender === '男' ? 'primary' : 'danger'" size="small">
              {{ row.gender }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="60" sortable />
        <el-table-column prop="phone" label="联系电话" width="125" />
        <el-table-column prop="lineName" label="所属线路" min-width="100">
          <template #default="{ row }">
            {{ row.lineName || '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="fleetName" label="所属车队" min-width="100" show-overflow-tooltip />
        <el-table-column label="身份" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.isCaptain" type="danger" size="small" effect="dark">队长</el-tag>
            <el-tag v-else-if="row.isRoadCaptain" type="warning" size="small" effect="dark">路队长</el-tag>
            <el-tag v-else type="success" size="small">司机</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="hireDate" label="入职日期" width="115" sortable />
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
      width="550px"
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
            <el-form-item v-if="isEdit" label="工号" prop="driverId">
              <el-input v-model="form.driverId" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="form.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number
                v-model="form.age"
                :min="18"
                :max="60"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="所属线路" prop="lineId">
          <el-select
            v-model="form.lineId"
            placeholder="请选择所属线路"
            clearable
            style="width: 100%"
            :disabled="form.isCaptain"
          >
            <el-option
              v-for="item in lineOptions"
              :key="item.lineId"
              :label="item.lineName"
              :value="item.lineId"
            />
          </el-select>
          <div v-if="form.isCaptain" class="form-tip">队长不分配线路</div>
        </el-form-item>
        <el-form-item label="身份" prop="role">
          <el-radio-group v-model="roleType" @change="handleRoleChange">
            <el-radio label="normal">普通司机</el-radio>
            <el-radio label="road_captain">路队长</el-radio>
            <el-radio label="captain">队长</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="入职日期" prop="hireDate">
          <el-date-picker
            v-model="form.hireDate"
            type="date"
            placeholder="请选择入职日期"
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
import { getDriverList, addDriver, updateDriver, deleteDriver } from '@/api/driver'
import { getAllLines } from '@/api/line'
import { getAllFleets } from '@/api/fleet'

// 搜索表单
const searchForm = reactive({
  keyword: '',
  lineId: '',
  fleetId: '',
  gender: ''
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
const lineOptions = ref([])
const fleetOptions = ref([])

// 获取线路列表
const fetchLineOptions = async () => {
  try {
    const res = await getAllLines()
    lineOptions.value = res.data || []
  } catch (e) {
    console.error('获取线路列表失败', e)
  }
}

// 获取车队列表
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
  driverId: '',
  name: '',
  gender: '男',
  age: 35,
  phone: '',
  lineId: '',
  isRoadCaptain: false,
  isCaptain: false,
  hireDate: ''
})

// 角色类型
const roleType = ref('normal')

// 监听角色变化
const handleRoleChange = (val) => {
  form.isCaptain = val === 'captain'
  form.isRoadCaptain = val === 'road_captain'
  if (val === 'captain') {
    form.lineId = ''
  }
}

// 表单规则
const rules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ],
  hireDate: [
    { required: true, message: '请选择入职日期', trigger: 'change' }
  ]
}

// 获取列表数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      keyword: searchForm.keyword || undefined,
      lineId: searchForm.lineId || undefined,
      fleetId: searchForm.fleetId || undefined,
      gender: searchForm.gender || undefined
    }
    const res = await getDriverList(params)
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
  searchForm.lineId = ''
  searchForm.fleetId = ''
  searchForm.gender = ''
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
  dialogTitle.value = '添加司机'
  roleType.value = 'normal'
  Object.assign(form, {
    driverId: null,
    name: '',
    gender: '男',
    age: 35,
    phone: '',
    lineId: '',
    isRoadCaptain: false,
    isCaptain: false,
    hireDate: ''
  })
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑司机'

  if (row.isCaptain) {
    roleType.value = 'captain'
  } else if (row.isRoadCaptain) {
    roleType.value = 'road_captain'
  } else {
    roleType.value = 'normal'
  }

  Object.assign(form, {
    driverId: row.driverId,
    name: row.name,
    gender: row.gender,
    age: row.age,
    phone: row.phone,
    lineId: row.lineId,
    isRoadCaptain: row.isRoadCaptain,
    isCaptain: row.isCaptain,
    hireDate: row.hireDate
  })
  dialogVisible.value = true
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确定要删除司机 "${row.name}" 吗？`, '提示', { type: 'warning' })
    await deleteDriver(row.driverId)
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
        if (submitData.lineId === '' || submitData.lineId === undefined) submitData.lineId = null
        if (submitData.driverId === '') submitData.driverId = null
        if (isEdit.value) {
          await updateDriver(submitData.driverId, submitData)
        } else {
          if (submitData.lineId === '') submitData.lineId = null
          await addDriver(submitData)
        }
        ElMessage.success(isEdit.value ? '编辑成功' : '添加成功')
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
  fetchData()
  fetchLineOptions()
  fetchFleetOptions()
})
</script>

<style lang="scss" scoped>
.driver-container {
  .search-bar {
    .el-input, .el-select {
      margin-right: 12px;
    }
  }

  .form-tip {
    font-size: 12px;
    color: var(--el-color-warning);
    margin-top: 4px;
  }
}
</style>
