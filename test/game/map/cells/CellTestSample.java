package game.map.cells;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import util.Coordinate;
import util.Direction;

import static org.junit.jupiter.api.Assertions.*;

class CellTestSample {
    private static Coordinate DEFAULT_COORDINATE = new Coordinate(1, 2);

    private Cell cell = null;

    @Test
    void givenUnknownCHar_whenCreateCellFromChar_assertNull() {
        cell = Cell.fromChar('A', DEFAULT_COORDINATE, null);

        assertNull(cell);
    }

    @Test
    void givenWallChar_whenCreateCellFromCharWithTerminationType_assertCorrectType() {
        cell = Cell.fromChar('W', DEFAULT_COORDINATE, TerminationCell.Type.SOURCE);

        assertTrue(cell instanceof Wall);
        assertEquals(DEFAULT_COORDINATE, cell.coord);
    }

    @Test
    void givenTerminationCellChar_whenCreateCellFromCharWithParams_assertCorrectType() {
        cell = Cell.fromChar('^', DEFAULT_COORDINATE, TerminationCell.Type.SOURCE);

        assertTrue(cell instanceof TerminationCell);
        assertEquals(DEFAULT_COORDINATE, cell.coord);
        assertEquals(Direction.UP, ((TerminationCell) cell).pointingTo);
        assertEquals(TerminationCell.Type.SOURCE, ((TerminationCell) cell).type);
    }

    @AfterEach
    void tearDown() {
        cell = null;
    }
}
