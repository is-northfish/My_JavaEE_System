CREATE DATABASE IF NOT EXISTS webshop
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE webshop;

CREATE TABLE IF NOT EXISTS `user` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(32) NOT NULL,
  salt VARCHAR(32) NOT NULL,
  role VARCHAR(10) NOT NULL DEFAULT 'USER',
  status TINYINT NOT NULL DEFAULT 1,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS book (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_id BIGINT NOT NULL,
  name VARCHAR(200) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  CONSTRAINT fk_book_category
    FOREIGN KEY (category_id)
    REFERENCES category(id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 管理员种子账号（ADMIN）
-- username: admin, password: admin123
-- 盐值: salt123456, password_hash = MD5('admin123' + 'salt123456')
INSERT INTO `user` (username, password_hash, salt, role, status) 
VALUES ('admin', MD5(CONCAT('admin123', 'salt123456')), 'salt123456', 'ADMIN', 1)
ON DUPLICATE KEY UPDATE username=username;

-- 基础测试数据（分类）
INSERT INTO category (id, name) VALUES (1, '编程语言') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (2, '数据库') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (3, '算法与数据结构') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (4, 'Web开发') ON DUPLICATE KEY UPDATE name=name;

-- 基础测试数据（图书）
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(1, 1, 'Java核心技术 卷I', 119.00, 50) ON DUPLICATE KEY UPDATE name=name;
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(2, 1, 'Python编程：从入门到实践', 89.00, 30) ON DUPLICATE KEY UPDATE name=name;
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(3, 2, 'MySQL必知必会', 79.00, 40) ON DUPLICATE KEY UPDATE name=name;
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(4, 2, 'Redis设计与实现', 89.00, 25) ON DUPLICATE KEY UPDATE name=name;
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(5, 3, '算法导论', 128.00, 20) ON DUPLICATE KEY UPDATE name=name;
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(6, 3, '数据结构与算法分析', 69.00, 35) ON DUPLICATE KEY UPDATE name=name;
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(7, 4, 'Spring实战', 99.00, 30) ON DUPLICATE KEY UPDATE name=name;
INSERT INTO book (id, category_id, name, price, stock) VALUES 
(8, 4, 'Vue.js权威指南', 89.00, 28) ON DUPLICATE KEY UPDATE name=name;
