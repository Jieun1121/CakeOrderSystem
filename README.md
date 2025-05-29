## 개발 환경
- Java 17 이상
- Gradle
- MySQL 8.0 (Workbench 추천)
- JDBC Driver for MySQL

## DB 초기 설정 방법
1. MySQL 설치 및 실행 
  * 포트번호 3306(MYSQL 기본 포트) 설정하거나, DatabaseUtil에서 URL을
    자신의 포트번호로로 수정하기
2. 데이터베이스 및 테이블 생성(아래 sql 복사 붙여넣기)

## sql
sql
CREATE DATABASE CakeOrderDB;
USE CakeOrderDB;

-- 고객 테이블 
CREATE TABLE Customer (
    customer_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    address VARCHAR(200)
);

-- 케이크 테이블
CREATE TABLE Cake (
    cake_id VARCHAR(10) PRIMARY KEY,
    is_customizable BOOLEAN NOT NULL,
    name VARCHAR(100),
    flavor VARCHAR(50),
    size ENUM('S', 'M', 'L'),
    price INT
);

-- 커스터마이즈 테이블
CREATE TABLE Customize (
    option_id VARCHAR(10) PRIMARY KEY,
    cake_id VARCHAR(10),
    is_customizable BOOLEAN NOT NULL,
    option_category VARCHAR(50),
    option_detail VARCHAR(100),
    letter VARCHAR(200),
    FOREIGN KEY (cake_id) REFERENCES Cake(cake_id)
);

-- 주문 테이블 
CREATE TABLE CakeOrder (
    order_id VARCHAR(10) PRIMARY KEY,
    customer_id VARCHAR(10),
    cake_id VARCHAR(10),
    option_id VARCHAR(10),
    order_date DATE,
    total_amount INT,
    status ENUM('pending', 'completed', 'cancelled'),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (cake_id) REFERENCES Cake(cake_id),
    FOREIGN KEY (option_id) REFERENCES Customize(option_id)
);


-- 결제 테이블 
CREATE TABLE Payment (
    payment_id VARCHAR(10) PRIMARY KEY,
    order_id VARCHAR(10),
    payment_method ENUM('card', 'cash'),
    payment_date DATE,
    amount_paid INT,
    FOREIGN KEY (order_id) REFERENCES CakeOrder(order_id)
);

CREATE VIEW View_OrderDetail AS
SELECT
  co.order_id, 
  cu.name AS customer_name, 
  c.name AS cake_name, 
  co.option_id AS customize_id, 
  co.total_amount, 
  co.status
FROM CakeOrder co
JOIN Customer cu ON co.customer_id = cu.customer_id
JOIN Cake c ON co.cake_id = c.cake_id;

CREATE VIEW View_CustomerOrderSummary AS
SELECT 
    cu.customer_id,
    cu.name AS customer_name,
    COUNT(co.order_id) AS total_orders,
    SUM(co.total_amount) AS total_spent
FROM Customer cu
JOIN CakeOrder co ON cu.customer_id = co.customer_id
GROUP BY cu.customer_id, cu.name;

CREATE INDEX idx_order_customer ON CakeOrder(customer_id);
CREATE INDEX idx_customize_cake ON Customize(cake_id);
CREATE INDEX idx_payment_order ON Payment(order_id);

-- initdata.sql
-- 1.customer
INSERT INTO Customer (customer_id, name, phone_number, address) VALUES
('CU001', 'Kim Minji', '010-1234-5678', 'Seoul, Korea'),
('CU002', 'Lee Jisoo', '010-2345-6789', 'Busan, Korea'),
('CU003', 'Park Sunghoon', '010-3456-7890', 'Incheon, Korea'),
('CU004', 'Choi Yuna', '010-4567-8901', 'Daegu, Korea'),
('CU005', 'Jung Wooseok', '010-5678-9012', 'Daejeon, Korea'),
('CU006', 'Kang Seulgi', '010-6789-0123', 'Gwangju, Korea'),
('CU007', 'Hong Jinho', '010-7890-1234', 'Suwon, Korea'),
('CU008', 'Yoon Ara', '010-8901-2345', 'Ulsan, Korea'),
('CU009', 'Nam Taehyun', '010-9012-3456', 'Jeju, Korea'),
('CU010', 'Baek Jiyoung', '010-0123-4567', 'Chuncheon, Korea');

