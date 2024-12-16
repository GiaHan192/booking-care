package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "creat_date")
    @CreatedDate
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roles;

    @Column(name = "is_activate")
    private boolean isActivate = true;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GENDER gender = GENDER.OTHER;

    @Getter
    public enum GENDER {
        MALE("Name"), FEMALE("Nữ"), OTHER("Khác");

        private final String name;

        GENDER(String name) {
            this.name = name;
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActivate;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActivate;
    }

    @Override
    public boolean isEnabled() {
        return isActivate;
    }
}
