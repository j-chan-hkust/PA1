package game.map.cells;

import game.pipes.Pipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import util.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class FillableCellTestSample {
    private static Coordinate DEFAULT_COORD = new Coordinate(1, 2);
    private static Pipe DEFAULT_PIPE = new Pipe(Pipe.Shape.CROSS);

    private FillableCell cell = null;

    @Test
    void givenFilledCell_assertCorrectCoordinatesAndPipe() {
        cell = new FillableCell(DEFAULT_COORD, DEFAULT_PIPE);

        assertEquals(DEFAULT_COORD, cell.coord);
        assertEquals(DEFAULT_PIPE, cell.getPipe().orElse(null));
    }

    @Test
    void givenCell_assertSingleCharRepresentation() {
        cell = new FillableCell(DEFAULT_COORD);

        assertEquals('.', cell.toSingleChar());
    }

    @AfterEach
    void tearDown() {
        cell = null;
    }
}
