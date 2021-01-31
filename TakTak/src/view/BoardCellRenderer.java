package src.view;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class BoardCellRenderer extends DefaultTableCellRenderer {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column) {

    JLabel jlb = (JLabel) value;
    return jlb;
  }

  public BoardCellRenderer() {
  }

}
