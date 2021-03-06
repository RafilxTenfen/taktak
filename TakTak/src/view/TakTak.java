package view;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.TitledBorder;

import controller.Board;
import controller.Observer;
import controller.Play;
import controller.Player;
import model.ModelBoard;
import model.ModelHouse;
import model.ModelPiece;
import model.TeamType;

public class TakTak extends JFrame implements Observer {

  private static final long serialVersionUID = 1L;
  private PlayerInterface playerInterface;
  private String title;

  private Board controller;
  private BoardModel tableModel;
  private JTable jtbTabela;

  private JPanel jpTabuleiro;

  private final Action actConnect = new ActionConnect();
  private final Action actDisconnect = new ActionDisconnect();
  private final Action actInitGame = new ActionInitGame();

  public TakTak() {
    initialize();
  }

  public Board getController() {
    return controller;
  }

  private void initialize() {
    controller = new Board();
    controller.addObserver(this);

    this.playerInterface = new PlayerInterface(this);
    setBounds(100, 100, 640, 720);
    getContentPane().setLayout(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    title = "White vs Black";

    initComponents();
  }

  private void initComponents() {
    Container container = this.getContentPane();
    container.setLayout(new BorderLayout());

    jpTabuleiro = new JPanel();
    jpTabuleiro.setBorder(new TitledBorder(title));
    jpTabuleiro.setLayout(new FlowLayout());

    tableModel = new BoardModel(controller);

    jtbTabela = new JTable(tableModel);
    jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    BoardCellRenderer boardCellRenderer = new BoardCellRenderer();
    jtbTabela.setDefaultRenderer(Object.class, boardCellRenderer);

    jpTabuleiro.add(jtbTabela);
    jtbTabela.setRowHeight(83);
    jtbTabela.addMouseListener(new TableMouseListener());

    container.add(BorderLayout.CENTER, jpTabuleiro);
    initMenu();
  }

  private void initMenu() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu mnNewMenu = new JMenu("Game");
    menuBar.add(mnNewMenu);

    JMenuItem mntmDisconect = new JMenuItem("Disconect");
    mntmDisconect.setAction(actDisconnect);
    mnNewMenu.add(mntmDisconect);

    JMenuItem mntmConnect = new JMenuItem("Connect");
    mntmConnect.setAction(actConnect);
    mnNewMenu.add(mntmConnect);

    JMenuItem mntmInitGame = new JMenuItem("Init Game");
    mntmInitGame.setAction(actInitGame);
    mnNewMenu.add(mntmInitGame);
  }

  public String askPlayerName() {
    String name = JOptionPane.showInputDialog("What is your name?");
    setTitle("TakTak game of " + name);
    return name;
  }

  public String getServerAddress() {
    String idServer = ("localhost");
    idServer = JOptionPane.showInputDialog(null, "Inform the server address", idServer);
    return idServer;
  }

  public void showCurrentState() {
    JOptionPane.showMessageDialog(null, "State");
  }

  public void notify(String notification) {
    JOptionPane.showMessageDialog(null, notification);
  }

  public void newMatch() {
    jtbTabela.setEnabled(true);
    jtbTabela.setVisible(true);
    tableModel.createBoard();
    this.actInitGame.setEnabled(false);
  }

  private class TableMouseListener implements MouseListener {

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
      int line = jtbTabela.getSelectedRow();
      int column = jtbTabela.getSelectedColumn();

