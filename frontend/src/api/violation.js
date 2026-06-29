import request from '@/utils/request'

// 获取违章记录列表
export function getViolationList(params) {
  return request({
    url: '/violation/list',
    method: 'get',
    params
  })
}

// 添加违章记录
export function addViolation(data) {
  return request({
    url: '/violation',
    method: 'post',
    data
  })
}

// 更新违章记录
export function updateViolation(id, data) {
  return request({
    url: `/violation/${id}`,
    method: 'put',
    data
  })
}

// 删除违章记录
export function deleteViolation(id) {
  return request({
    url: `/violation/${id}`,
    method: 'delete'
  })
}

// 模糊查询违章记录
export function searchViolations(params) {
  return request({
    url: '/violation/search',
    method: 'get',
    params
  })
}

// 获取违章统计（按车队）
export function getViolationStatsByFleet(params) {
  return request({
    url: '/violation/stats/fleet',
    method: 'get',
    params
  })
}

// 获取违章统计（按线路）
export function getViolationStatsByLine(params) {
  return request({
    url: '/violation/stats/line',
    method: 'get',
    params
  })
}

// 获取违章统计（按时间）
export function getViolationStatsByTime(params) {
  return request({
    url: '/violation/stats/time',
    method: 'get',
    params
  })
}

// 获取违章统计（按类型）
export function getViolationStatsByType(params) {
  return request({
    url: '/violation/stats/type',
    method: 'get',
    params
  })
}

// 获取违章类型列表
export function getViolationTypes() {
  return request({
    url: '/violation/types',
    method: 'get'
  })
}
