package com.company.myweb.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Date;

@Data
public class PostDTO {
    private long id;
    private String title = "";
    private String brief = "";
    private String content = "";
    private Boolean isVisible = false;
    private long readNumber = 0L;
    private String thumbnail;
    private Date createdDate;
}
