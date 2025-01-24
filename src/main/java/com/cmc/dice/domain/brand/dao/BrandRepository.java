package com.cmc.dice.domain.brand.dao;


import com.cmc.dice.domain.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}