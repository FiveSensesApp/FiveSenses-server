package fivesenses.server.fivesenses.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

            String htmlTemplate = htmlTemplateEngine.process("mail/lost_pw", context);

            helper.setText(htmlTemplate, true);





            Resource resource = new ClassPathResource("static/images/mail_1_pw.png");
            InputStream inputStream1 = resource.getInputStream();

            FileSystemResource mail_1_pw = new FileSystemResource(convertInputStreamToFile(inputStream1));
            helper.addInline("mail_1_pw", mail_1_pw);


            Resource resource2 = new ClassPathResource("static/images/mail_1_pw.png");
            InputStream inputStream2 = resource2.getInputStream();

            FileSystemResource mail_3 = new FileSystemResource(convertInputStreamToFile(inputStream2));
            helper.addInline("mail_3", mail_3);


//            FileSystemResource mail_1_pw = new FileSystemResource(new File("src/main/resources/static/images/mail_1_pw.png"));
//            helper.addInline("mail_1_pw", mail_1_pw);
//
//            FileSystemResource mail_3 = new FileSystemResource(new File("src/main/resources/static/images/mail_3.png"));
//            helper.addInline("mail_3", mail_3);

        } catch (MessagingException e) {
            throw new IllegalStateException("메세지 전송중 예외 발생 : lostpw");
        } catch (IOException e) {
            e.printStackTrace();
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

            String htmlTemplate = htmlTemplateEngine.process("mail/email_code", context);

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

    public static File convertInputStreamToFile(InputStream inputStream) throws IOException {

        File tempFile = File.createTempFile(String.valueOf(inputStream.hashCode()), ".tmp");
        tempFile.deleteOnExit();

        copyInputStreamToFile(inputStream, tempFile);

        return tempFile;
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file) {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}