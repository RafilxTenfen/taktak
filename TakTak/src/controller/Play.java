package controller;

import br.ufsc.inf.leobr.cliente.Jogada;
import model.ModelHouse;

public class Play implements Jogada {

  private static final long serialVersionUID = 1L;
  protected ModelHouse[][] houses;

  public Play(ModelHouse[][] houses) {
    this.houses = houses;
  }

}
