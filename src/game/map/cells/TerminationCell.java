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
        // TODO DONE
        super(coord);
        this.pointingTo = d;
        this.type = type;
    }

    /**
     * Sets this cell as filled.
     */
    public void setFilled() {
        // TODO DONE
        this.isFilled = true;
    }

    public boolean getFilled(){
        return this.isFilled;
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
        // TODO DONE
        if(isFilled){
            switch(pointingTo){
                case UP:
                    return (PipePatterns.Filled.UP_ARROW);
                case DOWN:
                    return (PipePatterns.Filled.DOWN_ARROW);
                case LEFT:
                    return (PipePatterns.Filled.LEFT_ARROW);
                case RIGHT:
                    return PipePatterns.Filled.RIGHT_ARROW;
            }
        }else{
            switch (pointingTo){
                case UP:
                    return (PipePatterns.Unfilled.UP_ARROW);
                case DOWN:
                    return (PipePatterns.Unfilled.DOWN_ARROW);
                case LEFT:
                    return (PipePatterns.Unfilled.LEFT_ARROW);
                case RIGHT:
                    return PipePatterns.Unfilled.RIGHT_ARROW;
            }
        }
        return '\0';
    }

    public enum Type {
        SOURCE, SINK
    }
}
