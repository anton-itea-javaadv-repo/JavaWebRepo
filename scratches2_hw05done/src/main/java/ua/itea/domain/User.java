package ua.itea.domain;

import java.util.Objects;

public class User {
    String name;
    String login;
    String password;
    String gender;
    String region;
    String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(login, user.login)
                && Objects.equals(password, user.password) && Objects.equals(gender, user.gender)
                && Objects.equals(region, user.region) && Objects.equals(comment, user.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, password, gender, region, comment);
    }
}
