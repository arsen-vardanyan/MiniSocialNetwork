package org.example.MiniSocialNetwork.manager;

import lombok.SneakyThrows;
import org.example.MiniSocialNetwork.models.Article;
import org.example.MiniSocialNetwork.models.Comment;
import org.example.MiniSocialNetwork.models.User;
import org.example.MiniSocialNetwork.provider.DBConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CommentManager {

    Logger logger = Logger.getLogger(CommentManager.class.getName());
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    @SneakyThrows
    public Comment save(Comment comment, Article article) {
        String sql = "insert into comments " +
                "(content, user_id, article_id)" +
                " values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, comment.getContent());
        preparedStatement.setInt(2, comment.getUserId());
        preparedStatement.setInt(3, article.getId());

        int execute = preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        int commentId = generatedKeys.getInt(1);
        logger.info("New comment created: " + comment);
        comment.setId(commentId);
        return comment;
    }


    @SneakyThrows
    public List<Comment> commentsByArticle(Article article) {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT c.*, u.name AS author_name," +
                " u.surname AS author_surname FROM comments c " +
                "INNER JOIN users u  ON c.author_id=u.id where c.article_id = ?");
        statement.setInt(1, article.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String content = resultSet.getString("content");
            int userId = resultSet.getInt("author_id");
            comments.add(Comment.builder()
                    .id(id)
                    .content(content)
                    .userId(userId)
                    .author(User.builder()
                            .id(userId)
                            .name(resultSet.getString("author_name"))
                            .surname(resultSet.getString("author_surname"))
                            .build())
                    .build());
        }
        return comments;
    }

}