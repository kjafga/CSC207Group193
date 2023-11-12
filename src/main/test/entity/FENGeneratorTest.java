package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FENGeneratorTest {

    @Test
    public void rootPosTest() {
        BoardData startPos = new BoardData();
        FENGenerator gen = new FENGenerator(startPos);
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", gen.toString());
    }

}
