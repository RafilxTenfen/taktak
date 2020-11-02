package controller;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Play implements Jogada {

  private static final long serialVersionUID = 1L;
  protected int line;
  protected int column;
  protected int previousLine;
  protected int previousColumn;

  public Play(int line, int column, int previousLine, int previousColumn) {
    this.line = line;
    this.column = column;
    this.previousColumn = previousColumn;
    this.previousLine = previousLine;
  }

}
