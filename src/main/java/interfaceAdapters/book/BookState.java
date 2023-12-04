package interfaceAdapters.book;

import java.util.Map;

public record BookState(String openingName, Map<String, int[]> openingMoves) {}
