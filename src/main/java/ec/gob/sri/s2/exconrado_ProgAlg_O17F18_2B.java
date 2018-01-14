/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.sri.s2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author excz010715
 */
public class exconrado_ProgAlg_O17F18_2B {

    private static String PRINT = "print";
    private static String PRINTLN = "println";
    private static String ERROR = "error";
    private static int PRECIO_FIJO = 30;
    private static int PRECIO_FIJO_REVISION = 20;
    private static int DIAS_LIMITE_REPARACION = 14;
    private static int DIAS_LIMITE_PINTURA = 21;
    private static int DIAS_LIMITE_REVISION = 7;
    static int identificador = 0;

    public static void main(String[] args) {
        Utilidades util = new Utilidades();
        List<Trabajo> trabajos = new ArrayList<>();
        util.imprimirEnPantalla("Registrar un trabajo: ", PRINTLN);
        for (int i = 0; i < 10; i++) {
            trabajos.add(new Revision(identificador++, "Revisión " + identificador, util.generarNumero(), Estado.INICIADO.toString(), 0, 0));
            trabajos.add(new ReparacionesMecanicas(identificador++, "Reparación Mecánica " + identificador, util.generarNumero(), Estado.INICIADO.toString(), 0, 0));
            trabajos.add(new ReparacionesChapasPintura(identificador++, "Reparación de Chapas y Pintura " + identificador, util.generarNumero(), Estado.INICIADO.toString(), 0, 0));
        }
        
        for (Trabajo trabajo : trabajos) {
            util.imprimirEnPantalla("Identificador: "+trabajo.getIdentificador()+" Descripción: "+trabajo.getDescripcion()+" Número de horas: "+trabajo.getHoras()+ " Estado del trabajo: "+trabajo.getEstado()+" Costo: "+trabajo.getCosto(), PRINTLN);
        }
        Trabajo trabajo = new ReparacionesMecanicas(0, "Reparación 1", 0, "ABIERTO", 0, util.generarNumero());
        /*System.out.println(trabajo.getIdentificador());
        System.out.println(trabajo.getDescripcion());
        System.out.println(trabajo.getHoras());
        System.out.println(trabajo.getEstado());
        System.out.println(trabajo.getCosto());
        util.imprimirEnPantalla(trabajo.getIdentificador(), PRINTLN);
        util.imprimirEnPantalla("--------------", PRINTLN);
        ReparacionesMecanicas trabajo1 = new ReparacionesMecanicas(1, "Reparaación 2", 20, "CERRADO", 100, 50);
        util.imprimirEnPantalla(trabajo1.getIdentificador(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getDescripcion(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getHoras(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getEstado(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.calcularPrecioTotalRepMecanicas(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getPrecioMaterial(), PRINTLN);*/

    }

    static class Trabajo {

        private int identificador;
        private String descripcion;
        private int horas;
        private String estado;
        private double costo;

        public Trabajo() {

        }

        public Trabajo(int identificador, String descripcion, int horas, String estado, double costo) {
            this.identificador = identificador;
            this.descripcion = descripcion;
            this.horas = horas;
            this.estado = estado;
            this.costo = costo;
        }

        public int getIdentificador() {
            return identificador;
        }

        public void setIdentificador(int identificador) {
            this.identificador = identificador;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public int getHoras() {
            return horas;
        }

        public void setHoras(int horas) {
            this.horas = horas;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public double getCosto() {
            return costo;
        }

        public void setCosto(double costo) {
            this.costo = costo;
        }
    }

    static class Reparaciones extends Trabajo {

        private double precioMaterial;

        public Reparaciones(int identificador, String descripcion, int horas, String estado, double costo) {
            super(identificador, descripcion, horas, estado, costo);
        }

        public Reparaciones(int identificador, String descripcion, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, horas, estado, costo);
            this.precioMaterial = precioMaterial;
        }

        public double calcularPrecioTotal() {
            return getHoras() * PRECIO_FIJO;
        }

        public double getPrecioMaterial() {
            return precioMaterial;
        }

        public void setPrecioMaterial(double precioMaterial) {
            this.precioMaterial = precioMaterial;
        }

    }

    static class ReparacionesMecanicas extends Reparaciones {

        public ReparacionesMecanicas(int identificador, String descripcion, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, horas, estado, costo, precioMaterial);
        }

        public double calcularPrecioTotalRepMecanicas() {
            return calcularPrecioTotal() + (getPrecioMaterial() / 3);
        }
    }

    static class ReparacionesChapasPintura extends Reparaciones {

        public ReparacionesChapasPintura(int identificador, String descripcion, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, horas, estado, costo, precioMaterial);
        }

        public double calcularPrecioTotalRepPintura() {
            return calcularPrecioTotal() + (getPrecioMaterial() / 5);
        }
    }

    static class Revision extends Reparaciones {

        public Revision(int identificador, String descripcion, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, horas, estado, costo, precioMaterial);
        }

        public double calcularPrecioTotalRevision() {
            return PRECIO_FIJO + PRECIO_FIJO_REVISION;
        }
    }

    /**
     * Clase que aporta con métodos transversales al sistema Mecánica
     */
    public static class Utilidades {

        void imprimirEnPantalla(Object mensaje, String tipo) {
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

        int generarNumero() {
            return new Random().nextInt(100);
        }

    }

    enum Estado {
        INICIADO, DETENIDO, TERMINADO
    }
}
