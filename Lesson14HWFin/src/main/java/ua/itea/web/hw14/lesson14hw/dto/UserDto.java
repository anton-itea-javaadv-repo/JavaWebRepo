package ua.itea.web.hw14.lesson14hw.dto;

import java.util.Objects;

public class UserDto {
    String name;
    String login;
    String password;
    String passwordRepeat;
    String gender;
    String region;
    String comment;
    String browser;

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public UserDto setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserDto setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public UserDto setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public UserDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getBrowser() {
        return browser;
    }

    public UserDto setBrowser(String browser) {
        this.browser = browser;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return Objects.equals(name, user.name) && Objects.equals(login, user.login)
                && Objects.equals(password, user.password) && Objects.equals(passwordRepeat, user.passwordRepeat)
                && Objects.equals(gender, user.gender) && Objects.equals(region, user.region)
                && Objects.equals(comment, user.comment) && Objects.equals(browser, user.browser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, password, passwordRepeat, gender, region, comment, browser);
    }
}
