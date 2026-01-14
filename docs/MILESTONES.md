# 图书商城系统 — GitHub 里程碑规划

## 概述
本文档定义了图书商城系统的6个递进式里程碑，聚焦三个核心功能：**用户注册/登录**、**商品浏览**、**购物车管理**。

每个里程碑包含具体的功能目标、交付物清单与验收标准，遵循项目的分层架构规范。

---

## 📍 里程碑 v0.1: 数据库与基础设施

**版本号**：v0.1  
**预计周期**：1周  
**状态**：未开始  
**依赖**：无  

### 功能目标
完成数据库设计、初始化脚本、实体类定义和数据库连接工具类实现，为后续功能提供数据层基础。

### 交付物清单
- [ ] 数据库ER图与设计文档（docs/DATABASE_DESIGN.md）
- [ ] MySQL初始化脚本（docs/sql/init.sql）
  - 用户表（user）
  - 分类表（category）
  - 图书表（book）
  - 测试数据插入
- [ ] 实体类（Entity/Bean）
  - [ ] User（用户）- src/main/java/com/example/bookmall/entity/User.java
  - [ ] Book（图书）- src/main/java/com/example/bookmall/entity/Book.java
  - [ ] Category（分类）- src/main/java/com/example/bookmall/entity/Category.java
  - [ ] CartItem（购物车项）- src/main/java/com/example/bookmall/entity/CartItem.java
- [ ] 数据库工具类
  - [ ] DBUtil（连接管理）- src/main/java/com/example/bookmall/util/DBUtil.java
- [ ] 环境验证
  - [ ] Docker Compose可正常启动MySQL + Tomcat
  - [ ] db.properties配置正确

### 验收标准
- ✅ 数据库表结构正确，主外键关系清晰
- ✅ DBUtil能正常获取数据库连接
- ✅ 实体类与数据库字段一一对应
- ✅ 初始化脚本可重复执行（包含测试数据）
- ✅ 代码提交遵循规范格式

### 关键技术点
- 使用MySQL 8.0 utf8mb4字符集
- 字段选择合理类型（INT、VARCHAR、DECIMAL等）
- 建立必要的索引（username唯一索引等）
- 单例模式实现DBUtil，支持连接复用

### 定义完成
**检查清单**：
```
□ 所有SQL表成功创建
□ 测试数据导入无误
□ DBUtil.getConnection()能返回有效连接
□ 4个实体类代码完整，属性名与字段一致
□ docs/DATABASE_DESIGN.md文档完整
```

---

## 📍 里程碑 v0.2: 用户认证模块（注册/登录）

**版本号**：v0.2  
**预计周期**：2周  
**状态**：未开始  
**依赖**：v0.1  

### 功能目标
实现用户注册、登录、退出的全流程，使用Session维持登录态，支持密码安全存储和登录状态验证。

### 交付物清单

#### 持久层（DAO）
- [ ] UserDAO - src/main/java/com/example/bookmall/dao/UserDAO.java
  - [ ] `findByUsername(String username)` - 查询用户
  - [ ] `insert(User user)` - 新增用户
  - [ ] `update(User user)` - 更新用户信息
  - [ ] `findById(int id)` - 按ID查询用户

#### 业务层（Service）
- [ ] UserService - src/main/java/com/example/bookmall/service/UserService.java
  - [ ] `register(String username, String password, String email)` - 注册业务
  - [ ] `login(String username, String password)` - 登录业务
  - [ ] `validateInput(String username, String password)` - 输入验证

#### 表现层（Servlet & Filter）
- [ ] LoginServlet - src/main/java/com/example/bookmall/servlet/LoginServlet.java
  - [ ] GET：返回登录表单页面
  - [ ] POST：处理登录请求，设置Session
- [ ] RegisterServlet - src/main/java/com/example/bookmall/servlet/RegisterServlet.java
  - [ ] GET：返回注册表单页面
  - [ ] POST：处理注册请求，验证并保存用户
