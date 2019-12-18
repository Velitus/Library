package com.library.tk.domain;


import java.util.Objects;

public class Book {

    public final String title;
    public final Integer year;
    public final String author;

    public Book(String title, Integer year, String author) {
        this.title = title;
        this.year = year;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Title: \"" + title + "\", Author: " + author + ", Year: " + year;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        return Objects.equals(this.title, other.title)
                && Objects.equals(this.author, other.author)
                && Objects.equals(this.year, other.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, author);
    }
}
