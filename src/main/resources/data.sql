-- USER 데이터 삽입
INSERT INTO employee (name, email, password, authority, deleted_yn)
VALUES ('김동욱', 'dongwook@test.com', 'wook123', 'MANAGER', FALSE);
INSERT INTO employee (name, email, password, authority, deleted_yn)
VALUES ('장명훈', 'myeonghoon@test.com', 'hoon123', 'STAFF', FALSE);

-- CUSTOM 데이터 삽입
INSERT INTO customer (name, customer_group, phone, address, employee_id, memo, gender, birth)
VALUES ('권성지', 'VVIP', '010-1234-4321', 'seongji123@test.com', 1, '', 'Male', '1999-01-29');
INSERT INTO customer (name, customer_group, phone, address, employee_id, memo, gender, birth)
VALUES ('하지민', '신규', '010-4321-4321', 'jimin123@test.com', 1, '', 'Female', '1998-01-01');
INSERT INTO customer (name, customer_group, phone, address, employee_id, memo, gender, birth)
VALUES ('강찬미', '기존', '010-4132-4132', 'chanmi123@test.com', 1, '', 'Female', '2002-01-02');
INSERT INTO customer (name, customer_group, phone, address, employee_id, memo, gender, birth)
VALUES ('김동윽', 'VIP', '010-9876-9876', 'dongwwwwww@test.com', 2, '', 'Male', '1995-04-10');
INSERT INTO customer (name, customer_group, phone, address, employee_id, memo, gender, birth)
VALUES ('장띵훈', '기존', '010-3289-8903', 'thinghoon123@test.com', 2, '', 'Male', '1998-04-21');
INSERT INTO customer (name, customer_group, phone, address, employee_id, memo, gender, birth)
VALUES ('풀도젠깃갓리찬미', '기존', '010-3428-1342', 'fulli123@test.com', 2, '', 'Female', '2002-10-30');
