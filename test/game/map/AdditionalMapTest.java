package game.map;

import game.map.cells.FillableCell;
import game.pipes.Pipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdditionalMapTest {
    /**
     * Tests whether the first pipe can be filled when connected with the source with the correct direction.
     * <p>
     * Succeeds if the pipe is filled.
     * </p>
     */
    @Test
    void defaultMapConstructorValid() {

        final var map = assertDoesNotThrow(() -> new Map(4,4));

    }

    /**
     * Tests whether the first pipe can be filled when not connected with the source due to incorrect direction.
     * <p>
     * Succeeds if the first pipe is not filled.
     * </p>
     */
    @Test
    void validateCheckPath() {
        final var cellRep =
                        "WWWW\n" +
                        "W.<W\n" +
                        "W..>\n" +
                        "WWWW";
        final var map = assertDoesNotThrow(() -> Map.fromString(4, 4, cellRep));

        assertTrue(() -> map.tryPlacePipe(1, 1, new Pipe(Pipe.Shape.BOTTOM_RIGHT)));

        assertFalse(()->map.checkPath());

        assertTrue(()-> map.tryPlacePipe(2,1,new Pipe(Pipe.Shape.TOP_RIGHT)));

        assertTrue(()-> map.tryPlacePipe(2,2,new Pipe(Pipe.Shape.HORIZONTAL)));

        assertTrue(()->map.checkPath());
    }

    /**
     * Tests whether a non-first pipes can be filled when connected with the previous pipe with the correct direction.
     * <p>
     * Succeeds if the pipe is filled.
     * </p>
     */
    @Test
    void givenSubsequentPipe_ifCanFillPipeFromCorrectDirection_thenSuccess() {
        final var cellRep =
                        "WWWW\n" +
                        "W.<W\n" +
                        "W..>\n" +
                        "WWWW";
        final var map = assertDoesNotThrow(() -> Map.fromString(4, 4, cellRep));

        assertTrue(() -> map.tryPlacePipe(1, 1, new Pipe(Pipe.Shape.BOTTOM_RIGHT)));
        assertTrue(() -> map.tryPlacePipe(2, 1, new Pipe(Pipe.Shape.TOP_RIGHT)));

        map.fillTiles(2);

        assertTrue(() -> map.cells[1][1] instanceof FillableCell);
        final var firstCell = (FillableCell) map.cells[1][1];
        assertTrue(() -> firstCell.getPipe().isPresent() && firstCell.getPipe().get().getFilled());

        assertTrue(() -> map.cells[2][1] instanceof FillableCell);
        final var secondCell = (FillableCell) map.cells[2][1];
        assertTrue(() -> secondCell.getPipe().isPresent() && secondCell.getPipe().get().getFilled());
    }

    /**
     * Tests whether a non-first pipe can be filled when not connected with the previous pipe due to incorrect
     * direction.
     * <p>
     * Succeeds if the pipe is not filled.
     * </p>
     */
    @Test
    void givenSubsequentPipe_ifCanFillPipeFromIncorrectDirection_thenFail() {
        final var cellRep =
                "WWWW\n" +
                        "W.<W\n" +
                        "W..>\n" +
                        "WWWW";
        final var map = assertDoesNotThrow(() -> Map.fromString(4, 4, cellRep));

        assertTrue(() -> map.tryPlacePipe(1, 1, new Pipe(Pipe.Shape.BOTTOM_RIGHT)));
        assertTrue(() -> map.tryPlacePipe(2, 1, new Pipe(Pipe.Shape.BOTTOM_RIGHT)));

        map.fillTiles(2);

        assertTrue(() -> map.cells[1][1] instanceof FillableCell);
        final var firstCell = (FillableCell) map.cells[1][1];
        assertTrue(() -> firstCell.getPipe().isPresent() && firstCell.getPipe().get().getFilled());

        assertTrue(() -> map.cells[2][1] instanceof FillableCell);
        final var secondCell = (FillableCell) map.cells[2][1];
        assertFalse(() -> secondCell.getPipe().isPresent() && secondCell.getPipe().get().getFilled());
    }
}
