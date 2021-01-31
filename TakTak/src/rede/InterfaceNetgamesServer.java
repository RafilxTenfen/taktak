package src.rede;

import src.controller.*;
import src.view.PlayerInterface;
import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;

public class InterfaceNetgamesServer implements OuvidorProxy {

	private static final long serialVersionUID = 1L;
	protected Proxy proxy;
	protected PlayerInterface playerInterface;
	protected boolean connected = false;

	public InterfaceNetgamesServer() {
		super();
		this.proxy = Proxy.getInstance();
		proxy.addOuvinte(this);
	}

	public void setPlayerInterface(PlayerInterface ator) {
		playerInterface = ator;
	}

	public void iniciarPartida() {
		try {
			proxy.iniciarPartida(2);
		} catch (NaoConectadoException e) {
			e.printStackTrace();
		}
	}

	public String connect(String servidor, String nome) {
		try {
			proxy.conectar(servidor, nome);
		} catch (JahConectadoException e) {
			e.printStackTrace();
			return "You're already connected";
		} catch (NaoPossivelConectarException e) {
			e.printStackTrace();
			return "It was not possible to connect";
		} catch (ArquivoMultiplayerException e) {
			e.printStackTrace();
			return "You forgot the properties file";
		}
		this.setConnected(true);
		return "Sucess: connected at Netgames Server";
	}

	public void desconectar() {
		try {
			proxy.desconectar();
		} catch (NaoConectadoException e) {
			e.printStackTrace();
		}
		this.setConnected(false);
	}

	public boolean startMatch() {
		try {
			proxy.iniciarPartida(2);
		} catch (NaoConectadoException e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void iniciarNovaPartida(Integer position) {
		System.out.println("iniciarNovaPartida position: " + position);
		int indiceAdversario = 1;
		if (position.equals(1)) {
			indiceAdversario = 2;
		}

		System.out.println("iniciarNovaPartida BEFORE proxy.obterNomeAdversario");
		String adversary = proxy.obterNomeAdversario(indiceAdversario);
		System.out.println("iniciarNovaPartida AFTER proxy.obterNomeAdversario");

		playerInterface.newMatch(position, adversary);
	}

	@Override
	public void finalizarPartidaComErro(String message) {
		System.out.println("InterfaceNetgames finalizarPartidaComErro" + message);
	}

	@Override
	public void receberMensagem(String msg) {
		System.out.println("Message Received of NetgamesServer: " + msg);
	}

	@Override
	public void receberJogada(Jogada jogada) {
		playerInterface.receivePlay((Play) jogada);
	}

	@Override
	public void tratarConexaoPerdida() {
		System.out.println("Conection lost");
	}

	@Override
	public void tratarPartidaNaoIniciada(String message) {
		playerInterface.getGui().notify("Game could not be initiated yet");
		System.out.println("Game not initiated yet");
	}

	public void enviarJogada(Play play) {
		try {
			proxy.enviaJogada(play);
		} catch (NaoJogandoException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return this.connected;
	}

	public void setConnected(boolean valor) {
		this.connected = valor;
	}

	public void finishMatch() {
		try {
			proxy.finalizarPartida();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

}
