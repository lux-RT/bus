-- ============================================================
-- 重建 driver_violation_view：英文列名，补全缺失字段
-- ============================================================

DROP VIEW IF EXISTS driver_violation_view;

CREATE VIEW driver_violation_view AS
SELECT vr.record_id,
       vr.driver_id,
       vr.vehicle_id,
       vr.line_id,
       vr.station_id,
       vr.violation_type_id,
       vr.violation_time,
       vr.description,
       vr.inputter_id,
       d.name          AS driver_name,
       v.license_plate,
       l.line_name,
       l.fleet_id,
       vt.type_name,
       s.station_name
FROM violation_record vr
JOIN driver d         ON vr.driver_id = d.driver_id
JOIN vehicle v        ON vr.vehicle_id = v.vehicle_id
JOIN line l           ON vr.line_id = l.line_id
JOIN violation_type vt ON vr.violation_type_id = vt.type_id
LEFT JOIN station s   ON vr.station_id = s.station_id;
