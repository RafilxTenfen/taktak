package model;

public class ModelHouse {
  protected final int posX, posY, r, g, b;
  protected String imagePath = null;
  protected ModelPiece piece = null;

  public ModelHouse(int posX, int posY, String imagePath, int r, int g, int b) {
    this.posX = posX;
    this.posY = posY;
    this.imagePath = imagePath;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public ModelHouse(int posX, int posY, String imagePath, ModelPiece piece, int r, int g, int b) {
    this.posX = posX;
    this.posY = posY;
    this.imagePath = imagePath;
    this.r = r;
    this.g = g;
    this.b = b;
    this.piece = piece;
  }

  public ModelHouse(ModelTeam team, int posX, int posY, String imagePath, String pieceName, int r, int g, int b,
      int points) {
    this.posX = posX;
    this.posY = posY;
    this.imagePath = imagePath;
    this.r = r;
    this.g = g;
    this.b = b;
    this.piece = new ModelPiece(pieceName, points, team, posX, posY, r, g, b);
  }

  public void setPiece(ModelPiece piece) {
    this.piece = piece;
  }

  public ModelPiece getPiece() {
    return piece;
  }

  public String getImagePath() {
    if (this.piece != null) {
      return "../view/assets/" + this.piece.getName() + ".png";
    }
    return this.imagePath;
  }
}