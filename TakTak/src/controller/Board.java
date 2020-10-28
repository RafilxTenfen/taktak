package controller;

import java.util.ArrayList;
import java.util.List;
import model.ModelBoard;
import model.ModelHouse;
import model.ModelPiece;
import model.TeamType;

public class Board implements Observable {

  protected boolean matchInProgress = false;
  protected boolean playInProgress = false;
  protected Player localPlayer;
  protected Player remotePlayer;
  protected ModelPiece selectedPiece;
  private ModelBoard board;
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

  private void init() {
    matchInProgress = false;
    playInProgress = false;
  }

  public void initNewMatch(Integer ordem, String adversaryName) {
    this.clean();
    localPlayer.init();
    remotePlayer = new Player(adversaryName);
    if (ordem.equals(1))
      localPlayer.setFirstPlayer();
    else
      remotePlayer.setFirstPlayer();
    matchInProgress = true;
    // this.definirEstadoInicial();
  }

  public Player getCurrentPlayer() {
    if (localPlayer.myTurn) {
      return localPlayer;
    }
    return remotePlayer;
  }

  public void registerLocalPlayer(String playerName) {
    localPlayer = new Player(playerName);
    localPlayer.init();
  }

  public boolean finishMatch() {
    if (matchInProgress) {
      this.finishMatchLocally();
    }
    return matchInProgress;
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

  public void notifyClearSelection() {
    if (this.selectedPiece == null) {
      return;
    }
    for (Observer o : observers) {
      o.notifyClearSelection(this.selectedPiece.getLine(), this.selectedPiece.getColumn(),
          this.selectedPiece.getTeam().type);
    }
  }

  public void boardClicked(int line, int column) throws Exception {
    System.out.println("Board boardClicked (line, column) = (" + line + ", " + column + ")");
    ModelPiece piece = ModelBoard.getInstance().getHouse(line, column).getPiece();

    if (this.selectedPiece == null) { // if none piece was selected
      if (piece != null) { // select one piece
        this.selectPiece(line, column);
        System.out.println("Board boardClicked BEFORE notifySelection (line, column) = (" + line + ", " + column + ")");
        // this.notifySelection(line, column, getCurrentPlayer().type);
        this.notifySelection(line, column, TeamType.WHITE); // temp white for tests
      }

      return;
    }
    // if a piece is already selected

    if (selectedPiece.getLine() == line && selectedPiece.getColumn() == column) {// if selected the same piece again
      // clear the selection
      this.selectedPiece = null;
      this.notifyClearSelection();
      return;
    }

    if (canMove(this.selectedPiece, line, column)) {
      int previousLine = this.selectedPiece.getLine();
      int previousColumn = this.selectedPiece.getColumn();

      if (ModelBoard.getInstance().getPiece(line, column) != null) { // if exists a piece in the selected place on board
        movePiece(selectedPiece, ModelBoard.getInstance().getPiece(line, column));
      } else {
        movePiece(selectedPiece, line, column);
      }

      System.out.println("Board boardClicked BEFORE notifyMovement (line, column) = (" + line + ", " + column + ")"
          + "(previousLine, previousColumn) = (" + previousLine + ", " + previousColumn + ")");
      this.selectedPiece = null;
      notifyMovement(previousLine, previousColumn, line, column);
      return;
    }

    System.out.println("Board boardClicked PIECE CAN NOT MOVE (line, column) = (" + line + ", " + column
        + ") - Selected piece name: " + this.selectedPiece.getName() + "(line, column) = ("
        + this.selectedPiece.getLine() + ", " + this.selectedPiece.getColumn() + ")");
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
      return ModelPiece.canAttack(alreadySelectedPiece, piece);
    }
    return true;
  }

  public static boolean validColumn(int selectedColumn, int column) {
    return column == selectedColumn || column == selectedColumn - 1 || column == selectedColumn + 1;
  }
}