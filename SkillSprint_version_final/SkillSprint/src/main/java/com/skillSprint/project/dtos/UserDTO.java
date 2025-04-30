package com.skillSprint.project.dtos;

import com.skillSprint.project.models.User;

/**
 * Data transfer object for exposing user profile data.
 */
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
            user.getUserId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole().name()
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
