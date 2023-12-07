package interfaceAdapters.book;

import useCase.book.BookOutputBoundary;
import useCase.book.BookOutputData;

import java.util.HashMap;
import java.util.Map;

public class BookPresenter implements BookOutputBoundary {

    private final BookViewModel viewModel;

    public BookPresenter(BookViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(BookOutputData outputData) {
        final String openingName = outputData.openingName();
        final Map<String, int[]> openingMoves = new HashMap<>();

        for (String move : outputData.bookMoves().keySet()) {
            final int[] counts = outputData.bookMoves().get(move);
            final int total = counts[0] + counts[1] + counts[2];
            openingMoves.put(move, new int[]{counts[0] * 100 / total, counts[1] * 100 / total, counts[2] * 100 / total});
        }

        viewModel.setState(new BookState(openingName, openingMoves));
        viewModel.firePropertyChanged();
    }

}
