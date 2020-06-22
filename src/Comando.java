public enum Comando {
	INC("inc"), DEC("dec"), SETZERO("set0"), ISZERO("is0"), GOTO("goto"), SET("set"), ADD("add"), IF("if"),
	WHILE("while"), DOISPONTOS(":"), ELSE("else");
	
	private String comando;
	
	Comando(String comando) {
		this.comando = comando;
	}
	
	public static Comando deString(String texto) {
		for(Comando c : Comando.values()) {
			if(c.comando.equalsIgnoreCase(texto)) {
				return c;
			}
		}
		return null;
	}
}