package rede;

import controller.*;
import view.Player;
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
	protected Player player;
	protected boolean conectado = false;

	public InterfaceNetgamesServer() {
		super();
		this.proxy = Proxy.getInstance();
		proxy.addOuvinte(this);
	}

	public void definirInterfaceJogador(Player ator) {
		player = ator;
	}

	public String conectar(String servidor, String nome) {
		try {
			proxy.conectar(servidor, nome);
		} catch (JahConectadoException e) {
			e.printStackTrace();
			return "Voce ja esta conectado";
		} catch (NaoPossivelConectarException e) {
			e.printStackTrace();
			return "Nao foi possivel conectar";
		} catch (ArquivoMultiplayerException e) {
			e.printStackTrace();
			return "Voce esqueceu o arquivo de propriedades";
		}
		this.definirConectado(true);
		return "Sucesso: conectado a Netgames Server";
	}

	public void desconectar() {
		try {
			proxy.desconectar();
		} catch (NaoConectadoException e) {
			e.printStackTrace();
		}
		this.definirConectado(false);
	}

	public void startMatch() {
		try {
			proxy.iniciarPartida(2);
		} catch (NaoConectadoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void iniciarNovaPartida(Integer position) {
		int indiceAdversario = 1;
		if (position.equals(1))
			indiceAdversario = 2;
		String adversary = proxy.obterNomeAdversario(indiceAdversario);
		player.newMatch(position, adversary);
	}

	@Override
	public void finalizarPartidaComErro(String message) {
		player.finishMatch();
	}

	@Override
	public void receberMensagem(String msg) {
		System.out.println("Message Received of NetgamesServer: " + msg);
	}

	@Override
	public void receberJogada(Jogada jogada) {
		player.receivePlay((Play) jogada);
	}

	@Override
	public void tratarConexaoPerdida() {
		System.out.println("Conection lost");
	}

	@Override
	public void tratarPartidaNaoIniciada(String message) {
		System.out.println("Game not initiated yet");
	}

	public void enviarJogada(Play play) {
		try {
			proxy.enviaJogada(play);
		} catch (NaoJogandoException e) {
			e.printStackTrace();
		}
	}

	public boolean informarConectado() {
		return conectado;
	}

	public void definirConectado(boolean valor) {
		conectado = valor;
	}

	public void finishMatch() {
		try {
			proxy.finalizarPartida();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
