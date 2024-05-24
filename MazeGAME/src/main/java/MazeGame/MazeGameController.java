package MazeGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Stack;

public class MazeGameController {
    private static final int CELL_SIZE = 50;
    private final boolean[][] mazeData = {
            {true, true, false, true, true, true, true, true, true, true, true, true, true, true},
            {true, false, false, true, true, false, true, false, false, false, true, true, true, true},
            {true, true, false, false, false, false, true, false, true, true, false, false, false, true},
            {true, true, false, true, true, false, true, false, true, false, false, true, true, true},
            {true, false, false, true, true, false, false, false, true, false, true, true, false, false},
            {true, true, false, true, true, false, true, true, true, false, true, true, false, true},
            {true, true, false, false, false, false, true, true, true, false, false, false, false, true},
            {true, false, false, true, true, false, true, true, true, false, true, true, true, true},
            {true, true, false, true, true, false, false, false, false, false, false, false, true, true},
            {true, false, false, true, true, true, true, true, true, false, true, true, true, true},
            {true, true, false, false, false, false, true, true, true, false, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true},
    };

    private final Stage primaryStage;

    public MazeGameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startGame() {
        int rows = mazeData.length;
        int cols = mazeData[0].length;
        Pane root = new Pane();
        root.setPrefSize(cols * CELL_SIZE, rows * CELL_SIZE);

        //we use graph to make rows and coulumns
        Graph mazeGraph = new Graph(rows, cols, mazeData);
        int startRow = 0;
        int startCol = 2;
        int endRow = 4;
        int endCol = 13;

        MazeCell startCell = mazeGraph.getCell(startRow, startCol);
        MazeCell endCell = mazeGraph.getCell(endRow, endCol);

        // We used stack to store visited cells
        Stack<MazeCell> stack = new Stack<>();
        stack.push(startCell);

        boolean foundEnd = false;
        while (!stack.isEmpty() && !foundEnd) {
            MazeCell currentCell = stack.pop();
            currentCell.visited = true;

            if (currentCell == endCell) {
                foundEnd = true;
            } else {
                List<MazeCell> neighbors = mazeGraph.getNeighbors(currentCell);
                for (MazeCell neighbor : neighbors) {
                    stack.push(neighbor);
                }
            }
        }


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                MazeCell cell = mazeGraph.getCell(i, j);
                Rectangle rect = new Rectangle(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (cell == startCell) {
                    rect.setFill(Color.GREEN);
                } else if (cell == endCell) {
                    rect.setFill(Color.RED);
                } else if (cell.isWall) {
                    rect.setFill(Color.BLACK);
                } else {
                    rect.setFill(Color.WHITE);
                }
                root.getChildren().add(rect);
            }
        }


        Rectangle player = new Rectangle(startCol * CELL_SIZE, startRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        player.setFill(Color.YELLOW);
        root.getChildren().add(player);


        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            int dx = 0, dy = 0;
            switch (event.getCode()) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            int newRow = (int) (player.getY() / CELL_SIZE) + dy;
            int newCol = (int) (player.getX() / CELL_SIZE) + dx;
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !mazeGraph.getCell(newRow, newCol).isWall) {
                player.setX(newCol * CELL_SIZE);
                player.setY(newRow * CELL_SIZE);
                if (newRow == endRow && newCol == endCol) {
                    Text gameOverText = new Text("Game Over");
                    gameOverText.setFont(Font.font(30));
                    gameOverText.setFill(Color.RED);
                    gameOverText.setX(cols * CELL_SIZE / 2 - 70);
                    gameOverText.setY(rows * CELL_SIZE / 2);
                    root.getChildren().add(gameOverText);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                        restart();
                    }));
                    timeline.play();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Game");
        primaryStage.show();
    }


    private void restart() {
        startGame();
    }
}