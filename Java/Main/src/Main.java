import java.io.*;
import java.util.*;

public class Main {

    static final int TOTAL_COURSE = 100;

    static String[] courseIds = new String[TOTAL_COURSE];
    static String[] courseNames = new String[TOTAL_COURSE];
    static int[] creditHours = new int[TOTAL_COURSE];
    static int numberOfCourses = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        optionsMenu();
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                login();
                break;
            case 2:
                registration();
                break;
            default:
                System.out.println("Invalid input");
        }
    }

    // ===== User Options =====
    static void optionsMenu() {
        System.out.println("\n************* WELCOME TO COURSE MANAGEMENT SYSTEM *************\n");
        System.out.println("1. Login");
        System.out.println("2. Register");
    }

    // ===== Registration =====
    static void registration() {
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        boolean exist = false;

        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (data[0].equals(studentId)) {
                    exist = true;
                    break;
                }
            }
        } catch (IOException e) {
            // file might not exist yet
        }

        if (exist) {
            System.out.println("This student ID " + studentId + " is already registered.");
        } else {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
                bw.write(studentId + " " + password);
                bw.newLine();
                System.out.println("Registration successful.");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ===== Login =====
    static void login() {
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        boolean success = false;

        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                if (data[0].equals(studentId) && data[1].equals(password)) {
                    success = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (success) {
            System.out.println("\n************** Login successful! Welcome **************");
            courseOptions();
        } else {
            System.out.println("Login failed! Invalid ID or password.");
        }
    }

    // ===== Course Options =====
    static void courseOptions() {
        while (true) {
            System.out.println("\n--- Course Options ---");
            System.out.println("1. Add Course");
            System.out.println("2. Delete Course");
            System.out.println("3. Search Course");
            System.out.println("4. Update Course");
            System.out.println("5. List All Courses");
            System.out.println("6. Logout / Exit");
            System.out.print("Select an Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            courseLoad();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    System.out.print("Enter the course ID: ");
                    String deleteId = scanner.nextLine();
                    deleteCourse(deleteId);
                    break;
                case 3:
                    System.out.print("Enter the course ID: ");
                    String searchId = scanner.nextLine();
                    searchCourse(searchId);
                    break;
                case 4:
                    System.out.print("Enter course ID to update: ");
                    String updateId = scanner.nextLine();
                    updateCourse(updateId);
                    break;
                case 5:
                    listCourses();
                    break;
                case 6:
                    return; // exit course menu
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    // ===== File Handling =====
    static void courseLoad() {
        numberOfCourses = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("course.txt"))) {
            String line;
            while ((line = br.readLine()) != null && numberOfCourses < TOTAL_COURSE) {
                String[] data = line.split(" ");
                courseIds[numberOfCourses] = data[0];
                courseNames[numberOfCourses] = data[1];
                creditHours[numberOfCourses] = Integer.parseInt(data[2]);
                numberOfCourses++;
            }
        } catch (IOException e) {
            // file may not exist initially
        }
    }

    static void saveCourses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("course.txt"))) {
            for (int i = 0; i < numberOfCourses; i++) {
                bw.write(courseIds[i] + " " + courseNames[i] + " " + creditHours[i]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    // ===== Course Management =====
    static void addCourse() {
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter credit hours: ");
        int hours = scanner.nextInt();
        scanner.nextLine();

        boolean exist = false;
        for (int i = 0; i < numberOfCourses; i++) {
            if (courseIds[i].equals(id)) {
                exist = true;
                break;
            }
        }

        if (exist) {
            System.out.println("This course ID already exists.");
        } else {
            courseIds[numberOfCourses] = id;
            courseNames[numberOfCourses] = name;
            creditHours[numberOfCourses] = hours;
            numberOfCourses++;
            saveCourses();
            System.out.println("Course added successfully.");
        }
    }

    static void deleteCourse(String targetId) {
        boolean found = false;
        for (int i = 0; i < numberOfCourses; i++) {
            if (courseIds[i].equals(targetId)) {
                for (int j = i; j < numberOfCourses - 1; j++) {
                    courseIds[j] = courseIds[j + 1];
                    courseNames[j] = courseNames[j + 1];
                    creditHours[j] = creditHours[j + 1];
                }
                numberOfCourses--;
                found = true;
                break;
            }
        }

        if (found) {
            saveCourses();
            System.out.println("Course " + targetId + " deleted successfully.");
        } else {
            System.out.println("Course " + targetId + " not found.");
        }
    }

    static void updateCourse(String targetId) {
        boolean found = false;
        for (int i = 0; i < numberOfCourses; i++) {
            if (courseIds[i].equals(targetId)) {
                System.out.print("Enter new course ID: ");
                String newId = scanner.nextLine();
                System.out.print("Enter new course name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new credit hours: ");
                int newHours = scanner.nextInt();
                scanner.nextLine();

                courseIds[i] = newId;
                courseNames[i] = newName;
                creditHours[i] = newHours;
                found = true;
                break;
            }
        }

        if (found) {
            saveCourses();
            System.out.println("Course updated successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    static void searchCourse(String searchId) {
        boolean found = false;
        for (int i = 0; i < numberOfCourses; i++) {
            if (courseIds[i].equals(searchId)) {
                System.out.println("\n*********** Course Found! ***********");
                System.out.println("ID: " + courseIds[i]);
                System.out.println("Name: " + courseNames[i]);
                System.out.println("Credit Hours: " + creditHours[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Course with ID " + searchId + " not found.");
        }
    }

    static void listCourses() {
        System.out.println("\n*********** All Courses ***********");
        for (int i = 0; i < numberOfCourses; i++) {
            System.out.println("ID: " + courseIds[i]);
            System.out.println("Name: " + courseNames[i]);
            System.out.println("Credit Hours: " + creditHours[i]);
            System.out.println();
        }
}
}