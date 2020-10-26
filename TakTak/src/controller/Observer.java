package controller;

public interface Observer {
  public void initBoard();

  public void notifySelection(int y, int x);

  public void endMatch();
}