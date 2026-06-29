import request from '@/utils/request'

// 获取司机分页列表
export function getDriverList(params) {
  return request({
    url: '/driver/page',
    method: 'get',
    params
  })
}

// 获取所有司机（用于下拉选择）
export function getAllDrivers() {
  return request({
    url: '/driver/all',
    method: 'get'
  })
}

// 根据线路获取司机
export function getDriversByLine(lineId) {
  return request({
    url: `/driver/line/${lineId}`,
    method: 'get'
  })
}

// 根据车队获取司机
export function getDriversByFleet(fleetId) {
  return request({
    url: `/driver/fleet/${fleetId}`,
    method: 'get'
  })
}

// 添加司机
export function addDriver(data) {
  return request({
    url: '/driver',
    method: 'post',
    data
  })
}

// 更新司机
export function updateDriver(id, data) {
  return request({
    url: `/driver/${id}`,
    method: 'put',
    data
  })
}

// 删除司机
export function deleteDriver(id) {
  return request({
    url: `/driver/${id}`,
    method: 'delete'
  })
}
