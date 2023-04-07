package ru.kradin.blog.services.interfaces;

public interface EmailService {
    public void sendSimpleMessage(String to, String subject, String text);
}
