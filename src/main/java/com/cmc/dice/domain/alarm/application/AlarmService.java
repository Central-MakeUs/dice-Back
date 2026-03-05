package com.cmc.dice.domain.alarm.application;

import com.cmc.dice.domain.alarm.dao.AlarmRepository;
import com.cmc.dice.domain.alarm.domain.Alarm;
import com.cmc.dice.domain.alarm.dto.AlarmInfoResponse;
import com.cmc.dice.domain.alarm.exception.AlarmNotFoundException;
import com.cmc.dice.domain.alarm.exception.AlarmUnAuthorizedException;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public List<AlarmInfoResponse> getAlarms(User user) {
        List<Alarm> alarms = alarmRepository.findAllByUserId(user.getId());
        return alarms.stream()
                .map(AlarmInfoResponse::of)
                .toList();
    }

    @Transactional
    public void readAlarm(User user, Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(AlarmNotFoundException::new);
        if (!alarm.getUser().getId().equals(user.getId())) {
            throw new AlarmUnAuthorizedException();
        }
        alarm.read();
    }

    @Transactional
    public void readAllAlarms(User user) {
        List<Alarm> alarms = alarmRepository.findAllByUserIdAndIsReadFalse(user.getId());
        for(Alarm alarm : alarms) {
            if (!alarm.getUser().getId().equals(user.getId())) {
                throw new AlarmUnAuthorizedException();
            }
            alarm.read();
        }
    }

    @Transactional
    public void removeAlarm(User user, Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(AlarmNotFoundException::new);
        if (!alarm.getUser().getId().equals(user.getId())) {
            throw new AlarmUnAuthorizedException();
        }
        alarmRepository.delete(alarm);
    }
}
