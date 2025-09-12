package com.cmc.dice.domain.reservation.domain;

import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String eventName;

    private String eventContent;

    private List<String> fileList;

    private String etcRequest;

    private String status;

    private String message;

    public void cancel() {
        this.status = "CANCEL";
    }

    public void decline() {
        this.status = "DECLINE";
    }

    public void accept() {
        this.status = "ACCEPT";
    }
}
