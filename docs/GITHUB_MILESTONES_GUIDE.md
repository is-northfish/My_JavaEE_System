# GitHub 里程碑配置指南

本文档说明如何在GitHub中创建和使用里程碑来追踪项目进度。

## 快速开始

### 方式一：手动创建（推荐）

1. 进入项目的GitHub页面
2. 点击 **Issues** → **Milestones**（或直接访问 `https://github.com/is-northfish/My_JavaEE_System/milestones`）
3. 点击 **New Milestone**
4. 按照下表填入每个里程碑的信息

### 方式二：批量导入（如果GitHub支持）

可以使用GitHub CLI或第三方脚本导入 `.github/milestones.json`

```bash
# 使用GitHub CLI (需要安装gh工具)
gh api repos/is-northfish/My_JavaEE_System/milestones \
  --input .github/milestones.json
```

## 里程碑配置表

### v0.1 - 数据库与基础设施

| 字段 | 值 |
|------|-----|
| **Title** | v0.1 - 数据库与基础设施 |
| **Description** | 完成数据库设计、初始化脚本、实体类定义和数据库连接工具类实现，为后续功能提供数据层基础。<br><br>**周期**: 1周<br>**验收标准**:<br>- 数据库表结构正确<br>- DBUtil能正常获取连接<br>- 实体类完整<br>- 初始化脚本可重复执行 |
| **Due date** | （留空或2周后日期） |
| **Labels** | `database` `infrastructure` `v0.1` |

### v0.2 - 用户认证模块

| 字段 | 值 |
|------|-----|
| **Title** | v0.2 - 用户认证模块（注册/登录） |
| **Description** | 实现用户注册、登录、退出的全流程，使用Session维持登录态，支持密码安全存储。<br><br>**周期**: 2周<br>**依赖**: v0.1<br>**验收标准**:<br>- 用户能成功注册<br>- 用户能成功登录<br>- Session正确维持<br>- 用户能正确退出 |
| **Due date** | （留空或3周后日期） |
| **Labels** | `authentication` `security` `v0.2` |

### v0.3 - 商品浏览模块

| 字段 | 值 |
|------|-----|
| **Title** | v0.3 - 商品浏览模块 |
| **Description** | 实现图书列表展示、按分类筛选、图书详情查询功能。<br><br>**周期**: 2周<br>**依赖**: v0.1, v0.2<br>**验收标准**:<br>- 图书列表展示完整<br>- 支持分类筛选<br>- 图书详情查询<br>- 分页导航正常 |
| **Due date** | （留空或4周后日期） |
| **Labels** | `feature` `product` `v0.3` |

### v0.4 - 购物车模块

| 字段 | 值 |
|------|-----|
| **Title** | v0.4 - 购物车模块 |
| **Description** | 实现购物车的添加、查看、修改、删除功能，支持实时计算总价。<br><br>**周期**: 2周<br>**依赖**: v0.2, v0.3<br>**验收标准**:<br>- 可添加图书到购物车<br>- 购物车页面展示完整<br>- 可修改数量和删除商品<br>- 总价计算正确 |
| **Due date** | （留空或6周后日期） |
| **Labels** | `feature` `core` `v0.4` |

### v0.5 - 代码优化与安全加固

| 字段 | 值 |
|------|-----|
| **Title** | v0.5 - 代码优化与安全加固 |
| **Description** | 代码优化、安全防护加强、异常处理完善、单元测试编写。<br><br>**周期**: 1周<br>**依赖**: v0.2, v0.3, v0.4<br>**关键任务**:<br>- XSS/SQL注入防护<br>- 异常处理统一<br>- 单元测试覆盖<br>- 代码注释补充 |
| **Due date** | （留空或7周后日期） |
| **Labels** | `optimization` `security` `testing` `v0.5` |

### v1.0 - 正式版发布与文档完善

