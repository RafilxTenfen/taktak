package src.controller;

import src.model.TeamType;

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

  public void init() {
    this.type = TeamType.BLACK;
    this.myTurn = false;
    this.points = 0;
    this.enterAttackZone = false;
  }

  public void reset() {
    this.myTurn = false;
    this.points = 0;
    this.enterAttackZone = false;
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

  public void setSecondPlayer() {
    this.myTurn = false;
    this.type = TeamType.BLACK;
  }

  @Override
  public String toString() {
    return "Name: " + this.name + " myTurn: " + this.myTurn + " - type: " + this.type.str();
  }
}