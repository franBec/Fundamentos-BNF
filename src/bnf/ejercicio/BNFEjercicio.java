package bnf.ejercicio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;

public class BNFEjercicio {
    static BNF bnf_desde_archivo = new BNF();
    static JFileChooser fileChooser = new JFileChooser();
    
    public static void main(String[] args) {
        bnf_desde_archivo = new BNF();
        FileReader fileContents = null;
        BufferedReader fileContentsB = null;
        int fileReturn = fileChooser.showOpenDialog(fileChooser);
        if (fileReturn == JFileChooser.APPROVE_OPTION) {
            File openFile = fileChooser.getSelectedFile();
            try {
                fileContents = new FileReader(openFile);
                fileContentsB = new BufferedReader(fileContents);
                bnf_desde_archivo.read(fileContentsB);
                fileContents.close();
                
                bnf_desde_archivo.generarCadenas();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
