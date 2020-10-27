package view;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import controller.BoardController;

public class BoardModel extends DefaultTableModel {

  private JLabel[][] houses = new JLabel[7][6];
  private BoardController controller;

  /**
   *
   */
  private static final long serialVersionUID = 1L;

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

  public void setController(BoardController controller) {
    this.controller = controller;
  }
}