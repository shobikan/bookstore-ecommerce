package org.bookstoreecommerce.controller;

import org.bookstoreecommerce.DTO.UserDTO;
import org.bookstoreecommerce.DTO.UserUpdateRequest;
import org.bookstoreecommerce.entity.Address;
import org.bookstoreecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addAddress/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addAddress(@PathVariable("userId") Long userId, @RequestBody Address address) {
        return ResponseEntity.ok(userService.addAddressForUser(userId, address));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateRequest));
    }

    @PutMapping("/updateAddress/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateAddress(@PathVariable("userId") Long userId, @RequestBody Address address) {
        return ResponseEntity.ok(userService.updateAddressForUser(userId, address));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

}
