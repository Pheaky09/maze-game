package MazeGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


//We used Graph for the maze layout, where each cell is a node in the graph
public class Graph {
    private MazeCell[][] maze;
    private int rows;
    private int cols;

    public Graph(int rows, int cols, boolean[][] mazeData) {
        this.rows = rows;
        this.cols = cols;
        maze = new MazeCell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new MazeCell(i, j, mazeData[i][j]);
            }
        }
    }

    public MazeCell getCell(int row, int col) {
        return maze[row][col];
    }

    public List<MazeCell> getNeighbors(MazeCell cell) {
        List<MazeCell> neighbors = new ArrayList<>();
        int row = cell.row;
        int col = cell.col;

        if (row > 0 && !maze[row - 1][col].isWall && !maze[row - 1][col].visited) {
            neighbors.add(maze[row - 1][col]);
        }
        if (col > 0 && !maze[row][col - 1].isWall && !maze[row][col - 1].visited) {
            neighbors.add(maze[row][col - 1]);
        }
        if (row < rows - 1 && !maze[row + 1][col].isWall && !maze[row + 1][col].visited) {
            neighbors.add(maze[row + 1][col]);
        }
        if (col < cols - 1 && !maze[row][col + 1].isWall && !maze[row][col + 1].visited) {
            neighbors.add(maze[row][col + 1]);
        }

        return neighbors;
    }
}
