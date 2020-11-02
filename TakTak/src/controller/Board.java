package controller;

import java.util.ArrayList;
import java.util.List;
import model.ModelBoard;
import model.ModelPiece;
import model.TeamType;

public class Board implements Observable {

  protected boolean matchInProgress = false;
  protected boolean playInProgress = false;
  protected Player localPlayer;
  protected Player remotePlayer;
  protected ModelPiece selectedPiece;
  private List<Observer> observers;

  public Board() {
    init();
    this.observers = new ArrayList<Observer>();
  }

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  public int getQntLine() {
    return ModelBoard.getInstance().getLine();
  }

  public int getQntColumn() {
    return ModelBoard.getInstance().getColumn();
  }

  public void createBoard() {
    ModelBoard.getInstance().mount();

    for (Observer o : observers) {
      o.initBoard(ModelBoard.getInstance().houses);
    }
  }

  private void init() {
    matchInProgress = false;
    playInProgress = false;
    this.selectedPiece = null;
    if (this.localPlayer != null) {
      this.localPlayer.reset();
    }
    if (this.remotePlayer != null) {
      this.remotePlayer.reset();
    }
  }

  public void initNewMatch(Integer ordem, String adversaryName) {
    this.clean();
    remotePlayer = new Player(adversaryName);
    if (ordem.equals(1)) {
      localPlayer.setFirstPlayer();
      remotePlayer.setSecondPlayer();
    } else {
      remotePlayer.setFirstPlayer();
      localPlayer.setSecondPlayer();
    }
    matchInProgress = true;
    notifyTitleFromPlayers();
  }

  public Player getCurrentPlayer() {
    if (localPlayer.myTurn) {
      return localPlayer;
    }
    return remotePlayer;
  }

  public void setCurrentPlayerAttackZone() {
    getCurrentPlayer().enterAttackZone = true;
  }

  public boolean bothPlayersEntereredInAttackZone() {
    return localPlayer.enterAttackZone && remotePlayer.enterAttackZone;
  }

