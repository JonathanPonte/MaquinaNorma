import java.util.List;

public class Instrucao {
	
	private Comando comando;
	private List<String> argumentos;
	private List<Instrucao> instrucoes;
	
	
	public Instrucao(Comando comando, List<String> argumentos, List<Instrucao> instrucoes) {
		super();
		this.comando = comando;
		this.argumentos = argumentos;
		this.instrucoes = instrucoes;
	}

	public Comando getComando() {
		return comando;
	}


	public void setComando(Comando comando) {
		this.comando = comando;
	}


	public List<String> getArgumentos() {
		return argumentos;
	}


	public void setArgumentos(List<String> argumentos) {
		this.argumentos = argumentos;
	}


	public List<Instrucao> getInstrucoes() {
		return instrucoes;
	}


	public void setInstrucoes(List<Instrucao> instrucoes) {
		this.instrucoes = instrucoes;
	}
	
	
	

	

}
