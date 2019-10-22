package game.map;

import game.map.cells.Cell;
import game.map.cells.FillableCell;
import game.map.cells.TerminationCell;
import game.map.cells.Wall;
import game.pipes.Pipe;
import io.Deserializer;
import org.jetbrains.annotations.NotNull;
import util.Coordinate;
import util.Direction;
import util.StringUtils;

import java.util.*;

/**
 * Map of the game.
 */
public class Map {

    private final int rows;
    private final int cols;
    @NotNull
    final public Cell[][] cells;

    private TerminationCell sourceCell;
    private TerminationCell sinkCell;


    /**
     * You can use this variable to implement the {@link Map#fillTiles} method, but it is not necessary
     */
    @NotNull
    private final Set<Coordinate> filledTiles = new HashSet<>();
    /**
     * You can use this variable to implement the {@link Map#fillTiles} method, but it is not necessary
     */
    private int prevFilledTiles = 0;
    /**
     * You can use this variable to implement the {@link Map#fillTiles} method, but it is not necessary
     */
    private Integer prevFilledDistance;

    /**
     * Creates a map with size of rows x cols.
     *
     * <p>
     * You should make sure that the map generated adheres to the specifications as stated in the README.
     * </p>
     *
     * @param rows Number of rows.
     * @param cols Number of columns.
     */
    public Map(int rows, int cols) {
        // TODO DONE
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
    }

    /**
     * Creates a map with the given cells.
     *
     * <p>
     * You should make sure that the map generated adheres to the specifications as stated in the README.
     * </p>
     *
     * @param rows  Number of rows.
     * @param cols  Number of columns.
     * @param cells Cells to fill the map.
     */
    public Map(int rows, int cols, @NotNull Cell[][] cells) {
        // TODO DONE
        this.rows = rows;
        this.cols = cols;
        this.cells = cells;
    }

    /**
     * Constructs a map from a map string.
     * <p>
     * This is a convenience method for unit testing.
     * </p>
     *
     * @param rows     Number of rows.
     * @param cols     Number of columns.
     * @param cellsRep String representation of the map, with columns delimited by {@code '\n'}.
     * @return A map with the cells set from {@code cellsRep}.
     * @throws IllegalArgumentException If the map is incorrectly formatted.
     */
    @NotNull
    static Map fromString(int rows, int cols, @NotNull String cellsRep) {
        var cells = Deserializer.parseString(rows, cols, cellsRep);

        return new Map(rows, cols, cells);
    }

    /**
     * Tries to place a pipe at (row, col).
     *
     * @param coord Coordinate to place pipe at.
     * @param pipe  Pipe to place in cell.
     * @return {@code true} if the pipe is placed in the cell, {@code false} otherwise.
     */
    public boolean tryPlacePipe(@NotNull final Coordinate coord, @NotNull final Pipe pipe) {
        return tryPlacePipe(coord.row, coord.col, pipe);
    }

    /**
     * Tries to place a pipe at (row, col).
     *
     * <p>
     * Note: You cannot overwrite the pipe of a cell once the cell is occupied.
     * </p>
     * <p>
     * Hint: Remember to check whether the map coordinates are within bounds, and whether the target cell is a 
     * {@link FillableCell}.
     * </p>
     *
     * @param row One-Based row number to place pipe at.
     * @param col One-Based column number to place pipe at.
     * @param p   Pipe to place in cell.
     * @return {@code true} if the pipe is placed in the cell, {@code false} otherwise.
     */
    boolean tryPlacePipe(int row, int col, @NotNull Pipe p) {
        // TODO DONE?
        //check if coord in bounds
        if(col>=cols||row>=rows){
            return false; //pipe placing fails
        }
        //check if cell is fillable
        if(cells[row][col].getClass()!=FillableCell.class){
            return false; //class check failed, it was a wall or something.
        }else{//check if pipe already exists
            FillableCell fillableCell = (FillableCell) cells[row][col];
            if(!fillableCell.getPipe().isEmpty()){
                cells[row][cols] = new FillableCell(new Coordinate(row, col),p);
                return true;
            }else{
                return false; //there was already a pipe!
            }
        }
    }

    /**
     * Displays the current map.
     */
    public void display() {
        final int padLength = Integer.valueOf(rows - 1).toString().length();

        Runnable printColumns = () -> {
            System.out.print(StringUtils.createPadding(padLength, ' '));
            System.out.print(' ');
            for (int i = 0; i < cols - 2; ++i) {
                System.out.print((char) ('A' + i));
            }
            System.out.println();
        };

        printColumns.run();

        for (int i = 0; i < rows; ++i) {
            if (i != 0 && i != rows - 1) {
                System.out.print(String.format("%1$" + padLength + "s", i));
            } else {
                System.out.print(StringUtils.createPadding(padLength, ' '));
            }

            Arrays.stream(cells[i]).forEachOrdered(elem -> System.out.print(elem.toSingleChar()));

            if (i != 0 && i != rows - 1) {
                System.out.print(i);
            }

            System.out.println();
        }

        printColumns.run();
    }

