package com.example.pensionat.Security.Services;

import com.example.pensionat.Models.Bokning;
import jakarta.mail.MessagingException;

public interface CustomerMailService {
    void sendConfirmationMail(Bokning b) throws MessagingException;
}
