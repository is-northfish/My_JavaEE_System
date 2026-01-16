# AGENTS.md
“在当前 Codespaces 环境中，应用通过 Codespaces Port Forward 访问；本容器内 localhost:8080 可能不可达，这是正常现象。”
## 角色与目标
你是我的开发助手（Codex）。

目标：交付一个 **基于 Java Web（Servlet + JSP + JDBC）** 的图书商城系统，覆盖以下核心能力：
- 用户注册 / 登录 / 退出
- 图书分类与浏览
- 购物车（基于 Session）
- 后台管理（分类 / 商品 / 用户）

优先级原则（必须遵守）：
> 正确性 > 可控性 > 简单性 > 扩展性  
> 任何可能增加复杂度的设计，一律禁止。

---

## 技术与架构硬约束（不可违反）

### 技术栈
- Java Web：Servlet + JSP + JDBC
- 实体：JavaBean（POJO）
- 数据库：MySQL 8.0
- 容器：Docker Compose（mysql + tomcat）
- 构建：Maven（war）
- 运行环境：Linux + Tomcat

### 明确禁止
- 禁止使用 Spring / Spring Boot / MyBatis / Hibernate
- 禁止使用 Lombok
- 禁止 ORM 或反射自动映射
- 禁止连接池（v0.x 阶段）
- 禁止软删除 / 级联删除
- 禁止并发库存控制
- 禁止过度抽象或重构

---

## 数据与业务决策（已最终确定，不再讨论）

### 用户（user）
- 字段：
  - id
  - username（唯一）
  - password_hash
  - salt
  - role（VARCHAR(10)：USER / ADMIN）
  - status（1=正常，0=禁用）
  - created_at
- 密码方案：盐 + MD5
- 用户删除策略：仅禁用（status=0），不物理删除

### 分类（category）与图书（book）
- 关系：Category 1 : N Book
- 外键策略：FOREIGN KEY + RESTRICT  
  - 分类下存在图书时禁止删除
- 金额类型：DECIMAL(10,2)
- 不实现软删除

### 购物车
- 存储位置：HttpSession
- 数据结构：Map<bookId, CartItem>
- 重复加入：数量累加
- 价格来源：必须从数据库读取
- 库存校验：仅校验 quantity <= stock（不处理并发）

### Session 约定
- 登录后 Session 中仅存：
  - userId
  - username
  - role
- 不在 Session 中存完整 User 对象

---

## 分层规范（严格执行）

- Web 层（Servlet / JSP）
  - 仅做参数接收、跳转、视图渲染
  - 禁止直接访问 JDBC 或 DAO

- Service 层
  - 仅处理业务规则与流程编排

- DAO 层
  - 仅负责 JDBC 操作与**手动字段映射**
  - 必须使用 PreparedStatement

- Entity（POJO）
  - 仅包含字段与 getter / setter

---

## Git 工作流（强制执行）

### 分支策略
- 主开发分支：develop
- 功能分支：feature/<task-slug>
- 禁止直接向 main 提交

### 若 develop 不存在（仅执行一次）
```bash
git checkout main
git pull
git checkout -b develop
git push -u origin develop
```
每个任务的固定流程
git status（必须干净）

git checkout develop && git pull

git checkout -b feature/<task-slug>

实现 一个且仅一个任务

执行自测（见下）

合并到 develop：

```bash
git checkout develop
git pull
git merge --no-ff feature/<task-slug>
git push
```
删除 feature 分支（可提示我）

自测标准
若任务涉及运行，必须满足以下步骤：

构建：

```bash
mvn -q clean package
```
部署：

```bash
docker compose restart tomcat
```
日志检查：

```bash
docker compose logs -f tomcat
```
不允许出现异常堆栈

页面验证：明确给出可访问的 URL

数据库验证：

```bash
docker compose exec mysql \
  mysql -uapp -papp123456 -D webshop -e "<SQL>"
```
本项目 不要求自动化测试，mvn test 不作为 DoD。

环境与实现注意事项（高优先级）
Tomcat 挂载 target/app.war 时：

必须先执行 mvn package，再启动或重启 Tomcat

JDBC 连接主机名必须使用 mysql（不是 localhost）

所有 SQL 必须使用 PreparedStatement

DAO 层必须手动映射字段（禁止反射）

Servlet 层必须打印基础日志（URI + 成功 / 失败）

Codex 输出格式（每次必须严格遵守）
- 任务确认（1–2 句话）
- 分支名称：feature/<task-slug>
- 文件改动清单（含路径）
- 具体命令（可复制）
- 关键实现说明（必要代码片段）
- 自测步骤（命令 + 预期结果）
- 合并到 develop 的命令
- 风险与回滚方案

输入约定
- 我一次只给一个任务
- 你只完成该任务
- 完成后立即停止，等待下一任务

```markdown
---
```