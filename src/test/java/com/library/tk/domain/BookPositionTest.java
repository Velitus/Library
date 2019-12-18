package com.library.tk.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookPositionTest {

    private static final String TITLE = "Game of Thrones";
    private static final Integer YEAR = 2017;
    private static final String AUTHOR = "George R.R. Martin";
    private static final String NAME_OF_BORROWER = "Andy Kowinski";

    @Test
    public void borrow_shouldIndicateNameOfBorrowerAndAvailability() {
        BookPosition bookPosition = new BookPosition(new Book(TITLE, YEAR, AUTHOR));

        bookPosition.borrow(NAME_OF_BORROWER);

        assertThat(bookPosition.nameOfBorrower()).isEqualTo(NAME_OF_BORROWER);
        assertThat(bookPosition.isAvailable()).isFalse();
    }

    @Test
    public void returnBook_shouldIndicateAvailabilityAndNoPersonWhoBorrowed() {
        BookPosition bookPosition = new BookPosition(new Book(TITLE, YEAR, AUTHOR));

        bookPosition.returnBook();

        assertThat(bookPosition.isAvailable()).isTrue();
        assertThat(bookPosition.nameOfBorrower()).isEmpty();
    }

}