- [ ] LogoutServlet - src/main/java/com/example/bookmall/servlet/LogoutServlet.java
  - [ ] 清除Session，重定向到首页
- [ ] LoginFilter - src/main/java/com/example/bookmall/filter/LoginFilter.java
  - [ ] 拦截需要登录的请求
  - [ ] 未登录则重定向到登录页

#### 视图（JSP）
- [ ] src/main/webapp/login.jsp - 登录页面
  - [ ] 用户名、密码输入框
  - [ ] 错误提示信息展示
  - [ ] 跳转注册链接
- [ ] src/main/webapp/register.jsp - 注册页面
  - [ ] 用户名、密码、确认密码、邮箱输入框
  - [ ] 表单验证提示
  - [ ] 已有账户登录链接

#### 工具类
- [ ] MD5Util或PasswordUtil - src/main/java/com/example/bookmall/util/PasswordUtil.java
  - [ ] `encode(String password)` - 密码加密
  - [ ] `verify(String plainPassword, String encodedPassword)` - 密码验证

### 验收标准
- ✅ 新用户能成功注册，数据正确存入数据库
- ✅ 注册时验证用户名是否已存在，防止重复
- ✅ 用户能使用正确的用户名和密码成功登录
- ✅ 登录成功后Session中能获取用户信息（id、username）
- ✅ 用户能成功退出，Session被清除
- ✅ 登录过滤器能正确拦截未登录访问
- ✅ 密码使用MD5或bcrypt加密存储，不存储明文
- ✅ 所有输入验证完整（非空、长度限制等）
- ✅ 无SQL注入漏洞（使用PreparedStatement）

### 关键技术点
- 使用HttpSession存储login_user或user_id
- 密码加密：MD5(password + salt) 或 bcrypt
- 输入验证：用户名长度、密码强度、邮箱格式
- 错误处理：用户已存在、密码错误等场景提示
- PreparedStatement防SQL注入

### 定义完成
**检查清单**：
```
□ 注册流程：无重复用户、密码加密、数据保存
□ 登录流程：验证成功、Session设置、异常提示
□ 退出流程：Session清除、重定向成功
□ 过滤器：未登录拦截、已登录放行
□ JSP页面：表单完整、提示信息清晰
□ 数据库：user表新增字段确认（role、created_at等）
```

---

## 📍 里程碑 v0.3: 商品浏览模块

**版本号**：v0.3  
**预计周期**：2周  
**状态**：未开始  
**依赖**：v0.1、v0.2  

### 功能目标
实现图书列表展示、按分类筛选、图书详情查询功能，提供良好的商品浏览体验。

### 交付物清单

#### 持久层（DAO）
- [ ] CategoryDAO - src/main/java/com/example/bookmall/dao/CategoryDAO.java
  - [ ] `findAll()` - 查询所有分类
  - [ ] `findById(int id)` - 按ID查询分类
  - [ ] `insert(Category category)` - 新增分类
  - [ ] `delete(int id)` - 删除分类
  
- [ ] BookDAO - src/main/java/com/example/bookmall/dao/BookDAO.java
  - [ ] `findAll()` - 查询所有图书（带分页）
  - [ ] `findById(int id)` - 按ID查询图书详情
  - [ ] `findByCategory(int categoryId)` - 按分类查询图书
  - [ ] `insert(Book book)` - 新增图书
  - [ ] `update(Book book)` - 更新图书信息
  - [ ] `delete(int id)` - 删除图书

#### 业务层（Service）
- [ ] CategoryService - src/main/java/com/example/bookmall/service/CategoryService.java
  - [ ] `getAllCategories()` - 获取所有分类列表
  - [ ] `getCategoryById(int id)` - 获取分类详情

- [ ] BookService - src/main/java/com/example/bookmall/service/BookService.java
  - [ ] `getAllBooks()` - 获取所有图书（分页）
  - [ ] `getBookById(int id)` - 获取图书详情
  - [ ] `getBooksByCategory(int categoryId)` - 按分类获取图书
  - [ ] `searchBooks(String keyword)` - 搜索图书（可选）

