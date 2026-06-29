<template>
  <div class="app-wrapper">
    <!-- 侧边栏 -->
    <div class="sidebar-container" :class="{ 'is-collapse': isCollapse }">
      <div class="logo">
        <el-icon class="logo-icon"><Bus /></el-icon>
        <span v-show="!isCollapse" class="logo-text">公交安全管理</span>
      </div>
      <el-scrollbar class="scrollbar-wrapper">
        <el-menu
          :default-active="activeMenu"
          :collapse="isCollapse"
          :collapse-transition="false"
          background-color="transparent"
          text-color="#b8c5d6"
          active-text-color="#ffffff"
          @select="handleMenuSelect"
        >
          <sidebar-item
            v-for="route in sidebarRoutes"
            :key="route.path"
            :item="route"
            :base-path="'/'"
          />
        </el-menu>
      </el-scrollbar>
    </div>

    <!-- 主内容区 -->
    <div class="main-container" :class="{ 'is-collapse': isCollapse }">
      <!-- 顶部导航 -->
      <div class="navbar">
        <div class="left">
          <el-icon class="hamburger" @click="toggleSidebar">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <breadcrumb />
        </div>
        <div class="right">
          <el-dropdown trigger="click">
            <div class="avatar-wrapper">
              <el-avatar :size="32" :icon="UserFilled" />
              <span class="username">管理员</span>
              <el-icon><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>

      <!-- 内容区 -->
      <div class="app-main">
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { UserFilled } from '@element-plus/icons-vue'
import SidebarItem from './components/SidebarItem.vue'
import Breadcrumb from './components/Breadcrumb.vue'
import { routes } from '@/router'

const route = useRoute()
const router = useRouter()
const isCollapse = ref(false)

// 过滤掉隐藏的路由和没有meta的路由
// 取根路由 / 的 children 作为侧边栏菜单
const sidebarRoutes = computed(() => {
  const rootRoute = routes.find(r => r.path === '/')
  if (!rootRoute || !rootRoute.children) return []
  return rootRoute.children.filter(r => !r.hidden)
})

const activeMenu = computed(() => {
  const { meta, path } = route
  if (meta.activeMenu) {
    return meta.activeMenu
  }
  return path
})

const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value
}

const handleMenuSelect = (index) => {
  router.push(index)
}

const logout = () => {
  localStorage.removeItem('isLoggedIn')
  router.push('/login')
}
</script>

<style lang="scss" scoped>
.app-wrapper {
  display: flex;
  height: 100vh;
  width: 100%;
}

.sidebar-container {
  width: var(--sidebar-width);
  height: 100%;
  background: linear-gradient(180deg, #1a2a3a 0%, #0d1b2a 100%);
  transition: width 0.3s;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 1001;
  display: flex;
  flex-direction: column;
  
  &.is-collapse {
    width: 64px;
  }
  
  .logo {
    height: var(--header-height);
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding: 0 16px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    
    .logo-icon {
      font-size: 32px;
      color: var(--primary-light);
      margin-left: 4px;
    }
    
    .logo-text {
      margin-left: 12px;
      font-size: 18px;
      font-weight: 600;
      color: #fff;
      white-space: nowrap;
    }
  }
  
  .scrollbar-wrapper {
    flex: 1;
    overflow: hidden;
    
    :deep(.el-menu) {
      border-right: none;
      
      .el-menu-item, .el-sub-menu__title {
        height: 50px;
        line-height: 50px;
        
        &:hover {
          background-color: rgba(255, 255, 255, 0.05) !important;
        }
        
        &.is-active {
          background: linear-gradient(90deg, var(--primary-color), transparent) !important;
          border-left: 3px solid var(--primary-light);
        }
      }
      
      .el-sub-menu {
        .el-menu {
          background-color: rgba(0, 0, 0, 0.2) !important;
        }
      }
    }
  }
}

.main-container {
  flex: 1;
  margin-left: var(--sidebar-width);
  transition: margin-left 0.3s;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  
  &.is-collapse {
    margin-left: 64px;
  }
}

.navbar {
  height: var(--header-height);
  background: #fff;
  box-shadow: var(--shadow-sm);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  position: sticky;
  top: 0;
  z-index: 1000;
  
  .left {
    display: flex;
    align-items: center;
    gap: 16px;
    
    .hamburger {
      font-size: 20px;
      cursor: pointer;
      color: var(--text-secondary);
      
      &:hover {
        color: var(--primary-color);
      }
    }
  }
  
  .right {
    .avatar-wrapper {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;
      padding: 4px 8px;
      border-radius: var(--radius-sm);
      transition: background-color 0.2s;
      
      &:hover {
        background-color: var(--bg-primary);
      }
      
      .username {
        font-size: 14px;
        color: var(--text-primary);
      }
    }
  }
}

.app-main {
  flex: 1;
  padding: 24px;
  background-color: var(--bg-primary);
  min-height: calc(100vh - var(--header-height));
}

.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.3s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-20px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(20px);
}
</style>
