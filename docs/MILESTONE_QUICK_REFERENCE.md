# 里程碑快速参考卡

## 📋 文件清单

已创建以下配置文件，可直接用于GitHub：

| 文件路径 | 用途 |
|---------|------|
| [docs/MILESTONES.md](../docs/MILESTONES.md) | **详细的里程碑规划文档** - 包含6个里程碑的完整功能目标、交付物清单、验收标准 |
| [docs/GITHUB_MILESTONES_GUIDE.md](../docs/GITHUB_MILESTONES_GUIDE.md) | **GitHub里程碑使用指南** - 说明如何在GitHub中创建Milestone、关联Issue、追踪进度 |
| [.github/milestones.json](../.github/milestones.json) | **Milestone配置文件（JSON格式）** - 可用于API导入或参考 |

---

## 🎯 6个里程碑概览

### 核心功能里程碑（5个）

```
┌─────────────────────────────────────────────────────────────┐
│ v0.1: 数据库与基础设施 (1周)                                 │
│ └─ 输出: ER图、建表脚本、4个实体类、DBUtil工具              │
│ └─ 验收: 表创建✓ / 连接池✓ / 实体类映射✓                   │
└─────────────────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────────────────┐
│ v0.2: 用户认证模块 (2周)    [依赖v0.1]                      │
│ └─ 功能: 注册 → 登录 → 退出 → Session管理                   │
│ └─ 输出: UserDAO/Service、LoginFilter、JSP页面             │
│ └─ 验收: 注册✓ / 登录✓ / 密码加密✓ / 拦截✓                │
└─────────────────────────────────────────────────────────────┘
        ↙                               ↘
┌──────────────────────┐    ┌──────────────────────┐
│ v0.3: 商品浏览 (2周) │    │ v0.4: 购物车 (2周)  │
│ [依赖v0.1,v0.2]      │    │ [依赖v0.2,v0.3]     │
├──────────────────────┤    ├──────────────────────┤
│ • 图书列表展示       │    │ • 加入购物车         │
│ • 分类筛选           │    │ • 查看购物车         │
│ • 详情查询           │    │ • 修改数量           │
│ • 分页导航           │    │ • 删除商品           │
│ • BookDAO/Service    │    │ • 总价计算           │
└──────────────────────┘    └──────────────────────┘
        ↓                           ↓
        └───────────┬───────────────┘
                    ↓
┌─────────────────────────────────────────────────────────────┐
│ v0.5: 优化与安全加固 (1周)  [依赖v0.2,v0.3,v0.4]          │
│ └─ 任务: SQL注入防护✓ / XSS防护✓ / 异常处理✓ / 单测✓       │
└─────────────────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────────────────┐
│ v1.0: 文档完善与发布 (1周)  [依赖v0.5]                     │
│ └─ 输出: API文档、部署指南、用户手册、Release标签          │
│ └─ 验收: 文档✓ / 部署成功✓ / 无关键BUG✓                   │
└─────────────────────────────────────────────────────────────┘
```

---

## ✅ 立即在GitHub中创建Milestone

### 方式1：手动创建（5分钟）

