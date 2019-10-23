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
        map.fillTiles(1);
        map.display();
        final var map1 = assertDoesNotThrow(() -> new Map(4,4));
        map1.fillTiles(1);
        map1.display();
        final var map2 = assertDoesNotThrow(() -> new Map(4,4));
        map2.fillTiles(1);
        map2.display();
        final var map3 = assertDoesNotThrow(() -> new Map(4,4));
        map3.fillTiles(1);
        map3.display();
        final var map4 = assertDoesNotThrow(() -> new Map(4,4));
        map4.fillTiles(1);
        map4.display();
        final var map5 = assertDoesNotThrow(() -> new Map(4,4));
        map5.fillTiles(1);
        map5.display();
        final var map6 = assertDoesNotThrow(() -> new Map(4,4));
        map6.fillTiles(1);
        map6.display();
        final var map7 = assertDoesNotThrow(() -> new Map(4,4));
        map7.fillTiles(1);
        map7.display();
        final var map8 = assertDoesNotThrow(() -> new Map(4,4));
        map8.fillTiles(1);
        map8.display();
        final var map9 = assertDoesNotThrow(() -> new Map(4,4));
        map9.fillTiles(1);
        map9.display();//tests for default constructor
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

    @Test
    void checkPipeValidationThenRunDisplay(){
        //haha
        final var cellRep =
                        "WWWW\n" +
                        "W.<W\n" +
                        "W..>\n" +
                        "WWWW";
        final var map = assertDoesNotThrow(() -> Map.fromString(4, 4, cellRep));
        assertTrue(() -> map.tryPlacePipe(1, 1, new Pipe(Pipe.Shape.BOTTOM_RIGHT)));
        assertTrue(() -> map.tryPlacePipe(2, 1, new Pipe(Pipe.Shape.VERTICAL)));
        assertTrue(() -> map.tryPlacePipe(2, 2, new Pipe(Pipe.Shape.CROSS)));
        assertFalse(() -> map.tryPlacePipe(1, 2, new Pipe(Pipe.Shape.BOTTOM_RIGHT)));
        assertFalse(() -> map.tryPlacePipe(0, 1, new Pipe(Pipe.Shape.BOTTOM_RIGHT)));
        map.display();
    }
    @Test
    void fillLotsOfPipes() {
        final var cellRep =
                "WWWWWW\n" +
                        "<...<W\n" +
                        "W....W\n" +
                        "WWWWWW";
        final var map = assertDoesNotThrow(() -> Map.fromString(4, 6, cellRep));

        assertTrue(() -> map.tryPlacePipe(1, 1, new Pipe(Pipe.Shape.HORIZONTAL)));
        assertTrue(() -> map.tryPlacePipe(1, 2, new Pipe(Pipe.Shape.HORIZONTAL)));
        assertTrue(() -> map.tryPlacePipe(1, 3, new Pipe(Pipe.Shape.HORIZONTAL)));

        map.fillTiles(4);

        map.display();
    }
}
