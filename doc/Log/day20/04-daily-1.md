第四天普瑞思

上午：代码结构 396 - 397

### 计划管理

```
Plan-Index-index.html
```

计划申请

```
Plan-Apply-index.html
```

计划审核

```
Plan-Check-index.html
```
计划论证

```
Plan-Argument-index.html
```

计划汇总

```
Plan-Collect-index.html
```

### 任务管理

```
Plan-Task-index.html
```

### 基础设置

论证设置

```
Plan-Set-index.html
```

计划申请类型管理

```
Plan-Type-index.html
```

计划首页管理

```
Plan-Remind-index.html
```

设备要求配置设置

```
Plan-Requirement-index.html
```

下午：15

计划，采购，库房，设备

财务，档案资产，科室，工程师，报表，设备科业务

消息，报表管理，知识

统计，设备监控，系统管理

```
// 计划 14
// reps_plan
CREATE TABLE `reps_plan` (
  `plan_id` int(8) NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(100) NOT NULL,
  `plan_code` varchar(255) NOT NULL,
  `plan_type_id` int(6) NOT NULL,
  `plan_type_info_id` int(8) NOT NULL,
  `department_id` int(8) NOT NULL,
  `user_id` int(10) NOT NULL DEFAULT '0',
  `user_name` varchar(10) NOT NULL,
  `equipment_apply_type` smallint(2) NOT NULL DEFAULT '0',
  `equipment_catalog_id` int(10) DEFAULT NULL,
  `equipment_name` varchar(100) NOT NULL,
  `equipment_code` varchar(255) NOT NULL,
  `category_id` int(8) NOT NULL,
  `equipment_engish_name` varchar(255) NOT NULL,
  `equipment_quantity` int(6) NOT NULL,
  `budget_amount` decimal(10,2) NOT NULL,
  `budget_amount_currency` tinyint(2) NOT NULL DEFAULT '1',
  `purchase_time` int(10) NOT NULL,
  `unit` varchar(10) NOT NULL,
  `attn` int(10) NOT NULL,
  `attn_phone` varchar(20) NOT NULL,
  `funding_sources` varchar(100) NOT NULL,
  `equipment_price` decimal(10,2) NOT NULL COMMENT '设备单价',
  `equipment_source` smallint(2) NOT NULL DEFAULT '0' COMMENT '设备来源',
  `purchase_mark` varchar(1000) NOT NULL,
  `is_new_item` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否新项目',
  `is_success_approval` tinyint(1) NOT NULL DEFAULT '0' COMMENT '项目是否获得审批',
  `install_address` varchar(255) NOT NULL DEFAULT '' COMMENT '科室预装地点',
  `staffing` text COMMENT '人员配置情况',
  `use_range` text COMMENT '使用用途适用范围',
  `purchase_category` tinyint(2) NOT NULL DEFAULT '1' COMMENT '申购设备类别',
  `purchase_category_info` text COMMENT '申购类别详情',
  `benefit_prediction` text COMMENT '医疗，社会效益预测',
  `social_benefit_prediction` varchar(30) DEFAULT NULL COMMENT '社会效益预估',
  `social_benefit_prediction_unit` varchar(10) DEFAULT NULL COMMENT '社会效益预估单位',
  `is_proof` tinyint(1) NOT NULL DEFAULT '1',
  `is_proof_online` tinyint(1) NOT NULL DEFAULT '1',
  `plan_progress` int(2) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  `check_status` tinyint(1) NOT NULL DEFAULT '1',
  `check_time` int(10) NOT NULL,
  `check_user_id` int(10) NOT NULL,
  `check_mark` varchar(1000) NOT NULL,
  `is_task` tinyint(1) NOT NULL DEFAULT '1',
  `task_time` int(10) NOT NULL,
  `plan_task_id` int(8) NOT NULL,
  `is_assigned` tinyint(1) NOT NULL DEFAULT '1',
  `assigned_time` int(10) NOT NULL,
  `equipment_requirement` text NOT NULL COMMENT '设备要求配置条件',
  `plan_step` tinyint(1) NOT NULL DEFAULT '1' COMMENT '计划步骤',
  `is_approval` tinyint(1) NOT NULL DEFAULT '2' COMMENT '是否多级审批 1是，2否',
  `is_perfect` tinyint(1) NOT NULL DEFAULT '2' COMMENT '是否完善可行性论证表：1.是 2.否',
  `reason_basis` text COMMENT '立项理由和依据',
  `existing_equipment_info` text COMMENT '现有同类设备情况',
  `benefit_analysis` text COMMENT '效益分析',
  `staffing_info` text COMMENT '人员配置情况',
  `support_condition` text COMMENT '配套条件分析',
  `equipment_technology` text COMMENT '设备技术情况',
  `config_requirement` text COMMENT '配置需求',
  `is_delete` tinyint(1) NOT NULL DEFAULT '1',
  `add_time` int(10) NOT NULL,
  `add_user_id` int(10) NOT NULL,
  `edit_time` int(10) NOT NULL,
  `edit_user_id` int(10) NOT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=440 DEFAULT CHARSET=utf8

// reps_plan_accessory
CREATE TABLE `reps_plan_accessory` (
  `accessory_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `plan_progress_id` int(11) NOT NULL,
  `plan_task_id` int(10) NOT NULL DEFAULT '0',
  `proof_id` int(11) NOT NULL,
  `accessory_type` tinyint(2) NOT NULL,
  `accessory_name` varchar(20) NOT NULL,
  `accessory_describe` varchar(255) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '1',
  `add_time` int(10) NOT NULL,
  `add_user_id` int(10) NOT NULL,
  `edit_time` int(10) NOT NULL,
  `edit_user_id` int(10) NOT NULL,
  PRIMARY KEY (`accessory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1117 DEFAULT CHARSET=utf8

// reps_plan_accessory_file

// reps_plan_check_log

// reps_plan_history

// reps_plan_item

// reps_plan_progress

// reps_plan_proof_expert_info

// reps_plan_signature

// reps_plan_task

// reps_plan_task_department

// reps_plan_task_product

// reps_plan_type

// reps_plan_type_info

```

