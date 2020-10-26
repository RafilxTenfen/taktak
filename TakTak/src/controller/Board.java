package controller;

public class Board {

  protected boolean matchInProgress = false;
  protected boolean playInProgress = false;
  protected Player localPlayer;
  protected Player remotePlayer;

  public Board() {
    init();
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

  public void finishMatchLocally() {
    this.clean();
  }

  public void clean() {
    this.init();
  }
}