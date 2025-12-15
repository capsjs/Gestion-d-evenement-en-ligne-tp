package com.eventplatform.userservice.user;

import jakarta.validation.constraints.NotBlank;

public class RoleUpdateRequest {

    @NotBlank(message = "Le rôle est obligatoire")
    private String role;

    public RoleUpdateRequest() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
