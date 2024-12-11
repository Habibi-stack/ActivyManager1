package com.examplealan.vixi;

public class Password {
    private int id;
    private String name;
    private String login;
    private String password;
    private String notes;
    Password(int id, String name, String login, String password, String notes) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.notes = notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNotes() {
        return notes;
    }
}
