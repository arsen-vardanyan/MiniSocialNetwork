package org.example.MiniSocialNetwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private int id;
    private String title;
    private String content;
    private int userId;
    private User author;
    private List<Comment> comments = new ArrayList<>();

}