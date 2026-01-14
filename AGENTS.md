# AGENTS.md

## 目标
- 交付一个基于 Java Web 的图书商城系统，覆盖注册/登录/浏览图书/购物车/后台管理等核心能力。
- 以可维护、可扩展为优先，确保功能与需求规格说明书一致。

## 技术约束
- 使用 Servlet、JSP、JDBC 实现。
- 使用 JavaBean 封装实体对象。
- 使用 Session 管理登录态与购物车。
- 使用 PreparedStatement 防止 SQL 注入，并进行基本输入校验。
- 运行环境：Linux + Tomcat + MySQL。

## 分层规范
- 表现层（Web）：Servlet/JSP，仅做请求处理与视图渲染。
- 业务层（Service）：封装业务规则与流程编排。
- 持久层（DAO）：仅负责数据库访问与对象映射。
- 实体层（Entity/Bean）：数据结构与传输对象。
- 严禁跨层直接访问（如 Servlet 直接操作 JDBC）。

## 目录规范
- `src/main/java`：Java 源码。
- `src/main/resources`：配置与资源文件。
- `src/main/webapp`：JSP、静态资源与 WEB-INF。
- `docs/`：需求与产品文档。
- `pom.xml`：依赖与构建配置。

## 常用命令
- 本地构建：`mvn -q -DskipTests package`
- 运行测试：`mvn -q test`
- 代码格式检查（如有）：`mvn -q -DskipTests verify`

## 提交规范
- 提交信息格式：`<type>(scope): <subject>`
- type 建议：`feat` `fix` `docs` `chore` `refactor` `test`
- subject 使用中文或英文均可，简洁描述变更目的。
- 每次提交聚焦单一目的，避免混合无关修改。
