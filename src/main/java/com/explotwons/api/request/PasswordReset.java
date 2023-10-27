package com.explotwons.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordReset {

    @JsonProperty("email")
    private String email = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
