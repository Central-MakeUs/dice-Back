CREATE TABLE spaces (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT NOT NULL,
                        image_urls JSON NOT NULL,
                        category VARCHAR(50) NOT NULL,
                        opening_time TIME,
                        closing_time TIME,
                        price_per_day INT NOT NULL,
                        discount_rate INT DEFAULT 0,
                        details TEXT,
                        location POINT NOT NULL,
                        website_url VARCHAR(255),
                        contact_number VARCHAR(50),
                        facility_info TEXT,
                        notice TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


INSERT INTO users
(user_role, created_at, id, updated_at, email, name, password, phone)
VALUES
    ('1', '2024-11-07 12:44:27.570979', '1', NULL, 'admin@admin.com', 'admin', '$2a$10$IEewf3yfCKXxRTavDLA48eDBZPAWvza3m6/d6zcsDTZh/kZuZj0KS', '01012341234');