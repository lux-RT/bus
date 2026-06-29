import request from '@/utils/request'

// 获取车队分页列表
export function getFleetList(params) {
  return request({
    url: '/fleet/page',
    method: 'get',
    params
  })
}

// 获取所有车队（用于下拉选择）
export function getAllFleets() {
  return request({
    url: '/fleet/all',
    method: 'get'
  })
}

// 添加车队
export function addFleet(data) {
  return request({
    url: '/fleet',
    method: 'post',
    data
  })
}

// 更新车队
export function updateFleet(id, data) {
  return request({
    url: `/fleet/${id}`,
    method: 'put',
    data
  })
}

// 删除车队
export function deleteFleet(id) {
  return request({
    url: `/fleet/${id}`,
    method: 'delete'
  })
}