#### 表现层（Servlet）
- [ ] BookListServlet - src/main/java/com/example/bookmall/servlet/BookListServlet.java
  - [ ] GET：展示图书列表（全部或按分类筛选）
  - [ ] 支持categoryId、page参数
  
- [ ] BookDetailServlet - src/main/java/com/example/bookmall/servlet/BookDetailServlet.java
  - [ ] GET：展示单本图书详情
  - [ ] 支持bookId参数

- [ ] CategoryServlet - src/main/java/com/example/bookmall/servlet/CategoryServlet.java
  - [ ] GET：返回分类列表（用于菜单）

#### 视图（JSP）
- [ ] src/main/webapp/index.jsp - 首页（图书列表）
  - [ ] 分类导航菜单
  - [ ] 图书网格或列表展示
  - [ ] 分页导航
  - [ ] 搜索框（可选）
  - [ ] 添加到购物车按钮

- [ ] src/main/webapp/book-detail.jsp - 图书详情页
  - [ ] 图书封面（可选）
  - [ ] 书名、作者、分类、价格、库存
  - [ ] 图书描述/简介
  - [ ] 添加到购物车按钮
  - [ ] 返回列表链接

#### 工具类
- [ ] PageUtil - src/main/java/com/example/bookmall/util/PageUtil.java（可选）
  - [ ] 分页计算（总页数、起始行等）

### 验收标准
- ✅ 图书列表能展示所有图书，信息完整（书名、作者、价格、库存）
- ✅ 支持按分类筛选显示符合条件的图书
- ✅ 分页功能正常（上一页、下一页、总页数显示）
- ✅ 点击图书能进入详情页，显示完整信息
- ✅ 分类菜单能正确加载所有分类
- ✅ 已登录用户能访问，未登录也可浏览
- ✅ 页面样式美观，用户体验良好
- ✅ 所有数据库查询使用PreparedStatement

### 关键技术点
- 使用PreparedStatement进行数据库查询
- 分页实现：LIMIT offset, pageSize
- Category与Book的关联查询
- JSP EL表达式与JSTL标签库简化页面开发
- 分类缓存（可选优化）

### 定义完成
**检查清单**：
```
□ CategoryDAO与BookDAO实现所有方法
□ CategoryService与BookService正确调用DAO
□ BookListServlet能展示分页列表
□ 分类筛选正确工作
□ BookDetailServlet能展示完整图书信息
□ JSP页面美观，图书信息显示清晰
□ 分页导航链接正确
□ 无SQL注入漏洞
```

---

## 📍 里程碑 v0.4: 购物车模块

**版本号**：v0.4  
**预计周期**：2周  
**状态**：未开始  
**依赖**：v0.2、v0.3  

### 功能目标
实现购物车的添加、查看、修改、删除功能，使用Session存储购物车数据，支持实时计算总价。

### 交付物清单

#### 业务层（Service）
- [ ] CartService - src/main/java/com/example/bookmall/service/CartService.java
  - [ ] `addToCart(Map<Integer, CartItem> cart, int bookId, int quantity)` - 添加到购物车
  - [ ] `removeFromCart(Map<Integer, CartItem> cart, int bookId)` - 从购物车移除
  - [ ] `updateQuantity(Map<Integer, CartItem> cart, int bookId, int quantity)` - 修改数量
  - [ ] `clearCart(Map<Integer, CartItem> cart)` - 清空购物车
  - [ ] `calculateTotal(Map<Integer, CartItem> cart)` - 计算总价
  - [ ] `getCartSize(Map<Integer, CartItem> cart)` - 获取购物车商品数

#### 表现层（Servlet）
- [ ] AddCartServlet - src/main/java/com/example/bookmall/servlet/AddCartServlet.java
  - [ ] POST：添加图书到购物车
  - [ ] 支持bookId、quantity参数
  - [ ] 返回购物车页面或AJAX响应

- [ ] CartServlet - src/main/java/com/example/bookmall/servlet/CartServlet.java
  - [ ] GET：展示购物车列表

