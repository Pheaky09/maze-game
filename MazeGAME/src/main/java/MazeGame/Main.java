package MazeGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MazeGameController mazeGameController = new MazeGameController(primaryStage);
        mazeGameController.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