| 字段 | 值 |
|------|-----|
| **Title** | v1.0 - 正式版发布与文档完善 |
| **Description** | 完成文档编写（部署指南、用户手册、开发指南）、最终测试、v1.0版本发布。<br><br>**周期**: 1周<br>**依赖**: v0.5<br>**验收标准**:<br>- 文档完整且可执行<br>- 部署指南成功验证<br>- 无关键BUG<br>- v1.0 Release已创建 |
| **Due date** | （留空或8周后日期） |
| **Labels** | `documentation` `release` `v1.0` |

## 创建Issue并关联Milestone

1. 点击 **Issues** → **New Issue**
2. 填写Issue信息（以v0.1为例）：

```
标题: feat(database): 创建数据库ER图和初始化脚本
描述:
## 需求
完成数据库表结构设计，包括user, book, category表。

## 交付物
- [ ] ER图文档
- [ ] init.sql初始化脚本
- [ ] 测试数据导入

## 完成标准
- 所有表创建成功
- 测试数据导入无误
- 索引建立完整
```

3. 在右侧栏选择 **Milestone** → 选择对应的 v0.1
4. 点击 **Create issue**

## 在Milestone中追踪进度

创建Milestone后，GitHub会自动计算完成百分比：

```
v0.1 - 数据库与基础设施  [████░░░░░] 40%
- 完成: 4 个Issue
- 待做: 6 个Issue
```

### 更新进度

1. Issue完成后，提交PR并关联Issue（在PR描述中写 `Fixes #123`）
2. PR合并后，Issue自动关闭
3. Milestone进度条自动更新

## 提交与里程碑的关联

在提交信息中引用Issue号，便于追踪：

```bash
# 提交时关联Issue #1（属于v0.1）
git commit -m "feat(database): 实现DBUtil工具类 (#1)"

# 提交时同时关联Issue和Milestone（可选）
git commit -m "feat(auth): 实现用户登录Servlet, Fixes #5 (v0.2)"
```

## Issue标签体系

建议为Issue添加以下标签，便于分类：

| 标签 | 说明 | 颜色 |
|------|------|------|
| `bug` | 代码缺陷 | 🔴 |
| `feature` | 新功能 | 🟢 |
| `documentation` | 文档相关 | 🔵 |
| `enhancement` | 功能改进 | 🟡 |
| `database` | 数据库相关 | 🟣 |
| `security` | 安全相关 | 🔴 |
| `testing` | 测试相关 | 🟡 |
| `v0.1` ~ `v1.0` | 版本标签 | 灰色 |
| `blocked` | 被阻断 | 黑色 |
| `help-wanted` | 需要帮助 | 🟠 |

## 每日站会检查清单

在进行每日或周期性的进度会议时，检查各Milestone：

```bash
# 检查所有open的Issue
gh issue list --state open --milestone "v0.1"

# 检查特定Milestone的完成率
gh milestone list | grep "v0.1"
```

## 常见问题

### Q: 如何移动Issue到不同的Milestone？
A: 在Issue右侧栏点击 **Milestone**，选择新的Milestone即可。

### Q: 如何设置Milestone的截止日期？
A: 在创建或编辑Milestone时，可以设置 **Due date**。GitHub会在截止前后发送提醒。

### Q: 如何查看某个Milestone的详细进度？
A: 点击 Milestone 名称，会显示：
- 完成的Issue数量
- 待做的Issue数量
- 完成百分比
- 所有关联的Issue列表

### Q: Milestone完成后应该做什么？
A: 
1. 验证所有Issue都已关闭
2. 创建GitHub Release（标签为版本号，如 v0.1）
3. 在Release中说明此版本的所有功能和修复
4. 更新 CHANGELOG.md

## 参考资源

- [GitHub Milestones 官方文档](https://docs.github.com/en/issues/using-labels-and-milestones-to-track-work/about-milestones)
- [GitHub Issues 最佳实践](https://guides.github.com/features/issues/)
- [Semantic Commit Messages](https://www.conventionalcommits.org/)

