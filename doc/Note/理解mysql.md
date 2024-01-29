## 理解理解[数据库中Schema（模式）概念的理解](https://www.cnblogs.com/csniper/p/5509620.html)

## MySQL视图（view）基本用法

https://blog.csdn.net/weixin_44377973/article/details/103229296#t1

```
CREATE [OR REPLACE] [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}]
    VIEW view_name [(column_list)]
    AS select_statement
   [WITH [CASCADED | LOCAL] CHECK OPTION]

	create view v1 as select * from salary>5000;
	wp_options
	create view v1_wp_options as select * from wp_options where option_id = 1;
	
	create table dept(
    dept_id int primary key auto_increment comment '部门编号',
    dept_name char(20) comment '部门名称'
);
insert into dept(dept_name) values('销售部'),('财务部'),('生产部'),('人事部');

create table emp(
    emp_id int primary key auto_increment comment '员工号',
    emp_name char(20) not null default '' comment '员工姓名',
    gender char(2) not null default '男' comment '性别',
    birth datetime not null default '1990-1-1' comment '出生日期',
    salary decimal(10,2) not null default 0 comment '工资',
    address varchar(200) not null default '' comment '通讯地址',
    dept_id int comment '部门编号'
);

create index idx_name on emp(emp_name);
create index idx_birth on emp(birth);
create index idx_deptid_name on emp(dept_id,emp_name);

insert into emp(emp_name,gender,birth,salary,address,dept_id) 
values('张晓红','女','1980-1-23',5800,'河南省郑州市中原路10号',1),
('张静静','女','1987-10-3',5400,'河南省新乡市平原路38号',1),
('王云飞','男','1992-11-15',5600,'河南省新乡市人民路28号',1),
('王鹏飞','男','1987-10-1',6800,'河南省新乡市东明大道12号',1),
('王大鹏','男','1989-2-11',5900,'河南省郑州市东风路15号',1),
('王萌萌','女','1986-12-30',5000,'河南省开封市五一路14号',2),
('王大光','男','1988-11-8',6200,'河南省开封市八一路124号',2),
('王小明','男','1998-1-3',4800,'河南省驻马店市雪松路128号',2),
('王娜娜','女','1994-3-5',5200,'河南省驻马店市车站路2号',2),
('刘云飞','男','1992-8-13',6800,'河南省南阳市民生路255号',3),
('张陆军','男','1991-9-6',6200,'河南省南阳市张仲景路14号',3);

mysql> create or replace view v_emp_dept_id_1 as select emp_name,address 
       from emp where dept_id=1;
Query OK, 0 rows affected (0.00 sec)

mysql> select * from v_emp_dept_id_1;
+-----------+-------------------------------------+
| emp_name  | address                             |
+-----------+-------------------------------------+
| 张晓红    | 河南省郑州市中原路10号              |
| 张静静    | 河南省新乡市平原路38号              |
| 王云飞    | 河南省新乡市人民路28号              |
| 王大鹏    | 河南省郑州市东风路15号              |
| 王鹏飞    | 河南省新乡市东明大道12号            |
+-----------+-------------------------------------+
5 rows in set (0.00 sec)

mysql>  create or replace view v_emp_dept as select emp_name,address,dept_name 
        from emp join dept on emp.dept_id=dept.dept_id where dept.dept_id=1;
Query OK, 0 rows affected (0.00 sec)

mysql> select * from v_emp_dept;
+-----------+-------------------------------------+-----------+
| emp_name  | address                             | dept_name |
+-----------+-------------------------------------+-----------+
| 张晓红    | 河南省郑州市中原路10号              | 销售部    |
| 张静静    | 河南省新乡市平原路38号              | 销售部    |
| 王云飞    | 河南省新乡市人民路28号              | 销售部    |
| 王大鹏    | 河南省郑州市东风路15号              | 销售部    |
| 王鹏飞    | 河南省新乡市东明大道12号            | 销售部    |
+-----------+-------------------------------------+-----------+
5 rows in set (0.00 sec)

mysql>  create or replace view v_dept_emp_count(dept_name,emp_count,avg_salary) 
        as select dept_name,count(*),avg(salary) 
        from empp join dept on emp.dept_id=dept.dept_id group by dept.dept_name;
Query OK, 0 rows affected (0.01 sec)

mysql> select * from v_dept_emp_count;
+-----------+-----------+-------------+
| dept_name | emp_count | avg_salary  |
+-----------+-----------+-------------+
| 生产部    |         2 | 6500.000000 |
| 财务部    |         4 | 5300.000000 |
| 销售部    |         5 | 5900.000000 |
+-----------+-----------+-------------+
3 rows in set (0.00 sec)


mysql> create view v_emp_dept_produce as select emp_name,salary from emp
    -> where dept_id=(select dept_id from dept where dept_name='生产部')
    -> with check option;
Query OK, 0 rows affected (0.01 sec)

mysql> select * from v_emp_dept_produce;
+-----------+---------+
| emp_name  | salary  |
+-----------+---------+
| 刘云飞    | 6800.00 |
| 张陆军    | 6200.00 |
+-----------+---------+
2 rows in set (0.01 sec)


```

## MySQL存储过程和存储函数

```
create procedure test1()
begin
	select 'Hello';
end;

call test1();

show procedure status;

-- 查看存储过程的创建语句
show create procedure test1;
drop procedure test1;
```

