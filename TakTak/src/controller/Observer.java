package controller;

import model.ModelHouse;
import model.ModelPiece;
import model.TeamType;

public interface Observer {
  public void initBoard(ModelHouse[][] houses);

  public void notifySelection(int line, int column, TeamType type);

  public void notifyMovement(int previousLine, int previousColumn, int line, int column, ModelHouse[][] houses);

  public void notifyClearSelection(int line, int column, TeamType type);

  public void notifyEndGame(Player local, Player remote);

  public void setTitleFromPlayers(Player local, Player remote);

  public void notify(String text);

  public void sendPlay(ModelPiece selectedPiece, int line, int column);

  public void endMatch();
}