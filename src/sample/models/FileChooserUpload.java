package sample.models;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileChooserUpload extends ExceptionAlerts{
  private File file;
  private Desktop desktop = Desktop.getDesktop();
  final FileChooser fileChooser = new FileChooser();

  public String searchFile(MouseEvent event) {
    fileChooser.setTitle("Pascal File...");
    fileChooser.setInitialDirectory(
        new File(System.getProperty("user.home"))
    );
    fileChooser.getExtensionFilters().clear();
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("TXT", "*.txt"),
        new FileChooser.ExtensionFilter("DAT", "*.dat"),
        new FileChooser.ExtensionFilter("Todos...", "*.*")
    );
    file = fileChooser.showOpenDialog(new Stage());
    if ( file != null ) {
      return file.getAbsolutePath();
    } else {
      return "File Name...";
    }
  }

  public FileLines getContentFile() {
    FileLines lines = new FileLines();

    try {
      String fileName = file.getAbsolutePath();
      String line = null;
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while((line = bufferedReader.readLine()) != null) {
        lines.addLine(line.toString());
      }

      bufferedReader.close();
      return lines;
    } catch(Exception ex) {
      setExceptionAlert(ex);
    }
    return null;
  }

  public void openFile(MouseEvent event) {
    if ( file != null ) {
      try {
        desktop.open(file);
      } catch (Exception ex) {
        setExceptionAlert(ex);
      }
    } else {
      showAlert("Warning", "Seleccione un archivo para abrir", Alert.AlertType.WARNING);
    }
  }
}
