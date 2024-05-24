package MazeGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MazeCell {
    int row;
    int col;
    boolean isWall;
    boolean visited;

    public MazeCell(int row, int col, boolean isWall) {
        this.row = row;
        this.col = col;
        this.isWall = isWall;
        this.visited = false;
    }
}
