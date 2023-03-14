package com.example.proyectospring.paintings.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "paintings")
public class Painting {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false,  length = 45)
    private String name;
    @Column(nullable = false,  length = 45)
    private String date;
    @Column(nullable = false,  length = 45)
    private String author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}