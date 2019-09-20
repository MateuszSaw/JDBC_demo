package com.javagda19.jdbc;

public interface StudentQueries {

    String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `students`(\n" +
            "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "`name` VARCHAR(255) NOT NULL,\n" +
            "`age` INT NOT NULL,\n" +
            "`average` DOUBLE NOT NULL,\n" +
            "`alive` TINYINT NOT NULL\n" +
            ")";

    String INSERT_QUERY =
            "INSERT INTO `students`\n" +
            "(`name`, `age`, `average`, `alive`)\n" +
            "VALUES\n" +
            "(?,?,?,?);";

    String DELETE_QUERY = "DELETE FROM`students`\n" + "WHERE `id` = ?";
    String SELECT_ALL_QUERY = "SELECT * FROM `students`";
    String SELECT_BY_ID_QUERY = "SELECT * FROM `students`" + "WHERE `id`= ?";
}
