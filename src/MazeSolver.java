/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        ArrayList<MazeCell> list = new ArrayList<MazeCell>();
        return getPath(list, maze.getEndCell());
    }

    // Recursive method that backtracks through parents to determine solution
    public ArrayList<MazeCell> getPath(ArrayList<MazeCell> list, MazeCell tile) {
        list.add(0,tile);
        if (tile.getParent() == null) {
            return list;
        }
        return getPath(list, tile.getParent());
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // LIFO alg, so stack
        Stack<MazeCell> toExplore = new Stack<MazeCell>();
        MazeCell curCell = maze.getStartCell();

        while (!curCell.equals(maze.getEndCell())) { // While the maze is unsolved
            if (!toExplore.isEmpty()) { // Should only be empty on first loop, this just stops index out of bounds error on startup
                // Move to next cell in toExplore
                curCell = toExplore.pop();
            }
            ArrayList<MazeCell> adjCells = validAdjCells(curCell);
            while (!adjCells.isEmpty()) {
                // From current cell, add all valid adjacent cells to toExplore, set them as explored, set curCell as parent
                adjCells.get(0).setExplored(true);
                adjCells.get(0).setParent(curCell);
                toExplore.add(adjCells.remove(0));
            }
        }

        // Find path taken
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // FIFO alg, so queue
        Queue<MazeCell> toExplore = new LinkedList<MazeCell>();
        MazeCell curCell = maze.getStartCell();

        while (!curCell.equals(maze.getEndCell())) { // While the maze is unsolved
            if (!toExplore.isEmpty()) { // Should only be empty on first loop, this just stops index out of bounds error on startup
                // Move to next cell in toExplore
                curCell = toExplore.remove();
            }
            ArrayList<MazeCell> adjCells = validAdjCells(curCell);
            // Add all valid adjacent cells to toExplore, set them as explored, set current cell as parent
            while (!adjCells.isEmpty()) {
                adjCells.get(0).setExplored(true);
                adjCells.get(0).setParent(curCell);
                toExplore.add(adjCells.remove(0));
            }
        }
        // Find path taken
        return getSolution();
    }

    // Returns all adjacent cells that are valid cells (in bounds, not wall)
    public ArrayList<MazeCell> validAdjCells(MazeCell tile) {
        ArrayList<MazeCell> cells = new ArrayList<MazeCell>();
        int row = tile.getRow();
        int col = tile.getCol();

        // North
        if (maze.isValidCell(row-1, col)) {
            cells.add(maze.getCell(row-1,col));
        }
        // East
        if (maze.isValidCell(row, col+1)) {
            cells.add(maze.getCell(row,col+1));
        }
        // South
        if (maze.isValidCell(row+1, col)) {
            cells.add(maze.getCell(row+1,col));
        }
        // West
        if (maze.isValidCell(row, col-1)) {
            cells.add(maze.getCell(row,col-1));
        }
        return cells;
    }
    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();
        System.out.println("\n");

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
