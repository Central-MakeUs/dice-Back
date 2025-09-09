package com.cmc.dice.domain.announcement.dao;

import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.announcement.domain.AnnouncementStatus;
import com.cmc.dice.domain.announcement.dto.AnnouncementFilterRequest;
import com.cmc.dice.domain.announcement.dto.AnnouncementInfoDto;
import com.cmc.dice.domain.announcement.dto.AnnouncementSimpleInfoDto;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.cmc.dice.domain.announcement.domain.QAnnouncement.announcement;
import static com.cmc.dice.domain.like.domain.QLikeAnnouncement.likeAnnouncement;
import static com.cmc.dice.domain.space.domain.QSpace.space;


@Repository
@RequiredArgsConstructor
@Slf4j
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<AnnouncementSimpleInfoDto> findAnnouncements(AnnouncementFilterRequest request, String keyword, User user, Pageable pageable) {
		// 기본 쿼리 작성
		var query = queryFactory
				.select(Projections.constructor(AnnouncementSimpleInfoDto.class,
						announcement,
						user != null ? likeAnnouncement.id.isNotNull() : Expressions.constant(false) // isLiked 여부
				))
				.from(announcement);

		if (keyword != null) {
			query.where(getKeywordsBooleanExpression(keyword));
		}

		if (request != null) {
			query.where(
					getKeywordsBooleanExpression(keyword), // 키워드 조건
					getCitiesAndDistrictsBooleanExpression(request), // 도시, 구 조건
					getStatus(request),
					getTarget(request)
			);

			// 정렬 옵션 추가
			if ("likeCount".equals(request.getSortBy())) {
				query.orderBy(announcement.likeCount.desc());
			} else if ("latest".equals(request.getSortBy())) {
				query.orderBy(announcement.createdAt.desc());
			} else if ("closing".equals(request.getSortBy())) {
				NumberExpression<Integer> statusOrder = new CaseBuilder()
						.when(announcement.status.eq(AnnouncementStatus.CLOSED)).then(0)
						.otherwise(1);

				query.orderBy(statusOrder.asc(), announcement.createdAt.desc()); // 마감 먼저 + 최신순
			}
		} else {
			query.orderBy(announcement.createdAt.desc());
		}

		if (user != null) {
			query.leftJoin(likeAnnouncement)
					.on(likeAnnouncement.announcement.id.eq(announcement.id)
							.and(likeAnnouncement.user.id.eq(user.getId())));
		}

		// 페이지네이션 추가
		query.offset(pageable.getOffset())
				.limit(pageable.getPageSize());


		// 데이터 페치
		List<AnnouncementSimpleInfoDto> content = query.fetch();

		return new PageImpl<>(content, pageable, query.fetchCount());
	}

	@Override
	public Optional<AnnouncementInfoDto> findAnnouncementDetail(User user, Long id) {
		var query = queryFactory
				.select(Projections.constructor(AnnouncementInfoDto.class,
						announcement,
						user != null
								? JPAExpressions
								.select(Expressions.booleanTemplate("COALESCE({0}, false)", likeAnnouncement.id.isNotNull()))
								.from(likeAnnouncement)
								.where(likeAnnouncement.announcement.id.eq(id)
										.and(likeAnnouncement.user.id.eq(user.getId())))
								: Expressions.constant(false) // 로그인하지 않은 경우 false
				))
				.from(announcement)
				.where(announcement.id.eq(id));

		return Optional.ofNullable(query.fetchOne());
	}

	private static BooleanExpression getKeywordsBooleanExpression(String keyword) {
		if (keyword == null) {
			return null;
		}

		return	announcement.name.contains(keyword)
				.or(announcement.district.contains(keyword));
	}

	private static BooleanExpression getTarget(AnnouncementFilterRequest request) {
		if (request.getTargets() == null) {
			return null;
		}

		return announcement.target.in(request.getTargets());
	}

	private static BooleanExpression getStatus(AnnouncementFilterRequest request) {
		if (request.getStatus() == null) {
			return null;
		}
		return announcement.status.eq(request.getStatus());
	}

	// 도시, 구를 받아 공간을 조회하는 메서드. 만약 구가 null 로 들어온다면 도시 조건만 체크
	private BooleanExpression getCitiesAndDistrictsBooleanExpression(AnnouncementFilterRequest filter) {
		String city = filter.getCity();
		String district = filter.getDistrict();

		if (city == null) {
			return null;
		}

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
