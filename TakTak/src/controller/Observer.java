package controller;

import model.ModelHouse;
import model.TeamType;

public interface Observer {
  public void initBoard(ModelHouse[][] houses);

  public void notifySelection(int line, int column, TeamType type);

  public void notifyMovement(int previousLine, int previousColumn, int line, int column, ModelHouse[][] houses);

  public void notifyClearSelection(int line, int column, TeamType type);

  public void endMatch();
}