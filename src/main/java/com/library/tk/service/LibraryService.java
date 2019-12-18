package com.library.tk.service;

import com.library.tk.domain.BookPosition;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    List<BookPosition> getAllBooks();

    void listAllBooks();

    void checkBookDetails(String id);

    void remove(String id);

    String add(String title, Integer year, String author);

    Optional<BookPosition> findBook(String title, Integer year, String author);

    Optional<BookPosition> findBook(String title, String author);

    void borrow(String id, String name) throws Exception;

    void returnBook(String id);

    void removeAll();
}
