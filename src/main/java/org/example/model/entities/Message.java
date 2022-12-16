package org.example.model.entities;

import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class Message {
    String author;
    String text;
}
