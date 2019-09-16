package com.javagda19.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student {
    private Long Id;
    private String name;
    private int age;
    private Double average;
    private boolean alive;

    public Student(String name, int age, Double average, boolean alive) {
        this.name = name;
        this.age = age;
        this.average = average;
        this.alive = alive;
    }
}
