package controller;

public class Player {
  protected String name;
  protected boolean myTurn = false;

  public Player(String name) {
    this.name = name;
  }

  public void init() {
    myTurn = false;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFirstPlayer() {
    this.myTurn = true;
  }
}