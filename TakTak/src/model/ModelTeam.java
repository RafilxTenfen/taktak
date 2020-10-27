package model;

import java.util.ArrayList;
import java.util.List;

public class ModelTeam {

  private List<ModelPiece> pieces = new ArrayList<>();
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ModelPiece> getPieces() {
    return pieces;
  }

  public void addPiece(ModelPiece p) {
    this.pieces.add(p);
  }

  public ModelPiece getPiece(int x, int y) {
    for (ModelPiece p : pieces) {
      if (p.x == x && p.y == y) {
        return p;
      }
    }

    return null;
  }

  public ModelPiece removePiece(ModelPiece p) {
    for (int i = 0; i < this.pieces.size(); i++) {
      ModelPiece piece = this.pieces.get(i);
      if (piece.getName().equalsIgnoreCase(p.getName())) {
        return pieces.remove(i);
      }
    }
    throw new NullPointerException("Peca not found");
  }
}