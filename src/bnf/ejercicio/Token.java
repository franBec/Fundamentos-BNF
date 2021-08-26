package bnf.ejercicio;

public class Token {
	private Tipo t;
	private String valor;
        private boolean dentro_iteracion;
        private boolean dentro_opcion;
	
	public Token(Tipo type, String val) {
		this.t = type;
		this.valor = val;
                this.dentro_opcion= false;
                this.dentro_iteracion= false;
	}
	
        public void setOpcion(boolean b){
            this.dentro_opcion = b;
        }
        
        public void setIteracion(boolean b){
            this.dentro_iteracion = b;
        }
        
        public boolean getOpcion(){
            return dentro_opcion;
        }
        
        public boolean getIteracion(){
            return dentro_iteracion ;
        }
	public Tipo getTipo() {
		return t;
	}
        public void setTipo(Tipo t) {
            this.t = t;
	}
	
	public String getValor() {
		return valor;
	}
        void setValor(String string) {
            valor = string;
        }
        
        void addChar(char c) {
            valor = valor + c;
        }

        public boolean isTerminal(){
            return this.t == Tipo.TERMINAL;
        }
	
	public boolean equals(Object object) {
	    if (object == null) return false;
	    if (!(object instanceof Token)) return false;
	    Token that = (Token)object;
	    return this.t == that.t && this.valor.equals(that.valor);
	}
	
	public int hashCode() {
            return valor.hashCode();
	}
	
	public String toString() {
	    return "<"+valor+", "+t+">";
	}

    
}
