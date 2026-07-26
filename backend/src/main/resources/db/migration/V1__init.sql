-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd.
CREATE TABLE erp_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(32) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(50) NOT NULL,
  role VARCHAR(20) NOT NULL,
  department VARCHAR(50),
  enabled BIT NOT NULL DEFAULT 1,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE erp_product (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sku VARCHAR(40) NOT NULL UNIQUE,
  name VARCHAR(120) NOT NULL,
  category VARCHAR(60) NOT NULL,
  unit VARCHAR(20) NOT NULL,
  cost_price DECIMAL(14,2) NOT NULL,
  sale_price DECIMAL(14,2) NOT NULL,
  stock_on_hand DECIMAL(14,3) NOT NULL DEFAULT 0,
  safety_stock DECIMAL(14,3) NOT NULL DEFAULT 0,
  enabled BIT NOT NULL DEFAULT 1,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_product_category(category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE erp_partner (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(40) NOT NULL UNIQUE,
  name VARCHAR(120) NOT NULL,
  type VARCHAR(20) NOT NULL,
  contact_name VARCHAR(50),
  phone VARCHAR(30),
  address VARCHAR(255),
  credit_limit DECIMAL(14,2) NOT NULL DEFAULT 0,
  status VARCHAR(20) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_partner_type(type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE erp_sales_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(40) NOT NULL UNIQUE,
  customer_id BIGINT NOT NULL,
  order_date DATE NOT NULL,
  item_summary VARCHAR(500) NOT NULL,
  item_count INT NOT NULL,
  total_amount DECIMAL(14,2) NOT NULL,
  paid_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
  status VARCHAR(20) NOT NULL,
  owner_name VARCHAR(50),
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_sales_status(status),
  CONSTRAINT fk_sales_customer FOREIGN KEY (customer_id) REFERENCES erp_partner(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE erp_purchase_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(40) NOT NULL UNIQUE,
  supplier_id BIGINT NOT NULL,
  order_date DATE NOT NULL,
  expected_date DATE,
  item_summary VARCHAR(500) NOT NULL,
  item_count INT NOT NULL,
  total_amount DECIMAL(14,2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  buyer_name VARCHAR(50),
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_purchase_status(status),
  CONSTRAINT fk_purchase_supplier FOREIGN KEY (supplier_id) REFERENCES erp_partner(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE erp_stock_movement (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  movement_no VARCHAR(40) NOT NULL UNIQUE,
  product_id BIGINT NOT NULL,
  type VARCHAR(20) NOT NULL,
  quantity DECIMAL(14,3) NOT NULL,
  before_quantity DECIMAL(14,3) NOT NULL,
  after_quantity DECIMAL(14,3) NOT NULL,
  reference_no VARCHAR(40),
  handled_by VARCHAR(50),
  occurred_at DATETIME(6) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_stock_time(occurred_at),
  CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES erp_product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE erp_finance_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(40) NOT NULL UNIQUE,
  type VARCHAR(20) NOT NULL,
  partner_name VARCHAR(120),
  amount DECIMAL(14,2) NOT NULL,
  settled_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
  due_date DATE,
  status VARCHAR(20) NOT NULL,
  reference_no VARCHAR(40),
  remark VARCHAR(500),
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_finance_type_status(type,status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

