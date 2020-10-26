package view;

import javax.swing.table.DefaultTableModel;

import controller.BoardController;

public class BoardModel extends DefaultTableModel {

  private BoardController controller;

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public void createBoard() {
    controller.createBoard();
  }

  public void setController(BoardController controller) {
    this.controller = controller;
  }
}