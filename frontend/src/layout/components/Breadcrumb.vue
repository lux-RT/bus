<template>
  <el-breadcrumb separator="/">
    <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="item.path">
      <span
        v-if="index === breadcrumbs.length - 1"
        class="no-redirect"
      >{{ item.meta.title }}</span>
      <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const breadcrumbs = ref([])

const getBreadcrumb = () => {
  breadcrumbs.value = route.matched.filter(item => {
    return item.meta && item.meta.title && item.meta.breadcrumb !== false
  })
}

const handleLink = (item) => {
  const { redirect, path } = item
  if (redirect) {
    router.push(redirect)
    return
  }
  router.push(path)
}

watch(() => route.path, getBreadcrumb, { immediate: true })
</script>

<style lang="scss" scoped>
.el-breadcrumb {
  font-size: 14px;
  
  .no-redirect {
    color: var(--text-muted);
    cursor: text;
  }
  
  a {
    color: var(--text-secondary);
    cursor: pointer;
    
    &:hover {
      color: var(--primary-color);
    }
  }
}
</style>
