CREATE DATABASE IF NOT EXISTS delivery_app;
USE delivery_app;

-- users 테이블
CREATE TABLE users (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       role ENUM('USER', 'OWNER') NOT NULL,
       nickname VARCHAR(100) NOT NULL,
       wallet_id BIGINT UNIQUE, -- 사용자 지갑 ID
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       deleted_at TIMESTAMP NULL
);

-- wallets 테이블 (사용자 지갑)
CREATE TABLE wallets (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         user_id BIGINT NOT NULL UNIQUE,  -- 한 유저당 하나의 지갑만 존재
         balance DECIMAL(10,2) NOT NULL DEFAULT 0.0,
         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
         deleted_at TIMESTAMP NULL,
         FOREIGN KEY (user_id) REFERENCES users(id) 
);

-- stores 테이블 (가게 정보)
CREATE TABLE stores (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT NOT NULL,  -- 가게 소유자 ID
        name VARCHAR(255) NOT NULL,
        category VARCHAR(100) NOT NULL,
        status ENUM('OPEN', 'CLOSED', 'DELETED') NOT NULL DEFAULT 'CLOSED',
        open_time TIME NOT NULL,
        close_time TIME NOT NULL,
        min_order_amount DECIMAL(10,2) NOT NULL DEFAULT 0.0,
        delivery_fee DECIMAL(10,2) NOT NULL DEFAULT 0.0,
        average_rating DECIMAL(3,2) DEFAULT 0.0,  -- 소수점 2자리까지 허용
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        deleted_at TIMESTAMP NULL,
        FOREIGN KEY (user_id) REFERENCES users(id) 
);

-- menus 테이블 (가게 메뉴)
CREATE TABLE menus (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       store_id BIGINT NOT NULL,
       name VARCHAR(255) NOT NULL,
       price DECIMAL(10,2) NOT NULL,
       description TEXT NULL,
       status ENUM('AVAILABLE', 'SOLD_OUT', 'DELETED') NOT NULL DEFAULT 'AVAILABLE',
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       deleted_at TIMESTAMP NULL,
       FOREIGN KEY (store_id) REFERENCES stores(id) 
);

-- orders 테이블 (주문)
CREATE TABLE orders (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT NOT NULL,
        store_id BIGINT NOT NULL,
        total_price DECIMAL(10,2) NOT NULL,
        status ENUM('REQUESTED', 'ACCEPTED', 'COOKING', 'DELIVERING', 'COMPLETED', 'CANCELED') NOT NULL DEFAULT 'REQUESTED',
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        deleted_at TIMESTAMP NULL,
        FOREIGN KEY (user_id) REFERENCES users(id) ,
        FOREIGN KEY (store_id) REFERENCES stores(id) 
);

-- order_items 테이블 (주문 상세 정보)
CREATE TABLE order_items (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         order_id BIGINT NOT NULL,
         menu_id BIGINT NOT NULL,
         quantity INT NOT NULL CHECK (quantity > 0),
         price DECIMAL(10,2) NOT NULL,  -- 주문 당시 가격 저장
         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
         deleted_at TIMESTAMP NULL,
         FOREIGN KEY (order_id) REFERENCES orders(id) ,
         FOREIGN KEY (menu_id) REFERENCES menus(id) 
);
