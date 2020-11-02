package view;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import controller.Board;

public class BoardModel extends DefaultTableModel {

  private JLabel[][] houses;
  private Board controller;

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public BoardModel(Board controller) {
    this.houses = new JLabel[7][6];
    this.controller = controller;
  }

  public int getRowCount() {
    if (controller != null) {
      return controller.getQntLine();
    }
    return 0;
  }

  @Override
  public int getColumnCount() {
    if (controller != null) {
      return controller.getQntColumn();
    }
    return 0;
  }

  public void createBoard() {
    controller.createBoard();
    System.out
        .println("BoardModel createBoard getRowCount(): " + getRowCount() + " - getColumnCount():" + getColumnCount());
    fireTableRowsInserted(getRowCount(), getColumnCount());
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return houses[rowIndex][columnIndex];
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    houses[rowIndex][columnIndex] = (JLabel) aValue;
    fireTableStructureChanged();
  }

  public void setController(Board controller) {
    this.controller = controller;
  }
}