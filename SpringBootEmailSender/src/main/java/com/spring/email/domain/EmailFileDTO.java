package com.spring.email.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailFileDTO {
    private MultipartFile file;
    private String[] toUser;
    private String subject;
    private String message;
}
