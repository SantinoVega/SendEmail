package com.spring.email.controller;

import com.spring.email.domain.EmailDto;
import com.spring.email.domain.EmailFileDTO;
import com.spring.email.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> reciveRequestEmail(@RequestBody EmailDto emailDto){
        System.out.println("Mensaje recibido: " + emailDto);

        emailService.sendEmail(emailDto.getToUser(), emailDto.getSubject(), emailDto.getMessage());

        Map<String,String> response = new HashMap<String,String>();
        response.put("estado","Enviado");
        response.put("message", "Email sent successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendFile")
    public ResponseEntity<?> sendFile(@ModelAttribute EmailFileDTO emailFileDTO){
        try {
            String fileName = emailFileDTO.getFile().getOriginalFilename();
            Path path = Paths.get("src/resources/files/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();
            emailService.sendEmailWithFile(emailFileDTO.getToUser(),emailFileDTO.getSubject(),emailFileDTO.getMessage(),file);

            Map<String,String> response = new HashMap<String,String>();
            response.put("estado","Enviado");
            response.put("message", "Email sent successfully");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            throw new RuntimeException("Error sending email with File " + e);
        }
    }
}
