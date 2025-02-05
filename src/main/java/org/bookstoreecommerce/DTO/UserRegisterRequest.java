package org.bookstoreecommerce.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
    private String name;
    private String username;
    private String email;
    private String password;
    private String phoneNo;
}
