package sample.models;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CustomizeButtons {
  private double lastX = 0.0d;
  private double lastY = 0.0d;
  private double lastWidth = 0.0d;
  private double lastHeight = 0.0d;

  public void onExitButtonClicked(MouseEvent event) {
    Platform.exit();
    System.exit(0);
  }

  public void onMinimizeButton(MouseEvent event) {
    Stage stage = (Stage)((ImageView)event.getSource()).getScene().getWindow();
    stage.setIconified(true);
  };

  public void onMaximizeButton(MouseEvent event, ImageView maximizeBtn) {
    Node n = (Node)event.getSource();
    Window w = n.getScene().getWindow();

    double currentX = w.getX();
    double currentY = w.getY();
    double currentWidth = w.getWidth();
    double currentHeight = w.getHeight();

    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    if( currentX != bounds.getMinX() &&
        currentY != bounds.getMinY() &&
        currentWidth != bounds.getWidth() &&
        currentHeight != bounds.getHeight() ) {
      w.setX(bounds.getMinX());
      w.setY(bounds.getMinY());
      w.setWidth(bounds.getWidth());
      w.setHeight(bounds.getHeight());
      lastX = currentX;
      lastY = currentY;
      lastWidth = currentWidth;
      lastHeight = currentHeight;
      maximizeBtn.setImage(new Image("sample/images/icon-restore.png"));
    } else {
      w.setX(lastX);
      w.setY(lastY);
      w.setWidth(lastWidth);
      w.setHeight(lastHeight);
      maximizeBtn.setImage(new Image("sample/images/icon-maximize.png"));
    }
    event.consume();
  }

}
