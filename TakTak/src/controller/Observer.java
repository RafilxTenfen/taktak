package controller;

import model.ModelHouse;

public interface Observer {
  public void initBoard(ModelHouse[][] houses);

  public void notifySelection(int y, int x);

  public void endMatch();
}