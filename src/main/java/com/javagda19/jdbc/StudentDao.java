package com.javagda19.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.javagda19.jdbc.StudentQueries.*;

public class StudentDao {

    private MysqlConnection mysqlConnection;

    public StudentDao() throws SQLException {
        mysqlConnection = new MysqlConnection();
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }
        }
    }

    public void insertStudent(Student student) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
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

    public void deleteStudent(Long StudentId) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
                statement.setLong(1, StudentId);

                boolean success = statement.execute();
                if (success) {
                    System.out.println("Success");
                }
            }

        }
    }

    public Optional<Student> findById(Long searchedId) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
                statement.setLong(1, searchedId);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Student student = loadStudentFromResultSet(resultSet);
                    return Optional.of(student);
                }
            }
        }
        return Optional.empty();
    }

    public List<Student> listAllStudent() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
                ResultSet resultSet = statement.executeQuery();

                loadMultipleStudentsFromResultSet(studentList, resultSet);
            }
        }
        return studentList;
    }

    public List<Student> listStudentWithAgeBeetwen(int from, int to) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
                statement.setInt(1,from);
                statement.setInt(2,to);

                ResultSet resultSet = statement.executeQuery();
                loadMultipleStudentsFromResultSet(studentList, resultSet);
            }
        }
        return studentList;
    }
    public List<Student> ListStudentByName(String name,String surname) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
                statement.setString(1,name);
                statement.setString(2,surname);

                ResultSet resultSet = statement.executeQuery();
                loadMultipleStudentsFromResultSet(studentList, resultSet);
            }
        }
        return studentList;
    }

    private void loadMultipleStudentsFromResultSet(List<Student> studentList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Student student = loadStudentFromResultSet(resultSet);
            studentList.add(student);
        }
    }

    private static Student loadStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getLong(1));
        student.setName(resultSet.getString(2));
        student.setAge(resultSet.getInt(3));
        student.setAverage(resultSet.getDouble(4));
        student.setAlive(resultSet.getBoolean(5));
        return student;
    }
}
