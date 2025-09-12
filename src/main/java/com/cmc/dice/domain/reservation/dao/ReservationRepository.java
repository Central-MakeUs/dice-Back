package com.cmc.dice.domain.reservation.dao;

import com.cmc.dice.domain.reservation.domain.Reservation;
import com.cmc.dice.domain.reservation.dto.DateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
        SELECT COUNT(r) > 0 FROM Reservation r
        WHERE r.space.id = :spaceId
        AND r.startDate <= :endDate
        AND r.endDate >= :startDate
    """)
    boolean existsOverlappingReservation(
            @Param("spaceId") Long spaceId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT new com.cmc.dice.domain.reservation.dto.DateDto(r.startDate, r.endDate)
        FROM Reservation r
        WHERE r.space.id = :spaceId
    """)
    List<DateDto> findReservedDatesBySpaceId(@Param("spaceId") Long spaceId);

    Page<Reservation> findByUserId(Long id, Pageable pageable);

    Page<Reservation> findByUserIdAndStatus(Long id, String status, PageRequest of);

	Page<Reservation> findBySpaceAdminIdAndStatus(Long id, String status, PageRequest of);
}