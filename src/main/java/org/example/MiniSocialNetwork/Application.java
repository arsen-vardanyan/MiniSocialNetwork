package org.example.MiniSocialNetwork;

import lombok.SneakyThrows;
import org.example.MiniSocialNetwork.enums.Gender;
import org.example.MiniSocialNetwork.manager.ArticleManager;
import org.example.MiniSocialNetwork.manager.UserManager;
import org.example.MiniSocialNetwork.models.Article;
import org.example.MiniSocialNetwork.models.Comment;
import org.example.MiniSocialNetwork.models.User;
import org.example.MiniSocialNetwork.util.Md5Util;

import java.util.List;
import java.util.Scanner;

public class Application {
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);
    private UserManager userManager = new UserManager();
    private ArticleManager articleManager = new ArticleManager();


    public void start() {
        boolean normalCommand = false;
        while (!normalCommand) {
            Command.home();
            String command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    normalCommand = true;
                    login();
                }
                case "2" -> {
                    normalCommand = true;
                    registration();
                }
                default -> System.err.println("Pleas press only 1 or 2");
            }
        }

    }

    @SneakyThrows
    private void registration() {
        System.out.println("Registration process");

        boolean emailCorrect = false;
        String email = "";
        while (!emailCorrect) {
            System.out.println("Press email");
            email = scanner.nextLine();
            emailCorrect = !userManager.existByEmail(email);
            if (!emailCorrect) System.out.printf("User with email = %s already exists%n", email);
        }

        System.out.println("Press name");
        String name = scanner.nextLine();

        System.out.println("Press surname");
        String surname = scanner.nextLine();

        System.out.println("Press password");
        String password = Md5Util.toHash(scanner.nextLine());

        System.out.println("Press age");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println("Press gender");
        Gender gender = Gender.valueOf(scanner.nextLine());

        System.out.println("Press phone number");
        String phoneNumber = scanner.nextLine();

        currentUser = userManager.save(User.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .age(age)
                .gender(gender)
                .build());
        System.out.println("Successfully registered");
        userHome();

    }

    private void login() {

        System.out.println("*** Login process ***");
        System.out.println("Press email");

        String email = scanner.nextLine();

        System.out.println("Press password");
        String password = scanner.nextLine();
        User byEmail = userManager.getByEmail(email);
        if (byEmail != null && Md5Util.match(password, byEmail.getPassword())) {
            currentUser = byEmail;
            userHome();
        } else {
            System.err.println("Wrong email or password");
            start();
        }


    }

    private void userHome() {
        boolean normalCommand = false;
        while (!normalCommand) {
            Command.userHome();
            String command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    normalCommand = true;
                    allArticles();
                }
                case "2" -> {
                    normalCommand = true;
                    userArticles();
                }
                case "3" -> {
                    normalCommand = true;
                    addArticle();
                }
                case "4" -> {
                    normalCommand = true;
                    boolean logOutCommand = false;
                    while (!logOutCommand) {
                        System.out.println("Do you really want out?\nPress 'y' for yes, and 'n' for no");
                        String yesOrNo = scanner.nextLine();
                        switch (yesOrNo) {
                            case "y" -> {
                                logOutCommand = true;
                                System.out.println("Successfully logged out!");
                                logOut();
                            }
                            case "n" -> userHome();
                            default -> System.err.println("Press only 'y' or 'n'");
                        }
                    }
                }
                default -> System.err.println("Press only {1,2,3,4} numbers");
            }
        }
    }

    private void allArticles() { //todo
        List<Article> all = articleManager.all();
        for (Article article : all) {
            System.out.printf("Article: id= %s | title =%s | content = %s | Author_Name = %s | Author_Surname = %s%n", article.getId(), article.getTitle(), article.getContent(),
                    article.getAuthor().getName(), article.getAuthor().getSurname());
            if (!article.getComments().isEmpty()) {
                System.out.println("comments");
                for (Comment comment : article.getComments()) {
                    System.out.println("     * ");
                }
            }

        }
        if (all.size() == 0) {
            System.out.println("Articles page is empty!");
            userHome();
        }
    }

    private void userArticles() { //todo
        List<Article> articlesByAuthor = articleManager.articlesByAuthor(currentUser);
        if (articlesByAuthor.size() == 0) System.out.println("Your articles page is empty!");
        userHome();
    }

    private void addArticle() { // todo

    }

    private void logOut() { //todo
        start();
    }

}