package model;

public class ModelHouse {
  protected final int posX;
  protected final int posY;
  protected String imagePath = null;
  protected ModelPiece piece = null;

  public ModelHouse(int posX, int posY, String imagePath) {
    this.posX = posX;
    this.posY = posY;
    this.imagePath = imagePath;
  }

  public void setPiece(ModelPiece piece) {
    this.piece = piece;
  }

  public ModelPiece getPiece() {
    return piece;
  }
}