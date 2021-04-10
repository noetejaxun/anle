package sample.models;

import javafx.scene.control.Alert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LexerGenerator extends ExceptionAlerts {
  public void generateLexer(String path) {
    try {
      File lexerFile = new File(path);
      JFlex.Main.generate(lexerFile);
      showAlert("Éxito", "Lexer generado correctamente :)", Alert.AlertType.INFORMATION);
    } catch (Exception ex) {
      setExceptionAlert(ex);
    }
  }

  public void generateCup(String[] rutas) throws Exception {
    //java_cup.Main.main(rutas);
    Path pathSym = Paths.get(System.getProperty("user.dir") +"/src/sample/sym.java");
    if ( Files.exists(pathSym) ) {
      Files.delete(pathSym);
    }

    Path pathSyntax = Paths.get(System.getProperty("user.dir") + "/src/sample/Syntax.java");
    if ( Files.exists(pathSyntax) ) {
      Files.delete(pathSyntax);
    }

    Files.move(
        Paths.get(System.getProperty("user.dir") + "/sym.java"),
        Paths.get(System.getProperty("user.dir") + "/src/sample/sym.java")
    );
    Files.move(
        Paths.get(System.getProperty("user.dir") + "/Syntax.java"),
        Paths.get(System.getProperty("user.dir") + "/src/sample/Syntax.java")
    );

    showAlert("Éxito", "Syntaxis generada correctamente :)", Alert.AlertType.INFORMATION);

  }
}
