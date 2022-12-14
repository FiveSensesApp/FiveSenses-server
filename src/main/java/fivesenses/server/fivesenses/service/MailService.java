package fivesenses.server.fivesenses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static fivesenses.server.fivesenses.common.FileConvertUtils.convertInputStreamToFile;

@Async
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    private static final String FROM_ADDRESS = "hi.mangpo@gmail.com";

    public void lostPw(String userEmail, String randomPw) {

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");

            helper.setFrom(FROM_ADDRESS);
            helper.setTo(userEmail);
            helper.setSubject("[오감] 임시 비밀번호를 발급합니다!");

            Map<String, Object> variables = new HashMap<>();
            variables.put("pw", randomPw);

            Context context = new Context();
            context.setVariables(variables);

            String htmlTemplate = htmlTemplateEngine.process("mail/lost_pw", context);

            helper.setText(htmlTemplate, true);

            helper.addInline("mail_1_pw", convertInputStreamToFile(
                    new ClassPathResource("static/images/mail_1_pw.png").getInputStream()));
            helper.addInline("mail_3", convertInputStreamToFile(
                    new ClassPathResource("static/images/mail_3.png").getInputStream()));

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("메세지 전송중 예외 발생 : lostpw");
        }

        mailSender.send(mimeMessage);
    }

    public void validateEmail(String userEmail, String randomCode) {

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");

            helper.setFrom(FROM_ADDRESS);
            helper.setTo(userEmail);
            helper.setSubject("[오감] 이메일을 인증해 주세요");

            Map<String, Object> variables = new HashMap<>();
            variables.put("code", randomCode);

            Context context = new Context();
            context.setVariables(variables);

            String htmlTemplate = htmlTemplateEngine.process("mail/email_code", context);

            helper.setText(htmlTemplate, true);

            helper.addInline("mail_1_email", convertInputStreamToFile(
                    new ClassPathResource("static/images/mail_1_email.png").getInputStream()));
            helper.addInline("mail_3", convertInputStreamToFile(
                    new ClassPathResource("static/images/mail_3.png").getInputStream()));

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("메세지 전송중 예외 발생 : validateEmail");
        }

        mailSender.send(mimeMessage);
    }


}