      try {
        System.out.println("Mouse clicked on the board (LINE, COLUMN) = (" + line + ", " + column + ")");
        controller.boardClicked(line, column);
      } catch (Exception ex) {
        System.out.println(ex);
      }

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }

  }

  private class ActionConnect extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ActionConnect() {
      putValue(NAME, "Connect");
      putValue(SHORT_DESCRIPTION, "Connect to Netgames Server");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      playerInterface.connect();
    }
  }

  private class ActionDisconnect extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ActionDisconnect() {
      putValue(NAME, "Disconnect");
      putValue(SHORT_DESCRIPTION, "Disconnect to Netgames Server");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      playerInterface.disconnect();
    }
  }

  public static void main(String[] args) throws Exception {
    TakTak frame = new TakTak();
    frame.setVisible(true);
  }

  private class ActionInitGame extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ActionInitGame() {
      putValue(NAME, "Init Game");
      putValue(SHORT_DESCRIPTION, "Init TakTak Game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      System.out.println("Action Performed of init game");
      this.setEnabled(false);
      playerInterface.initGame();
    }
  }

  @Override
  public void initBoard(ModelHouse[][] houses) {
    System.out.println("TakTak initBoard len houses: " + houses.length);
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 6; j++) {

        try {
          JLabel l = new JLabel(getIcon(ModelBoard.getInstance().getHouse(i, j)));

          jtbTabela.add(l);
          System.out.println("TakTak initBoard tableModel: i: " + i + " - j: " + j);
          tableModel.setValueAt(l, i, j);
        } catch (Exception e) {
          // do nothing, jtbTabela.add(l); generate a error
        }
      }
    }
    this.actInitGame.setEnabled(false);
  }

  public Icon getIcon(ModelHouse house) {
    String path = house.getImagePath();
    ImageIcon imgIcon = new ImageIcon(getClass().getResource(path));
    Image image = imgIcon.getImage();
    Image newimg = image.getScaledInstance(80, 85, java.awt.Image.SCALE_SMOOTH);
    return new ImageIcon(newimg);
  }

  @Override
  public void notifySelection(int line, int column, TeamType type) {
    System.out.println("Tak Tak notifySelection (LINE, COLUMN) = (" + line + ", " + column + ")");
    JLabel atual = (JLabel) tableModel.getValueAt(line, column);
    atual.setBorder(BorderFactory.createLineBorder(Color.red));
    jtbTabela.repaint();
  }

  @Override
  public void endMatch() {

  }

  @Override
  public void notifyClearSelection(int line, int column, TeamType type) {
    JLabel atual = (JLabel) tableModel.getValueAt(line, column);
    atual.setBorder(BorderFactory.createEmptyBorder());
    jtbTabela.repaint();
  }

  @Override
  public void notifyMovement(int previousLine, int previousColumn, int line, int column, ModelHouse[][] houses) {
    updateJLabel(previousLine, previousColumn, houses[previousLine][previousColumn]);
    updateJLabel(line, column, houses[line][column]);
    jtbTabela.repaint();
  }

  public void updateJLabel(int line, int column, ModelHouse house) {
    JLabel jLabel = (JLabel) tableModel.getValueAt(line, column);
    jLabel.setIcon(getIcon(house));
    jLabel.setBorder(BorderFactory.createEmptyBorder());
  }

  @Override
  public void notifyEndGame(controller.Player local, controller.Player remote) {
    JOptionPane.showMessageDialog(null, "The game is finished! \nPoints: \n" + local.type.str() + ": " + local.points
        + "\n" + remote.type.str() + ": " + remote.points);
    System.out.println("notifyEndGame JOptionPane.showMessageDialog");
    this.playerInterface.finishMatch();
  }

  public void restart() {
    System.out.println("TAKTAK received restart");

    ModelBoard.getInstance().reset();
    try {
      for (int i = 0; i < 7; i++) {
        for (int j = 0; j < 6; j++) {
          JLabel l = new JLabel();
          jtbTabela.add(l);
          tableModel.setValueAt(l, i, j);
        }
      }
    } catch (Exception e) {
      // do nothing, jtbTabela.add(l); generate a error
    }
    this.actInitGame.setEnabled(true);
  }

  @Override
  public void setTitleFromPlayers(Player local, Player remote) {
    System.out.println("Taktak setTitleFromPlayers local: ");
    if (local.myTurn) {
      this.title = playerTitleTurn(local);
    } else {
      this.title = playerTitleTurn(remote);
    }
    jpTabuleiro.setBorder(new TitledBorder(title));
  }

  public String playerTitleTurn(Player p) {
    return "Player turn: " + p.name + " - Team: " + p.type.str();
  }

  @Override
  public void sendPlay(ModelPiece selectedPiece, int line, int column) {
    System.out.println("TakTak sendPlay (line, column) = (" + line + ", " + column + ")");

    playerInterface.sendPlay(new Play(line, column, selectedPiece.getLine(), selectedPiece.getColumn()));
  }

}