- [ ] UpdateCartServlet - src/main/java/com/example/bookmall/servlet/UpdateCartServlet.java
  - [ ] POST：更新购物车中图书的数量
  - [ ] 支持bookId、quantity参数

- [ ] RemoveCartServlet - src/main/java/com/example/bookmall/servlet/RemoveCartServlet.java
  - [ ] POST：从购物车删除图书
  - [ ] 支持bookId参数

#### 视图（JSP）
- [ ] src/main/webapp/cart.jsp - 购物车页面
  - [ ] 购物车为空提示
  - [ ] 购物车商品表格（书名、单价、数量、小计）
  - [ ] 数量编辑框
  - [ ] 删除商品按钮
  - [ ] 购物车总价显示
  - [ ] 继续购物与结算按钮

#### 工具类
- [ ] CartUtil - src/main/java/com/example/bookmall/util/CartUtil.java（可选）
  - [ ] 购物车相关的辅助方法

### 验收标准
- ✅ 用户能将图书添加到购物车，后续相同图书数量累加
- ✅ 购物车页面能正确显示所有商品及数量
- ✅ 用户能修改购物车中商品的数量
- ✅ 用户能从购物车删除单个商品
- ✅ 购物车总价计算正确（单价×数量求和）
- ✅ 用户退出登录后购物车清空
- ✅ 购物车数据存储在Session中，服务器重启不丢失（本次会话内）
- ✅ 添加购物车时验证库存是否充足
- ✅ 页面UI美观，交互流畅

### 关键技术点
- 购物车存储结构：`Map<Integer, CartItem>` 存入Session
- CartItem包含：bookId、bookTitle、bookPrice、quantity、subtotal
- Session属性名：cart 或 shopping_cart
- 数量修改、删除操作后实时重新计算总价
- 可选：AJAX异步修改购物车（无刷新更新页面）
- 库存验证：修改数量时检查库存是否充足

### 定义完成
**检查清单**：
```
□ CartService实现所有购物车业务方法
□ AddCartServlet正确添加图书，相同书籍累加
□ UpdateCartServlet能修改数量，验证库存
□ RemoveCartServlet能删除商品
□ 购物车页面显示完整，总价计算正确
□ Session购物车数据结构清晰
□ 登出时购物车清空
□ 库存验证逻辑完整
```

---

## 📍 里程碑 v0.5: 代码优化与安全加固

**版本号**：v0.5  
**预计周期**：1周  
**状态**：未开始  
**依赖**：v0.2、v0.3、v0.4  

### 功能目标
对既有代码进行全面优化，加强安全防护、异常处理、代码规范，为v1.0正式版做准备。

### 交付物清单

#### 安全加固
- [ ] XSS防护 - 所有JSP页面使用EL表达式`${}`，避免`<%=%>`
- [ ] SQL注入防护 - 全量审查，确保所有数据库操作使用PreparedStatement
- [ ] 会话安全 - 添加JSESSIONID HttpOnly标志、Secure标志（HTTPS环境）
- [ ] 输入验证 - 服务层添加参数校验（非空、长度、格式）
- [ ] 错误页面 - 自定义错误页面（404、500），不暴露技术栈信息

#### 异常处理
- [ ] BaseException与自定义异常类 - src/main/java/com/example/bookmall/exception/
  - [ ] BusinessException（业务异常）
  - [ ] DataAccessException（数据库异常）
- [ ] 异常统一处理 - src/main/java/com/example/bookmall/filter/ExceptionFilter.java
- [ ] 日志框架集成（可选SLF4J+Logback）

#### 代码规范
- [ ] 代码审查与重构
  - [ ] 消除重复代码（提取公共方法）
  - [ ] 改进变量、方法命名
  - [ ] 补充代码注释与JavaDoc
- [ ] 包结构检查
  - [ ] 验证无跨层直接访问
  - [ ] 确认分层清晰（表现层/业务层/持久层/实体层/工具层）

