package org.example.model.entities;

import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class Like {
    int id;
    int author_id;
    int post_id;
}
