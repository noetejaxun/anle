package sample.models;

public class Item {
  private int line;
  private String content;
  private String result;

  public Item(int line, String content, String result) {
    this.line = line;
    this.content = content;
    this.result = result;
  }

  public int getLine() { return line; }

  public void setLine(int line) { this.line = line; }

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public String getResult() { return result; }

  public void setResult(String result) { this.result = result; }
}
