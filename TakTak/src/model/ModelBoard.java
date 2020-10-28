package model;

public class ModelBoard {

  private static ModelBoard instance;

  private ModelTeam teamBlack;
  private ModelTeam teamWhite;

  public synchronized static ModelBoard getInstance() {
    if (instance == null) {
      instance = new ModelBoard();
    }

    return instance;
  }

  private final int maxLine = 7;
  private final int maxColumn = 6;
  public ModelHouse[][] houses;

  private ModelTeam mountTeamBlack() {
    ModelTeam teamBlack = new ModelTeam(TeamType.BLACK);
    // line 0
    teamBlack.addPiece(new ModelPiece("black_30_yellow", 30, PieceCollor.YELLOW, teamBlack, 0, 0, 255, 255, 0)); // yellow
    teamBlack.addPiece(new ModelPiece("black_40_yellow", 40, PieceCollor.YELLOW, teamBlack, 0, 1, 255, 255, 0)); // yellow
    teamBlack.addPiece(new ModelPiece("black_40_blue", 40, PieceCollor.BLUE, teamBlack, 0, 2, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_40_green", 40, PieceCollor.GREEN, teamBlack, 0, 3, 0, 128, 0)); // green
    teamBlack.addPiece(new ModelPiece("black_20_blue", 20, PieceCollor.BLUE, teamBlack, 0, 4, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_30_green", 30, PieceCollor.BLUE, teamBlack, 0, 5, 0, 0, 255)); // blue

    // line 1
    teamBlack.addPiece(new ModelPiece("black_10_yellow", 10, PieceCollor.YELLOW, teamBlack, 1, 0, 255, 255, 0)); // yellow
    teamBlack.addPiece(new ModelPiece("black_10_blue", 10, PieceCollor.BLUE, teamBlack, 1, 1, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_30_blue", 30, PieceCollor.BLUE, teamBlack, 1, 2, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_20_green", 20, PieceCollor.GREEN, teamBlack, 1, 3, 0, 128, 0)); // green
    teamBlack.addPiece(new ModelPiece("black_10_green", 10, PieceCollor.GREEN, teamBlack, 1, 4, 0, 128, 0)); // green
    teamBlack.addPiece(new ModelPiece("black_20_yellow", 20, PieceCollor.GREEN, teamBlack, 1, 5, 255, 255, 0)); // green

    return teamBlack;
  }

  private ModelTeam mountTeamWhite() {
    ModelTeam teamWhite = new ModelTeam(TeamType.WHITE);
    // line 6
    teamWhite.addPiece(new ModelPiece("white_30_yellow", 30, PieceCollor.YELLOW, teamWhite, 6, 0, 255, 255, 0)); // yellow
    teamWhite.addPiece(new ModelPiece("white_40_yellow", 40, PieceCollor.YELLOW, teamWhite, 6, 1, 255, 255, 0)); // yellow
    teamWhite.addPiece(new ModelPiece("white_40_blue", 40, PieceCollor.BLUE, teamWhite, 6, 2, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_40_green", 40, PieceCollor.GREEN, teamWhite, 6, 3, 0, 128, 0)); // green
    teamWhite.addPiece(new ModelPiece("white_20_blue", 20, PieceCollor.BLUE, teamWhite, 6, 4, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_30_green", 30, PieceCollor.BLUE, teamWhite, 6, 5, 0, 0, 255)); // blue

    // line 5
    teamWhite.addPiece(new ModelPiece("white_10_yellow", 10, PieceCollor.YELLOW, teamWhite, 5, 0, 255, 255, 0)); // yellow
    teamWhite.addPiece(new ModelPiece("white_10_blue", 10, PieceCollor.BLUE, teamWhite, 5, 1, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_30_blue", 30, PieceCollor.BLUE, teamWhite, 5, 2, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_20_green", 20, PieceCollor.GREEN, teamWhite, 5, 3, 0, 128, 0)); // green
    teamWhite.addPiece(new ModelPiece("white_10_green", 10, PieceCollor.GREEN, teamWhite, 5, 4, 0, 128, 0)); // green
    teamWhite.addPiece(new ModelPiece("white_20_yellow", 20, PieceCollor.GREEN, teamWhite, 5, 5, 255, 255, 0)); // green

    return teamWhite;
  }

  public void mount() {
    this.houses = new ModelHouse[maxLine][maxColumn];

    this.teamBlack = mountTeamBlack();
    this.teamWhite = mountTeamWhite();

    for (int line = 0; line < maxLine; line++) {
      for (int column = 0; column < maxColumn; column++) {
        if (line == 0 || line == 1) {
          this.houses[line][column] = new ModelHouse(line, column, "../view/assets/house_team.png",
              teamBlack.getPiece(line, column), 0, 0, 0);
        } else if (line == 5 || line == 6) {
          this.houses[line][column] = new ModelHouse(line, column, "../view/assets/house_team.png",
              teamWhite.getPiece(line, column), 0, 0, 0);
        } else {
          this.houses[line][column] = new ModelHouse(line, column, "../view/assets/middle.png", 0, 0, 0);
        }
      }
    }

  }

  public ModelHouse getHouse(int line, int column) {
    return houses[line][column];
  }

  public ModelPiece getPiece(int line, int column) {
    return houses[line][column].getPiece();
  }

  public void setPiece(int line, int column, ModelPiece p) {
    System.out.println("ModelBoard setPiece (line, column) = (" + line + ", " + column + "), p is null:" + p == null);
    houses[line][column].setPiece(p);
  }

  public int getLine() {
    return maxLine;
  }

  public boolean hasPiecesOnAttackField() {
    for (int line = 2; line < 5; line++) {
      for (int column = 0; column < maxColumn; column++) {
        if (this.getPiece(line, column) != null) {
          return true;
        }
      }
    }
    return false;
  }

  public int getColumn() {
    return maxColumn;
  }
}