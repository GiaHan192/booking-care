package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Table(name = "post")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title = "";
    @Column(name = "brief")
    private String brief = "";
    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content = "";
    @Column(name = "is_visible")
    private Boolean isVisible = false;
    @Column(name = "read_number")
    private long readNumber = 0L;
    @Lob
    @Column(name = "thumbnail", columnDefinition = "LONGTEXT")
    private String thumbnail;
    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;
}
