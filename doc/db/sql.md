#mysql 入门总结

##蠢问题

数据类型，索引，查询

##聪明问题

分库分表，btree,优化，日志，慢查询，事务

##更好的问题

主从复制，并发，锁



#redis 入门总结

##蠢问题

5种数据结构，输入输入，有效时间

string

hash

list

soft

zsoft

##聪明问题

分布式，业务场景

##更好的问题

锁，场景瓶颈，解决方案

##Elasticsearch

使用，封装，作用



```php
$esclient = Elasticsearch\ClientBuilder::create()
  ->setHosts(["localhost:9200"])
  ->build();
$params = [
  'index' => 'social-*',
  'body' => [
    'query' => [
      'match' => ['message' => 'myProduct']
    ],
    'aggs' => [
      'top_10_states' => [
        'terms' => [
          'field' => 'state',
          'size' => 10,
        ]
      ]
    ]
  ]
];
$response = $esclient->search($params);
```

```go
es, _ := elasticsearch.NewClient(elasticsearch.Config{
  Addresses: []string{"http://localhost:9200"},
})

body := `{
  "query": {
    "match": { "message": "myProduct" }
  },
  "aggregations": {
    "top_10_states": { "terms": { "field": "state", "size": 10 } }
  }
}`

res, err := es.Search(
  es.Search.WithIndex("social-*"),
  es.Search.WithBody(strings.NewReader(body)),
  es.Search.WithPretty(),
)
if err != nil {
  log.Fatalf("Error getting response: %s", err)
}

defer res.Body.Close()
```

### Elasticsearch基本概念

#### 全文搜索(Full-text Search)

 全文检索是指计算机索引程序通过扫描文章中的每一个词，对每一个词建立一个索引，指明该词在文章中出现的次数和位置，当用户查询时，检索程序就根据事先建立的索引进行查找，并将查找的结果反馈给用户的检索方式。
  在全文搜索的世界中，存在着几个庞大的帝国，也就是主流工具，主要有：

- Apache Lucene
- Elasticsearch
- Solr
- Ferret

#### 倒排索引（Inverted Index）

 该索引表中的每一项都包括一个属性值和具有该属性值的各记录的地址。由于不是由记录来确定属性值，而是由属性值来确定记录的位置，因而称为倒排索引(inverted index)。Elasticsearch能够实现快速、高效的搜索功能，正是基于倒排索引原理。

#### 节点 & 集群（Node & Cluster）

 Elasticsearch 本质上是一个分布式数据库，允许多台服务器协同工作，每台服务器可以运行多个Elasticsearch实例。单个Elasticsearch实例称为一个节点（Node），一组节点构成一个集群（Cluster）。

#### 索引（Index）

 Elasticsearch 数据管理的顶层单位就叫做 Index（索引），相当于关系型数据库里的数据库的概念。另外，每个Index的名字必须是小写。

#### 文档（Document）

 Index里面单条的记录称为 Document（文档）。许多条 Document 构成了一个 Index。Document 使用 JSON 格式表示。同一个 Index 里面的 Document，不要求有相同的结构（scheme），但是最好保持相同，这样有利于提高搜索效率。

#### 类型（Type）

 Document 可以分组，比如employee这个 Index 里面，可以按部门分组，也可以按职级分组。这种分组就叫做 Type，它是虚拟的逻辑分组，用来过滤 Document，类似关系型数据库中的数据表。
  不同的 Type 应该有相似的结构（Schema），性质完全不同的数据（比如 products 和 logs）应该存成两个 Index，而不是一个 Index 里面的两个 Type（虽然可以做到）。

### 文档元数据（Document metadata）

 文档元数据为_index, _type, _id, 这三者可以唯一表示一个文档，_index表示文档在哪存放，_type表示文档的对象类别，_id为文档的唯一标识。

#### 字段（Fields）

 每个Document都类似一个JSON结构，它包含了许多字段，每个字段都有其对应的值，多个字段组成了一个 Document，可以类比关系型数据库数据表中的字段。
  在 Elasticsearch 中，文档（Document）归属于一种类型（Type），而这些类型存在于索引（Index）中，下图展示了Elasticsearch与传统关系型数据库的类比：

