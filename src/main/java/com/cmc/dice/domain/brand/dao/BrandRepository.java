package com.cmc.dice.domain.brand.dao;


import aj.org.objectweb.asm.commons.Remapper;
import com.cmc.dice.domain.brand.domain.Brand;
import com.cmc.dice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

	List<Brand> findByAdmin(User user);
}