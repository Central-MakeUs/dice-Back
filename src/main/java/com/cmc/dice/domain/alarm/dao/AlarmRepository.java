package com.cmc.dice.domain.alarm.dao;

import com.cmc.dice.domain.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByUserId(Long userId);

    List<Alarm> findAllByUserIdAndIsReadFalse(Long userId);
}
