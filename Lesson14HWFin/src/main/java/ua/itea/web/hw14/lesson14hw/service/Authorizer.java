package ua.itea.web.hw14.lesson14hw.service;

public interface Authorizer {
    String isAuthorized(String login, String password);
}
