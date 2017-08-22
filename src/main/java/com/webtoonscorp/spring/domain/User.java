package com.webtoonscorp.spring.domain;

import com.webtoonscorp.spring.type.Level;

public class User {

    private String id;
    private String name;
    private String password;
    private String email;

    private Level level;
    private int login;
    private int recommend;

    public User() {

    }

    public User(String id, String name, String password, String email, Level level, int login, int recommend) {

        setId(id);
        setName(name);
        setPassword(password);
        setEmail(email);

        setLevel(level);
        setLogin(login);
        setRecommend(recommend);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public void upgradeLevel() {

        Level next = level.next();

        if (next == null) {
            throw new IllegalStateException("Cannot upgrade : " + level);
        }

        this.level = next;
    }
}
