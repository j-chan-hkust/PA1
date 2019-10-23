package game;

import game.map.cells.FillableCell;
import game.pipes.Pipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Coordinate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AdditionalGameTests {
    @Test
    void testHasLost() {
        final var cellRep =
                        "WWWW\n" +
                        "W.<W\n" +
                        "W..>\n" +
                        "WWWW";
        final var initPipes = Collections.singletonList(new Pipe(Pipe.Shape.CROSS));
        final Game g = assertDoesNotThrow(() -> Game.fromString(4, 4, 0, cellRep, initPipes));
        assertTrue(g.placePipe(1,'A'));
        g.updateState();
        g.updateState();
        assertTrue(g.hasLost());
    }

    //toy test case
    @Test
    void testHasWon() {
        final var cellRep =
                "WWW\n" +
                        "<<W\n" +
                        "WWW";
        final var initPipes = Collections.singletonList(new Pipe(Pipe.Shape.CROSS));
        final Game g = assertDoesNotThrow(() -> Game.fromString(3, 3, 0, cellRep, initPipes));
        assertTrue(g.hasWon());
    }

    @Test
    void validateDefaultConstructor() {

        final Game g = assertDoesNotThrow(() -> new Game(5,5));
        g.display();
        assertDoesNotThrow(()->g.placePipe(1,'A'));
        assertDoesNotThrow(()->g.placePipe(1,'B'));
        assertDoesNotThrow(()->g.placePipe(1,'B'));
        assertDoesNotThrow(()->g.placePipe(1,'C'));
        assertDoesNotThrow(()->g.placePipe(2,'A'));
        assertDoesNotThrow(()->g.placePipe(3,'B'));
        assertDoesNotThrow(()->g.placePipe(5,'B'));
        g.updateState();
        g.display();

    }

}