-- 2. cake
INSERT INTO Cake (cake_id, is_customizable, name, flavor, size, price) VALUES ('CK001', TRUE, '초코 케이크', 'Chocolate', 'M', 25000),
('CK002', FALSE, '딸기 생크림 케이크', 'Strawberry', 'S', 22000),
('CK003', TRUE, '레드벨벳 케이크', 'Red Velvet', 'L', 32000),
('CK004', FALSE, '고구마 케이크', 'Sweet Potato', 'M', 24000),
('CK005', TRUE, '블루베리 케이크', 'Blueberry', 'L', 27000),
('CK006', TRUE, '망고 요거트 케이크', 'Mango', 'S', 28000),
('CK007', FALSE, '티라미수 케이크', 'Tiramisu', 'M', 30000),
('CK008', TRUE, '말차 케이크', 'Green Tea', 'M', 26000),
('CK009', TRUE, '오레오 크림 케이크', 'Oreo', 'L', 29000),
('CK010', FALSE, '카라멜 케이크', 'Caramel', 'S', 25000),
('CK011', TRUE, '유자 케이크', 'Yuja', 'M', 22000),
('CK012', TRUE, '당근 케이크', 'Carrot', 'L', 24000),
('CK013', FALSE, '바나나 케이크', 'Banana', 'S', 22000),
('CK014', TRUE, '코코넛 케이크', 'Coconut', 'M', 28000),
('CK015', TRUE, '체리 케이크', 'Cherry', 'L', 31000),
('CK016', FALSE, '레몬 케이크', 'Lemon', 'S', 25000),
('CK017', TRUE, '복숭아 케이크', 'Peach', 'M', 22000),
('CK018', FALSE, '호두 케이크', 'Walnut', 'L', 28000),
('CK019', TRUE, '얼그레이 케이크', 'Earl Grey', 'S', 24000),
('CK020', TRUE, '치즈 케이크', 'Cheese', 'M', 20000);

-- 3. cake
INSERT INTO Customize (option_id, cake_id, is_customizable, option_category, option_detail, letter) VALUES
('OPT001', 'CK001', TRUE, 'Topping', 'Bear Decoration', 'HBD'),
('OPT002', 'CK001', TRUE, 'Topping', 'Rabbit Decoration', 'Happy Birthday!'),
('OPT003', 'CK001', TRUE, 'Color', 'Blue', ''), 
('OPT004', 'CK003', TRUE, 'Topping', 'Rainbow Cream', ''),
('OPT005', 'CK003', TRUE, 'Color', 'Purple', ''),
('OPT006', 'CK003', TRUE, 'Topping', 'Tree Decoration', ''),
('OPT007', 'CK003', TRUE, 'Topping', 'Fruit Mix', ''),
('OPT008', 'CK005', TRUE, 'Topping', 'Star Decoration', 'Thank you'),
('OPT009', 'CK005', TRUE, 'Topping', 'Snowflake Decoration', 'I love you'), 
('OPT010', 'CK006', TRUE, 'Topping', 'Puppy Drawing', ''),
('OPT011', 'CK006', TRUE, 'Topping', 'Heart Decoration', ''),
('OPT012', 'CK006', TRUE, 'Topping', 'Grape Slice', ''),
('OPT013', 'CK008', TRUE, 'Topping', 'Flower Decoration', 'Congratulations'), 
('OPT014', 'CK008', TRUE, 'Topping', 'Ribbon Decoration', 'Happy Day!'),
('OPT015', 'CK009', TRUE, 'Color', 'Mint', ''),
('OPT016', 'CK009', TRUE, 'Topping', 'Chocolate Chip', 'HAPPY BIRTHDAY'),
('OPT017', 'CK011', TRUE, 'Color', 'Yellow', ''),
('OPT018', 'CK011', TRUE, 'Topping', 'Citron Slice', 'Happy Birthday'),
('OPT019', 'CK012', TRUE, 'Topping', 'Carrot Drawing', ''),
('OPT020', 'CK012', TRUE, 'Topping', 'Cloud Decoration', 'Congrats on graduation!'),
('OPT021', 'CK014', TRUE, 'Color', 'Green', ''),
('OPT022', 'CK014', TRUE, 'Topping', 'Butterfly Decoration', ''),
('OPT023', 'CK015', TRUE, 'Color', 'Wine', 'Thank you!'),
('OPT024', 'CK015', TRUE, 'Topping', 'Lemon Slice', ''),
('OPT025', 'CK017', TRUE, 'Topping', 'Peach Drawing', ''),
('OPT026', 'CK017', TRUE, 'Color', 'Beige', 'Wish you happiness'),
('OPT027', 'CK019', TRUE, 'Color', 'Rainbow', ''),
('OPT028', 'CK019', TRUE, 'Topping', 'Character Decoration', ''),
('OPT029', 'CK020', TRUE, 'Topping', 'Strawberry Slice', 'Be happy'), 
('OPT030', 'CK020', TRUE, 'Topping', 'Chick Decoration', '');

