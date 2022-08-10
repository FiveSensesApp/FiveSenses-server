package fivesenses.server.fivesenses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    private static final String FROM_ADDRESS = "hi.mangpo@gmail.com";


    public void lostPw(String userEmail, String randomPw) {

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper helper; // true = multipart

        try {
            helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");

            helper.setFrom(FROM_ADDRESS);
            helper.setTo(userEmail);
            helper.setSubject("[오감] 임시 비밀번호를 발급합니다!");


            Map<String, Object> variables = new HashMap<>();
            variables.put("pw", randomPw);

            Context context = new Context();
            context.setVariables(variables);

            String htmlTemplate = htmlTemplateEngine.process("/mail/lost_pw.html", context);

            helper.setText(htmlTemplate, true);

            FileSystemResource mail_1_pw = new FileSystemResource(new File("src/main/resources/static/images/mail_1_pw.png"));
            helper.addInline("mail_1_pw", mail_1_pw);

            FileSystemResource mail_3 = new FileSystemResource(new File("src/main/resources/static/images/mail_3.png"));
            helper.addInline("mail_3", mail_3);

        } catch (MessagingException e) {
            throw new IllegalStateException("메세지 전송중 예외 발생 : lostpw");
        }

        mailSender.send(mimeMessage);
    }


    public void validateEmail(String userEmail, String randomCode) {

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper helper; // true = multipart

        try {
            helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");

            helper.setFrom(FROM_ADDRESS);
            helper.setTo(userEmail);
            helper.setSubject("[오감] 이메일을 인증해 주세요");


            Map<String, Object> variables = new HashMap<>();
            variables.put("code", randomCode);

            Context context = new Context();
            context.setVariables(variables);

            String htmlTemplate = htmlTemplateEngine.process("/mail/email_code.html", context);

            helper.setText(htmlTemplate, true);

            FileSystemResource mail_1_email = new FileSystemResource(new File("src/main/resources/static/images/mail_1_email.png"));
            helper.addInline("mail_1_email", mail_1_email);

            FileSystemResource mail_3 = new FileSystemResource(new File("src/main/resources/static/images/mail_3.png"));
            helper.addInline("mail_3", mail_3);

        } catch (MessagingException e) {
            throw new IllegalStateException("메세지 전송중 예외 발생 : validateEmail");
        }

        mailSender.send(mimeMessage);
    }
}