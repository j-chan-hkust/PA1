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
    private int prevFilledTiles = -1;
    /**
     * You can use this variable to implement the {@link Map#fillTiles} method, but it is not necessary
     */
    private Integer prevFilledDistance = 0;

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
        for(int i = 0; i<rows; i++){
            for(int j = 0;j<cols; j++){
                if(i==0||i==rows-1||j==0||j==cols-1){//this is the case where it is on the edge
                    cells[i][j] = new Wall(new Coordinate(i,j));
                }else{
                    cells[i][j] = new FillableCell(new Coordinate(i,j));
                }
            }
        }

        int wall = (int) (Math.random()*3);
        int col = (int) (Math.random()*(cols-2))+1;
        int row = (int) (Math.random()*(rows-2))+1;
        int dir;
        switch (wall){
            case 0://top wall
                sinkCell = new TerminationCell(new Coordinate(0,col),Direction.UP, TerminationCell.Type.SINK);
                cells[0][col] = sinkCell;
                break;
            case 1: //bottom wall
                sinkCell = new TerminationCell(new Coordinate(rows-1,col),Direction.DOWN, TerminationCell.Type.SINK);
                cells[rows-1][col] = sinkCell;
                break;
            case 2: //left wall
                sinkCell = new TerminationCell(new Coordinate(row,0),Direction.LEFT, TerminationCell.Type.SINK);
                cells[row][0] = sinkCell;
                break;
            case 3: //left wall
                sinkCell = new TerminationCell(new Coordinate(row,cols-1),Direction.RIGHT, TerminationCell.Type.SINK);
                cells[row][cols-1] = sinkCell;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + wall);
        }

        do{
            col = (int) (Math.random()*(cols-2))+1;
            row = (int) (Math.random()*(rows-2))+1;
            dir = (int) (Math.random()*(3));
            Direction direction = Direction.values()[dir];
            sourceCell = new TerminationCell(new Coordinate(row,col),direction, TerminationCell.Type.SINK);
            cells[row][col] = sourceCell;
        } while(cells[sourceCell.pointingTo.getOffset().add(sourceCell.coord).row]
                [sourceCell.pointingTo.getOffset().add(sourceCell.coord).col] instanceof Wall);

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
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                if(cells[i][j] instanceof TerminationCell){
                    TerminationCell terminationCell = (TerminationCell) cells[i][j];
                    if(terminationCell.type == TerminationCell.Type.SINK){
                        sinkCell = terminationCell;
                    }else if(terminationCell.type == TerminationCell.Type.SOURCE){
                        sourceCell = terminationCell;
                    }
                }
            }
        }
        if (sourceCell == null||sinkCell == null){
            throw new IllegalStateException();
        }
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
        if(col>=cols||row>=rows||row<0||col<0){
            return false; //pipe placing fails
        }
        //check if cell is fillable
        if(!(cells[row][col] instanceof FillableCell)){
            return false; //class check failed, it was a wall or something.
        }else{//check if pipe already exists
            FillableCell fillableCell = (FillableCell) cells[row][col];
            if(fillableCell.getPipe().isEmpty()){
                cells[row][col] = new FillableCell(new Coordinate(row, col),p);
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
        filledTiles.add(sourceCell.coord);
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
        // TODO figure out initial case.
        if(distance<=0){
            return;
        }

        int count = distance-prevFilledDistance;
        prevFilledDistance += distance;
        prevFilledTiles = 0;
        Set<Cell> filledCells = new HashSet<Cell>();
        fillBeginTile();
        for(Coordinate coord : filledTiles)
            filledCells.add(cells[coord.row][coord.col]);

        while(count>0){
            Set<Cell> tempSet = new HashSet<Cell>();
            for(Cell cell : filledCells){
                Direction[] directions = null;
                if(cell instanceof FillableCell){
                    directions = ((FillableCell) cell).getPipe().get().getConnections();
                }
                else if(cell instanceof TerminationCell){
                    directions = new Direction[]{((TerminationCell) cell).pointingTo};
                }
                for (Direction d : directions){
                    Coordinate candidateCoord = new Coordinate(cell.coord.row, cell.coord.col);
                    candidateCoord = candidateCoord.add(d.getOffset());
                    Cell candidateCell = cells[candidateCoord.row][candidateCoord.col];
                    if(candidateCell instanceof FillableCell){
                        if(((FillableCell)candidateCell).getPipe().isPresent()){
                            if(!((FillableCell)candidateCell).getPipe().get().getFilled()){
                                for(Direction cd : ((FillableCell)candidateCell).getPipe().get().getConnections()){
                                    if(cd.getOpposite()==d){
                                        ((FillableCell)candidateCell).getPipe().get().setFilled();
                                        prevFilledTiles++;
                                        filledTiles.add(candidateCell.coord);
                                        tempSet.add(candidateCell);
                                    }
                                }
                            }
                        }
                    }
                    if(candidateCell instanceof TerminationCell){
                        if(!((TerminationCell)candidateCell).getFilled()){
                            ((TerminationCell)candidateCell).setFilled();
                            prevFilledTiles++;
                            filledTiles.add(candidateCell.coord);
                            tempSet.add(candidateCell);
                        }
                    }
                }
            }
            count--;
            if(tempSet.isEmpty())
                break;//evidently, there are no more pipes to explore, and we can end.
            filledCells = tempSet;
        }
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
        Set<Cell> filledCells = new HashSet<Cell>();
        filledCells.add(this.sourceCell);

        while(true){
            Set<Cell> tempSet = new HashSet<>(filledCells);
            for(Cell cell : filledCells){
                Direction[] directions = null;
                if(cell instanceof FillableCell){
                    directions = ((FillableCell) cell).getPipe().get().getConnections();
                }
                else if(cell instanceof TerminationCell){
                    directions = new Direction[]{((TerminationCell) cell).pointingTo};
                }
                for (Direction d : directions){
                    Coordinate candidateCoord = new Coordinate(cell.coord.row, cell.coord.col);
                    candidateCoord = candidateCoord.add(d.getOffset());
                    Cell candidateCell = cells[candidateCoord.row][candidateCoord.col];
                    if(candidateCell instanceof FillableCell){
                        if(((FillableCell)candidateCell).getPipe().isPresent()){
                                for(Direction cd : ((FillableCell)candidateCell).getPipe().get().getConnections()){
                                    if(cd.getOpposite()==d){
                                        tempSet.add(candidateCell);
                                    }
                                }
                        }
                    }
                    if(candidateCell instanceof TerminationCell){
                        if(((TerminationCell)candidateCell).type== TerminationCell.Type.SINK){
                            return true;
                        }
                    }
                }
            }
            if(tempSet.size()==filledCells.size())
                break;//evidently, there are no more pipes to explore, and we can end.
            filledCells = tempSet;
        }

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
        if ((!sinkCell.getFilled())&&prevFilledTiles==0){
            return true;
        }
        return false;
    }
}
