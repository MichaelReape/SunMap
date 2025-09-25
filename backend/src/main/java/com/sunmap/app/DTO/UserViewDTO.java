package com.sunmap.app.DTO;

import java.time.LocalDateTime;

public class UserViewDTO {
    private long id;
    private String email;
    private LocalDateTime createdAt;

    public UserViewDTO() {
    }

    public UserViewDTO(long id, String email, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
    }

    // getters
    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
