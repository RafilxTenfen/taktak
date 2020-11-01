package controller;

import model.TeamType;

public class Player {

  public String name;
  public boolean myTurn;
  public TeamType type;
  public boolean enterAttackZone;
  public int points;

  public Player(String name) {
    this.name = name;
    this.myTurn = false;
    this.enterAttackZone = false;
    this.type = TeamType.BLACK;
    this.points = 0;
  }

  public void invertTurn() {
    this.myTurn = !this.myTurn;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  // WHITE START FIRST
  public void setFirstPlayer() {
    this.myTurn = true;
    this.type = TeamType.WHITE;
  }
}