package game;

import game.map.cells.FillableCell;
import game.pipes.Pipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CellStackTestSample {
    private CellStack stack;

    @BeforeEach
    void setUp() {
        stack = new CellStack();
    }

    @AfterEach
    void tearDown() {
        stack = null;
    }

    /**
     * Tests whether popping an empty stack returned null.
     */
    @Test
    void givenEmptyStack_ifPop_returnNull() {
        final var poppedCell = assertDoesNotThrow(() -> stack.pop());

        assertNull(poppedCell);
    }

    /**
     * Tests whether pushing into an empty stack does not throw.
     */
    @Test
    void givenEmptyStack_ifPush_noThrow() {
        assertDoesNotThrow(() -> {
            stack.push(new FillableCell(new Coordinate(0, 0)));
            stack.push(new FillableCell(new Coordinate(0, 1)));
            stack.push(new FillableCell(new Coordinate(0, 2)));
        });
    }


    /**
     * Tests whether a initially-constructed stack has an undo count of 0.
     */
    @Test
    void givenEmptyStack_assertUndoCountEqualsZero() {
        assertEquals(0, stack.getUndoCount());
    }

    /**
     * Tests whether popping an element from a stack changes the undo count.
     * <p>
     * Succeeds if the undo count increments by 1 after popping.
     * </p>
     */
    @Test
    void givenStack_whenPop_incUndoCount() {
        int originalCount = stack.getUndoCount();

        stack.push(new FillableCell(new Coordinate(0, 0), new Pipe(Pipe.Shape.CROSS)));
        stack.pop();

        assertEquals(originalCount + 1, stack.getUndoCount());
    }

}