-- 4. Cakeorder
INSERT INTO CakeOrder (order_id, customer_id, cake_id, option_id, order_date, total_amount, status) VALUES
('OR001', 'CU005', 'CK010', NULL, '2024-05-02', 25000, 'pending'),
('OR002', 'CU002', 'CK016', NULL, '2024-05-03', 25000, 'pending'),
('OR003', 'CU001', 'CK007', NULL, '2024-05-04', 30000, 'cancelled'),
('OR004', 'CU008', 'CK004', NULL, '2024-05-05', 24000, 'completed'),
('OR005', 'CU003', 'CK018', NULL, '2024-05-06', 28000, 'cancelled'),
('OR006', 'CU005', 'CK009', 'OPT016', '2024-05-07', 29000, 'pending'),
('OR007', 'CU009', 'CK018', NULL, '2024-05-08', 28000, 'completed'),
('OR008', 'CU009', 'CK008', 'OPT013', '2024-05-09', 26000, 'cancelled'),
('OR009', 'CU008', 'CK011', 'OPT018', '2024-05-10', 22000, 'completed'),
('OR010', 'CU007', 'CK005', 'OPT009', '2024-05-11', 27000, 'cancelled'),
('OR011', 'CU001', 'CK007', NULL, '2024-05-12', 30000, 'completed'),
('OR012', 'CU002', 'CK008', NULL, '2024-05-13', 26000, 'cancelled'),
('OR013', 'CU003', 'CK007', NULL, '2024-05-14', 30000, 'cancelled'),
('OR014', 'CU007', 'CK019', NULL, '2024-05-15', 24000, 'cancelled'),
('OR015', 'CU010', 'CK011', 'OPT017', '2024-05-16', 22000, 'pending'),
('OR016', 'CU005', 'CK010', NULL, '2024-05-17', 25000, 'completed'),
('OR017', 'CU008', 'CK012', 'OPT020', '2024-05-18', 24000, 'cancelled'),
('OR018', 'CU008', 'CK016', NULL, '2024-05-19', 25000, 'completed'),
('OR019', 'CU006', 'CK016', NULL, '2024-05-20', 25000, 'pending'),
('OR020', 'CU003', 'CK007', NULL, '2024-05-21', 30000, 'completed'),
('OR021', 'CU004', 'CK009', 'OPT015', '2024-05-22', 29000, 'completed'),
('OR022', 'CU002', 'CK001', 'OPT001', '2024-05-23', 25000, 'completed'),
('OR023', 'CU006', 'CK010', NULL, '2024-05-24', 25000, 'completed');
 
 -- 5. Payment
 INSERT INTO Payment (payment_id, order_id, payment_method, payment_date, amount_paid) VALUES
('PM001', 'OR004', 'cash', '2024-05-05', 24000),
('PM002', 'OR007', 'card', '2024-05-08', 28000),
('PM003', 'OR009', 'card', '2024-05-10', 22000),
('PM004', 'OR011', 'cash', '2024-05-12', 30000),
('PM005', 'OR016', 'cash', '2024-05-17', 25000),
('PM006', 'OR018', 'cash', '2024-05-19', 25000),
('PM007', 'OR020', 'card', '2024-05-21', 30000),
('PM008', 'OR021', 'cash', '2024-05-22', 29000),
('PM009', 'OR022', 'card', '2024-05-23', 25000),
('PM010', 'OR023', 'cash', '2024-05-24', 25000);

## 실행
.\gradlew build
cd .\build\classes\java\main\
java cakeapp.Main