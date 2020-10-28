package controller;

import model.TeamType;

public class Player {

  protected String name;
  protected boolean myTurn;
  protected TeamType type;
  protected boolean enterAttackZone;

  public Player(String name) {
    this.name = name;
    this.myTurn = false;
    this.enterAttackZone = false;
  }

  public void init() {
    this.myTurn = false;
    this.type = TeamType.BLACK;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFirstPlayer() {
    this.myTurn = true;
    this.type = TeamType.WHITE;
  }
}