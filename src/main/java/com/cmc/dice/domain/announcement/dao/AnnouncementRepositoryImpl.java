package com.cmc.dice.domain.announcement.dao;

import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.announcement.dto.AnnouncementFilterRequest;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cmc.dice.domain.announcement.domain.QAnnouncement.announcement;
import static com.cmc.dice.domain.space.domain.QSpace.space;


@Repository
@RequiredArgsConstructor
@Slf4j
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Announcement> findAnnouncements(AnnouncementFilterRequest request, Pageable pageable) {
		// 기본 쿼리 작성
		var query = queryFactory.selectFrom(announcement)
				.where(
						getCitiesAndDistrictsBooleanExpression(request), // 도시, 구 조건
						announcement.status.eq(request.getStatus())
				);

		// 페이지네이션 추가
		query.offset(pageable.getOffset())
				.limit(pageable.getPageSize());


		// 데이터 페치
		List<Announcement> content = query.fetch();

		return new PageImpl<>(content, pageable, query.fetchCount());
	}

	// 도시, 구를 받아 공간을 조회하는 메서드. 만약 구가 null 로 들어온다면 도시 조건만 체크
	private BooleanExpression getCitiesAndDistrictsBooleanExpression(AnnouncementFilterRequest filter) {
		String city = filter.getCity();
		String district = filter.getDistrict();

		if (district == null) {
			return announcement.city.eq(city);
		}

		return announcement.city.eq(city).and(announcement.district.eq(district));
	}


	private BooleanTemplate getContainsBooleanExpression(Double latitude, Double longitude, Integer radius) {
		String target = "Point(%f %f)".formatted(latitude, longitude);
		String geoFunction = "ST_CONTAINS(ST_BUFFER(ST_GeomFromText('%s', 4326), {0}), point)";
		String expression = String.format(geoFunction, target);

		return Expressions.booleanTemplate(expression, radius);
	}
}