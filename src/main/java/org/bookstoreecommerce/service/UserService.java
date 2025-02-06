package org.bookstoreecommerce.service;

import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.UserDTO;
import org.bookstoreecommerce.DTO.UserUpdateRequest;
import org.bookstoreecommerce.entity.Address;
import org.bookstoreecommerce.entity.User;
import org.bookstoreecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO addAddressForUser(Long userId, Address address) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setAddress(address);
        userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO getUserById(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return convertToDTO(user);
    }

    public UserDTO updateUser(Long userId, UserUpdateRequest request) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getUsername() != null) {
            userRepository.findByUsername(request.getUsername())
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("Username already exists");
                    });
            user.setUsername(request.getUsername());
        }
        if (request.getPhoneNo() != null) {
            user.setPhoneNo(request.getPhoneNo());
        }
        userRepository.save(user);

        return convertToDTO(user);
    }

    public UserDTO updateAddressForUser(Long userId, Address address) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Address updatedAddress = updateAddress(user.getAddress(), address);
        user.setAddress(updatedAddress);
        userRepository.save(user);

        return convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public String deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return "User with id " + userId + " deleted successfully";
    }

    public UserDTO getUserByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return convertToDTO(user);
    }



    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNo(user.getPhoneNo())
                .address(user.getAddress())
                .build();
    }

    private Address updateAddress(Address userAddress, Address newAddress) {
        if (newAddress.getHouseNo() != null) {
            userAddress.setHouseNo(newAddress.getHouseNo());
        }
        if (newAddress.getStreet() != null) {
            userAddress.setStreet(newAddress.getStreet());
        }
        if (newAddress.getCity() != null) {
            userAddress.setCity(newAddress.getCity());
        }
        if (newAddress.getDistrict() != null) {
            userAddress.setDistrict(newAddress.getDistrict());
        }
        if (newAddress.getProvince() != null) {
            userAddress.setProvince(newAddress.getProvince());
        }
        if (newAddress.getPostalCode() != null) {
            userAddress.setPostalCode(newAddress.getPostalCode());
        }
        return userAddress;
    }




}
