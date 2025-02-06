package org.bookstoreecommerce.DTO;

import lombok.Builder;
import lombok.Data;
import org.bookstoreecommerce.entity.Address;

@Data
@Builder
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private String phoneNo;
    private Address address;
}
