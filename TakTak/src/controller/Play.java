package controller;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Play implements Jogada {

  private static final long serialVersionUID = 1L;
  protected boolean giro = false;
  protected int linha = 0;
  protected int coluna = 0;
  protected int quadrante = 0;
  protected int sentido = 0;

  public Play(boolean defGiro) {
    giro = defGiro;
  }

  public void definirPosicao(int aLinha, int aColuna) {
    linha = aLinha;
    coluna = aColuna;
    giro = false;
  }

  public void definirGiro(int oQuadrante, int oSentido) {
    quadrante = oQuadrante;
    sentido = oSentido;
    giro = true;
  }

  public int informarLinha() {
    return linha;
  }

  public int informarColuna() {
    return coluna;
  }

  public boolean informarGiro() {
    return giro;
  }

  public int informarQuadrante() {
    return quadrante;
  }

  public int informarSentido() {
    return sentido;
  }

  public boolean informarSentidoHorario() {
    // sentido == 0 -> horario
    // sentido == 1 -> anti-horario
    return sentido == 0;
  }

}
