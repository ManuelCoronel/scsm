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
   
       String text[] = unidad.split("");
       return null;
   }
        
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        String cadena = sc.next();
        
        registrarMicrocurriculo rm = new registrarMicrocurriculo();
        rm.obtenerContenidos(cadena);
        System.out.println(cadena);
    }
   
    
}
