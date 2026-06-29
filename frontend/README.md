# 公交安全管理系统

基于 Vue 3 + Element Plus 的公交安全管理系统前端项目。

## 项目简介

本系统是为公交公司设计的安全管理解决方案，用于管理车队、线路、车辆、司机以及违章记录等信息。系统支持违章查询、统计分析等功能。

## 技术栈

- **前端框架**: Vue 3.3.4
- **UI 组件库**: Element Plus 2.3.14
- **路由管理**: Vue Router 4.2.4
- **状态管理**: 原生 Vue 3 Composition API
- **图表库**: ECharts 5.4.3
- **HTTP 客户端**: Axios 1.5.0
- **构建工具**: Vue CLI 5.0.8
- **样式预处理**: SCSS/Sass

## 功能模块

### 1. 车队管理
- 车队信息的增删改查
- 车队队长分配
- 车队统计信息展示

### 2. 线路管理
- 线路信息的增删改查
- 线路站点管理
- 路队长分配

### 3. 车辆管理
- 车辆信息的增删改查
- 车辆状态管理（正常/维修/报废）
- 车牌号格式验证

### 4. 司机管理
- 司机信息的增删改查
- 身份管理（普通司机/路队长/队长）
- 年龄、性别等属性约束

### 5. 违章管理
- 违章记录的增删改查
- 违章类型管理
- 录入人权限控制

### 6. 违章查询增强
- 多条件组合查询（司机、车队、线路、时间段、类型）
- 模糊查询支持
- 排序功能（按时间、司机、线路）
- 分页展示
- 结果导出

### 7. 违章统计增强
- 按车队统计
- 按线路统计
- 按时间统计
- 按类型统计
- 多维度图表展示
- 趋势分析

### 8. 站点管理
- 站点信息的增删改查
- 站点地址管理

## 项目结构

```
bus-safety-system/
├── public/                  # 静态资源
│   └── index.html
├── src/
│   ├── api/                 # API 接口定义
│   │   ├── fleet.js         # 车队接口
│   │   ├── line.js          # 线路接口
│   │   ├── vehicle.js       # 车辆接口
│   │   ├── driver.js        # 司机接口
│   │   ├── violation.js     # 违章接口
│   │   └── station.js       # 站点接口
│   ├── assets/              # 资源文件
│   ├── components/          # 公共组件
│   ├── layout/              # 布局组件
│   │   ├── index.vue        # 主布局
│   │   └── components/      # 布局子组件
│   ├── router/              # 路由配置
│   │   └── index.js
│   ├── styles/              # 全局样式
│   │   └── index.scss
│   ├── utils/               # 工具函数
│   │   └── request.js       # HTTP 请求封装
│   ├── views/               # 页面视图
│   │   ├── login/           # 登录页
│   │   ├── dashboard/       # 首页
│   │   ├── fleet/           # 车队管理
│   │   ├── line/            # 线路管理
│   │   ├── vehicle/         # 车辆管理
│   │   ├── driver/          # 司机管理
│   │   ├── violation/       # 违章管理
│   │   └── station/         # 站点管理
│   ├── App.vue              # 根组件
│   └── main.js              # 入口文件
├── package.json             # 项目依赖
├── vue.config.js            # Vue 配置
└── jsconfig.json            # JS 配置
```

## 数据库表结构

系统对应以下数据库表：

1. **fleet** - 车队表
2. **line** - 线路表
3. **driver** - 司机表
4. **vehicle** - 车辆表
5. **station** - 站点表
6. **line_station** - 线路站点关联表
7. **violation_type** - 违章类型表
8. **violation_record** - 违章记录表

## 安装与运行

### 环境要求
- Node.js >= 16.0.0
- npm >= 8.0.0

### 安装依赖
```bash
npm install
```

### 开发环境运行
```bash
npm run serve
```

### 生产环境构建
```bash
npm run build
```

### 代码检查
```bash
npm run lint
```

## 默认登录信息

- 用户名: admin
- 密码: 123456

## 界面特点

1. **现代化设计**: 采用 Element Plus 组件库，界面简洁大气
2. **响应式布局**: 适配不同屏幕尺寸
3. **深色侧边栏**: 渐变蓝色主题，专业美观
4. **卡片式布局**: 信息展示清晰有序
5. **动画效果**: 页面切换和交互动画流畅
6. **数据可视化**: 集成 ECharts 图表展示统计数据

## API 接口说明

系统预设了以下 API 接口（需配合后端服务使用）：

### 车队接口
- GET /api/fleet/list - 获取车队列表
- POST /api/fleet - 添加车队
- PUT /api/fleet/:id - 更新车队
- DELETE /api/fleet/:id - 删除车队

### 线路接口
- GET /api/line/list - 获取线路列表
- POST /api/line - 添加线路
- PUT /api/line/:id - 更新线路
- DELETE /api/line/:id - 删除线路

### 车辆接口
- GET /api/vehicle/list - 获取车辆列表
- POST /api/vehicle - 添加车辆
- PUT /api/vehicle/:id - 更新车辆
- DELETE /api/vehicle/:id - 删除车辆

### 司机接口
- GET /api/driver/list - 获取司机列表
- POST /api/driver - 添加司机
- PUT /api/driver/:id - 更新司机
- DELETE /api/driver/:id - 删除司机

### 违章接口
- GET /api/violation/list - 获取违章列表
- POST /api/violation - 添加违章
- PUT /api/violation/:id - 更新违章
- DELETE /api/violation/:id - 删除违章
- GET /api/violation/search - 搜索违章
- GET /api/violation/stats/* - 违章统计

### 站点接口
- GET /api/station/list - 获取站点列表
- POST /api/station - 添加站点
- PUT /api/station/:id - 更新站点
- DELETE /api/station/:id - 删除站点

## 开发说明

### 前端开发规范
1. 使用 Vue 3 Composition API 风格
2. 组件命名使用 PascalCase
3. 使用 `<script setup>` 语法糖
4. 样式使用 SCSS，遵循 BEM 命名规范

### 后端对接说明
1. 修改 `vue.config.js` 中的代理配置指向实际后端服务
2. 修改 `src/utils/request.js` 中的 baseURL
3. 根据实际接口调整 `src/api/` 下的接口定义

## 许可证

MIT License

## 作者

数据库系统实验项目
