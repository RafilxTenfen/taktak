package view;

import controller.Board;
import controller.Play;
import rede.InterfaceNetgamesServer;

public class Player {

  protected InterfaceNetgamesServer ngames;
  protected Board board;
  protected TakTak gui;

  public Player(TakTak game) {
    ngames = new InterfaceNetgamesServer();
    board = new Board();
    gui = game;
  }

  public void connect() {
    boolean conectado = ngames.informarConectado();
    if (conectado) {
      gui.notify("You are already connected");
      return;
    }

    String player = gui.askPlayerName();
    String servidor = gui.getServerAddress();
    String notificacao = ngames.conectar(servidor, player);
    board.registerLocalPlayer(player);
    gui.notify(notificacao);
  }

  public boolean disconnect() {
    boolean conectado = ngames.informarConectado();
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
    board.initNewMatch(order, adversaryName);
    gui.showCurrentState();
  }

  public void finishMatch() {
    gui.notify("Match Finished");
  }

  public boolean initGame() {
    boolean connected = ngames.informarConectado();
    boolean updateInterface = false;

    if (connected) {
      updateInterface = board.finishMatch();
      if (updateInterface)
        ngames.finishMatch();
      ngames.startMatch();
    }

    return updateInterface;
  }

  public void receivePlay(Play play) {
    // board.receivePlay
    play.informarSentidoHorario();
    gui.showCurrentState();
  }
}