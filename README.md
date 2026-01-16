# My_JavaEE_System
JavaEE期末作业 - Java Web 图书商城系统

## 技术栈
- Java 17
- Servlet + JSP + JDBC
- MySQL 8.0
- Tomcat 9.0
- Maven

## 快速启动

### 1. 启动环境（Docker Compose）
```bash
# 启动 MySQL 和 Tomcat 容器
docker-compose up -d

# 查看容器状态
docker-compose ps
```

### 2. 初始化数据库
```bash
# 方式一：使用 MySQL 客户端
docker exec -i my_javaee_system-mysql-1 mysql -uroot -proot < init.sql

# 方式二：进入容器手动执行
docker exec -it my_javaee_system-mysql-1 mysql -uroot -proot
# 然后执行：source /path/to/init.sql
```

### 3. 构建项目
```bash
# 使用 Maven 构建 war 包
mvn clean package

# 构建成功后，target/app.war 会自动映射到 Tomcat
```

### 4. 访问系统
- 首页：http://localhost:8080/
- 数据库测试：http://localhost:8080/dbCheck
- Hello测试：http://localhost:8080/hello

### 默认账号
- 管理员账号：`admin` / `admin123`

## 重置数据库
```bash
# 停止容器
docker-compose down

# 删除数据卷（会清空所有数据）
docker volume rm my_javaee_system_mysql-data

# 重新启动并初始化
docker-compose up -d
docker exec -i my_javaee_system-mysql-1 mysql -uroot -proot < init.sql
```

## 常见问题

### 1. 容器启动失败
**问题**：`docker-compose up -d` 失败  
**解决**：
- 检查端口 3306 和 8080 是否被占用
- 确保 Docker 服务正在运行

### 2. 数据库连接失败
**问题**：访问 /dbCheck 显示连接失败  
**解决**：
- 确认 MySQL 容器正在运行：`docker-compose ps`
- 检查 db.properties 配置是否正确
- 确认数据库已初始化：`docker exec -it my_javaee_system-mysql-1 mysql -uroot -proot -e "USE webshop; SHOW TABLES;"`

### 3. 中文乱码
**问题**：页面或数据库中文显示乱码  
**解决**：
- 已配置 EncodingFilter 统一使用 UTF-8
- 数据库使用 utf8mb4 字符集
- 确保所有 JSP 页面都包含 `<%@ page contentType="text/html;charset=UTF-8" %>`

### 4. Tomcat 自动部署失败
**问题**：war 包更新后 Tomcat 没有重新加载  
**解决**：
```bash
# 重启 Tomcat 容器
docker-compose restart tomcat

# 或者重新构建并重启
mvn clean package
docker-compose restart tomcat
```

### 5. 权限问题
**问题**：容器内文件权限不足  
**解决**：
```bash
# 修改文件权限
chmod -R 755 src/
chmod 644 pom.xml
```

## 项目结构
```
.
├── src/main/
│   ├── java/com/example/bookmall/
│   │   ├── filter/         # 过滤器（编码、登录验证）
│   │   ├── servlet/        # 控制器
│   │   ├── util/           # 工具类（DBUtil）
│   │   ├── dao/            # 数据访问层
│   │   └── service/        # 业务逻辑层
│   ├── resources/
│   │   └── db.properties   # 数据库配置
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml     # Web 配置
│       └── *.jsp           # JSP 页面
├── init.sql                # 数据库初始化脚本
├── docker-compose.yml      # Docker 编排配置
├── pom.xml                 # Maven 配置
└── README.md               # 项目文档
```

## 开发流程
1. 修改代码
2. 运行 `mvn clean package` 构建
3. 重启 Tomcat：`docker-compose restart tomcat`
4. 访问 http://localhost:8080/ 测试

## 数据库说明

### 表结构
- **user**：用户表（支持普通用户和管理员）
- **category**：图书分类表
- **book**：图书表（外键关联分类）

### 约束说明
- username 唯一索引
- 分类与图书：外键 RESTRICT（有图书的分类不可删除）
- 用户删除策略：禁用（status=0），不物理删除

## 后续开发计划
参考 [TODO.md](./TODO.md) 查看详细开发计划
