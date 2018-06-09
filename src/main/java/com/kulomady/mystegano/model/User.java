package com.kulomady.mystegano.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)

public class User implements Serializable {

    @Id
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String email;
    @NotBlank
    private String secreetMessage;
    private String digitalKeyUrl;



    public User() {}

    public User(@NotBlank String username,
                @NotBlank String password,
                @NotBlank String email,
                @NotBlank String secreetMessage,
                @NotBlank String digitalKeyUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.secreetMessage = secreetMessage;
        this.digitalKeyUrl = digitalKeyUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecreetMessage() {
        return secreetMessage;
    }

    public void setSecreetMessage(String secreetMessage) {
        this.secreetMessage = secreetMessage;
    }

    public String getDigitalKeyUrl() {
        return digitalKeyUrl;
    }

    public void setDigitalKeyUrl(String digitalKeyUrl) {
        this.digitalKeyUrl = digitalKeyUrl;
    }
}
