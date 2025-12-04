package com.cmc.dice.domain.brand.domain;

import com.cmc.dice.domain.brand.dto.CreateBrandRequest;
import com.cmc.dice.domain.space.domain.ImageUrlListConverter;
import com.cmc.dice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User admin;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String logoUrl;

	@Convert(converter = ImageUrlListConverter.class)
	@Column(columnDefinition = "TEXT")
	private List<String> imageUrls;

	@Convert(converter = ImageUrlListConverter.class)
	@Column(columnDefinition = "TEXT")
	private List<String> targetGender;

	@Convert(converter = ImageUrlListConverter.class)
	private List<String> targetAgeGroup;

	public void update(CreateBrandRequest request) {
		this.name = request.getName();
		this.description = request.getDescription();
		this.logoUrl = request.getLogoUrl();
		this.imageUrls = request.getImageUrls();
		this.targetGender = request.getTargetGender();
		this.targetAgeGroup = request.getTargetAgeGroup();
	}
}
