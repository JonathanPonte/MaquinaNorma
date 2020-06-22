import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Norma {
	private int[] registradores;
	private List<Instrucao> instrucoes;
	private int pularBlocoIfWhile;
	private Integer valorGoToIfWhile;

	public Norma(int numeroRegistradores) {
		this.registradores = new int[numeroRegistradores];
		this.instrucoes = new ArrayList<>();
	}

	public void acao() throws Exception {

		for (int i = 0; i < instrucoes.size(); i++) {

			Instrucao instrucao = instrucoes.get(i);
			Comando comando = instrucao.getComando();

			switch (comando) {
			case ISZERO:

				break;
			case GOTO:
				i = goTo(instrucao);
				break;
			case IF:
				checarCondicaoIfElse(instrucao);
				if(valorGoToIfWhile != null) {
					i = valorGoToIfWhile;
					valorGoToIfWhile = null;
				}
				break;
			case WHILE:
				checarCondicaoWhile(instrucao);
				if(valorGoToIfWhile != null) {
					i = valorGoToIfWhile;
					valorGoToIfWhile = null;
				}
				break;
			default:
				executarInstrucao(instrucao);
			}

		}

	}

	
	public int goTo(Instrucao instucao) {

		for (int i = 0; i < instrucoes.size(); i++) {

			Instrucao interna = instrucoes.get(i);

			if (interna.getArgumentos().get(0).equals(":" + instucao.getArgumentos().get(0))) {
				return i;
			}

		}

		return 0;

	}

	
	public void checarCondicaoWhile(Instrucao instrucao) throws Exception {
		
		int registrador = registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];
		
		System.out.println(" WHILE ");

		while(registrador == 0) {
			
			if(valorGoToIfWhile == null) {
				execultarCondicaoIfElseWhile(instrucao);
				registrador = registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];		
			}else {
				registrador = 1;
			}
		}
		
	}

	public void checarCondicaoIfElse(Instrucao instrucao) throws Exception {

		int registrador = registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];

		if (registrador == 0) {
			
			System.out.println("IF " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			
			execultarCondicaoIfElseWhile(instrucao);

		} else {

			
			Instrucao instrucaoElse = procurarInstrucaoElse(instrucao);

			if (instrucaoElse != null) {
				
				System.out.println("ELSE");
				
				execultarCondicaoIfElseWhile(instrucaoElse);

			}

		}

	}

	
	public void execultarCondicaoIfElseWhile(Instrucao instrucao) throws Exception {
		
		List<Instrucao> instrucoesInterna = instrucao.getInstrucoes();

		for (int i = 0; i < instrucoesInterna.size(); i++) {

			Instrucao instrucaoInterna = instrucoesInterna.get(i);

			if (instrucaoInterna.getComando() == Comando.IF) {
				checarCondicaoIfElse(instrucaoInterna);
			} else if (instrucaoInterna.getComando() == Comando.WHILE) {
				checarCondicaoWhile(instrucaoInterna);
			} else {

				if (instrucaoInterna.getComando() == Comando.ELSE)
					continue;
				if (instrucaoInterna.getComando() == Comando.GOTO) {
					valorGoToIfWhile  = goTo(instrucaoInterna);
					break;
				}
					

				executarInstrucao(instrucaoInterna);
			}

		}
		
	}
	
	
	public Instrucao procurarInstrucaoElse(Instrucao instrucao) {

		List<Instrucao> instrucoesInternaIf = instrucao.getInstrucoes();

		for (int i = 0; i < instrucoesInternaIf.size(); i++) {

			Instrucao instrucaoInternaIf = instrucoesInternaIf.get(i);

			if (instrucaoInternaIf.getComando() == Comando.ELSE) {
				return instrucaoInternaIf;
			}

		}

		return null;

	}

	public void executarInstrucao(Instrucao instrucao) throws Exception {
		Comando comando = instrucao.getComando();

		switch (comando) {
		case INC:
			System.out.println("INC " + instrucao.getArgumentos().get(0));
			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))]++;
			imprimirRegistradores();
			break;
		case DEC:
			System.out.println("DEC " + instrucao.getArgumentos().get(0));
			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))]--;
			imprimirRegistradores();
			break;
		case SETZERO:
			System.out.println("SET0 " + instrucao.getArgumentos().get(0));
			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))] = 0;
			imprimirRegistradores();
			break;
		case SET:
			System.out.println("SET " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))] = Integer
					.parseInt(instrucao.getArgumentos().get(1));
			imprimirRegistradores();
			break;
		case ADD:
			System.out.println("ADD " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))] = registradores[Integer
					.parseInt(instrucao.getArgumentos().get(0))]
					+ registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];
			imprimirRegistradores();
			break;
		default:
			break;
		}

	}

	public void imprimirRegistradores() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("R0:" + registradores[0] + " " + "R1:" + registradores[1] + " " + "R2:" + registradores[2]
				+ " " + "R3:" + registradores[3] + " " + "R4:" + registradores[4] + "\n" + "R5:" + registradores[5]
				+ " " + "R6:" + registradores[6] + " " + "R7:" + registradores[7] + " " + "R8:" + registradores[8] + " "
				+ "R9:" + registradores[9] + " ");

	}

	public void receberInstrucoes(String codigo) throws Exception {
		List<String> instucoesString = new ArrayList<String>(Arrays.asList(codigo.split(";")));

		for (int i = 0; i < instucoesString.size(); i++) {
			String instrucao = instucoesString.get(i);
			pularBlocoIfWhile = 0;

			List<String> linha = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			validarComando(instrucao);
			validarArgumentos(linha);

			Instrucao inst = gerarInstrucao(linha, instucoesString, i);

			if (inst != null) {
				if (inst.getComando() == Comando.IF || inst.getComando() == Comando.WHILE || inst.getComando() == Comando.ELSE) {
					pularBlocoIfWhile = 0;
					pularBlocosIfWhile(instucoesString, i);
					i = i + pularBlocoIfWhile;
				}
				instrucoes.add(inst);
			}

		}

	}

	public Instrucao gerarInstrucao(List<String> linha, List<String> instucoesString, int i) {
		String comando = linha.get(0);

		if (comando.contains(":")) {
			comando = ":";
		}

		switch (comando) {
		case "inc":
			linha.remove(0);
			return new Instrucao(Comando.INC, linha, null);

		case "dec":
			linha.remove(0);
			return new Instrucao(Comando.DEC, linha, null);

		case "set0":
			linha.remove(0);
			return new Instrucao(Comando.SETZERO, linha, null);

		case "is0":
			linha.remove(0);
			return new Instrucao(Comando.ISZERO, linha, null);

		case "goto":
			linha.remove(0);
			return new Instrucao(Comando.GOTO, linha, null);
		case "if":
			return gerarIntrucaoIfWhile(instucoesString, i, linha);
		case "while":
			return gerarIntrucaoIfWhile(instucoesString, i, linha);
		case "set":
			linha.remove(0);
			return new Instrucao(Comando.SET, linha, null);
		case "add":
			linha.remove(0);
			return new Instrucao(Comando.ADD, linha, null);
		case ":":
			return new Instrucao(Comando.DOISPONTOS, linha, null);

		default:

		}

		return null;
	}


	public Instrucao gerarIntrucaoIfWhile(List<String> instucoesString, int i, List<String> linha) {
		List<Instrucao> instrucoesInternas = new ArrayList<Instrucao>();

		for (int j = i + 1; j < instucoesString.size(); j++) {
			String instrucao = instucoesString.get(j);

			List<String> linhaInterna = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			String comando = linhaInterna.get(0);

			if (comando.equals("if") || comando.equals("while") || comando.equals("else")) {
				instrucoesInternas.add(gerarIntrucaoIfWhile(instucoesString, j, linhaInterna));
				pularBlocosIfWhile(instucoesString, j);
				j = j + pularBlocoIfWhile;
				pularBlocoIfWhile = 0;
			} else {
				if (comando.equals("endif") || comando.equals("endwhile") || comando.equals("endelse")) {
					break;
				} else {
					instrucoesInternas.add(gerarInstrucao(linhaInterna, instucoesString, 0));
				}

			}

		}

		String comando = linha.get(0);
		linha.remove(0);

		if (comando.equals("if")) {
			return new Instrucao(Comando.IF, linha, instrucoesInternas);
		} else if(comando.equals("while")) {
			return new Instrucao(Comando.WHILE, linha, instrucoesInternas);
		}else {
			return new Instrucao(Comando.ELSE, linha, instrucoesInternas);
		}

	}

	public int pularBlocosIfWhile(List<String> instucoesString, int i) {
		
		int pularBloco = 0;

		for (int j = i + 1; j < instucoesString.size(); j++) {
			String instrucao = instucoesString.get(j);

			List<String> linhaInterna = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			String comando = linhaInterna.get(0);

			if (comando.equals("if") || comando.equals("while") || comando.equals("else")) {
				pularBlocoIfWhile++;
				pularBloco++;
				j = j + pularBlocosIfWhile(instucoesString, j);
			} else {
				if (comando.equals("endif") || comando.equals("endwhile") || comando.equals("endelse")) {
					pularBlocoIfWhile++;
					pularBloco++;
					break;
				} else {
					pularBloco++;
					pularBlocoIfWhile++;
				}

			}

		}

		return pularBloco;

	}


	private void validarComando(String instrucao) throws Exception {
		if (instrucao.split(" ")[0] == null) {
			throw new Exception("Erro ao encontrar comando na instrução: " + instrucao);
		}
	}

	private void validarArgumentos(List<String> argumentos) throws Exception {
		if (argumentos.size() == 0) {
			throw new Exception("Erro ao encontrar agumentos");
		}
	}
}