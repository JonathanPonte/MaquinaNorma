

public class Principal {

	public static void main(String[] args) throws Exception {
		
		String codigo = "set 0 2;"
				      + "set 1 8;"
				 	  + "add 0 1;"
				 	  + "set0 1;";
	 
	
		Norma n = new Norma(10);
		n.receberInstrucoes(codigo);
		n.acao();
		
		
		
	}

}
