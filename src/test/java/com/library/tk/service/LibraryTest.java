package com.library.tk.service;

import com.library.tk.domain.Book;
import com.library.tk.domain.BookPosition;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryTest {

    private static final String TITLE_1 = "Learning Spark";
    private static final String TITLE_2 = "Game of Thrones";
    private static final Integer YEAR_2017 = 2017;
    private static final String AUTHOR_1 = "Matei Zaharia";
    private static final String AUTHOR_2 = "George R.R. Martin";
    private static final String BORROWER_1 = "Borrower 1";
    private static final String BORROWER_2 = "Borrower 2";

    private LibraryService libraryService;

    @Before
    public void setUpLibraryService() {
        libraryService = new Library();
        libraryService.removeAll();
    }

    @Test
    public void add_addingTwoSameBooks() {
        libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);
        libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);

        List<BookPosition> allBooks = libraryService.getAllBooks();
        assertThat(allBooks).hasSize(2);
        BookPosition bookPosition1 = allBooks.get(0);
        assertThat(bookPosition1.book).isEqualTo(new Book(TITLE_1, YEAR_2017, AUTHOR_1));
        BookPosition bookPosition2 = allBooks.get(1);
        assertThat(bookPosition2.book).isEqualTo(new Book(TITLE_1, YEAR_2017, AUTHOR_1));
    }

    @Test
    public void add_addingTwoDifferentBooks() {
        libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);
        libraryService.add(TITLE_2, YEAR_2017, AUTHOR_2);

        List<BookPosition> allBooks = libraryService.getAllBooks();
        assertThat(allBooks).hasSize(2);
        BookPosition bookPosition1 = allBooks.get(0);
        assertThat(bookPosition1.book).isEqualTo(new Book(TITLE_1, YEAR_2017, AUTHOR_1));
        BookPosition bookPosition2 = allBooks.get(1);
        assertThat(bookPosition2.book).isEqualTo(new Book(TITLE_2, YEAR_2017, AUTHOR_2));
    }

    @Test
    public void remove_removingWhenBookAvailable() {
        String id = libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);

        libraryService.remove(id);

        List<BookPosition> allBooks = libraryService.getAllBooks();
        assertThat(allBooks).isEmpty();
    }

    @Test
    public void remove_notRemovingWhenBookUnavailable() throws Exception {
        String id = libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);
        libraryService.borrow(id, BORROWER_1);

        libraryService.remove(id);

        List<BookPosition> allBooks = libraryService.getAllBooks();
        assertThat(allBooks).hasSize(1);
    }


    @Test
    public void findBook_byTitleYearAndAuthor_shouldFindBookWhenExist() {
        libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);

        Optional<BookPosition> book = libraryService.findBook(TITLE_1, YEAR_2017, AUTHOR_1);
        assertThat(book).isPresent();
    }

    @Test
    public void findBook_byTitleYearAndAuthor_shouldNotFindBookWhenDoesNotExist() {
        libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);

        Optional<BookPosition> book = libraryService.findBook(TITLE_2, YEAR_2017, AUTHOR_1);
        assertThat(book).isNotPresent();
    }

    @Test
    public void findBook_byTitleAndAuthor_shouldFindBookWhenExist() {
        libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);

        Optional<BookPosition> book = libraryService.findBook(TITLE_1, AUTHOR_1);
        assertThat(book).isPresent();
    }

    @Test
    public void findBook_byTitleAndAuthor_shouldNotFindBookWhenDoesNotExist() {
        libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);

        Optional<BookPosition> book = libraryService.findBook(TITLE_2, AUTHOR_2);
        assertThat(book).isNotPresent();
    }

    @Test
    public void borrow_shouldChangeAvailabilityAndNameOfBorrower() throws Exception {
        String id = libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);

        libraryService.borrow(id, BORROWER_1);

        BookPosition bookPosition = libraryService.getAllBooks().get(0);
        assertThat(bookPosition.isAvailable()).isFalse();
        assertThat(bookPosition.nameOfBorrower()).isEqualTo(BORROWER_1);
    }

    @Test(expected = Exception.class)
    public void borrow_shouldThrowExceptionWithNameWhenUnavailable() throws Exception {
        String id = libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);
        libraryService.borrow(id, BORROWER_1);

        libraryService.borrow(id, BORROWER_2);
    }

    @Test
    public void returnBook_shouldReturnBookIfSuchExisted() throws Exception {
        String id = libraryService.add(TITLE_1, YEAR_2017, AUTHOR_1);
        libraryService.borrow(id, BORROWER_1);

        libraryService.returnBook(id);

        BookPosition bookPosition = libraryService.getAllBooks().get(0);
        assertThat(bookPosition.isAvailable()).isTrue();
        assertThat(bookPosition.nameOfBorrower()).isEmpty();
    }

}