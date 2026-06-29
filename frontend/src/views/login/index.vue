<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-left">
        <div class="brand">
          <el-icon class="brand-icon"><Bus /></el-icon>
          <h1 class="brand-title">公交安全管理系统</h1>
          <p class="brand-subtitle">Bus Safety Management System</p>
        </div>
        <div class="features">
          <div class="feature-item">
            <el-icon><Check /></el-icon>
            <span>车队管理智能化</span>
          </div>
          <div class="feature-item">
            <el-icon><Check /></el-icon>
            <span>违章记录实时追踪</span>
          </div>
          <div class="feature-item">
            <el-icon><Check /></el-icon>
            <span>数据统计分析</span>
          </div>
        </div>
      </div>
      <div class="login-right">
        <div class="login-form-wrapper">
          <h2 class="form-title">欢迎登录</h2>
          <p class="form-subtitle">请使用您的账号密码登录系统</p>
          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            class="login-form"
            @keyup.enter="handleLogin"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                :prefix-icon="User"
                size="large"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                :prefix-icon="Lock"
                size="large"
                show-password
              />
            </el-form-item>
            <el-form-item>
              <el-button
                :loading="loading"
                type="primary"
                size="large"
                class="login-button"
                @click="handleLogin"
              >
                登 录
              </el-button>
            </el-form-item>
          </el-form>
          <div class="login-tips">
            <p>演示账号：admin / 123456</p>
          </div>
        </div>
      </div>
    </div>
    <div class="login-footer">
      <p>© 2025 公交安全管理系统 | 数据库系统实验项目</p>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, Bus, Check } from '@element-plus/icons-vue'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: 'admin',
  password: '123456'
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const handleLogin = () => {
  loginFormRef.value.validate(valid => {
    if (valid) {
      loading.value = true
      if (loginForm.username === 'admin' && loginForm.password === '123456') {
        localStorage.setItem('isLoggedIn', 'true')
        setTimeout(() => {
          loading.value = false
          ElMessage.success('登录成功')
          router.push('/')
        }, 500)
      } else {
        loading.value = false
        ElMessage.error('用户名或密码错误')
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #1a5fb4 0%, #0d3a8a 50%, #1a2a3a 100%);
  position: relative;
  overflow: hidden;
  
  &::before {
    content: '';
    position: absolute;
    top: -50%;
    right: -20%;
    width: 80%;
    height: 200%;
    background: radial-gradient(ellipse, rgba(255,255,255,0.1) 0%, transparent 70%);
    animation: pulse 8s ease-in-out infinite;
  }
  
  @keyframes pulse {
    0%, 100% { transform: scale(1); opacity: 0.5; }
    50% { transform: scale(1.1); opacity: 0.8; }
  }
}

.login-box {
  flex: 1;
  display: flex;
  max-width: 1200px;
  width: 90%;
  margin: auto;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(10px);
  z-index: 1;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #1a5fb4 0%, #0d3a8a 100%);
  padding: 60px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  color: white;
  position: relative;
  overflow: hidden;
  
  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="50" cy="50" r="40" fill="none" stroke="rgba(255,255,255,0.1)" stroke-width="0.5"/></svg>') repeat;
    background-size: 100px;
    opacity: 0.5;
  }
  
  .brand {
    position: relative;
    z-index: 1;
    
    .brand-icon {
      font-size: 64px;
      margin-bottom: 24px;
    }
    
    .brand-title {
      font-size: 36px;
      font-weight: 700;
      margin-bottom: 8px;
    }
    
    .brand-subtitle {
      font-size: 16px;
      opacity: 0.8;
      font-weight: 300;
    }
  }
  
  .features {
    margin-top: 48px;
    position: relative;
    z-index: 1;
    
    .feature-item {
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 16px;
      font-size: 16px;
      
      .el-icon {
        font-size: 20px;
        color: #2ec27e;
      }
    }
  }
}

.login-right {
  flex: 1;
  padding: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-form-wrapper {
  width: 100%;
  max-width: 360px;
  
  .form-title {
    font-size: 28px;
    font-weight: 600;
    color: var(--text-primary);
    margin-bottom: 8px;
  }
  
  .form-subtitle {
    font-size: 14px;
    color: var(--text-secondary);
    margin-bottom: 32px;
  }
  
  .login-form {
    .el-input {
      --el-input-height: 48px;
    }
    
    .login-button {
      width: 100%;
      height: 48px;
      font-size: 16px;
      font-weight: 500;
      margin-top: 8px;
    }
  }
  
  .login-tips {
    margin-top: 24px;
    text-align: center;
    color: var(--text-muted);
    font-size: 13px;
  }
}

.login-footer {
  text-align: center;
  padding: 24px;
  color: rgba(255, 255, 255, 0.6);
  font-size: 13px;
  z-index: 1;
}

@media (max-width: 900px) {
  .login-left {
    display: none;
  }
  
  .login-right {
    padding: 40px 24px;
  }
}
</style>
