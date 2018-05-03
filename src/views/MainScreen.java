package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Task;

import java.time.LocalDate;

public class MainScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Task task = new Task("Rewrite LoginViewController", "LoginViewController needs to be rewritten.", LocalDate.now());
        TaskViewController testTaskView = new TaskViewController(task);
        BorderPane pane = new BorderPane();
        pane.setCenter(testTaskView);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
