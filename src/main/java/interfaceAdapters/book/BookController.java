package interfaceAdapters.book;

import useCase.book.BookInputBoundary;

import java.io.IOException;

public class BookController {

    private final BookInputBoundary inputBoundary;

    public BookController(BookInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void getBookMoves() throws IOException {
        inputBoundary.getBookMoves();
    }

}
