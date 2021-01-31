package src.model;

public class ModelHouse {
  protected final int line, column, r, g, b;
  protected String imagePath = null;
  protected ModelPiece piece = null;

  public ModelHouse(int line, int column, String imagePath, int r, int g, int b) {
    this.line = line;
    this.column = column;
    this.imagePath = imagePath;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public ModelHouse(int line, int column, String imagePath, ModelPiece piece, int r, int g, int b) {
    this.line = line;
    this.column = column;
    this.imagePath = imagePath;
    this.r = r;
    this.g = g;
    this.b = b;
    this.piece = piece;
  }

  public ModelHouse(ModelTeam team, PieceCollor collor, int line, int column, String imagePath, String pieceName, int r,
      int g, int b, int points) {
    this.line = line;
    this.column = column;
    this.imagePath = imagePath;
    this.r = r;
    this.g = g;
    this.b = b;
    this.piece = new ModelPiece(pieceName, points, collor, team, line, column, r, g, b);
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