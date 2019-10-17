package game;


import game.pipes.Pipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PipeQueueTestSample {
    private static final List<Pipe> INIT_PIPES = Arrays.asList(
            new Pipe(Pipe.Shape.CROSS),
            new Pipe(Pipe.Shape.VERTICAL),
            new Pipe(Pipe.Shape.HORIZONTAL),
            new Pipe(Pipe.Shape.TOP_RIGHT),
            new Pipe(Pipe.Shape.TOP_LEFT)
    );

    private PipeQueue queue = null;

    @BeforeEach
    void setUp() {
        queue = new PipeQueue(INIT_PIPES);
    }

    @Test
    void givenQueue_ifQueueContainsGivenPipes_thenSucceed() {
        INIT_PIPES.forEach(pipe -> {
            assertEquals(pipe, queue.peek());
            queue.consume();
        });
    }

    @Test
    void givenQueue_ifQueuePeekIsImmutable_thenSucceed() {
        assertEquals(INIT_PIPES.get(0), queue.peek());
        assertEquals(INIT_PIPES.get(0), queue.peek());
    }

    @Test
    void givenQueue_ifQueueUndoAddsElementAtFront_thenSucceed() {
        final var elemToAdd = new Pipe(Pipe.Shape.BOTTOM_LEFT);

        queue.undo(elemToAdd);

        assertEquals(elemToAdd, queue.peek());
    }

    @AfterEach
    void tearDown() {
        queue = null;
    }
}
