# My_JavaEE_System

JavaEE 期末作业 - Java Web 图书商城系统

## 📋 项目简介

基于 Servlet + JSP + JDBC 的图书商城管理系统，支持前台购物和后台管理功能。项目使用 Docker Compose 编排开发环境，包含 MySQL 数据库、Tomcat 应用服务器和开发容器，实现一键启动和自动化部署。

## 🚀 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 开发语言 |
| Servlet | 4.0.1 | Web 框架 |
| JSP + JSTL | 1.2 | 视图层 |
| JDBC | - | 数据访问 |
| MySQL | 8.0 | 数据库 |
| Tomcat | 9.0 (JDK 17) | 应用服务器 |
| Maven | 3.9 | 构建工具 |
| Docker Compose | - | 容器编排 |

## 🎯 主要功能

### 前台功能
-  用户注册/登录/登出
-  图书分类浏览
-  图书列表展示（支持按分类筛选）
-  图书详情查看
-  购物车管理（添加/修改数量/删除）

### 后台管理（需管理员权限）
-  图书管理（添加/编辑/删除/列表）
-  分类管理（添加/编辑/删除/列表）
-  用户管理（列表/编辑/禁用）
-  权限控制（AdminFilter 拦截未授权访问）

### 技术特性
-  密码加盐 MD5 哈希存储
-  Session 会话管理
-  Filter 过滤器（编码、登录验证、权限控制）

##  快速启动

### 前置要求
- Docker 和 Docker Compose
- 端口 3306（MySQL）和 8080（Tomcat）未被占用

