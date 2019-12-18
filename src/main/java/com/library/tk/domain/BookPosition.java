package com.library.tk.domain;

import java.util.Objects;
import java.util.UUID;

public class BookPosition {

    private static final String EMPTY_STRING = "";

    public final String id;
    public final Book book;
    private boolean available;
    private String nameOfBorrower;

    public BookPosition(Book book) {
        this.book = book;
        this.available = true;
        this.nameOfBorrower = EMPTY_STRING;
        id = UUID.randomUUID().toString();
    }

    public boolean isAvailable() {
        return available;
    }

    public String nameOfBorrower() {
        return nameOfBorrower;
    }

    public void borrow(String nameOfBorrower) {
        this.nameOfBorrower = nameOfBorrower;
        this.available = false;
    }

    public void returnBook() {
        this.nameOfBorrower = EMPTY_STRING;
        this.available = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(book.toString());
        sb.append(", Status: ");
        if(available) {
            sb.append("available");
        } else {
            sb.append("unavailable");
            sb.append(", Lent to: ").append(nameOfBorrower);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookPosition other = (BookPosition) obj;
        return Objects.equals(this.book, other.book)
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.available, other.available)
                && Objects.equals(this.nameOfBorrower, other.nameOfBorrower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.book, this.available, this.nameOfBorrower);
    }

}
