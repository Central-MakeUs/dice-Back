CREATE TABLE spaces (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id BIGINT NOT NULL, -- 공간 소유자 (User 테이블과 연관)
    name VARCHAR(255) NOT NULL, -- 공간 이름
    description VARCHAR(1000) NOT NULL, -- 공간 한줄 소개
    image_urls TEXT NOT NULL, -- 이미지 URL 리스트 (JSON 형식 또는 TEXT로 저장)
    category VARCHAR(50) NOT NULL, -- 공간 카테고리
    opening_time TIME, -- 공간 운영 시작 시간
    closing_time TIME, -- 공간 운영 마감 시간
    capacity INT NOT NULL, -- 수용 인원
    price_per_day INT NOT NULL, -- 1일 대여 비용
    discount_rate INT DEFAULT 0, -- 할인율 (%)
    details TEXT, -- 공간 상세 소개
    location POINT NOT NULL SRID 4326, -- 공간 위치 정보 (위도, 경도)
    city VARCHAR(100), -- 도시
    district VARCHAR(100), -- 구/군
    address VARCHAR(255), -- 상세 주소
    website_url VARCHAR(255), -- 웹사이트 URL
    contact_number VARCHAR(50), -- 연락처
    facility_info TEXT, -- 시설 이용 안내
    notice TEXT, -- 공지사항
    like_count INT DEFAULT 0, -- 좋아요 수
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성일
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정일

    -- 외래 키 설정
    CONSTRAINT fk_spaces_admin_id FOREIGN KEY (admin_id) REFERENCES users (id)
);


INSERT INTO users
(user_role, created_at, id, updated_at, email, name, password, phone)
VALUES
    ('1', '2024-11-07 12:44:27.570979', '1', NULL, 'admin@admin.com', 'admin', '$2a$10$IEewf3yfCKXxRTavDLA48eDBZPAWvza3m6/d6zcsDTZh/kZuZj0KS', '01012341234'),
    ('1', '2024-11-07 13:00:00.000000', '2', NULL, 'user1@example.com', 'user1', '$2a$10$X1XJk2yF8L3xRTavDLA48eDBZPAWvza3m6/d6zcsDTZh/kZuZj0KS', '01011112222'),
    ('1', '2024-11-07 13:10:00.000000', '3', NULL, 'user2@example.com', 'user2', '$2a$10$Y2YJk3yF9L3xRTavDLA48eDBZPAWvza3m6/d6zcsDTZh/kZuZj0KS', '01033334444'),
    ('1', '2024-11-07 13:20:00.000000', '4', NULL, 'user3@example.com', 'user3', '$2a$10$Z3ZJk4yF0L3xRTavDLA48eDBZPAWvza3m6/d6zcsDTZh/kZuZj0KS', '01055556666');


-- 강남구에 위치한 공간 데이터 삽입
INSERT INTO spaces (
    admin_id, name, description, image_urls, category, opening_time, closing_time,
    capacity, price_per_day, discount_rate, details, location, city, district, address,
    website_url, contact_number, facility_info, notice, like_count
)
VALUES
(
    1,
    '강남 모던 갤러리',
    '현대적이고 세련된 갤러리 공간',
    '["https://example.com/gallery1.jpg", "https://example.com/gallery2.jpg"]',
    'GALLERY',
    '10:00:00',
    '18:00:00',
    50,
    150000,
    10,
    '현대 미술 전시를 위한 최적의 공간입니다.',
    ST_GeomFromText('POINT(37.49794 127.02761)', 4326), -- 위도(latitude), 경도(longitude)
    '서울특별시',
    '강남구',
    '강남대로 123',
    'https://modern-gallery.com',
    '02-111-2222',
    '주차 가능, 무료 와이파이 제공',
    '음식물 반입 금지',
    120
),
(
    2,
    '강남 루프탑 카페',
    '야경이 아름다운 루프탑 카페 공간',
    '["https://example.com/rooftop1.jpg", "https://example.com/rooftop2.jpg"]',
    'CAFE',
    '15:00:00',
    '23:00:00',
    30,
    100000,
    5,
    '서울의 야경을 즐길 수 있는 특별한 공간입니다.',
    ST_GeomFromText('POINT(37.50130 127.03567)', 4326),
    '서울특별시',
    '강남구',
    '테헤란로 456',
    'https://rooftop-cafe.com',
    '02-333-4444',
    '주차 공간 없음, 야외 테이블 제공',
    '애완동물 동반 불가',
    80
);

-- 서초구에 위치한 공간 데이터 삽입
INSERT INTO spaces (
    admin_id, name, description, image_urls, category, opening_time, closing_time,
    capacity, price_per_day, discount_rate, details, location, city, district, address,
    website_url, contact_number, facility_info, notice, like_count
)
VALUES
(
    3,
    '서초 예술 스튜디오',
    '아티스트를 위한 창작 공간',
    '["https://example.com/studio1.jpg", "https://example.com/studio2.jpg"]',
    'STUDIO',
    '08:00:00',
    '20:00:00',
    20,
    80000,
    15,
    '창작 활동을 위한 최적의 환경을 제공합니다.',
    ST_GeomFromText('POINT(37.48538 127.01047)', 4326),
    '서울특별시',
    '서초구',
    '서초대로 789',
    'https://art-studio.com',
    '02-555-6666',
    '주차 가능, 공용 키친 제공',
    '흡연 구역은 별도 지정',
    50
),
(
    4,
    '서초 클래식 콘서트홀',
    '음악 공연을 위한 고급스러운 공간',
    '["https://example.com/concerthall1.jpg", "https://example.com/concerthall2.jpg"]',
    'HALL',
    '14:00:00',
    '22:00:00',
    200,
    500000,
    20,
    '최고의 음향 시설을 갖춘 콘서트홀입니다.',
    ST_GeomFromText('POINT(37.49188 127.01594)', 4326),
    '서울특별시',
    '서초구',
    '남부순환로 987',
    'https://concert-hall.com',
    '02-777-8888',
    '장애인 좌석 제공, 음료 반입 가능',
    '음향 테스트는 사전 예약 필수',
    200
);