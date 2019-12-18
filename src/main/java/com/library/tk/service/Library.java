package com.library.tk.service;

import com.library.tk.domain.Book;
import com.library.tk.domain.BookPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Library implements LibraryService {

    private List<BookPosition> books = new ArrayList<>();

    @Override
    public List<BookPosition> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public String add(String title, Integer year, String author) {
        BookPosition bookPosition = new BookPosition(new Book(title, year, author));
        books.add(bookPosition);
        return bookPosition.id;
    }

    @Override
    public void remove(String id) {
        Optional<BookPosition> book = books.stream()
                .filter(bookPosition -> bookPosition.id.equals(id) && bookPosition.isAvailable())
                .findFirst();
        book.ifPresent(books::remove);
    }

    @Override
    public Optional<BookPosition> findBook(String title, Integer year, String author) {
        return books.stream().filter(bookPosition -> bookPosition.isAvailable()
                && bookPosition.book.title.equals(title)
                && bookPosition.book.author.equals(author)
                && bookPosition.book.year.equals(year))
                .findFirst();
    }

    @Override
    public Optional<BookPosition> findBook(String title, String author) {
        return books.stream().filter(bookPosition -> bookPosition.isAvailable()
                && bookPosition.book.title.equals(title)
                && bookPosition.book.author.equals(author))
                .findFirst();
    }

    @Override
    public void borrow(String id, String nameOfBorrower) throws Exception {
        Optional<BookPosition> book = filterBooksById(id).findFirst();
        if (book.isPresent() && book.get().isAvailable()) {
            BookPosition bookPosition = book.get();
            bookPosition.borrow(nameOfBorrower);
        } else if (book.isPresent()) {
            throw new Exception(book.get().nameOfBorrower());
        }
    }

    @Override
    public void returnBook(String id) {
        Optional<BookPosition> book = filterBooksById(id).findFirst();
        book.ifPresent(BookPosition::returnBook);
    }

    private Stream<BookPosition> filterBooksById(String id) {
        return books.stream().filter(bookPosition -> bookPosition.id.equals(id));
    }

    @Override
    public void removeAll() {
        books = new ArrayList<>();
    }

    @Override
    public void listAllBooks() {
        Map<Book, CopiesStatus> booksInTheLibrary = getBooksWithCopiesStatus();
        printBooksInLibrary(booksInTheLibrary);
    }

    private Map<Book, CopiesStatus> getBooksWithCopiesStatus() {
        Map<Book, CopiesStatus> booksInTheLibrary = new HashMap<>();

        books.forEach(bookPosition -> {
            if(booksInTheLibrary.containsKey(bookPosition.book) && bookPosition.isAvailable()) {
                CopiesStatus copiesStatus = booksInTheLibrary.get(bookPosition.book);
                Integer availableCopies = copiesStatus.getAvailableCopies();
                copiesStatus.setAvailableCopies(++availableCopies);

            } else if(booksInTheLibrary.containsKey(bookPosition.book) && !bookPosition.isAvailable()) {
                CopiesStatus copiesStatus = booksInTheLibrary.get(bookPosition.book);
                Integer lentCopies = copiesStatus.getLentCopies();
                copiesStatus.setLentCopies(++lentCopies);

            } else if(!booksInTheLibrary.containsKey(bookPosition.book)) {
                if(bookPosition.isAvailable()) {
                    booksInTheLibrary.put(bookPosition.book, new CopiesStatus(1, 0));
                } else {
                    booksInTheLibrary.put(bookPosition.book, new CopiesStatus(0, 1));
                }
            }
        });
        return booksInTheLibrary;
    }

    private void printBooksInLibrary(Map<Book, CopiesStatus> booksInTheLibrary) {
        System.out.println("Books in the library:");
        booksInTheLibrary.forEach((book, info) ->
                System.out.println(book + ", Available: " + info.getAvailableCopies() + ", Lent: " + info.getLentCopies()));
    }


    @Override
    public void checkBookDetails(String id) {
        filterBooksById(id).forEach(bookPosition -> System.out.println(bookPosition.toString()));
    }

    private class CopiesStatus {

        private Integer availableCopies;
        private Integer lentCopies;

        private CopiesStatus(Integer availableCopies, Integer lentCopies) {
            this.availableCopies = availableCopies;
            this.lentCopies = lentCopies;
        }

        private Integer getAvailableCopies() {
            return availableCopies;
        }

        private Integer getLentCopies() {
            return lentCopies;
        }

        private void setAvailableCopies(Integer availableCopies) {
            this.availableCopies = availableCopies;
        }

        private void setLentCopies(Integer lentCopies) {
            this.lentCopies = lentCopies;
        }

    }
}