  public boolean isAnyPieceInTheAttackZone(TeamType type) {
    for (int i = 2; i <= 4; i++) {
      for (int j = 0; j < 6; j++) {
        ModelPiece piece = ModelBoard.getInstance().getPiece(i, j);
        if (piece != null) {
          if (piece.getTeam().type == type) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public void registerLocalPlayer(String playerName) {
    localPlayer = new Player(playerName);
  }

  public boolean finishMatch() {
    if (matchInProgress) {
      this.finishMatchLocally();
      return true;
    }
    return false;
  }

  public void selectPiece(int line, int column) {
    this.selectedPiece = ModelBoard.getInstance().getPiece(line, column);
  }

  public void finishMatchLocally() {
    this.clean();
  }

  public void clean() {
    this.init();
  }

  public void notifySelection(int line, int column, TeamType type) {
    System.out.println("Board boardClicked notifySelection (line, column) = (" + line + ", " + column + ")"
        + "\n Observers: " + observers.size());

    for (Observer o : observers) {
      o.notifySelection(line, column, type);
    }
  }

  public void notifyMovement(int previousLine, int previousColumn, int line, int column) {
    for (Observer o : observers) {
      o.notifyMovement(previousLine, previousColumn, line, column, ModelBoard.getInstance().houses);
    }
  }

  public void notifyEndGame(Player local, Player loser) {
    calculatePoints(local);
    calculatePoints(loser);

    for (Observer o : observers) {
      o.notifyEndGame(local, loser);
    }
  }

  public int calculatePoints(Player p) {
    // if player is from team black it should calculate
    // the points in the adversary field
    int initI = 5;
    int total = 0;

    if (p.type == TeamType.WHITE) {
      initI = 0;
    }

    for (int line = initI; line <= initI + 1; line++) {
      for (int column = 0; column < 6; column++) {
        ModelPiece piece = ModelBoard.getInstance().getPiece(line, column);
        if (piece != null) {
          total += piece.calculatePoints();
        }
      }
    }

    p.setPoints(total);
    return total;
  }

  public void notifyClearSelection() {
    if (this.selectedPiece == null) {
      return;
    }
    for (Observer o : observers) {
      o.notifyClearSelection(this.selectedPiece.getLine(), this.selectedPiece.getColumn(),
          this.selectedPiece.getTeam().type);
    }
  }

  public void notifyTitleFromPlayers() {
    System.out.println("Board notifyTitleFromPlayers: " + observers.size());

    for (Observer o : observers) {
      o.setTitleFromPlayers(this.localPlayer, this.remotePlayer);
    }
  }

  public void notifyUser(String text) {
    for (Observer o : observers) {
      o.notify(text);
    }
  }

  public void notifySendPlay(int line, int column) {
    for (Observer o : observers) {
      o.sendPlay(this.selectedPiece, line, column);
    }
  }

  public void boardClicked(int line, int column) throws Exception {
    if (!this.localPlayer.myTurn) {
      notifyUser("Wait for your turn to play");
      return;
    }

    System.out.println("Board boardClicked (line, column) = (" + line + ", " + column + ")");
    ModelPiece piece = ModelBoard.getInstance().getPiece(line, column);

    if (this.selectedPiece == null) { // if none piece was selected
      if (piece != null) { // select one piece
        if (this.localPlayer.type != piece.getTeam().type) {
          notifyUser("Select a piece of your side: " + this.localPlayer.type.str());
          return;
        }

        this.selectPiece(line, column);
        System.out.println("Board boardClicked BEFORE notifySelection (line, column) = (" + line + ", " + column + ")");
        this.notifySelection(line, column, this.localPlayer.type);
      } else {
        notifyUser("Select a piece of the collor: " + this.localPlayer.type.str());
      }

      return;
    }

    // if a piece is already selected
    if (selectedPiece.getLine() == line && selectedPiece.getColumn() == column) {// if selected the same piece again
      System.out.println("Board boardClicked THE SAME PIECE AGAIN (line, column) = (" + line + ", " + column + ")");
      // clear the selection
      this.notifyClearSelection();
      this.selectedPiece = null;
      return;
    }

    if (canMove(this.selectedPiece, line, column)) {
      notifySendPlay(line, column);
      if (this.localPlayer.myTurn) {
        runPlay(line, column);
      }
    }
    return;
  }

  public void runPlay(int line, int column) {
    if (canMove(this.selectedPiece, line, column)) {
      int previousLine = this.selectedPiece.getLine();
      int previousColumn = this.selectedPiece.getColumn();

      if (!getCurrentPlayer().enterAttackZone) {
        if (isAttackZone(line) && this.selectedPiece.getTeam().type == getCurrentPlayer().type) {
          setCurrentPlayerAttackZone();
        }
      }

      if (ModelBoard.getInstance().getPiece(line, column) != null) { // if exists a piece in the selected place on board
        movePiece(selectedPiece, ModelBoard.getInstance().getPiece(line, column));
      } else {
        movePiece(selectedPiece, line, column);
      }

      System.out.println("Board boardClicked BEFORE notifyMovement (line, column) = (" + line + ", " + column + ")"
          + "(previousLine, previousColumn) = (" + previousLine + ", " + previousColumn + ")");
      this.selectedPiece = null;
      notifyMovement(previousLine, previousColumn, line, column);

      // verify end game 2 players already entered the attack zone at least one time,
      // but there is no
      // piece in the attack zone from their team type
      System.out.println("Board boardClicked BEFORE bothPlayersEntereredInAttackZone \nlocal enter attack: "
          + this.localPlayer.enterAttackZone + "\nremote enter attack: " + this.remotePlayer.enterAttackZone);
      if (bothPlayersEntereredInAttackZone()) {
        System.out.println("Board boardClicked bothPlayersEntereredInAttackZone()");

        if (!isAnyPieceInTheAttackZone(this.localPlayer.type)) {
          System.out.println(
              "Board boardClicked bothPlayersEntereredInAttackZone() isAnyPieceInTheAttackZone(this.localPlayer.type) - "
                  + localPlayer.type.str());
          notifyEndGame(this.localPlayer, this.remotePlayer);
        }

        if (!isAnyPieceInTheAttackZone(this.remotePlayer.type)) {
          System.out.println(
              "Board boardClicked bothPlayersEntereredInAttackZone() isAnyPieceInTheAttackZone(this.remotePlayer.type) - "
                  + remotePlayer.type.str());
          notifyEndGame(this.remotePlayer, this.localPlayer);
        }
      }

      invertTurn();
      notifyTitleFromPlayers();
      return;
    }

    System.out.println("Board boardClicked PIECE CAN NOT MOVE (line, column) = (" + line + ", " + column
        + ") - Selected piece name: " + this.selectedPiece.getName() + "(line, column) = ("
        + this.selectedPiece.getLine() + ", " + this.selectedPiece.getColumn() + ")");
  }

  public void receivePlay(Play p) {
    this.selectedPiece = ModelBoard.getInstance().getPiece(p.previousLine, p.previousColumn);

    this.runPlay(p.line, p.column);
  }

  public void invertTurn() {
    this.localPlayer.invertTurn();
    this.remotePlayer.invertTurn();
  }

  public void movePiece(ModelPiece selectedPiece, ModelPiece pieceAttacked) {
    ModelBoard.getInstance().setPiece(selectedPiece.getLine(), selectedPiece.getColumn(), null);
    selectedPiece.attack(pieceAttacked);

    ModelBoard.getInstance().setPiece(pieceAttacked.getLine(), pieceAttacked.getColumn(), selectedPiece);
  }

  public void movePiece(ModelPiece selectedPiece, int line, int column) {
    ModelBoard.getInstance().setPiece(selectedPiece.getLine(), selectedPiece.getColumn(), null);

    selectedPiece.move(line, column);
    ModelBoard.getInstance().setPiece(line, column, selectedPiece);
  }

  // returns true if the selected piece can move to the selected space
  public static boolean canMove(ModelPiece alreadySelectedPiece, int line, int column) {
    // verify if it's moving foward
    if (alreadySelectedPiece.getTeam().type == TeamType.WHITE) {
      if (!(line == alreadySelectedPiece.getLine() - 1 && validColumn(alreadySelectedPiece.getColumn(), column))) {
        return false;
      }
    } else {
      if (!(line == alreadySelectedPiece.getLine() + 1 && validColumn(alreadySelectedPiece.getColumn(), column))) {
        return false;
      }
    }

    ModelPiece piece = ModelBoard.getInstance().getPiece(line, column); // current house selected
    if (piece != null) { // if exists a piece, have to verify if can attack
      if (alreadySelectedPiece.getTeam().type != piece.getTeam().type) {
        if (!isAttackZone(line)) { // if is tryng to attack the oponent, it should be in the attack zone
          System.out.println("Tryng to attack the opponet outside of attack zone");
          return false;
        }
      }
      return ModelPiece.canAttack(alreadySelectedPiece, piece);
    }
    return true;
  }

  public static boolean isAttackZone(int line) {
    return line >= 2 && line <= 4;
  }

  public static boolean validColumn(int selectedColumn, int column) {
    return column == selectedColumn || column == selectedColumn - 1 || column == selectedColumn + 1;
  }
}