import request from '@/utils/request'

// 获取站点分页列表
export function getStationList(params) {
  return request({
    url: '/station/page',
    method: 'get',
    params
  })
}

// 获取所有站点（用于下拉选择）
export function getAllStations() {
  return request({
    url: '/station/all',
    method: 'get'
  })
}

// 添加站点
export function addStation(data) {
  return request({
    url: '/station',
    method: 'post',
    data
  })
}

// 更新站点
export function updateStation(id, data) {
  return request({
    url: `/station/${id}`,
    method: 'put',
    data
  })
}

// 删除站点
export function deleteStation(id) {
  return request({
    url: `/station/${id}`,
    method: 'delete'
  })
}
