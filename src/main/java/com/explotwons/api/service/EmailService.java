package com.explotwons.api.service;

import com.explotwons.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendRegistrationEmail(User user) {
        Context context = new Context();
        context.setVariable("user", user);

        String htmlContent = templateEngine.process("registration-template", context);

        sendEmail(user.getEmail(), "Welcome to Our Platform!", htmlContent);
    }

    public void sendPasswordResetEmail(User user, String resetToken, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("resetUrl", baseUrl + "/password-reset?token=" + resetToken);

        String htmlContent = templateEngine.process("password-reset-template", context);
        sendEmail(user.getEmail(), "Password Reset Request", htmlContent);
    }

    private void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
