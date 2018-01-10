/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.sri.s2;

/**
 *
 * @author excz010715
 */
public class Mecanica {

    private static String PRINT = "print";
    private static String PRINTLN = "print";
    private static String ERROR = "error";

    public static void main(String[] args) {
        imprimirEnPantalla(ERROR, PRINT);

    }

    static void imprimirEnPantalla(String mensaje, String tipo) {
        switch (tipo) {
            case "print":
                System.out.print(mensaje);
                break;
            case "println":
                System.out.println(mensaje);
                break;
            case "error":
                System.err.println(mensaje);
                break;                    
            default:
                System.out.println("No se pudo identificar el tipo de impresión solicitada");
        }
    }
}
