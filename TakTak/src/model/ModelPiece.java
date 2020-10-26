package model;

public class ModelPiece {

  protected ModelBoard board;
  protected String name;
  protected ModelTeam team;
  protected int x, y, strength;

  public ModelPiece(String name, int strength, ModelTeam team, int x, int y) {
    this.x = x;
    this.y = y;
    this.name = name;
    this.team = team;
    this.strength = strength;
    this.board = ModelBoard.getInstance();
  }

  public String getName() {
    return name;
  }

}