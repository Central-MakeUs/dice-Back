package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.announcement.dto.AnnouncementFilterRequest;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
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
import static com.cmc.dice.domain.like.domain.QLikeSpace.likeSpace;


@Repository
@RequiredArgsConstructor
@Slf4j
public class SpaceRepositoryImpl implements SpaceRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<SpaceSimpleInfoDto> findSpaces(SpaceFilterDto filter, User user, Pageable pageable) {
		// 기본 쿼리 작성
		var query = queryFactory
				.select(Projections.constructor(SpaceSimpleInfoDto.class,
						space,
						user != null ? likeSpace.id.isNotNull() : Expressions.constant(false) // isLiked 여부
				))
				.from(space);

		if (filter != null) {
				query.where(
					getMaxCapacity(filter.getMaxCapacity()), // 수용 인원 조건
					getPricePerDayBetween(filter.getMinPrice(), filter.getMaxPrice()), // 가격 조건
					getCitiesAndDistrictsBooleanExpression(filter) // 도시, 구 조건
			);

			// 정렬 옵션 추가
			if ("likeCount".equals(filter.getSortBy())) {
				query.orderBy(space.likeCount.desc());
			} else if ("latest".equals(filter.getSortBy())) {
				query.orderBy(space.createdAt.desc());
			} else if ("price".equals(filter.getSortBy())) {
				query.orderBy(space.pricePerDay.asc());
			}
		} else {
			query.orderBy(space.createdAt.desc());
		}

		if (user != null) {
			query.leftJoin(likeSpace)
					.on(likeSpace.space.id.eq(space.id)
							.and(likeSpace.user.id.eq(user.getId())));
		}

		// 페이지네이션 추가
		query.offset(pageable.getOffset())
				.limit(pageable.getPageSize());

		// 데이터 페치
		List<SpaceSimpleInfoDto> content = query.fetch();

		return new PageImpl<>(content, pageable, query.fetchCount());
	}

	private static BooleanExpression getPricePerDayBetween(Integer minPrice, Integer maxPrice) {
		if (maxPrice == null) {
			return space.pricePerDay.goe(minPrice);
		}
		return space.pricePerDay.between(minPrice, maxPrice);
	}

	private static BooleanExpression getMaxCapacity(Integer maxCapacity) {
		if (maxCapacity == null) {
			return null;
		}

		return space.capacity.loe(maxCapacity);
	}

	// 도시, 구를 받아 공간을 조회하는 메서드. 만약 구가 null 로 들어온다면 도시 조건만 체크
	private BooleanExpression getCitiesAndDistrictsBooleanExpression(SpaceFilterDto filter) {
		String city = filter.getCity();
		String district = filter.getDistrict();

		if (district == null) {
			return space.city.eq(city);
		}

		return space.city.eq(city).and(space.district.eq(district));
	}


	private BooleanTemplate getContainsBooleanExpression(Double latitude, Double longitude, Integer radius) {
		String target = "Point(%f %f)".formatted(latitude, longitude);
		String geoFunction = "ST_CONTAINS(ST_BUFFER(ST_GeomFromText('%s', 4326), {0}), point)";
		String expression = String.format(geoFunction, target);

		return Expressions.booleanTemplate(expression, radius);
	}
}