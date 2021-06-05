/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Scanner;

/**
 *
 * @author Manuel
 */
public class registrarMicrocurriculo {

    public registrarMicrocurriculo() {
    }
    
    
   public String[] obtenerContenidos (String unidad){
       
       formatearContenidos(unidad);
   
   return null;
   }
   
   public String[] formatearContenidos(String unidad){
   
       String[] text = unidad.split("-");
       
       System.out.println("Formateo");
       System.out.println(text.length);
       for (String string : text) {
           System.out.println(string); 
       }
       return null;
   }
        
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        
        
         String cadena = "";
            while(sc.hasNext()){
            cadena += sc.next();
            
            }
         System.out.println("ORIGINAL");
          System.out.println(cadena);
         registrarMicrocurriculo rm = new registrarMicrocurriculo();
         rm.obtenerContenidos(cadena);
        
    }
   
    
}
