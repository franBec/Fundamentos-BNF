package bnf.ejercicio;

import java.util.ArrayList;

public class ParteDerecha {
    ArrayList<Token> tokens;
    ParteDerecha(){
        tokens = new ArrayList<> ();
    }
    public void addToken(Token t){
        tokens.add(t);
    }
    public String getValores(){
        String ss = "";
        for (Token t : tokens){
            ss = ss + t.getValor();
        }
        return ss;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(tokens);
        //sb.append('}');
        return sb.toString();
    }
}
