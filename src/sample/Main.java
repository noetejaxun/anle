package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
  private double xOffset;
  private double yOffset;

  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    /*root.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
      }
    });
    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        primaryStage.setX(event.getSceneX() - xOffset);
        primaryStage.setY(event.getSceneY() - yOffset);
      }
    });*/

    Scene scene = new Scene(root);
    /*scene.setFill(Color.TRANSPARENT);
    primaryStage.initStyle(StageStyle.TRANSPARENT);*/
    primaryStage.setTitle("Analizador Derivadas - Compiladores");
    //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png")));
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
