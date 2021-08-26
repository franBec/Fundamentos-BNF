package bnf.ejercicio;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;

public class BNF {
    LinkedHashMap<Token, ArrayList<ParteDerecha>> reglas;
    ArrayList al;
    Token token_corriente;
    /**
     * Constructor para la clase BNF 
     */
    public BNF() {
        this.reglas = new LinkedHashMap<Token, ArrayList<ParteDerecha>>();
    }
    
    /**
     * Lee desde un archivo todas las reglas de una bnf
     * Se encarga de pasar a una estructura en java el archivo
     * Cuyo formarto sea Backus-Naur Form grammar con notación extendida
     * 
     * 
     * @param reader Dado lector de archivo transforma a BNFE
     */
    public void read(BufferedReader reader) {
        
        try {
            String line = reader.readLine();
            while(line != null){
                String [] partes = line.split("::="); 
                String a =  partes[0].replace("<", "").replace(">","");                
                Token pi = new Token(Tipo.NOTERMINAL, a);//partes[0];//parte derecha
                //partes[1];//parte derecha
                String [] opciones = partes[1].split("\\|");
                al = new ArrayList();
                for(String opcion: opciones){
                    ParteDerecha pd = new ParteDerecha();
                    int largo = opcion.length();                                        
                    token_corriente = new Token(Tipo.TERMINAL,"");                    
                    for (int i = 0; i < largo; i++) {                        
                        switch(opcion.charAt(i)) {                                                        
                            case '<':
                                token_corriente.setTipo(Tipo.NOTERMINAL);
                                break;
                            case '>':                                
                                pd.addToken(token_corriente);
                                token_corriente = new Token(Tipo.TERMINAL,"");   
                                break;       
                            case ' ':
                                token_corriente.setTipo(Tipo.TERMINAL);
                                pd.addToken(token_corriente);
                                token_corriente = new Token(Tipo.TERMINAL,"");   
                                break;       
                            case '[':
                                token_corriente.setOpcion(true);
                                break;
                            case ']':                                
                                token_corriente.setValor("");
                                token_corriente.setOpcion(false);                                
                                break;
                            case '{':
                                token_corriente.setIteracion(true);
                                break;
                            case '}':
                                token_corriente.setIteracion(false);
                                break;
                            default:
                                token_corriente.addChar(opcion.charAt(i));
                        }
                    }
                    al.add(pd);
                }
                reglas.put(pi, al);
                line = reader.readLine();
            }
            //System.out.println(reglas.toString());
        } catch (RuntimeException e) {
            System.out.println("En tiempo de ejecucion. Mensaje:  " + e.getMessage());
            throw new RuntimeException("Problema de lectura del archivo.");
        } catch (Exception e) {
            System.out.println("Mensaje:  " + e.getMessage());
            throw new RuntimeException("Problema de lectura del archivo.");
        }
    }
    
    public void generarCadenas(){
	//Estructuras de datos auxiliares
	LinkedHashMap<String, ArrayList<ArrayList<String>>> nuevasReglas = new LinkedHashMap<>();

	//Proceso iterativo
        ListIterator<Token> iteratorClaves = new ArrayList(reglas.keySet()).listIterator(reglas.size());
    	while (iteratorClaves.hasPrevious()){
            Token claveActual = iteratorClaves.previous();
    	    ArrayList<ParteDerecha> terminos = new ArrayList<>(reglas.get(claveActual));
            ArrayList<ArrayList<String>> nuevaParteDerecha = new ArrayList<>();
                
    	    for(ParteDerecha termino : terminos){
    	        ArrayList<Token> tokens = new ArrayList<>(termino.getTokens());
                ArrayList<String> arrString = new ArrayList<>();
                    
    	        for(Token t : tokens){
    	            if(t.isTerminal()){
                        if(arrString.isEmpty()){
                            arrString.add(t.getValor());
                        }
                        else{
                            ArrayList<String> arrStringAux = new ArrayList<>(arrString);
                            arrString.clear();
                            for(String s : arrStringAux){
                                arrString.add(s.concat(t.getValor()));
                            }
                        }
                    }
                    else{
                        ArrayList<ArrayList<String>> arrTerminales = nuevasReglas.get(t.getValor());
                        if(arrString.isEmpty()){
                            for (ArrayList<String> arr : arrTerminales){
                                for (String stringTerminal : arr){
                                    arrString.add(stringTerminal);
                                }
                            }
                        }
                        else{
                            ArrayList<String> arrStringAux = new ArrayList<>(arrString);
                            arrString.clear();

                        for (String s : arrStringAux){
                            for (ArrayList<String> arr : arrTerminales){
                                for (String stringTerminal : arr){
                                    arrString.add(s.concat(stringTerminal));
                                    }
                                }
                            }
                        }
                    }
    	        }
                nuevaParteDerecha.add(new ArrayList<>(arrString));
    	    }
    	    nuevasReglas.put(claveActual.getValor(), nuevaParteDerecha);
    	}
    	
        //Imprimir cadenas
        imprimirCadenas(getLastNuevaParteDerecha(nuevasReglas));
    }
    
    public ArrayList<ArrayList<String>> getLastNuevaParteDerecha(LinkedHashMap<String, ArrayList<ArrayList<String>>> map){
        ListIterator <ArrayList<ArrayList<String>>> iteratorAux = new ArrayList<>(map.values()).listIterator(map.size());
        while (iteratorAux.hasPrevious())
            return iteratorAux.previous();
        return null;
    }
    
    public void imprimirCadenas(ArrayList<ArrayList<String>> nuevaParteDerecha){       
        for(ArrayList<String> arr : nuevaParteDerecha){
            for(String s : arr){
                System.out.println(s);
            }
        }
    }
    
    public void imprimirReglas() {
        // Imprimimos el Mapa que contiene todas las reglas
        Iterator it = reglas.keySet().iterator();
        
        while(it.hasNext()){
          Token key = (Token)it.next();
          for (ParteDerecha pd : reglas.get(key)){
              System.out.println("Clave: " + key.getValor() + " -> Valor: " + pd.getValores());
          }          
        }
    }
    
}
