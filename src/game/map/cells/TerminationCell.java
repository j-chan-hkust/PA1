package game.map.cells;

import org.jetbrains.annotations.NotNull;
import util.Coordinate;
import util.Direction;
import util.PipePatterns;

/**
 * Represents a source or a sink {@link Cell}.
 */
public class TerminationCell extends Cell {

    private boolean isFilled = false;
    @NotNull
    public final Direction pointingTo;
    @NotNull
    public final Type type;

    /**
     *
     * @param coord coordination of this cell
     * @param d direction of this termination
     * @param type type of this termination
     */
    public TerminationCell(Coordinate coord, @NotNull Direction d, @NotNull Type type) {
        // TODO
    }

    /**
     * Sets this cell as filled.
     */
    public void setFilled() {
        // TODO
    }

    /**
     * <p>
     * Hint: use {@link util.PipePatterns}
     * </p>
     *
     * @return the character representation of a termination cell in game
     */
    @Override
    public char toSingleChar() {
        // TODO
        return '\0';
    }

    public enum Type {
        SOURCE, SINK
    }
}
