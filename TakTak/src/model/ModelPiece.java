package model;

public class ModelPiece {

  protected ModelBoard board;
  protected String name;
  protected ModelTeam team;
  protected int x, y, points, r, g, b;

  public ModelPiece(String name, int points, ModelTeam team, int x, int y, int r, int g, int b) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.g = g;
    this.b = b;
    this.name = name;
    this.team = team;
    this.points = points;
    this.board = ModelBoard.getInstance();
  }

  public String getName() {
    return name;
  }

  public void move(int x, int y) {
    // board.get
  }

  public boolean isMovePossible(int x, int y) {

    return true;
  }

}