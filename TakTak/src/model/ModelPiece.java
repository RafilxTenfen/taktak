package model;

import java.util.ArrayList;
import java.util.List;

public class ModelPiece {

  protected ModelBoard board;
  protected String name;
  protected ModelTeam team;
  protected int line, column, points, r, g, b;
  protected List<ModelPiece> attackedPieces;
  protected PieceCollor collor;

  public ModelPiece(String name, int points, PieceCollor collor, ModelTeam team, int line, int column, int r, int g,
      int b) {
    this.line = line;
    this.column = column;
    this.r = r;
    this.g = g;
    this.b = b;
    this.name = name;
    this.team = team;
    this.points = points;
    this.collor = collor;
    this.board = ModelBoard.getInstance();
    attackedPieces = new ArrayList<ModelPiece>();
  }

  public String getName() {
    return name;
  }

  public void move(int line, int column) {
    this.line = line;
    this.column = column;
  }

  public ModelTeam getTeam() {
    return team;
  }

  public int getColumn() {
    return column;
  }

  public int getLine() {
    return line;
  }

  public boolean isMovePossible(int line, int column) {

    return true;
  }

  // if the both pieces are the same collor or points
  public static boolean canAttack(ModelPiece selectedPiece, ModelPiece piece) {
    return selectedPiece.collor == piece.collor || selectedPiece.points == piece.points;
  }

  public void attack(ModelPiece piece) {
    for (ModelPiece p : piece.attackedPieces) {
      this.attackedPieces.add(p);
    }
    piece.attackedPieces.clear();
    this.attackedPieces.add(piece);
    this.move(piece.line, piece.column);
  }

  public int calculatePoints() {
    int total = this.points;

    for (ModelPiece p : this.attackedPieces) {
      total += p.points;
    }

    return total;
  }

}