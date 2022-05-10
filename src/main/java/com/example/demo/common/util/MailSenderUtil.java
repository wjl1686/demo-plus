package com.example.demo.common.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wujl on 2017/5/8.
 */
@Component
@Slf4j
public class MailSenderUtil {


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 发送简单邮件
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     * @throws Exception
     */
    public void sendSimpleMail(String from, String to, String subject, String text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        sendSimple(message);
    }

    /**
     * 发送带有附件的邮件
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     * @throws Exception
     */
    public void sendAttachmentsMail(String from, String to, String subject, String text) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
//        FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
//        helper.addAttachment("附件-1.jpg", file);
//        helper.addAttachment("附件-2.jpg", file);
        send(mimeMessage);
    }

    /**
     * 发送模板邮件
     *
     * @param from
     * @param to
     * @param subject
     * @param templateLocation
     * @throws Exception
     */
    public void sendTemplateMail(String from, String to, String subject, String templateLocation, Map<String, Object> model) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateLocation);//"RetrievePasswordSendEmail.ftl"
            // 模板中用${XXX}站位，map中key为XXX的value会替换占位符内容。
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(text, true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        send(mimeMessage);
    }

    /**
     * 发送多人模板邮件
     *
     * @param from
     * @param to
     * @param subject
     * @param templateLocation
     * @throws Exception
     */
    public void sendBathTemplateMail(String from, String[] to, String subject, String templateLocation, Map<String, Object> model) throws Exception {

//        MailSSLSocketFactory sf = null;
//        try {
//            sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//        } catch (GeneralSecurityException e1) {
//            e1.printStackTrace();
//        }
//        prop.put("mail.smtp.ssl.enable", "true");
//        prop.put("mail.smtp.ssl.socketFactory", sf);
//        //
//        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
//        session.setDebug(true);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateLocation);//"RetrievePasswordSendEmail.ftl"
            // 模板中用${XXX}站位，map中key为XXX的value会替换占位符内容。
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(text, true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        send(mimeMessage);
    }

    /**
     * 启动线程发送邮件
     *
     * @param message
     */
    private void sendSimple(SimpleMailMessage message) {
        Runnable send = new Runnable() {
            @Override
            public void run() {
                try {
                    mailSender.send(message);
                } catch (MailException e) {
                    log.error("发送邮件出现问题e={}：" + e);
                }
            }
        };
        new Thread(send).start();
    }

    /**
     * 启动线程发送邮件
     *
     * @param mimeMessage
     */
    private void send(MimeMessage mimeMessage) {
        //后续使用线程池
        Runnable send = new Runnable() {
            @Override
            public void run() {
                try {
                    mailSender.send(mimeMessage);
                } catch (MailException e) {
                    log.info("发送邮件出现问题：" + e.getCause());
                }
            }
        };
        new Thread(send).start();
    }

}