    /**
     * Undoes a step from the map.
     *
     * <p>
     * Effectively replaces the cell with an empty cell in the coordinate specified.
     * </p>
     *
     * @param coord Coordinate to reset.
     * @throws IllegalArgumentException if the cell is not an instance of {@link FillableCell}.
     */
    public void undo(@NotNull final Coordinate coord) {
        // TODO DONE
        cells[coord.row][coord.col] = new FillableCell(coord); //creates empty fillable cell in the area.
    }

    /**
     * Fills the source cell.
     */
    public void fillBeginTile() {
        sourceCell.setFilled();
    }

    /**
     * Fills all pipes that are within {@code distance} units from the {@code sourceCell}.
     * 
     * <p>
     * Hint: There are two ways to approach this. You can either iteratively fill the tiles by distance (i.e. filling 
     * distance=0, distance=1, etc), or you can save the tiles you have already filled, and fill all adjacent cells of 
     * the already-filled tiles. Whichever method you choose is up to you, as long as the result is the same.
     * </p>
     *
     * @param distance Distance to fill pipes.
     */
    public void fillTiles(int distance) {
        // TODO
        if (distance<=0){
            System.out.println("cannot fill a negative/zero distance!");
        }

        Stack<FillableCell> cellStack = new Stack<FillableCell>();
        List<Coordinate> nextFillCoord = new LinkedList<Coordinate>();
        nextFillCoord.add(sourceCell.pointingTo.getOffset().add(sourceCell.coord)); //we add to next fill list
        int count = 0;

        do {
            //perform checks on the valid connections of the coord we looking at
            Coordinate currCord = nextFillCoord.get(0);

            if(cells[currCord.row][currCord.col].getClass() == Cell.class){
                continue; //if its just a normal cell, no point
            }
            if(cells[currCord.row][currCord.col].getClass() == FillableCell.class){
                //we know its fillable, thus we cast to fillable
                FillableCell fillableCell = (FillableCell) cells[currCord.row][currCord.col];
                if(!fillableCell.getPipe().isEmpty()){//non null pipe
                    if(!fillableCell.getPipe().get().getFilled()){ //not filled
                        //if all these hold, then we can get the connections
                        for(Direction direction : fillableCell.getPipe().get().getConnections()){
                            Coordinate nextCoord = direction.getOffset().add(currCord);
                            nextFillCoord.add(nextCoord); //we dont perform extra checks, cuz we do that before anyway
                            //there may be repeated work, but i really dont care
                        }
                        fillableCell.getPipe().get().setFilled();//finally, once we have explored the pipe
                    }
                }
            }



            //add to stack
            //pop top coord.
        }while(!nextFillCoord.isEmpty());
    }

    /**
     * Checks whether there exists a path from {@code sourceCell} to {@code sinkCell}.
     * 
     * <p>
     * Hint: This problem is similar to finding a specific node in a graph. As stated in the README, one of the ways you
     * could approach this is to use Breadth-First Search.
     * </p>
     *
     * @return {@code true} if a path exists, else {@code false}.
     */
    public boolean checkPath() {
        // TODO DONE?
        /*Coordinate sinkCellPointCoord = this.sinkCell.pointingTo.getOffset().add(this.sinkCell.coord);
        if(cells[sinkCellPointCoord.row][sinkCellPointCoord.col].getClass() == FillableCell.class) {
            //we know its fillable, thus we cast to fillable
            FillableCell fillableCell = (FillableCell) cells[sinkCellPointCoord.row][sinkCellPointCoord.col];
            if (!fillableCell.getPipe().isEmpty()) {//non null pipe
                if (fillableCell.getPipe().get().getFilled()) { //not filled
                    return true; //points to a fillable, has a pipe, pipe is filled
                }
            }
        }*/

        if(this.sinkCell.getFilled())
            return true;

        return false;
    }

    /**
     * <p>
     * Hint: From the README: {@code The game is lost when a round ends and no pipes are filled during the round.} Is
     * there a way to check whether pipes are filled during a round?
     * </p>
     *
     * @return {@code true} if the game is lost.
     */
    public boolean hasLost() {
        // TODO
        return false;
    }
}
