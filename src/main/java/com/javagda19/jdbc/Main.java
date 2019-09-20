package com.javagda19.jdbc;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        StudentDao studentDao;

        try {
            studentDao = new StudentDao();
        } catch (SQLException e) {
            System.err.println("Student dao cannot be created. Mysql error");
            System.err.println("Error" + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            command = scanner.nextLine();
            try {
                if (command.equalsIgnoreCase("insert")) {
                    System.out.println("Name");
                    String name = scanner.nextLine();
                    System.out.println("Age");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.println("Average");
                    double average = Double.parseDouble(scanner.next());
                    System.out.println("Alive");
                    Boolean alive = Boolean.parseBoolean(scanner.nextLine());

                    Student student = new Student(name, age, average, alive);
                    studentDao.insertStudent(student);
                } else if (command.equalsIgnoreCase("delete")) {
                    System.out.println("enter the student ID you want to delete");
                    Long id = Long.parseLong(scanner.nextLine());

                    studentDao.deleteStudent(id);

                } else if (command.equalsIgnoreCase("List")) {
                    studentDao.listAllStudent();

                } else if (command.equalsIgnoreCase("Find by ID")) {
                    System.out.println("Set Id");

                    Long id = Long.parseLong(scanner.nextLine());
                    studentDao.findById(id);
                }
            } catch (SQLException e) {
                System.err.println("Error executing comand" + e.getMessage());
            }
        } while (!command.equalsIgnoreCase("exit"));

    }
}
