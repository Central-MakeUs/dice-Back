package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.dto.*;
import com.cmc.dice.domain.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import static com.cmc.dice.domain.message.domain.QMessageRoom.messageRoom;
import static com.cmc.dice.domain.space.domain.QSpace.space;
import static com.cmc.dice.domain.like.domain.QLikeSpace.likeSpace;
import static com.cmc.dice.domain.space.domain.QSpaceAnalysisPeople.spaceAnalysisPeople;


@Repository
@RequiredArgsConstructor
@Slf4j
public class SpaceRepositoryImpl implements SpaceRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<SpaceSimpleInfoDto> findSpaces(SpaceFilterDto filter, String keyword, User user, Pageable pageable) {
		// 기본 쿼리 작성
		var query = queryFactory
				.select(Projections.constructor(SpaceSimpleInfoDto.class,
						space,
						user != null ? likeSpace.id.isNotNull() : Expressions.constant(false) // isLiked 여부
				))
				.from(space);

		query.where(space.isActivated.isTrue()); // 활성화된 공간만 조회

		if (keyword != null) {
			query.where(getKeywordsBooleanExpression(keyword));
		}

		if (filter != null) {
				query.where(
					getCapacity(filter.getMinCapacity(), filter.getMaxCapacity()), // 수용 인원 조건
					getPricePerDayBetween(filter.getMinPrice(), filter.getMaxPrice()), // 가격 조건
					getCitiesAndDistrictsBooleanExpression(filter) // 도시, 구 조건
			);

			// 정렬 옵션 추가
			if ("likeCount".equals(filter.getSortBy())) {
				query.orderBy(space.likeCount.desc());
			} else if ("latest".equals(filter.getSortBy())) {
				query.orderBy(space.createdAt.desc());
			} else if ("priceAsc".equals(filter.getSortBy())) {
				query.orderBy(space.pricePerDay.asc());
			} else if ("priceDesc".equals(filter.getSortBy())) {
				query.orderBy(space.pricePerDay.desc());
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

	@Override
	public Page<SpaceSimpleInfoDto> findSpacesV2(SpaceFilterDtoV2 filter, String keyword, User user, Pageable pageable) {
		// 기본 쿼리 작성
		var query = queryFactory
				.select(Projections.constructor(SpaceSimpleInfoDto.class,
						space,
						user != null ? likeSpace.id.isNotNull() : Expressions.constant(false) // isLiked 여부
				))
				.from(space);

		query.where(space.isActivated.isTrue()); // 활성화된 공간만 조회

		if (keyword != null) {
			query.where(getKeywordsBooleanExpression(keyword));
		}

		if (filter != null) {
			if (filter.getGender() != null) {
				query.join(space.analysisPeople, spaceAnalysisPeople);
			}
			query.where(
					getSize(filter.getMinSize(), filter.getMaxSize()),
					getPricePerDayBetween(filter.getMinPrice(), filter.getMaxPrice()), // 가격 조건
					getCitiesAndDistrictsBooleanExpressionV2(filter) // 도시, 구 조건
//					getGender(filter.getGender()), // 성별
//					getAgeGroup(filter.getAgeGroup()), // 연령대
//					getDayOfWeek(filter.getDayOfWeek()), // 요일
//					getPurpose(filter.getPurpose())
			);

			// 정렬 옵션 추가
			if ("likeCount".equals(filter.getSortBy())) {
				query.orderBy(space.likeCount.desc());
			} else if ("latest".equals(filter.getSortBy())) {
				query.orderBy(space.createdAt.desc());
			} else if ("priceAsc".equals(filter.getSortBy())) {
				query.orderBy(space.pricePerDay.asc());
			} else if ("priceDesc".equals(filter.getSortBy())) {
				query.orderBy(space.pricePerDay.desc());
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

	@Override
	public Optional<SpaceInfoDto> findSpaceDetail(User user, Long id) {
		var query = queryFactory
				.select(Projections.constructor(SpaceInfoDto.class,
						space,
						user != null
								? JPAExpressions
								.select(Expressions.booleanTemplate("COALESCE({0}, false)", likeSpace.id.isNotNull()))
								.from(likeSpace)
								.where(likeSpace.space.id.eq(id)
										.and(likeSpace.user.id.eq(user.getId())))
								: Expressions.constant(false), // 로그인하지 않은 경우 false
						user != null
								? JPAExpressions
								.select(messageRoom.id.castToNum(Long.class))
								.from(messageRoom)
								.where(messageRoom.space.id.eq(id)
										.and(messageRoom.guest.id.eq(user.getId())))
								: Expressions.nullExpression() // 채팅방 id가 없으면 null 반환
				))
				.from(space)
				.where(space.id.eq(id));

		return Optional.ofNullable(query.fetchOne());
	}

	@Override
	public Optional<SpaceInfoDtoV2> findSpaceDetailV2(User user, Long id) {
		var query = queryFactory
				.select(Projections.constructor(SpaceInfoDtoV2.class,
						space,
						user != null
								? JPAExpressions
								.select(Expressions.booleanTemplate("COALESCE({0}, false)", likeSpace.id.isNotNull()))
								.from(likeSpace)
								.where(likeSpace.space.id.eq(id)
										.and(likeSpace.user.id.eq(user.getId())))
								: Expressions.constant(false),
						user != null
								? JPAExpressions
								.select(messageRoom.id.castToNum(Long.class))
								.from(messageRoom)
								.where(messageRoom.space.id.eq(id)
										.and(messageRoom.guest.id.eq(user.getId())))
								: Expressions.nullExpression()
				))
				.from(space)
				.where(space.id.eq(id));

		return Optional.ofNullable(query.fetchOne());
	}

//	private BooleanExpression getGender(String gender) {
//		if (gender == null || gender.isBlank()) {
//			return null;
//		}
//		return spaceAnalysisPeople.gender.equalsIgnoreCase(gender);
//	}
//
//	private BooleanExpression getAgeGroup(int ageGroup) {
//		// ageGroup이 0 또는 유효하지 않은 값일 경우 필터링하지 않음
//		if (ageGroup <= 0) {
//			return null;
//		}
//		return spaceAnalysisPeople.ageGroup.eq(ageGroup);
//	}
//
//	// SpaceOperatingHours 라는 별도의 운영 요일 엔티티가 있다는 가정 하에 작성
//	private BooleanExpression getDayOfWeek(DayOfWeek dayOfWeek) {
//		if (dayOfWeek == null) {
//			return null;
//		}
//		// 서브쿼리를 사용하여 특정 요일에 운영하는 공간인지 확인
//		return JPAExpressions.selectOne()
//				.from(spaceOperatingHours)
//				.where(spaceOperatingHours.space.id.eq(space.id)
//						.and(spaceOperatingHours.dayOfWeek.eq(dayOfWeek)))
//				.exists();
//	}
//
//	private BooleanExpression getPurpose(String purpose) {
//		if (purpose == null || purpose.isBlank()) {
//			return null;
//		}
//		try {
//			// purpose 문자열을 SpaceCategory Enum으로 변환하여 비교
//			return space.category.eq(SpaceCategory.valueOf(purpose.toUpperCase()));
//		} catch (IllegalArgumentException e) {
//			// 유효하지 않은 purpose 값일 경우 무시
//			return null;
//		}
//	}

	private static BooleanExpression getKeywordsBooleanExpression(String keyword) {
		if (keyword == null) {
			return null;
		}

		return space.name.contains(keyword)
				.or(space.city.contains(keyword))
				.or(space.district.contains(keyword));
	}

	private static BooleanExpression getPricePerDayBetween(Integer minPrice, Integer maxPrice) {
		if (minPrice == null) {
			if (maxPrice == null) {
				return null;
			}
			return space.pricePerDay.loe(maxPrice);
		}
		if (maxPrice == null) {
			return space.pricePerDay.goe(minPrice);
		}
		return space.pricePerDay.between(minPrice, maxPrice);
	}

	private static BooleanExpression getCapacity(Integer minCapacity, Integer maxCapacity) {
		if (minCapacity == null) {
			if (maxCapacity == null) {
				return null;
			}
			return space.capacity.loe(maxCapacity);
		}
		
		if (maxCapacity == null) {
			return space.capacity.goe(minCapacity);
		}

		return space.capacity.between(minCapacity, maxCapacity);
	}

	private static BooleanExpression getSize(Integer minSize, Integer maxSize) {
		if (minSize == null) {
			if (maxSize == null) {
				return null;
			}
			return space.capacity.loe(maxSize);
		}

		if (maxSize == null) {
			return space.capacity.goe(minSize);
		}

		return space.capacity.between(minSize, maxSize);
	}

	// 도시, 구를 받아 공간을 조회하는 메서드. 만약 구가 null 로 들어온다면 도시 조건만 체크
	private BooleanExpression getCitiesAndDistrictsBooleanExpression(SpaceFilterDto filter) {
		String city = filter.getCity();
		String district = filter.getDistrict();

		if (city == null) {
			return null;
		}

		if (district == null) {
			return space.city.eq(city);
		}

		return space.city.eq(city).and(space.district.eq(district));
	}

	// 도시, 구를 받아 공간을 조회하는 메서드. 만약 구가 null 로 들어온다면 도시 조건만 체크
	private BooleanExpression getCitiesAndDistrictsBooleanExpressionV2(SpaceFilterDtoV2 filter) {
		String city = filter.getCity();
		String district = filter.getDistrict();

		if (city == null) {
			return null;
		}

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
