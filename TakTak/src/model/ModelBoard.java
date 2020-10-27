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

  private final int line = 7;
  private final int column = 6;
  public ModelHouse[][] houses;

  private ModelTeam mountTeamBlack() {
    ModelTeam teamBlack = new ModelTeam();
    // line 0
    teamBlack.addPiece(new ModelPiece("black_30_yellow", 30, teamBlack, 0, 0, 255, 255, 0)); // yellow
    teamBlack.addPiece(new ModelPiece("black_40_yellow", 40, teamBlack, 0, 1, 255, 255, 0)); // yellow
    teamBlack.addPiece(new ModelPiece("black_40_blue", 40, teamBlack, 0, 2, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_40_green", 40, teamBlack, 0, 3, 0, 128, 0)); // green
    teamBlack.addPiece(new ModelPiece("black_20_blue", 20, teamBlack, 0, 4, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_30_green", 30, teamBlack, 0, 5, 0, 0, 255)); // blue

    // line 1
    teamBlack.addPiece(new ModelPiece("black_10_yellow", 10, teamBlack, 1, 0, 255, 255, 0)); // yellow
    teamBlack.addPiece(new ModelPiece("black_10_blue", 10, teamBlack, 1, 1, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_30_blue", 30, teamBlack, 1, 2, 0, 0, 255)); // blue
    teamBlack.addPiece(new ModelPiece("black_20_green", 20, teamBlack, 1, 3, 0, 128, 0)); // green
    teamBlack.addPiece(new ModelPiece("black_10_green", 10, teamBlack, 1, 4, 0, 128, 0)); // green
    teamBlack.addPiece(new ModelPiece("black_20_yellow", 20, teamBlack, 1, 5, 255, 255, 0)); // green

    return teamBlack;
  }

  private ModelTeam mountTeamWhite() {
    ModelTeam teamWhite = new ModelTeam();
    // line 6
    teamWhite.addPiece(new ModelPiece("white_30_yellow", 30, teamWhite, 6, 0, 255, 255, 0)); // yellow
    teamWhite.addPiece(new ModelPiece("white_40_yellow", 40, teamWhite, 6, 1, 255, 255, 0)); // yellow
    teamWhite.addPiece(new ModelPiece("white_40_blue", 40, teamWhite, 6, 2, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_40_green", 40, teamWhite, 6, 3, 0, 128, 0)); // green
    teamWhite.addPiece(new ModelPiece("white_20_blue", 20, teamWhite, 6, 4, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_30_green", 30, teamWhite, 6, 5, 0, 0, 255)); // blue

    // line 5
    teamWhite.addPiece(new ModelPiece("white_10_yellow", 10, teamWhite, 5, 0, 255, 255, 0)); // yellow
    teamWhite.addPiece(new ModelPiece("white_10_blue", 10, teamWhite, 5, 1, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_30_blue", 30, teamWhite, 5, 2, 0, 0, 255)); // blue
    teamWhite.addPiece(new ModelPiece("white_20_green", 20, teamWhite, 5, 3, 0, 128, 0)); // green
    teamWhite.addPiece(new ModelPiece("white_10_green", 10, teamWhite, 5, 4, 0, 128, 0)); // green
    teamWhite.addPiece(new ModelPiece("white_20_yellow", 20, teamWhite, 5, 5, 255, 255, 0)); // green

    return teamWhite;
  }

  public void mount() {
    this.houses = new ModelHouse[line][column];

    this.teamBlack = mountTeamBlack();
    this.teamWhite = mountTeamWhite();

    for (int i = 0; i < line; i++) {
      for (int j = 0; j < column; j++) {
        if (i == 0 || i == 1) {
          this.houses[i][j] = new ModelHouse(i, j, "../view/assets/house_team.png", teamBlack.getPiece(i, j), 0, 0, 0);
        } else if (i == 5 || i == 6) {
          this.houses[i][j] = new ModelHouse(i, j, "../view/assets/house_team.png", teamWhite.getPiece(i, j), 0, 0, 0);
        } else {
          this.houses[i][j] = new ModelHouse(i, j, "../view/assets/middle.png", 0, 0, 0);
        }
      }
    }

  }

  public ModelHouse getHouse(int x, int y) {
    return houses[x][y];
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }
}