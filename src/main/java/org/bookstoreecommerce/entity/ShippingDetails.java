package org.bookstoreecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ShippingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingDetailsId;
    private String fullName;
    private String phoneNo;
    private String houseNo;
    private String street;
    private String city;
    private String district;
    private String province;
    private Long postalCode;

    @OneToOne(mappedBy = "shippingDetails", fetch = FetchType.EAGER)
    @JsonIgnore
    private Order order;
}
