# TODO – Java Web 图书商城（简化执行版）

执行规则：
- 一次只做一条
- 勾选前必须能跑（页面或 SQL 可验证）
- 每勾一条就 commit
- 出问题立刻回滚到上一个已勾选点

---

## v0.1 数据库与基础设施（必须稳）

- [√] docker-compose 固化（MySQL + Tomcat）
- [√] init.sql：建表（user / category / book）
- [√] 约束与索引（username UNIQUE，分类外键 RESTRICT）
- [√] utf8mb4 字符集 + Asia/Shanghai 时区
- [√] 基础测试数据（分类 + 图书）
- [√] 管理员种子账号（ADMIN）
- [√] db.properties（host=mysql）
- [√] DBUtil（DriverManager + properties）
- [√] dbCheck 页面（能 SELECT NOW()）
- [√] EncodingFilter（UTF-8）
- [√] README：启动 / 重置数据库 / 常见坑

> v0.1 结束标准：  
> - 数据库可一键重置  
> - 能通过页面或 Servlet 连库查询  
> - 新环境 5 分钟跑起来  

---

## v0.2 用户认证（前台最小闭环）

- [√] UserDAO（findByUsername / insert / findById）
- [√] PasswordUtil（盐 + MD5）
- [√] UserService（register / login）
- [√] register.jsp（错误回显）
- [√] login.jsp（错误回显）
- [√] RegisterServlet
- [√] LoginServlet（session：userId / username / role）
- [√] LogoutServlet
- [√] LoginFilter（拦 /cart/*）
- [√] AdminFilter（拦 /admin/*）

> v0.2 结束标准：  
> - 能注册 → 登录 → 退出  
> - Session 正常  
> - 普通用户进不了 /admin  

---

## v0.3 商品浏览（不分页版）

- [√] CategoryDAO.listAll
- [√] BookDAO.findById
- [√] BookDAO.listByCategory
- [√] BookListServlet（按分类）
- [√] BookDetailServlet
- [ ] 首页 JSP（分类 + 图书列表）
- [ ] 详情页 JSP

> v0.3 结束标准：  
> - 不登录也能浏览分类和图书  
> - 详情页数据正确  

---

## v0.4 购物车（Session 版）

- [ ] CartItem（Book + quantity）
- [ ] CartService（Map<bookId, CartItem>）
- [ ] 加入购物车（数量累加）
- [ ] 购物车列表 Servlet
- [ ] cart.jsp（展示 / 改数量 / 删除）
- [ ] 库存校验（quantity <= stock）

> v0.4 结束标准：  
> - 登录后可加入购物车  
> - 数量、总价计算正确  

---
v0.5 后台管理（最小可控 CRUD）
后台基础

 admin/index.jsp（后台首页 + 导航）

链接入口：分类管理 / 图书管理 / 用户管理

依赖：AdminFilter（已完成）

分类管理（Category）

 CategoryDAO.listAll（后台复用）

 AdminCategoryListServlet（/admin/categories）

 admin/category_list.jsp（分类列表）

 CategoryDAO.insert

 AdminCategoryAddServlet（GET 表单 / POST 提交）

 admin/category_add.jsp（新增分类）

 CategoryDAO.deleteById

 AdminCategoryDeleteServlet（/admin/category/delete）

行为：若分类下存在图书 → 提示“不可删除”

（可选但推荐）

 CategoryDAO.update

 AdminCategoryEditServlet

 admin/category_edit.jsp（编辑分类）

图书管理（Book）

 BookDAO.listAll（后台列表用）

 AdminBookListServlet（/admin/books）

 admin/book_list.jsp（图书列表）

 BookDAO.insert

 AdminBookAddServlet（GET 表单 / POST 提交）

 admin/book_add.jsp（新增图书，分类下拉）

 BookDAO.update

 AdminBookEditServlet

 admin/book_edit.jsp（编辑图书）

 BookDAO.deleteById

 AdminBookDeleteServlet（/admin/book/delete）

用户管理（User）

 UserDAO.listAll（不返回密码字段）

 AdminUserListServlet（/admin/users）

 admin/user_list.jsp（用户列表）

 UserDAO.updateStatus

 AdminUserDisableServlet（/admin/user/disable）

行为：status=0 禁用用户，禁止其登录
---

## v1.0 提交与展示（只做必要项）

- [ ] 项目说明文档（功能说明 + 技术说明）
- [ ] 功能截图（前台 + 后台）
- [ ] 演示视频（完整流程）
- [ ] 最终提交包整理

> v1.0 结束标准：  
> - 老师按文档和视频能完整看到系统运行  
> - 不需要读源码也能理解功能  

---

## 明确不做（已确认）
- 不做分页
- 不做软删除
- 不做连接池
- 不做 ORM / 自动映射
- 不做并发库存控制
- 不做 GitHub Release
- 不写自动化测试
