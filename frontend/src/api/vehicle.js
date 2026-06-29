import request from '@/utils/request'

// 获取车辆分页列表
export function getVehicleList(params) {
  return request({
    url: '/vehicle/page',
    method: 'get',
    params
  })
}

// 获取所有车辆（用于下拉选择）
export function getAllVehicles() {
  return request({
    url: '/vehicle/all',
    method: 'get'
  })
}

// 根据线路获取车辆
export function getVehiclesByLine(lineId) {
  return request({
    url: `/vehicle/line/${lineId}`,
    method: 'get'
  })
}

// 添加车辆
export function addVehicle(data) {
  return request({
    url: '/vehicle',
    method: 'post',
    data
  })
}

// 更新车辆
export function updateVehicle(id, data) {
  return request({
    url: `/vehicle/${id}`,
    method: 'put',
    data
  })
}

// 删除车辆
export function deleteVehicle(id) {
  return request({
    url: `/vehicle/${id}`,
    method: 'delete'
  })
}