#### 数据库优化
- [ ] SQL优化
  - [ ] 添加必要的索引（username、categoryId等）
  - [ ] 查询性能验证
- [ ] 连接管理
  - [ ] DBUtil改进（连接复用、异常处理）
  - [ ] 可选：集成HikariCP连接池

#### 测试
- [ ] 单元测试编写
  - [ ] UserService单元测试
  - [ ] BookService单元测试
  - [ ] CartService单元测试
- [ ] 集成测试
  - [ ] 用户注册到登录流程
  - [ ] 图书浏览与购物车流程
- [ ] 测试覆盖率报告（目标 > 60%）

### 验收标准
- ✅ 无明显的代码坏味道（重复、过长方法等）
- ✅ 所有数据库操作使用PreparedStatement
- ✅ 异常处理完整，无堆栈信息泄露
- ✅ 代码注释充分，关键逻辑清晰
- ✅ 单元测试覆盖主要业务逻辑
- ✅ 所有功能在优化后仍能正常运行
- ✅ 性能无明显下降

### 关键技术点
- 异常处理策略：业务异常捕获、数据库异常转换、统一响应
- 代码审查检查清单：安全性、可读性、性能、可维护性
- 日志：记录关键业务事件、错误堆栈、性能指标
- 单元测试框架：JUnit 4+Mockito 或 JUnit 5

### 定义完成
**检查清单**：
```
□ 全量SQL审查，无SQL注入风险
□ JSP页面改用EL表达式，无XSS风险
□ 异常处理完整，错误提示友好
□ 代码注释充分，无明显坏味道
□ 单元测试覆盖关键服务
□ 所有功能测试通过，无回归
```

---

## 📍 里程碑 v1.0: 正式版发布与文档完善

**版本号**：v1.0  
**预计周期**：1周  
**状态**：未开始  
**依赖**：v0.5  

### 功能目标
完成项目的最终文档编写、部署指南、用户手册，确保项目可在生产环境正常运行。

### 交付物清单

#### 文档编写
- [ ] API文档 - docs/API.md
  - [ ] 所有Servlet端点的URL、参数、返回值
  - [ ] 示例请求与响应
  
- [ ] 部署指南 - docs/DEPLOYMENT.md
  - [ ] 系统要求（Java版本、MySQL版本、Tomcat版本）
  - [ ] Docker Compose快速启动步骤
  - [ ] 手动部署步骤
  - [ ] 数据库初始化
  - [ ] 配置调整

- [ ] 用户手册 - docs/USER_GUIDE.md
  - [ ] 注册与登录说明
  - [ ] 图书浏览与搜索
  - [ ] 购物车操作步骤
  - [ ] 常见问题解答

- [ ] 开发指南 - docs/DEVELOPMENT.md
  - [ ] 项目结构说明
  - [ ] 代码规范与提交规范
  - [ ] 本地开发环境搭建
  - [ ] 测试运行方式

- [ ] 更新日志 - CHANGELOG.md
  - [ ] 各版本功能变化记录
  - [ ] 已知问题与限制

#### 版本管理
- [ ] Git标签 - 创建v1.0的Release标签
- [ ] README.md更新 - 项目简介、快速开始、文档索引
- [ ] 项目信息更新
  - [ ] pom.xml版本号改为1.0
  - [ ] 贡献者信息

#### 最终验证
- [ ] 完整功能测试（注册、登录、浏览、购物车）
- [ ] 性能基准测试（响应时间、并发用户数）
- [ ] 兼容性测试（浏览器、操作系统）
- [ ] 安全检查最终审计
- [ ] 文档完整性检查

### 验收标准
- ✅ 所有文档完整、清晰、可执行
- ✅ 部署指南能被独立执行成功
- ✅ 新手用户能快速上手系统
- ✅ API文档与实现代码一致
- ✅ 无已知的关键BUG
- ✅ 代码提交历史清晰规范

