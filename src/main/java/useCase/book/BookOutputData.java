package useCase.book;

import java.util.Map;

public record BookOutputData(String openingName, Map<String, int[]> bookMoves) {}
