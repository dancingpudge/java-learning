## 面试题

### 基础

概念

- **简述在MySQL数据库中MyISAM和InnoDB的区别**
- 

### 初级

使用

### 中级

设计

### 高级

优化

解决问题思路

### 参考





## 索引问题



## 索引类型

https://www.cnblogs.com/winformasp/articles/12700814.html

常见的：

FULLTEXT	

HASH

BTREE

RTREE

## 索引种类

- 普通索引：仅加速查询
- 唯一索引：加速查询 + 列值唯一（可以有null）
- 主键索引：加速查询 + 列值唯一（不可以有null）+ 表中只有一个
- 组合索引：多列值组成一个索引，专门用于组合搜索，其效率大于索引合并
- 全文索引：对文本的内容进行分词，进行搜索

## 索引原理



索引实践



设计原则