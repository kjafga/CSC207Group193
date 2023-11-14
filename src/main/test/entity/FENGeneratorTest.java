package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FENGeneratorTest {

    @Test
    public void rootPosTest() {
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", new Board().toString());
    }

}
