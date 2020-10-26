package view;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.TitledBorder;

import controller.BoardController;

public class TakTak extends JFrame {

  private static final long serialVersionUID = 1L;
  private Player player;

  private BoardController controller = new BoardController();
  private BoardModel tableModel;
  private JTable jtbTabela;

  private JPanel jpPontuation;
  private JPanel jpTabuleiro;

  private JLabel pontuationWhite = new JLabel("White: ");
  private JLabel pontuationBlack = new JLabel("Black: ");

  private final Action actConnect = new ActionConnect();
  private final Action actDisconnect = new ActionDisconnect();
  private final Action actInitGame = new ActionInitGame();

  public TakTak() {
    initialize();
  }

  private void initialize() {
    this.player = new Player(this);
    // setSize(900, 700);
    setBounds(100, 100, 640, 720);
    getContentPane().setLayout(null);
    // 7 linhas
    // 6 colunas
    // setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setTitle("TakTak");
    initComponents();
  }

  private void initComponents() {
    Container container = this.getContentPane();
    container.setLayout(new BorderLayout());

    jpTabuleiro = new JPanel();
    jpTabuleiro.setBorder(new TitledBorder("Tabuleiro"));
    jpTabuleiro.setLayout(new FlowLayout());

    tableModel = new BoardModel();
    tableModel.setController(controller);

    jtbTabela = new JTable(tableModel);
    jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    jpTabuleiro.add(jtbTabela);
    jtbTabela.setRowHeight(60);

    jpPontuation = new JPanel();
    jpPontuation.setLayout(new FlowLayout());
    jpPontuation.setBorder(new TitledBorder("Pontuation"));
    jpPontuation.add(pontuationBlack);
    jpPontuation.add(pontuationWhite);

    container.add(BorderLayout.CENTER, jpTabuleiro);
    container.add(BorderLayout.EAST, jpPontuation);
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
    return JOptionPane.showInputDialog("What is your name?");
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
      player.connect();
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
      player.disconnect();
    }
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
      player.initGame();
    }
  }

}