1. 打开 [Milestones页面](https://github.com/is-northfish/My_JavaEE_System/milestones)
2. 点击 **New Milestone**
3. 逐个添加6个Milestone，使用下表信息：

| 顺序 | 名称 | 描述摘要 | 周期 | 标签 |
|-----|------|---------|------|------|
| 1 | v0.1 | 数据库、ER图、实体类、DBUtil | 1周 | `database` `infrastructure` |
| 2 | v0.2 | 注册、登录、Session、密码加密 | 2周 | `authentication` `security` |
| 3 | v0.3 | 图书列表、分类、详情、分页 | 2周 | `feature` `product` |
| 4 | v0.4 | 购物车：加、删、改、算总价 | 2周 | `feature` `core` |
| 5 | v0.5 | SQL注入、XSS、异常处理、单测 | 1周 | `optimization` `security` |
| 6 | v1.0 | API文档、部署指南、用户手册 | 1周 | `documentation` `release` |

### 方式2：参考配置（查阅用）

- 详细配置见 [docs/MILESTONES.md](../docs/MILESTONES.md) 的各里程碑小节
- JSON格式配置见 [.github/milestones.json](../.github/milestones.json)
- GitHub使用指南见 [docs/GITHUB_MILESTONES_GUIDE.md](../docs/GITHUB_MILESTONES_GUIDE.md)

---

## 📝 为Milestone创建Issue

以v0.2为例，创建如下Issue：

```
标题: feat(auth): 实现UserDAO和用户认证
Milestone: v0.2
Labels: authentication, feature
分配: [你的用户名]
描述:

## 目标
实现用户注册、登录、退出功能

## 交付物
- [ ] UserDAO (insert, findByUsername, findById)
- [ ] UserService (register, login, validateInput)
- [ ] LoginServlet & RegisterServlet & LogoutServlet
- [ ] LoginFilter (登录状态验证)
- [ ] PasswordUtil (MD5加密)
- [ ] login.jsp & register.jsp

## 完成标准
- 用户可成功注册与登录
- Session正确维持登录状态
- 密码加密存储
- 无SQL注入漏洞
```

---

## 📊 项目周期估算

| 阶段 | 里程碑 | 周期 | 累计 | 完成标志 |
|------|--------|------|------|---------|
| 基础 | v0.1 | 1周 | 1周 | 数据库就绪 |
| 认证 | v0.2 | 2周 | 3周 | 用户登录可用 |
| 功能 | v0.3 | 2周 | 5周 | 图书浏览可用 |
| 功能 | v0.4 | 2周 | 7周 | 购物车可用 |
| 优化 | v0.5 | 1周 | 8周 | 安全加固完成 |
| 发布 | v1.0 | 1周 | 9周 | ✨ 正式版上线 |

---

## 🔄 GitHub Issue与提交规范

### Issue标题格式
```
type(scope): 简短描述

示例:
- feat(auth): 实现用户认证模块
- fix(cart): 修复购物车数量计算错误
- docs(readme): 更新部署指南
```

### 提交消息格式
```bash
# 关联Issue
git commit -m "feat(auth): 实现LoginServlet和密码加密 (#5)"

# 关键提交
git commit -m "feat(database): 初始化数据库表结构 (v0.1)"

# 修复
git commit -m "fix(security): 防止SQL注入漏洞 (#12)"
```

### Milestone完成检查清单
```
□ 所有关联Issue都已关闭
□ PR都已合并
□ 代码审查通过
□ 测试覆盖≥60%
□ CHANGELOG已更新
□ 创建Release标签
```

---

## 🎓 实施建议

### 第1步：准备阶段（完成 ✓）
- ✅ MILESTONES.md已生成
- ✅ GitHub使用指南已提供
- ✅ 配置文件已创建

### 第2步：在GitHub中创建Milestone
- 按上表手动添加6个Milestone
- 或参考JSON配置用API创建

### 第3步：为各Milestone创建Issue
- 每个交付物创建一个Issue
- 关联到对应的Milestone
- 添加合适的标签

### 第4步：日常开发流程
```bash
# 1. 创建分支
git checkout -b feature/auth-module

# 2. 完成代码
# ... 编写代码 ...

# 3. 提交时关联Issue
git commit -m "feat(auth): 实现UserDAO (#10)"

# 4. 推送并创建PR
git push origin feature/auth-module

# 5. PR合并后，Issue自动关闭
# 6. Milestone进度自动更新
```

### 第5步：定期检查进度
- 每周查看各Milestone的完成百分比
- 如有阻碍，在Issue中讨论并更新计划
- Milestone完成后创建Release

---

## 📚 关键文档导航

| 文档 | 说明 |
|------|------|
| [MILESTONES.md](../docs/MILESTONES.md) | **完整里程碑规划** - 每个里程碑的功能目标、交付物、验收标准 |
| [GITHUB_MILESTONES_GUIDE.md](../docs/GITHUB_MILESTONES_GUIDE.md) | **GitHub使用指南** - 如何创建/管理Milestone和Issue |
| [AGENTS.md](../AGENTS.md) | 项目目标、技术约束、分层规范 |
| [需求规格说明书](../docs/需求规格说明书.md) | 业务需求详细说明 |
| [产品说明](../docs/产品说明.md) | 产品功能与用户场景 |

---

## 🚀 快速开始

1. **打开MILESTONES.md**
   ```bash
   cat docs/MILESTONES.md
   ```

2. **查看使用指南**
   ```bash
   cat docs/GITHUB_MILESTONES_GUIDE.md
   ```

3. **在GitHub中创建Milestone**
   - 访问 https://github.com/is-northfish/My_JavaEE_System/milestones
   - 逐个添加6个Milestone

4. **开始创建Issue并关联Milestone**
   - 每个Milestone创建若干Issue
   - 按照提交规范进行开发

---

**祝您开发顺利！** 🎉

