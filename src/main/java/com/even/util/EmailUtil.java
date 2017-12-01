package com.even.util;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * 推送工具类
 */
public class EmailUtil {

    private static final String MY_EMAIL_ACCOUNT = "lyhevenss@163.com";
    private static final String MY_EMAIL_PASSWORD = "zxcvbnm123";
    private static final String MY_EMAIL_SMTP_HOST = "smtp.163.com";

    private Properties mProps;
    private Session mSession;
    private EmailUtil util;

    public EmailUtil getInstance() {
        if (null == util) {
            util = new EmailUtil();
        }
        return util;
    }

    private EmailUtil() {
        // 创建参数配置, 用于连接邮件服务器的参数配置
        mProps = new Properties();
        mProps.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        mProps.setProperty("mail.smtp.host", MY_EMAIL_SMTP_HOST);   // 发件人的邮箱的 SMTP 服务器地址
        mProps.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        //开启 SSL 安全连接
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
//        final String smtpPort = "465";
//        props.setProperty("mail.smtp.port", smtpPort);
//        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // 根据配置创建会话对象, 用于和邮件服务器交互
        mSession = Session.getInstance(mProps);
        mSession.setDebug(false);                                 // 设置为debug模式, 可以查看详细的发送 log
    }

    /**
     * 发送邮件
     *
     * @param receiveMail 收件人邮箱
     * @param title       标题
     * @param content     发送内容
     */
    public void sendEmail(String receiveMail, String title, String content) {
        try {
            // 创建邮件
            MimeMessage message = createMimeMessage(mSession, MY_EMAIL_ACCOUNT, receiveMail, title, content);

            // 根据 Session 获取邮件传输对象
            Transport transport = mSession.getTransport();

            // 连接邮件服务器
            transport.connect(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);

            // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 关闭连接
            transport.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.out.println("发送邮件：NoSuchProviderException 异常");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件：MessagingException 异常");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件：异常");
        }

    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String title, String content) {
        try {
            // 1. 创建一封邮件
            MimeMessage message = new MimeMessage(session);

            // 2. From: 发件人
            message.setFrom(new InternetAddress(sendMail, "一文助行提醒您", "UTF-8"));

            // 3. To: 收件人（可以增加多个收件人、抄送、密送）
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));

            // 4. Subject: 邮件主题
            message.setSubject(title, "UTF-8");

            // 5. Content: 邮件正文（可以使用html标签）
            message.setContent(content, "text/html;charset=UTF-8");

            // 6. 设置发件时间
            message.setSentDate(new Date());

            // 7. 保存设置
            message.saveChanges();

            return message;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("邮件解析异常");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("邮件异常");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件异常");
        }
        return null;
    }
}
