package game;


import game.pipes.Pipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdditionalPipeQueueTest {
    private static final List<Pipe> INIT_PIPES = Arrays.asList(
            new Pipe(Pipe.Shape.CROSS),
            new Pipe(Pipe.Shape.VERTICAL),
            new Pipe(Pipe.Shape.HORIZONTAL),
            new Pipe(Pipe.Shape.TOP_RIGHT),
            new Pipe(Pipe.Shape.TOP_LEFT),
            new Pipe(Pipe.Shape.BOTTOM_RIGHT),
            new Pipe(Pipe.Shape.BOTTOM_LEFT)
    );
    private static final List<String> INIT_STRING = Arrays.asList(
            "CR",
            "VT",
            "HZ",
            "TR",
            "TL",
            "BR",
            "BL"
    );

    private PipeQueue queue = null;

    @BeforeEach
    void setUp() {
        queue = new PipeQueue(INIT_PIPES);
    }

    @Test
    void checkPipeFromString() {
        for(int i = 0; i<7; i++){
            assertEquals(INIT_PIPES.get(i).toSingleChar(),Pipe.fromString(INIT_STRING.get(i)).toSingleChar());
        }
    }
}
