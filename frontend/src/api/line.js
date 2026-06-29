import request from '@/utils/request'

// 获取线路分页列表
export function getLineList(params) {
  return request({
    url: '/line/page',
    method: 'get',
    params
  })
}

// 获取所有线路（用于下拉选择）
export function getAllLines() {
  return request({
    url: '/line/all',
    method: 'get'
  })
}

// 根据车队获取线路
export function getLinesByFleet(fleetId) {
  return request({
    url: `/line/fleet/${fleetId}`,
    method: 'get'
  })
}

// 添加线路
export function addLine(data) {
  return request({
    url: '/line',
    method: 'post',
    data
  })
}

// 更新线路
export function updateLine(id, data) {
  return request({
    url: `/line/${id}`,
    method: 'put',
    data
  })
}

// 删除线路
export function deleteLine(id) {
  return request({
    url: `/line/${id}`,
    method: 'delete'
  })
}

// 获取线路的站点列表
export function getLineStations(lineId) {
  return request({
    url: `/line/${lineId}/stations`,
    method: 'get'
  })
}

// 为线路添加站点
export function addLineStation(lineId, data) {
  return request({
    url: `/line/${lineId}/stations`,
    method: 'post',
    data
  })
}

// 从线路移除站点
export function removeLineStation(lineId, stationId) {
  return request({
    url: `/line/${lineId}/stations/${stationId}`,
    method: 'delete'
  })
}

// 设置线路路队长
export function setRoadCaptain(lineId, driverId) {
  return request({
    url: `/line/${lineId}/road-captain/${driverId}`,
    method: 'put'
  })
}
