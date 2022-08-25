package org.example.MiniSocialNetwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private int id;
    private String content;
    private int userId;
    private User author;

}