package com.explotwons.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {

    @JsonProperty("username")
    private String username = null;

    @JsonProperty("password")
    private String password = null;

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
}
