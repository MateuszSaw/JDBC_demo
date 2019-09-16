package com.javagda19.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `students`(\n" +
            "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "`name` VARCHAR(255) NOT NULL,\n" +
            "`age` INT NOT NULL,\n" +
            "`average` DOUBLE NOT NULL,\n" +
            "`alive` TINYINT NOT NULL\n" +
            ")";

    private static final String INSERT_QUERY = "INSERT INTO `students`\n" +
            "(`name`, `age`, `average`, `alive`)\n" +
            "VALUES\n" +
            "(?,?,?,?);";

    private static final String DELETE_QUERY = "DELETE FROM`students`\n" + "WHERE `id` = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM `students`";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM `students`" + "WHERE `id`= ?";


    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "czarymary1234";
    private static final String DB_NAME = "jdbc_students";

    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setPort(Integer.parseInt(DB_PORT));
        dataSource.setUser(DB_USERNAME);
        dataSource.setServerName(DB_HOST);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDatabaseName(DB_NAME);
        try {
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = dataSource.getConnection();
            System.out.println("HUURRA!");


            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }

//            Student student = new Student("Don Mateo", 27, 5.0, true);
//            insertStudent(connection, student);

            Scanner scanner = new Scanner(System.in);
            String command;
            do {
                command = scanner.nextLine();

                if (command.equalsIgnoreCase("insert")) {
                    System.out.println("Name");
                    String name = scanner.nextLine();
                    System.out.println("Age");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.println("Average");
                    double average = Double.parseDouble(scanner.next());
                    System.out.println("Alive");
                    Boolean alive = Boolean.parseBoolean(scanner.nextLine());

                    Student student1 = new Student(name, age, average, alive);
                    insertStudent(connection, student1);
                } else if (command.equalsIgnoreCase("delete")) {
                    System.out.println("enter the student ID you want to delete");
                    Long id = Long.parseLong(scanner.nextLine());

                    deleteStudent(connection, id);

                } else if (command.equalsIgnoreCase("List")) {
                    listAllStudent(connection);

                } else if (command.equalsIgnoreCase("Find by ID")) {
                    System.out.println("Set Id");

                    Long id = Long.parseLong(scanner.nextLine());
                    findById(connection, id);
                }
            } while (!command.equalsIgnoreCase("exit"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void findById(Connection connection, Long searchedId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setLong(1, searchedId);

            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                Student student = new Student();

                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));

                System.out.println(student);
            } else {
                System.out.println("There's no any student");
            }
        }
    }


    private static void listAllStudent(Connection connection) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();

                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));

                studentList.add(student);
            }
        }
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    private static void deleteStudent(Connection connection, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);

            boolean success = statement.execute();
            if (success) {
                System.out.println("Success");
            }
        }

    }


    private static void insertStudent(Connection connection, Student student) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setDouble(3, student.getAverage());
            statement.setBoolean(4, student.isAlive());

            boolean success = statement.execute();

            if (success) {
                System.out.println("SUKCES!");
            }
        }
    }

}
