package com.skillSprint.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillSprint.project.dtos.UserDTO;
import com.skillSprint.project.models.User;
import com.skillSprint.project.models.User.Role;
import com.skillSprint.project.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns the currently authenticated user's profile.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User user) {
        UserDTO dto = UserDTO.fromEntity(user);
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/posts/user/role/{role}")
    public List<UserDTO> getUsersByRole(@PathVariable("role") Role role) {
        return userService.getUsersByRole(role);
    }
}
