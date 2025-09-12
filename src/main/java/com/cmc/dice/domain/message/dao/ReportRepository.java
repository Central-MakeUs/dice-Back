package com.cmc.dice.domain.message.dao;

import com.cmc.dice.domain.message.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