### 定义完成
**检查清单**：
```
□ API文档完整，包含所有端点与示例
□ 部署指南完整，能在全新环境部署成功
□ 用户手册覆盖所有主要功能
□ 开发指南对新开发者有指导意义
□ README更新，包含项目概述与快速开始
□ CHANGELOG记录所有版本变化
□ v1.0标签与Release已创建
□ 最终功能与性能测试通过
```

---

## 依赖关系与并行开发

```
v0.1（数据库与基础设施）
  ↓
  ├─→ v0.2（用户认证）
  │   ├─→ v0.3（商品浏览）  [可与v0.2并行]
  │   │   └─→ v0.4（购物车）
  │   └─→ v0.4（购物车）     [依赖v0.2完成]
  │
  └─→ v0.3（商品浏览）      [依赖v0.1完成]
      └─→ v0.4（购物车）
  
  v0.2 + v0.3 + v0.4 完成后：
      ↓
    v0.5（代码优化与安全加固）
      ↓
    v1.0（正式版发布与文档完善）
```

**并行开发建议**：
- v0.2与v0.3可在v0.1完成后同时进行
- v0.4须等待v0.2与v0.3完成
- v0.5与v1.0必须串行执行

---

## 快速参考：各里程碑的关键验收项

| 里程碑 | 关键验收项 |
|--------|----------|
| **v0.1** | 数据库表创建✓ / DBUtil可用✓ / 实体类完整✓ |
| **v0.2** | 用户注册✓ / 用户登录✓ / Session维持✓ / 登出清空✓ |
| **v0.3** | 图书列表✓ / 分类筛选✓ / 图书详情✓ / 分页导航✓ |
| **v0.4** | 添加购物车✓ / 查看购物车✓ / 修改数量✓ / 删除商品✓ / 总价计算✓ |
| **v0.5** | SQL注入防护✓ / XSS防护✓ / 异常处理✓ / 单元测试✓ |
| **v1.0** | API文档✓ / 部署指南✓ / 用户手册✓ / 功能测试✓ / Release标签✓ |

---

## 提交规范参考

```bash
# v0.1提交示例
git commit -m "feat(db): 初始化数据库表结构与实体类"
git commit -m "feat(util): 实现DBUtil数据库连接工具"

# v0.2提交示例
git commit -m "feat(auth): 实现用户注册与登录功能"
git commit -m "feat(servlet): 添加LoginFilter登录过滤器"

# v0.3提交示例
git commit -m "feat(book): 实现图书列表与详情查询"
git commit -m "feat(category): 支持按分类筛选图书"

# v0.4提交示例
git commit -m "feat(cart): 实现购物车添加、修改、删除功能"

# v0.5提交示例
git commit -m "fix(security): 加强SQL注入与XSS防护"
git commit -m "test(service): 为UserService添加单元测试"

# v1.0提交示例
git commit -m "docs: 编写部署指南和用户手册"
git commit -m "chore: 发布v1.0版本"
```

---

## 使用方式

1. **在GitHub中创建对应的Milestone**
   - Settings → Milestones → New Milestone
   - 按照本文档创建6个Milestone，名称分别为 v0.1、v0.2、...、v1.0
   - 复制本文档中各里程碑的目标与验收标准到GitHub Description

2. **创建Issue并关联Milestone**
   - 对每个交付物创建一个Issue（或多个子Issue）
   - Issue标题参考交付物名称（如 "feat: 实现UserDAO"）
   - 在Issue中勾选Checklist，追踪任务进度
   - 关联对应的Milestone

3. **定期更新进度**
   - 完成每个Issue后关闭，PR自动关联
   - 追踪Milestone的完成百分比
   - 如有阻碍，在Issue中讨论并更新里程碑计划

4. **版本发布**
   - 各Milestone完成后，创建GitHub Release
   - Release标签与Milestone版本号一致
   - 附上CHANGELOG内容

---

## 修订历史

| 日期 | 版本 | 修订内容 |
|------|------|---------|
| 2026-01-14 | v1.0 | 初始创建，聚焦用户注册、商品浏览、购物车三个核心功能 |

