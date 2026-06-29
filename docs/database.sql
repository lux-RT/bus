create table driver
(
    driver_id       int auto_increment
        primary key,
    name            varchar(20)          not null comment '姓名',
    gender          varchar(2)           null comment '性别',
    age             int                  null comment '年龄',
    phone           varchar(20)          null comment '联系电话',
    line_id         int                  null comment '所属线路ID（逻辑外键，关联线路表，队长可为空）',
    is_road_captain tinyint(1) default 0 null comment '是否为路队长',
    is_captain      tinyint(1) default 0 null comment '是否为队长（车队级）',
    hire_date       date                 null comment '入职日期',
    constraint phone
        unique (phone),
    constraint chk_captain_line
        check (((`is_captain` = true) and (`line_id` is null)) or (`is_captain` = false)),
    check (`gender` in (_utf8mb4\'ç·\',_utf8mb4\'å¥³\')),
	check (`age` between 18 and 60)
)
comment '司机信息表';

create index idx_driver_line
    on driver (line_id);

create table fleet
(
    fleet_id    int auto_increment
        primary key,
    fleet_name  varchar(50) not null comment '车队名称',
    captain_id  int         null comment '队长ID（逻辑外键，关联司机表）',
    create_time date        null comment '车队成立时间',
    constraint captain_id
        unique (captain_id)
)
    comment '车队信息表';

create table line
(
    line_id        int auto_increment
        primary key,
    line_name      varchar(100) not null comment '线路名称',
    fleet_id       int          not null comment '所属车队ID（逻辑外键，关联车队表）',
    total_stations int          null comment '总站点数'
)
    comment '公交线路表';

create table line_station
(
    line_id    int not null comment '线路ID（逻辑外键）',
    station_id int not null comment '站点ID（逻辑外键）',
    sequence   int not null comment '站点在线路上的顺序',
    primary key (line_id, station_id),
    constraint uk_line_sequence
        unique (line_id, sequence)
)
    comment '线路站点顺序表';

create table station
(
    station_id   int auto_increment
        primary key,
    station_name varchar(100) not null comment '站点名称',
    address      varchar(200) null comment '站点地址',
    constraint uk_station_name_address
        unique (station_name, address) comment '同名不同址唯一'
)
    comment '站点信息表';

create table vehicle
(
    vehicle_id    int auto_increment
        primary key,
    license_plate varchar(20)                not null comment '车牌号',
    line_id       int                        not null comment '所属线路ID（逻辑外键，关联线路表）',
    model         varchar(50)                null comment '车型',
    purchase_time date                       null comment '购买时间',
    status        varchar(20) default '正常' null comment '车辆状态',
    constraint license_plate
        unique (license_plate),
    check (`status` in (_utf8mb4\'æ­£å¸¸\',_utf8mb4\'ç»´ä¿®\',_utf8mb4\'æ¥åº\'))
)
comment '车辆信息表';

create table violation_record
(
    record_id         int auto_increment comment '记录ID（自增主键）'
        primary key,
    driver_id         int       not null comment '违章司机ID（逻辑外键）',
    vehicle_id        int       not null comment '涉事车辆ID（逻辑外键）',
    line_id           int       not null comment '违章线路ID（逻辑外键）',
    station_id        int       null comment '违章站点ID（逻辑外键，可为空）',
    violation_type_id int       not null comment '违章类型ID（逻辑外键）',
    violation_time    timestamp not null comment '违章发生时间',
    description       text      null comment '详细描述',
    inputter_id       int       not null comment '录入人ID（逻辑外键，必须为队长/路队长）',
    constraint uk_violation_unique
        unique (driver_id, vehicle_id, violation_time, violation_type_id)
)
    comment '违章记录表';

create index idx_violation_driver_time
    on violation_record (driver_id, violation_time);

create index idx_violation_line_time
    on violation_record (line_id, violation_time);

create table violation_type
(
    type_id   int         not null comment '违章类型编号'
        primary key,
    type_name varchar(50) not null comment '违章类型名称',
    constraint type_name
        unique (type_name)
)
    comment '违章类型字典表';

create definer = root@localhost view driver_violation_view as
select `vr`.`record_id`         AS `record_id`,
       `vr`.`driver_id`         AS `driver_id`,
       `vr`.`vehicle_id`        AS `vehicle_id`,
       `vr`.`line_id`           AS `line_id`,
       `vr`.`station_id`        AS `station_id`,
       `vr`.`violation_type_id` AS `violation_type_id`,
       `vr`.`violation_time`    AS `violation_time`,
       `vr`.`description`       AS `description`,
       `vr`.`inputter_id`       AS `inputter_id`,
       `d`.`name`               AS `driver_name`,
       `v`.`license_plate`      AS `license_plate`,
       `l`.`line_name`          AS `line_name`,
       `l`.`fleet_id`           AS `fleet_id`,
       `vt`.`type_name`         AS `type_name`,
       `s`.`station_name`       AS `station_name`
from (((((`public_transportation_management`.`violation_record` `vr` join `public_transportation_management`.`driver` `d`
          on ((`vr`.`driver_id` = `d`.`driver_id`))) join `public_transportation_management`.`vehicle` `v`
         on ((`vr`.`vehicle_id` = `v`.`vehicle_id`))) join `public_transportation_management`.`line` `l`
        on ((`vr`.`line_id` = `l`.`line_id`))) join `public_transportation_management`.`violation_type` `vt`
       on ((`vr`.`violation_type_id` = `vt`.`type_id`))) left join `public_transportation_management`.`station` `s`
      on ((`vr`.`station_id` = `s`.`station_id`)));

-- comment on column driver_violation_view.record_id not supported: 记录ID（自增主键）

-- comment on column driver_violation_view.driver_id not supported: 违章司机ID（逻辑外键）

-- comment on column driver_violation_view.vehicle_id not supported: 涉事车辆ID（逻辑外键）

-- comment on column driver_violation_view.line_id not supported: 违章线路ID（逻辑外键）

-- comment on column driver_violation_view.station_id not supported: 违章站点ID（逻辑外键，可为空）

-- comment on column driver_violation_view.violation_type_id not supported: 违章类型ID（逻辑外键）

-- comment on column driver_violation_view.violation_time not supported: 违章发生时间

-- comment on column driver_violation_view.description not supported: 详细描述

-- comment on column driver_violation_view.inputter_id not supported: 录入人ID（逻辑外键，必须为队长/路队长）

-- comment on column driver_violation_view.driver_name not supported: 姓名

-- comment on column driver_violation_view.license_plate not supported: 车牌号

-- comment on column driver_violation_view.line_name not supported: 线路名称

-- comment on column driver_violation_view.fleet_id not supported: 所属车队ID（逻辑外键，关联车队表）

-- comment on column driver_violation_view.type_name not supported: 违章类型名称

-- comment on column driver_violation_view.station_name not supported: 站点名称

