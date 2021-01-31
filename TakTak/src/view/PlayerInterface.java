package src.view;

import src.controller.Board;
import src.controller.Play;
import src.rede.InterfaceNetgamesServer;

public class PlayerInterface {

  protected InterfaceNetgamesServer ngames;
  protected Board board;
  protected TakTak gui;

  public PlayerInterface(TakTak gui) {
    super();
    init(gui);
  }

  public Board getBoard() {
    return board;
  }

  private void init(TakTak gui) {
    this.gui = gui;
    ngames = new InterfaceNetgamesServer();
    board = gui.getController();
    ngames.setPlayerInterface(this);
  }

  public void connect() {
    boolean conectado = ngames.isConnected();
    if (conectado) {
      gui.notify("You are already connected");
      return;
    }

    String player = gui.askPlayerName();
    String servidor = gui.getServerAddress();
    String notificacao = ngames.connect(servidor, player);
    board.registerLocalPlayer(player);
    gui.notify(notificacao);
  }

  public TakTak getGui() {
    return gui;
  }

  public boolean disconnect() {
    boolean conectado = ngames.isConnected();
    boolean updateGui = false;
    if (conectado) {
      updateGui = board.finishMatch();
      if (updateGui)
        ngames.finishMatch();
      ngames.desconectar();
      gui.notify("You are disconnected");
    } else {
      gui.notify("You are not connected");
    }
    return updateGui;
  }

  public void newMatch(Integer order, String adversaryName) {
    System.out.println("newMatch received order: " + order + " adversaryName: " + adversaryName);

    gui.newMatch();
    board.initNewMatch(order, adversaryName);
  }

  public void finishMatch() {
    board.finishMatchLocally();
    ngames.finishMatch();
    gui.notify("Match Finished - Reset!");
    gui.restart();
  }

  public void initGame() {
    if (ngames.isConnected()) {
      System.out.println("initGame is connected");
      ngames.startMatch();
    } else {
      gui.notify("You're not connected");
    }
  }

  public void receivePlay(Play play) {
    System.out.println("receivePlay received play");
    board.receivePlay(play);
  }

  public void sendPlay(Play play) {
    ngames.enviarJogada(play);
  }
}