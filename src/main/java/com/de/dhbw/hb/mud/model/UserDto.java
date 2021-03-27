package com.de.dhbw.hb.mud.model;


import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity(name="registration_data")
public class UserDto {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    private String name;


    @NotEmpty
    @Email
    private String eMail;

    @NotEmpty
    private String passwordSalt;

    @NotEmpty
    private String passwordHash;

    public UserDto() {
    }

    public UserDto(@NotEmpty String name, @NotEmpty @Email String eMail, @NotEmpty String password) {
        this.name = name;
        this.eMail = eMail;
        this.passwordSalt = "Ti";
        //this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
        this.passwordHash=password;
    }


    public boolean checkPassword(String password) {
        //return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
        return password.equals(passwordHash);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}


