package org.example.model.entities;

import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class Post {
    long id;
    String owner;
    String name;
    String text;
    long likes;
    String isEditable;
}
