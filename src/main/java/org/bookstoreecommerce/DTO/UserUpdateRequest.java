package org.bookstoreecommerce.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequest {
    private String name;
    private String username;
    private String phoneNo;
}
