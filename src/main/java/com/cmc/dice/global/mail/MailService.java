package com.cmc.dice.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    private final MailRepository mailRepository;

    private int authNumber;

    //임의의 6자리 양수를 반환합니다.
    public String makeRandomNumber() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString().substring(0, 6);
    }


    //mail을 어디서 보내는지, 어디로 보내는지 , 인증 번호를 html 형식으로 어떻게 보내는지 작성합니다.
    public String joinEmail(String email) {
        String randomNumber = makeRandomNumber();

        mailRepository.findByEmail(email).ifPresentOrElse(mail -> {
            mail.updateCode(randomNumber);
            mailRepository.save(mail);
        }
        , () -> {
            Mail mail = Mail.builder()
                    .email(email)
                    .code(randomNumber)
                    .build();
            mail.preUpdate();
            mailRepository.save(mail);
        });

        String setFrom = "dice.minipop@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = email;
        String title = "[DICE] 이메일 인증번호를 확인해주세요."; // 이메일 제목
        String content =
                "안녕하세요, DICE를 이용해 주셔서 감사합니다." + // 더 자연스럽게 수정
                        "<br><br>" +
                        "아래 인증번호를 입력하여 이메일 인증을 완료해주세요." +
                        "<br><br>" +
                        "<strong>인증번호: " + randomNumber + "</strong>" + // 인증번호 강조
                        "<br><br>" +
                        "인증번호는 <strong>10분 동안</strong>만 유효합니다. 기한 내에 입력해주세요." + // 유효 시간 안내
                        "<br><br>" +
                        "감사합니다.<br>" +
                        "DICE 팀 드림"; // 깔끔한 마무리

        mailSend(setFrom, toMail, title, content);
        return randomNumber;
    }

    //이메일을 전송합니다.
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }


    }

}