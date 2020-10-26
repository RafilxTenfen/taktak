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

  public void createBoard() {
    board = ModelBoard.getInstance();
    board.mount();
  }

}