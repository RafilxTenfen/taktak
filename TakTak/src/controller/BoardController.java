package controller;

import java.util.ArrayList;
import java.util.List;

import model.ModelBoard;

public class BoardController implements Observable {

  private ModelBoard board;
  private List<Observer> observers = new ArrayList<>();

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  public int getQntLine() {
    if (board == null) {
      return 0;
    }
    return board.getLine();
  }

  public int getQntColumn() {
    if (board == null) {
      return 0;
    }
    return board.getColumn();
  }

  public void createBoard() {
    board = ModelBoard.getInstance();
    board.mount();

    for (Observer o : observers) {
      o.initBoard(board.houses);
    }
  }

}