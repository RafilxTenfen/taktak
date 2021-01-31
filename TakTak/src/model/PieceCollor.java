package src.model;

public enum PieceCollor {
  BLUE(1), YELLOW(2), GREEN(3);

  public int valueCollor;

  PieceCollor(int value) {
    valueCollor = value;
  }
}