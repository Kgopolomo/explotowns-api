package com.explotwons.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordUpdateRequest {

    @JsonProperty("newPassword")
    private String newPassword = null;

    @JsonProperty("resetToken")
    private String resetToken = null;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
