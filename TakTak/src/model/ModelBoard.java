package model;

public class ModelBoard {

  private static ModelBoard instance;

  public synchronized static ModelBoard getInstance() {
    if (instance == null) {
      instance = new ModelBoard();
    }

    return instance;
  }

  private final int line = 6;
  private final int column = 7;
  private ModelHouse[][] houses;

  public void mount() {
    this.houses = new ModelHouse[line][column];
    this.houses[0][0] = new ModelHouse(0, 0, "../view/assets/black_10.png");
  }

}