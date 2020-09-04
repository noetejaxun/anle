package sample.models;

import java.util.ArrayList;
import java.util.List;

public class FileLines {
  private List<String> lines = new ArrayList<String>();

  public List<String> getLines() {
    return lines;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }

  public void addLine(String line) {
    this.lines.add(line);
  }

}
