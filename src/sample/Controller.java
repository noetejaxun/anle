package sample;

import java_cup.runtime.Symbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import sample.models.*;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class Controller extends ExceptionAlerts {
    FileChooserUpload fileUpload = new FileChooserUpload();
    FileChooserUpload lexerFileUpload = new FileChooserUpload();
    FileChooserUpload lexerCupFileUpload = new FileChooserUpload();
    FileChooserUpload syntaxFileUpload = new FileChooserUpload();
    CustomizeButtons customButtons = new CustomizeButtons();
    LexerGenerator lexer = new LexerGenerator();

    @FXML private ImageView maximizeBtn;
    @FXML private Label fileName;
    @FXML private Label lexerFileName;
    @FXML private Label lexerCupFileName;
    @FXML private Label syntaxFileName;
    @FXML private TableView<Item> tableContent = new TableView<Item>();;
    @FXML private TableColumn line = new TableColumn("Línea");
    @FXML private TableColumn content = new TableColumn("Contenido");
    @FXML private TableColumn resultLine = new TableColumn("Resultado");

    private final ObservableList<Item> data = FXCollections.observableArrayList();
    private String concatenatedData = new String();

    public void lexerResult(MouseEvent event) {
        getContentFile(event);
        try {
            Reader reader = new BufferedReader(new FileReader(fileName.getText()));
            Lexer lexer = new Lexer(reader);
            String result = "";
            int i = 0;
            int count = 0;
            while (true){
                Tokens tokens = lexer.yylex();
                if ( tokens == null ) {
                    data.get(i).setResult(result);
                    setTableContent();
                    lexicalAnalyzer(event);
                    return;
                }
                switch (tokens) {
                    case Linea:
                        int size = data.size();
                        if ( i < size ) {
                            data.get(i).setResult(result);
                            i++;
                        }
                        result = "";
                        break;
                    case Comillas:
                    case Cadena:
                    case T_dato:
                    case If:
                    case Else:
                    case Do:
                    case While:
                    case For:
                    case Igual:
                    case Suma:
                    case Resta:
                    case Multiplicacion:
                    case Division:
                    case Op_logico:
                    case Op_incremento:
                    case Op_relacional:
                    case Op_atribucion:
                    case Op_booleano:
                    case Parentesis_a:
                    case Parentesis_c:
                    case Llave_a:
                    case Llave_c:
                    case Corchete_a:
                    case Corchete_c:
                    case Main:
                    case P_coma:
                    case Identificador:
                    case Numero:
                        result += "<" + tokens + "> " + lexer.lexeme + "\n";
                        break;
                    case ERROR:
                        if ( count != 0 ) {
                            result += "<Simbolo no definido>\n";
                        }
                        break;
                    default:
                        //result += "  < " + lexer.lexeme + " >\n";
                        break;
                }
                count++;
            }
        } catch (FileNotFoundException ex) {
            setExceptionAlert(ex);
        } catch (Exception ex) {
            //setExceptionAlert(ex);
        }
    }

    public void setPath(MouseEvent event) {
        lexerFileName.setText(System.getProperty("user.dir") + "/src/sample/Lexer.flex");
        lexerCupFileName.setText(System.getProperty("user.dir") + "/src/sample/LexerCup.flex");
        syntaxFileName.setText(System.getProperty("user.dir") + "/src/sample/Syntax.cup");
    }

    public void searchLexerFile(MouseEvent event) { lexerFileName.setText(lexerFileUpload.searchFile(event)); }

    public void generateLexer(MouseEvent event) { setPath(event); lexer.generateLexer(lexerFileName.getText()); }


    public void searchLexerCupFile(MouseEvent event) { lexerCupFileName.setText(lexerCupFileUpload.searchFile(event)); }

    public void generateLexerCup(MouseEvent event) { setPath(event); lexer.generateLexer(lexerCupFileName.getText()); }


    public void searchSyntaxFile(MouseEvent event) { syntaxFileName.setText(syntaxFileUpload.searchFile(event)); }

    public void generateSyntax(MouseEvent event) throws Exception { setPath(event); lexer.generateCup(new String[] {"-parser", "Syntax", syntaxFileName.getText()}); }


    public void searchFile(MouseEvent event) {
        fileName.setText(fileUpload.searchFile(event));
    }

    public void openFile(MouseEvent event) {
        fileUpload.openFile(event);
    }

    public void getContentFile(MouseEvent event) {
        FileLines fl = fileUpload.getContentFile();
        data.clear();
        int i = 1;
        if ( fl != null ) {
            List<String> items = fl.getLines();
            for ( Object line : items) {
                data.add(new Item(i, line.toString(), ""));
                concatenatedData = concatenatedData + line.toString() + "\r\n";
                i++;
            }

            setTableContent();
        } else {
            showAlert("Archio Vacío", "Seleccione un archivo valido para continuar.", Alert.AlertType.WARNING);
        }
    }

    public void lexicalAnalyzer(MouseEvent event) {
        try {
            File file = new File(fileName.getText());
            String ST = new String(Files.readAllBytes(file.toPath()));

            Syntax syntax = new Syntax(new sample.LexerCup(new StringReader(ST)));
            try {
                syntax.parse();
                showAlert("Correcto", "Análisis realizado correctamente.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                Symbol symbol = syntax.getS();
                if ( symbol != null ) {
                    if ( !( symbol.right == 0 && symbol.left == 0 ) ){
                        showAlert("Error", "Error de sintaxis. Línea: " + (symbol.right + 1) + " Columna: " + (symbol.left + 1) + ", Texto: " + "\"" + (symbol.value) + "\"", Alert.AlertType.ERROR);
                    }
                } else {
                    setExceptionAlert(e);
                }
            }
        } catch (Exception e) {
            setExceptionAlert(e);
        }
    }

    public void setTableContent() {
        tableContent.setEditable(true);
        tableContent.setItems(data);
        tableContent.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures p) {
                return true;
            }
        });
        line.setCellValueFactory( new PropertyValueFactory<Item, Integer>("line"));
        content.setCellValueFactory( new PropertyValueFactory<Item, String>("content"));
        resultLine.setCellValueFactory( new PropertyValueFactory<Item, String>("result") );

        line.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell<Item, Integer>() {
                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if(isEmpty()) {
                            setText("");
                        }  else {
                            //setTextFill(Color.BLUE);
                            setFont(Font.font ("Consolas", 14));
                            setText(item.toString());
                        }
                    }
                };
            }
        });
        content.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell<Item, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(isEmpty())  {
                            setText("");
                        } else {
                            //setTextFill(Color.BLACK);
                            setFont(Font.font ("Consolas", 14));
                            setText(item);
                        }
                    }
                };
            }
        });
        resultLine.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell<Item, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(isEmpty())  {
                            setText("");
                        } else {
                            setTextFill(Color.rgb(20, 129, 186));
                            setFont(Font.font ("Consolas", 14));
                            setText(item);
                        }
                    }
                };
            }
        });


    }


    public void onExitButtonClicked(MouseEvent event) { customButtons.onExitButtonClicked(event); }

    public void onMinimizeButton(MouseEvent event) { customButtons.onMinimizeButton(event); };

    public void onMaximizeButton(MouseEvent event) { customButtons.onMaximizeButton(event, maximizeBtn); }
}
