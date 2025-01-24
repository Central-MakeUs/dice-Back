package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.Space;
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

import static com.cmc.dice.domain.space.domain.QSpace.space;


@Repository
@RequiredArgsConstructor
@Slf4j
public class SpaceRepositoryImpl implements SpaceRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Space> findSpaces(Point location, Integer radius, Integer minCapacity, String sortBy, Pageable pageable) {
		// 기본 쿼리 작성
		var query = queryFactory.selectFrom(space)
				.where(
						space.capacity.goe(minCapacity), // 수용 인원 조건
						getContainsBooleanExpression(location.getX(), location.getY(), radius) // 반경 조건
				);

		// 정렬 옵션 추가
		if ("likeCount".equals(sortBy)) {
			query.orderBy(space.likeCount.desc());
		} else if ("latest".equals(sortBy)) {
			query.orderBy(space.createdAt.desc());
		} else if ("price".equals(sortBy)) {
			query.orderBy(space.pricePerDay.asc());
		}

		// 페이지네이션 추가
		query.offset(pageable.getOffset())
				.limit(pageable.getPageSize());

		// 데이터 페치
		List<Space> content = query.fetch();

		// 전체 데이터 개수
		long total = queryFactory.selectFrom(space)
				.where(
						space.capacity.goe(minCapacity), // 수용 인원 조건
						getContainsBooleanExpression(location.getX(), location.getY(), radius) // 반경 조건
				)
				.fetchCount();

		// Page 객체 생성
		return new PageImpl<>(content, pageable, total);
	}

	private BooleanTemplate getContainsBooleanExpression(Double latitude, Double longitude, Integer radius) {
		String target = "Point(%f %f)".formatted(latitude, longitude);
		String geoFunction = "ST_CONTAINS(ST_BUFFER(ST_GeomFromText('%s', 4326), {0}), point)";
		String expression = String.format(geoFunction, target);

		return Expressions.booleanTemplate(expression, radius);
	}
}