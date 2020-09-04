package sample.models;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionAlerts {
  public static void showAlert(String title, String content, Alert.AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);

    alert.showAndWait();
  }

  public static void setExceptionAlert(Exception ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Dialogo de Excepción");
    alert.setHeaderText("Se ha producido un error:");
    alert.setContentText("Para más detalles extienda el diálogo...");

    // Create expandable Exception.
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    String exceptionText = sw.toString();

    Label label = new Label("Detalles...");
    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);
    alert.getDialogPane().setExpandableContent(expContent);

    alert.showAndWait();
  }

}