### 一键启动
\`\`\`bash
# 克隆项目
git clone <repository-url>
cd My_JavaEE_System

# 启动所有服务（自动构建 Tomcat 镜像并初始化数据库）
docker compose up -d --build

# 查看容器状态
docker compose ps

# 查看日志（可选）
docker compose logs -f
\`\`\`

**启动过程说明**：
1. MySQL 容器启动并自动执行 `init.sql` 初始化数据库和测试数据
2. MySQL 健康检查通过后（约 30 秒）
3. Tomcat 容器启动并部署 WAR 包

### 访问系统
| 功能 | URL |
|------|-----|
|  前台首页 | http://localhost:8080/ |
|  用户登录 | http://localhost:8080/login.jsp |
|  用户注册 | http://localhost:8080/register.jsp |
|  图书列表 | http://localhost:8080/books?categoryId=1 |
|  图书详情 | http://localhost:8080/book?id=1 |
|  购物车 | http://localhost:8080/cart/list |
|  后台首页 | http://localhost:8080/admin/ |
|  数据库测试 | http://localhost:8080/dbCheck |

### 默认账号
| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | admin123 | ADMIN | 管理员（可访问后台） |

##  开发指南

### 本地开发流程
\`\`\`bash
# 1. 修改 Java 代码或 JSP 页面

# 2. 重新构建 WAR 包
mvn clean package

# 3. 重启 Tomcat 容器
docker compose restart tomcat

# 4. 查看 Tomcat 日志
docker compose logs -f tomcat
\`\`\`

### 数据库操作
\`\`\`bash
# 连接到 MySQL（应用账号）
docker compose exec mysql mysql -uapp -papp123456 webshop

# 连接到 MySQL（管理员账号）
docker compose exec mysql mysql -uroot -proot

# 查看数据库字符集
docker compose exec mysql mysql -uroot -proot -e "SHOW VARIABLES LIKE 'character%';"

# 查看表结构
docker compose exec mysql mysql -uapp -papp123456 -e "USE webshop; SHOW TABLES;"

# 查看图书数据
docker compose exec mysql mysql -uapp -papp123456 -e "USE webshop; SELECT * FROM book;"
\`\`\`

### 完全重置环境
\`\`\`bash
# 停止并删除所有容器和数据卷
docker compose down -v

# 重新启动（会重新初始化数据库）
docker compose up -d --build
\`\`\`

##  项目结构

\`\`\`
My_JavaEE_System/
├── src/main/
│   ├── java/com/example/bookmall/
│   │   ├── dao/                    # 数据访问层
│   │   │   ├── BookDao.java
│   │   │   ├── CategoryDao.java
│   │   │   ├── UserDao.java
│   │   │   └── DBCheckDao.java
│   │   ├── entity/                 # 实体类
│   │   │   ├── Book.java
│   │   │   ├── Category.java
│   │   │   ├── User.java
│   │   │   ├── CartItem.java
│   │   │   └── DBStatus.java
│   │   ├── service/                # 业务逻辑层
│   │   │   ├── BookService.java
│   │   │   ├── CategoryService.java
│   │   │   ├── UserService.java
│   │   │   ├── CartService.java
│   │   │   └── DBCheckService.java
│   │   ├── servlet/                # 控制器层（23个Servlet）
│   │   │   ├── HomeServlet.java
│   │   │   ├── LoginServlet.java
│   │   │   ├── RegisterServlet.java
│   │   │   ├── LogoutServlet.java
│   │   │   ├── BookListServlet.java
│   │   │   ├── BookDetailServlet.java
│   │   │   ├── Cart*Servlet.java   # 购物车相关
│   │   │   ├── Admin*Servlet.java  # 后台管理相关
│   │   │   └── DBCheckServlet.java
│   │   ├── filter/                 # 过滤器
│   │   │   ├── EncodingFilter.java # UTF-8编码过滤
│   │   │   ├── LoginFilter.java    # 登录验证
│   │   │   └── AdminFilter.java    # 管理员权限验证
│   │   ├── util/                   # 工具类
│   │   │   └── DBUtil.java         # 数据库连接工具
│   │   └── HelloServlet.java       # 测试Servlet
│   ├── resources/
│   │   └── db.properties           # 数据库配置
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml             # Web应用配置
│       ├── admin/                  # 后台管理页面
│       │   ├── index.jsp
│       │   ├── book_*.jsp          # 图书管理页面
│       │   ├── category_*.jsp      # 分类管理页面
│       │   └── user_*.jsp          # 用户管理页面
│       ├── assets/css/
│       │   └── app.css             # 样式文件
│       ├── index.jsp               # 前台首页
│       ├── login.jsp               # 登录页
│       ├── register.jsp            # 注册页
│       ├── book_detail.jsp         # 图书详情
│       └── cart.jsp                # 购物车
├── docs/                           # 项目文档
│   ├── 产品说明.md
│   └── 需求规格说明书.md
├── docker-compose.yml              # Docker编排配置
├── Dockerfile.tomcat               # Tomcat镜像构建
├── init.sql                        # 数据库初始化脚本
├── mysql.cnf                       # MySQL配置文件
├── pom.xml                         # Maven配置
├── TODO.md                         # 开发计划
└── README.md                       # 项目说明
\`\`\`

##  数据库设计

### 表结构

#### user（用户表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| username | VARCHAR(50) | 用户名，唯一 |
| password_hash | VARCHAR(32) | MD5哈希密码 |
| salt | VARCHAR(32) | 密码盐值 |
| role | VARCHAR(10) | 角色（USER/ADMIN） |
| status | TINYINT | 状态（1启用/0禁用） |
| created_at | DATETIME | 创建时间 |

#### category（分类表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| name | VARCHAR(100) | 分类名称 |

#### book（图书表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键，自增 |
| category_id | BIGINT | 外键，关联分类 |
| name | VARCHAR(200) | 图书名称 |
| price | DECIMAL(10,2) | 价格 |
| stock | INT | 库存 |

### 约束说明
- **username 唯一索引**：防止重复注册
- **分类与图书外键**：RESTRICT 策略（有图书的分类不可删除）
- **用户删除策略**：逻辑删除（status=0），不物理删除

### 初始测试数据
- **分类**：编程语言、数据库、算法与数据结构、Web开发
- **图书**：8本测试图书（Java、Python、MySQL、Redis等）
- **管理员**：admin / admin123

##  配置说明

### Docker Compose 配置要点

#### MySQL 服务
\`\`\`yaml
environment:
  MYSQL_ROOT_PASSWORD: root
  MYSQL_DATABASE: webshop
  MYSQL_USER: app
  MYSQL_PASSWORD: app123456
  TZ: Asia/Shanghai
command: >
  --character-set-server=utf8mb4 
  --collation-server=utf8mb4_unicode_ci 
  --init-connect='SET NAMES utf8mb4'
healthcheck:
  test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-uroot", "-proot"]
  interval: 5s
  timeout: 5s
  retries: 20
  start_period: 30s
\`\`\`

**关键配置说明**：
- 字符集设置：命令行参数确保 UTF-8 支持（解决中文乱码）
- 健康检查：确保 MySQL 完全启动后再启动 Tomcat
- 自动初始化：`init.sql` 在首次启动时自动执行

#### Tomcat 服务
\`\`\`yaml
depends_on:
  mysql:
    condition: service_healthy
\`\`\`
- 等待 MySQL 健康检查通过后再启动
- 使用多阶段构建优化镜像大小

### 字符集配置（解决中文乱码）

本项目实现了全链路 UTF-8 配置：

1. **MySQL 服务端**：
   - \`--character-set-server=utf8mb4\`
   - \`--collation-server=utf8mb4_unicode_ci\`

2. **init.sql 初始化**：
   \`\`\`sql
   SET NAMES utf8mb4;
   SET CHARACTER SET utf8mb4;
   CREATE DATABASE ... DEFAULT CHARACTER SET utf8mb4;
   \`\`\`

3. **JDBC 连接**：
   \`\`\`properties
   db.url=jdbc:mysql://mysql:3306/webshop?useUnicode=true&characterEncoding=UTF-8&...
   \`\`\`

4. **EncodingFilter 过滤器**：
   \`\`\`java
   request.setCharacterEncoding("UTF-8");
   response.setCharacterEncoding("UTF-8");
   \`\`\`

5. **JSP 页面**：
   \`\`\`jsp
   <%@ page contentType="text/html;charset=UTF-8" %>
   \`\`\`

##  常见问题

### 1. 容器启动失败
**问题**：\`docker compose up -d\` 失败  
**解决**：
\`\`\`bash
# 检查端口占用
lsof -i :3306
lsof -i :8080

# 查看详细错误日志
docker compose logs

# 确保 Docker 服务运行
docker version
\`\`\`

### 2. 页面无法访问
**问题**：访问 http://localhost:8080/ 报错  
**解决**：
\`\`\`bash
# 1. 确认容器都在运行
docker compose ps

# 2. 检查 MySQL 是否健康
docker compose exec mysql mysqladmin ping -uroot -proot

# 3. 查看 Tomcat 日志
docker compose logs tomcat

# 4. 等待 Tomcat 完全启动（约 30-60 秒）
\`\`\`

### 3. 数据库连接失败
**问题**：访问 /dbCheck 显示连接失败  
**解决**：
\`\`\`bash
# 1. 测试 MySQL 连接
docker compose exec mysql mysql -uapp -papp123456 -e "SELECT 1;"

# 2. 检查数据库配置
cat src/main/resources/db.properties

# 3. 查看 Tomcat 错误日志
docker compose logs tomcat | grep -i "SQLException"
\`\`\`

### 4. 中文显示乱码
**问题**：页面或数据库中文显示为 \`ä¸­æ–‡\` 或其他乱码  
**解决**：
\`\`\`bash
# 1. 检查 MySQL 字符集配置
docker compose exec mysql mysql -uroot -proot \\
  -e "SHOW VARIABLES LIKE 'character%';"

# 预期输出：character_set_server = utf8mb4

# 2. 如果配置不对，完全重置
docker compose down -v
docker compose up -d --build
\`\`\`

### 5. Tomcat 自动部署失败
**问题**：修改代码后 Tomcat 没有重新加载  
**解决**：
\`\`\`bash
# 方案一：仅重启 Tomcat
mvn clean package
docker compose restart tomcat

# 方案二：完全重建 Tomcat 镜像
docker compose up -d --build tomcat
\`\`\`

### 6. 后台管理无法访问
**问题**：访问 /admin/ 被重定向到登录页  
**解决**：
- 确保使用管理员账号登录（admin / admin123）
- 普通用户无法访问后台（被 AdminFilter 拦截）

##  业务流程

### 用户注册流程
1. 访问 \`/register.jsp\` 填写注册信息
2. 提交到 \`RegisterServlet\`
3. 生成随机盐值，MD5(密码 + 盐值) 存储
4. 插入数据库，默认角色 \`USER\`
5. 跳转到登录页

### 用户登录流程
1. 访问 \`/login.jsp\` 输入用户名密码
2. 提交到 \`LoginServlet\`
3. 查询用户信息和盐值
4. 验证 MD5(输入密码 + 盐值) == 存储的哈希值
5. 验证成功：Session 写入 \`userId\`, \`username\`, \`role\`
6. 跳转到首页

### 购物车流程
1. **添加**：详情页点击"加入购物车" → \`CartAddServlet\`
2. **查看**：访问 \`/cart/list\` → \`CartListServlet\`
3. **修改**：购物车页修改数量 → \`CartUpdateServlet\`
4. **删除**：购物车页删除 → \`CartRemoveServlet\`

注：购物车数据存储在 Session 中（未持久化到数据库）

### 后台管理流程
1. 管理员登录后访问 \`/admin/\`
2. AdminFilter 验证 \`session.getAttribute("role") == "ADMIN"\`
3. 通过验证：可访问后台功能
4. 未通过：重定向到 \`/login.jsp\`

##  许可证

本项目仅用于学习交流，请勿用于商业用途。

##  作者

is-northfish
5901220123@xcu.edu.cn

---

**如有问题，请提交 Issue 或查看项目文档,或邮件联系。**