![img](https:////upload-images.jianshu.io/upload_images/9419034-4f8eb4926bc326de.png?imageMogr2/auto-orient/strip|imageView2/2/w/924/format/webp)

### Elasticsearch入门

 Elasticsearch提供了多种交互使用方式，包括Java API和RESTful API ，本文主要介绍RESTful API 。所有其他语言可以使用RESTful API 通过端口 *9200* 和 Elasticsearch 进行通信，你可以用你最喜爱的 web 客户端访问 Elasticsearch 。甚至，你还可以使用 `curl` 命令来和 Elasticsearch 交互。
  一个Elasticsearch请求和任何 HTTP 请求一样，都由若干相同的部件组成：



```xml
curl -X<VERB> '<PROTOCOL>://<HOST>:<PORT>/<PATH>?<QUERY_STRING>' -d '<BODY>'
```

返回的数据格式为JSON，因为Elasticsearch中的文档以JSON格式储存。其中，被 `< >` 标记的部件：

| 部件         | 说明                                                         |
| ------------ | ------------------------------------------------------------ |
| VERB         | 适当的 HTTP *方法* 或 *谓词* : `GET`、 `POST`、 `PUT`、 `HEAD` 或者 `DELETE`。 |
| PROTOCOL     | `http` 或者 `https`（如果你在 Elasticsearch 前面有一个 `https` 代理） |
| HOST         | Elasticsearch 集群中任意节点的主机名，或者用 `localhost` 代表本地机器上的节点。 |
| PORT         | 运行 Elasticsearch HTTP 服务的端口号，默认是 `9200` 。       |
| PATH         | API 的终端路径（例如 `_count` 将返回集群中文档数量）。Path 可能包含多个组件，例如：`_cluster/stats` 和 `_nodes/stats/jvm` 。 |
| QUERY_STRING | 任意可选的查询字符串参数 (例如 `?pretty` 将格式化地输出 JSON 返回值，使其更容易阅读) |
| BODY         | 一个 JSON 格式的请求体 (如果请求需要的话)                    |

对于HTTP方法，它们的具体作用为：

| HTTP方法 | 说明                   |
| -------- | ---------------------- |
| GET      | 获取请求对象的当前状态 |
| POST     | 改变对象的当前状态     |
| PUT      | 创建一个对象           |
| DELETE   | 销毁对象               |
| HEAD     | 请求获取对象的基础信息 |

 我们以下面的数据为例，来展示Elasticsearch的用法。

![img](https:////upload-images.jianshu.io/upload_images/9419034-ed0cb3d4e39aab3c.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

以下全部的操作都在Kibana中完成，创建的index为conference, type为event .

##### 插入数据

 首先创建index为conference, 创建type为event, 插入id为1的第一条数据，只需运行下面命令就行：



```csharp
PUT /conference/event/1
{
  "host": "Dave",
  "title": "Elasticsearch at Rangespan and Exonar",
  "description": "Representatives from Rangespan and Exonar will come and discuss how they use Elasticsearch",
  "attendees": ["Dave", "Andrew", "David", "Clint"],
  "date": "2013-06-24T18:30",
  "reviews": 3
}
```

在上面的命令中，路径/conference/event/1表示文档的index为conference, type为event, id为1. 类似于上面的操作，依次插入剩余的4条数据，完成插入后，查看数据如下：

![img](https:////upload-images.jianshu.io/upload_images/9419034-789a863712a499a1.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

插入数据

##### 删除数据

 比如我们想要删除conference中event里面id为5的数据，只需运行下面命令即可：



```csharp
DELETE /conference/event/5
```

返回结果如下：



```json
{
  "_index" : "conference",
  "_type" : "event",
  "_id" : "5",
  "_version" : 2,
  "result" : "deleted",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

表示该文档已成功删除。如果想删除整个event类型，可输入命令:



```csharp
DELETE /conference/event
```

如果想删除整个conference索引，可输入命令:



```undefined
DELETE /conference
```

##### 修改数据

 修改数据的命令为POST, 比如我们想要将conference中event里面id为4的文档的作者改为Bob，那么需要运行命令如下：



```csharp
POST /conference/event/4/_update
{
  "doc": {"host": "Bob"}
}
```

返回的信息如下：（表示修改数据成功）



```json
{
  "_index" : "conference",
  "_type" : "event",
  "_id" : "4",
  "_version" : 7,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 7,
  "_primary_term" : 1
}
```

查看修改后的数据如下：

![img](https:////upload-images.jianshu.io/upload_images/9419034-5eaf26bb79f406da.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

修改数据

##### 查询数据

 查询数据的命令为GET，查询命令也是Elasticsearch最为重要的功能之一。比如我们想查询conference中event里面id为1的数据，运行命令如下：



```csharp
GET /conference/event/1
```

返回的结果如下：



```json
{
  "_index" : "conference",
  "_type" : "event",
  "_id" : "1",
  "_version" : 2,
  "found" : true,
  "_source" : {
    "host" : "Dave",
    "title" : "Elasticsearch at Rangespan and Exonar",
    "description" : "Representatives from Rangespan and Exonar will come and discuss how they use Elasticsearch",
    "attendees" : [
      "Dave",
      "Andrew",
      "David",
      "Clint"
    ],
    "date" : "2013-06-24T18:30",
    "reviews" : 3
  }
}
```

在_source 属性中，内容是原始的 JSON 文档，还包含有其它属性，比如_index, _type, _id, _found等。
  如果想要搜索conference中event里面所有的文档，运行命令如下：



```csharp
GET /conference/event/_search
```

返回结果包括了所有四个文档，放在数组 hits 中。
  当然，Elasticsearch 提供更加丰富灵活的查询语言叫做 *查询表达式* ， 它支持构建更加复杂和健壮的查询。利用*查询表达式*，我们可以检索出conference中event里面所有host为Bob的文档，命令如下：



```csharp
GET /conference/event/_search
{
    "query" : {
        "match" : {
            "host" : "Bob"
        }
    }
}
```

返回的结果只包括了一个文档，放在数组 hits 中。
  接着，让我们尝试稍微高级点儿的全文搜索——一项传统数据库确实很难搞定的任务。搜索下所有description中含有"use Elasticsearch"的event：



```csharp
GET /conference/event/_search
{
    "query" : {
        "match" : {
            "description" : "use Elasticsearch"
        }
    }
}
```

返回的结果（部分）如下：



```bash
{
 ...
  "hits" : {
    "total" : 2,
    "max_score" : 0.65109104,
    "hits" : [
      {
        ...
        "_score" : 0.65109104,
        "_source" : {
          "host" : "Dave Nolan",
          "title" : "real-time Elasticsearch",
          "description" : "We will discuss using Elasticsearch to index data in real time",
          ...
        }
      },
      {
        ...
        "_score" : 0.5753642,
        "_source" : {
          "host" : "Dave",
          "title" : "Elasticsearch at Rangespan and Exonar",
          "description" : "Representatives from Rangespan and Exonar will come and discuss how they use Elasticsearch",
          ...
        }
      }
    ]
  }
}
```

返回的结果包含了两个文档，放在数组 hits 中。让我们对这个结果做一些分析，第一个文档的description里面含有“using Elasticsearch”，这个能匹配“use Elasticsearch”是因为Elasticsearch含有内置的词干提取算法，之后两个文档按_score进行排序，_score字段表示文档的相似度（默认的相似度算法为BM25）。
  如果想搜索下所有description中严格含有"use Elasticsearch"这个短语的event，可以使用下面的命令：



```csharp
GET /conference/event/_search
{
    "query" : {
        "match_phrase": {
            "description" : "use Elasticsearch"
        }
    }
}
```

这时候返回的结果只有一个文档，就是上面输出的第二个文档。
  当然，Elasticsearch还支持更多的搜索功能，比如过滤器，高亮搜索，结构化搜索等，希望接下来能有更多的时间和经历来介绍~



作者：山阴少年
链接：https://www.jianshu.com/p/d48c32423789


> - 知识，技能，能力，选择，运气，出生
>
> 

## RDBMS 术语

  - **数据库:** 数据库是一些关联表的集合。
  - **数据表:** 表是数据的矩阵。在一个数据库中的表看起来像一个简单的电子表格。
  - **列:** 一列(数据元素) 包含了相同类型的数据, 例如邮政编码的数据。
  - **行：**一行（=元组，或记录）是一组相关的数据，例如一条用户订阅的数据。
  - **冗余**：存储两倍数据，冗余降低了性能，但提高了数据的安全性。
  - **主键**：主键是唯一的。一个数据表中只能包含一个主键。你可以使用主键来查询数据。
  - **外键：**外键用于关联两个表。
  - **复合键**：复合键（组合键）将多个列作为一个索引键，一般用于复合索引。
  - **索引：**使用索引可快速访问数据库表中的特定信息。索引是对数据库表中一列或多列的值进行排序的一种结构。类似于书籍的目录。
  - **参照完整性:** 参照的完整性要求关系中不允许引用不存在的实体。与实体完整性是关系模型必须满足的完整性约束条件，目的是保证数据的一致性。

## 自学sql

http://xuesql.cn/lesson/select_queries_introduction

https://zhuanlan.zhihu.com/p/345488420

count(*)

select 1+1

where id < 5

SQL Lesson 2: 条件查询 (constraints) (Pt. 1)

```
WHERE year BETWEEN 2000 AND 2010
NOT BETWEEN
LIMIT 5
WHERE Year>=2010 AND Length_minutes<120
```

SQL Lesson 3: 中 LIKE（模糊查询） 和 %（通配符）

```
WHERE Title LIKE "%Toy%" 
WHERE Director!="John Lasseter"
WHERE Title LIKE "WALL-%" 
WHERE Year=1998
```

SQL Lesson 4: 查询结果Filtering过滤 和 sorting排序

```
DISTINCT  GROUP BY  ORDER BY column ASC/DESC;
LIMIT num_limit OFFSET num_offset;

SELECT DISTINCT column, another_column, …
FROM mytable
WHERE condition(s);
DISTINCT Director FROM movies ORDER BY Director ASC;
ORDER BY Year DESC  LIMIT 4;
ORDER BY Title ASC LIMIT 5 OFFSET 5;
WHERE Director='John Lasseter'ORDER BY Length_minutes ASC  limit 1 offset 2;

WHERE Country='Canada'
WHERE  City='Chicago' AND
Longitude<87.629798
ORDER BY Longitude DESC;
WHERE Country='Mexico'
ORDER BY Population DESC
LIMIT 2;
WHERE Country='United States' ORDER BY  Population LIMIT 2 OFFSET 2
```

**SQL Lesson 6: 用JOINs进行多表联合查询**

```
数据库范式(normalization)
SELECT column, another_table_column, …
FROM mytable （主表）
INNER JOIN another_table （要连接的表）
    ON mytable.id = another_table.id (想象一下刚才讲的主键连接，两个相同的连成1条)
WHERE condition(s)
ORDER BY column, … ASC/DESC
LIMIT num_limit OFFSET num_offset;

```

**SQL Lesson 7: 外连接（OUTER JOINs）**

```
用LEFT/RIGHT/FULL JOINs 做多表查询
SELECT column, another_column, …
FROM mytable
INNER/LEFT/RIGHT/FULL JOIN another_table
    ON mytable.id = another_table.matching_id
WHERE condition(s)
ORDER BY column, … ASC/DESC
LIMIT num_limit OFFSET num_offset;
```

SQL Lesson 8: 关于特殊关键字 NULLs

```

```

SQL Lesson 9: 在查询中使用表达式

```

```

SQL Lesson 10: 在查询中进行统计I (Pt. 1)

```

```

SQL Lesson 12: 查询执行顺序

```
这才是完整的SELECT查询
SELECT DISTINCT column, AGG_FUNC(column_or_expression), …
FROM mytable
    JOIN another_table
      ON mytable.column = another_table.column
    WHERE constraint_expression
    GROUP BY column
    HAVING constraint_expression
    ORDER BY column ASC/DESC
    LIMIT count OFFSET COUNT;
```





## 集合运算

如何学习 SQL 语言？ - 李启方的回答 - 知乎 https://www.zhihu.com/question/19552975/answer/2011125959



## **数学基础**

交换律（Commutative Laws）： 

A ∪ B = B∪A， A ∩ B = B ∩ A 

结合律（Associative Laws）： 

(A ∪ B) ∪ C = A ∪ (B∪C) = A ∪ B∪C

 (A ∩ B) ∩ C = A ∩ (B ∩ C) = A ∩ B ∩ C 

分配律（Distributive Laws）： 

(A ∩ B) ∪C = (A∪C) ∩ (B∪C)

 (A∪B) ∩ C = (A ∩ C) ∪(B ∩ C) 

等幂律（Impotent laws）: 

A∪A = A，A∩A = A 

吸收律（Absorption laws）： 

(A∩B)∪A = A，(A∪B)∩A = A 

同一律（Domination laws）： 

A∪Ø = A，A∩Ω= A

 A∪Ω=Ω，A∩Ø = Ø；

mysql联合 索引(复合索引)的探讨 https://blog.csdn.net/bigtree_3721/article/details/87478706

复合索引定义，使用 https://www.cnblogs.com/jjflover/p/11024697.html





