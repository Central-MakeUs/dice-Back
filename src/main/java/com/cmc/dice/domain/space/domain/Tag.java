package com.cmc.dice.domain.space.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "spaces")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
}