package com.de.dhbw.hb.mud.service.registration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendEMail(String mailaddr,String name)throws MailException {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(mailaddr);
        mail.setFrom("hollo.dietrich@gmail.com");
        mail.setSubject("test");
        mail.setText("Hallo "+name+",\ndies ist eine Wunderbare E-Mail.\ngrüße Binäratops");

        javaMailSender.send(mail);
    }

}
