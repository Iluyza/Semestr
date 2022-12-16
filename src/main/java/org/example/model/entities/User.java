package org.example.model.entities;

import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class User {
    int id;
    String username;
    int password;
    String email;
}
