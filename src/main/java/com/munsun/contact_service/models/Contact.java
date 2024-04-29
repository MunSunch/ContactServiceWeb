package com.munsun.contact_service.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Contact {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}