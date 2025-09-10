import java.util.*;

public class OnlineExamConsoleApp {
    static Scanner scanner = new Scanner(System.in);

    static class User {
        String username;
        String password;
        String name;
        String email;

        User(String username, String password, String name, String email) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;
        }
    }

    static User user = new User("student", "pass123", "John Doe", "john@example.com");

    static class Question {
        String question;
        String[] options;
        int correctAnswer; // 0-based index

        Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    static Question[] questions = new Question[] {
        new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, 0),
        new Question("Which language is used for web apps?", new String[]{"Python", "JavaScript", "C++", "Java"}, 1),
        new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1)
    };

    static boolean loggedIn = false;

    public static void main(String[] args) {
        while (true) {
            if (!loggedIn) {
                login();
            } else {
                showMenu();
            }
        }
    }

    static void login() {
        System.out.println("=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (username.equals(user.username) && password.equals(user.password)) {
            loggedIn = true;
            System.out.println("Login successful!\n");
        } else {
            System.out.println("Invalid credentials. Try again.\n");
        }
    }

    static void showMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Update Profile");
        System.out.println("2. Take Exam");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                updateProfile();
                break;
            case "2":
                takeExam();
                break;
            case "3":
                logout();
                break;
            default:
                System.out.println("Invalid option.\n");
        }
    }

    static void updateProfile() {
        System.out.println("\n=== Update Profile ===");
        System.out.print("Enter new name (current: " + user.name + "): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            user.name = name;
        }
        System.out.print("Enter new email (current: " + user.email + "): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) {
            user.email = email;
        }
        System.out.print("Enter new password (leave blank to keep current): ");
        String newPassword = scanner.nextLine();
        if (!newPassword.trim().isEmpty()) {
            user.password = newPassword;
        }
        System.out.println("Profile updated!\n");
    }

    static void takeExam() {
        System.out.println("\n=== Exam Started ===");
        System.out.println("You have 2 minutes to complete the exam.");
        Map<Integer, Integer> answers = new HashMap<>();

        long startTime = System.currentTimeMillis();
        long timeLimit = 2 * 60 * 1000; // 2 minutes in milliseconds

        for (int i = 0; i < questions.length; i++) {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed > timeLimit) {
                System.out.println("\nTime is up! Auto-submitting your exam.");
                break;
            }
            Question q = questions[i];
            System.out.println("\nQ" + (i + 1) + ": " + q.question);
            for (int j = 0; j < q.options.length; j++) {
                System.out.println((j + 1) + ". " + q.options[j]);
            }
            System.out.print("Your answer (1-" + q.options.length + ", or 0 to skip): ");

            // Read answer with timeout check
            int answer = -1;
            while (true) {
                String input = scanner.nextLine();
                try {
                    answer = Integer.parseInt(input);
                    if (answer >= 0 && answer <= q.options.length) break;
                } catch (NumberFormatException e) {}
                System.out.print("Invalid input. Enter a number between 0 and " + q.options.length + ": ");
            }
            if (answer != 0) {
                answers.put(i, answer - 1);
            }
        }

        // Calculate score
        int score = 0;
        for (int i = 0; i < questions.length; i++) {
            Integer ans = answers.get(i);
            if (ans != null && ans == questions[i].correctAnswer) {
                score++;
            }
        }
        System.out.println("\nExam finished!");
        System.out.println("Your score: " + score + " out of " + questions.length + "\n");
    }

    static void logout() {
        loggedIn = false;
        System.out.println("Logged out.\n");
    }
}