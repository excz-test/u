/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.sri.s2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        util.imprimirEnPantalla("Registrar un trabajo: ", PRINTLN);

        /*
        * Crear Listado de Trabajos por tipo
         */
        List<ReparacionesMecanicas> mecanicas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mecanicas.add(new ReparacionesMecanicas(identificador++, "Reparación Mecánica " + identificador, Tipo.R.toString(), 0, Estado.INICIADO.toString(), 0, 0));
        }
        List<ReparacionesChapasPintura> pinturas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pinturas.add(new ReparacionesChapasPintura(identificador++, "Reparación de Chapas y Pintura " + identificador, Tipo.M.toString(), 0, Estado.INICIADO.toString(), 0, 0));
        }
        List<Revision> revisiones = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            revisiones.add(new Revision(identificador++, "Revisión " + identificador, Tipo.P.toString(), 0, Estado.INICIADO.toString(), 0, 0));
        }

        /*
        * Actualizar Datos
         */
        for (ReparacionesMecanicas mecanica : mecanicas) {
            mecanica.setHoras(util.generarNumero());
            mecanica.setPrecioMaterial(util.generarDouble());
            if (mecanica.getIdentificador() % 2 == 0) {
                mecanica.setEstado(Estado.FINALIZADO.toString());
            }
            if (mecanica.getEstado().equals(Estado.FINALIZADO.toString())) {
                mecanica.setCosto(mecanica.calcularPrecioTotalRepMecanicas());
            }
        }

        for (ReparacionesChapasPintura pintura : pinturas) {
            pintura.setHoras(util.generarNumero());
            pintura.setPrecioMaterial(util.generarDouble());
            if (!(pintura.getIdentificador() % 2 == 0)) {
                pintura.setEstado(Estado.FINALIZADO.toString());
            }
            if (pintura.getEstado().equals(Estado.FINALIZADO.toString())) {
                pintura.setCosto(pintura.calcularPrecioTotalRepPintura());
            }
        }
        for (Revision revision : revisiones) {
            revision.setHoras(util.generarNumero());
            if (revision.getIdentificador() % 2 == 0) {
                revision.setEstado(Estado.FINALIZADO.toString());
            }
            if (revision.getEstado().equals(Estado.FINALIZADO.toString())) {
                revision.setCosto(revision.calcularPrecioTotalRevision());
            }
        }

        /*for (Reparaciones trabajo1 : mecanicas) {
            util.imprimirEnPantalla(trabajo1.getDescripcion() + "\t", PRINT);
            util.imprimirEnPantalla(trabajo1.getHoras() + "\t", PRINT);
            util.imprimirEnPantalla(trabajo1.getEstado() + "\t", PRINT);
            util.imprimirEnPantalla(trabajo1.getCosto() + "\t", PRINT);
            util.imprimirEnPantalla(trabajo1.getPrecioMaterial() + "\t", PRINTLN);
            util.imprimirEnPantalla("-------------0-----------------", PRINTLN);
        }*/
        String plazo=null;
        try {
            Formatter outArchivo = new Formatter("exconrado_DatosSalida.csv");
            outArchivo.format("Identificador;");
            outArchivo.format("Descripción;");
            outArchivo.format("Tipo;");
            outArchivo.format("Horas;");
            outArchivo.format("Estado;");
            outArchivo.format("Precio Material;");
            outArchivo.format("Costo Total;");
            outArchivo.format("Plazo;\n");
            for (ReparacionesMecanicas mecanica : mecanicas) {
                int dias=mecanica.getHoras()/24;
                if (dias>DIAS_LIMITE_REPARACION) {
                    plazo="VENCIDO";
                }else{
                    plazo="A TIEMPO";
                }
                outArchivo.format("%d;%s;%s;%d;%s;%f;%f;%s\n", mecanica.getIdentificador(), mecanica.getDescripcion(),
                mecanica.getTipo(),mecanica.getHoras(),mecanica.getEstado(),mecanica.getPrecioMaterial(),mecanica.getCosto(),plazo);                
            }
            for (ReparacionesChapasPintura pintura : pinturas) {
                int dias=pintura.getHoras()/24;
                if (dias>DIAS_LIMITE_PINTURA) {
                    plazo="VENCIDO";
                }else{
                    plazo="A TIEMPO";
                }
                outArchivo.format("%d;%s;%s;%d;%s;%f;%f;%s\n", pintura.getIdentificador(), pintura.getDescripcion(),
                pintura.getTipo(),pintura.getHoras(),pintura.getEstado(),pintura.getPrecioMaterial(),pintura.getCosto(),plazo);
            }
            for (Revision revision : revisiones) {
                int dias=revision.getHoras()/24;
                if (dias>DIAS_LIMITE_PINTURA) {
                    plazo="VENCIDO";
                }else{
                    plazo="A TIEMPO";
                }
                outArchivo.format("%d;%s;%s;%d;%s;%f;%f;%s\n", revision.getIdentificador(), revision.getDescripcion(),
                revision.getTipo(),revision.getHoras(),revision.getEstado(),revision.getPrecioMaterial(),revision.getCosto(),plazo);
            }
            outArchivo.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(exconrado_ProgAlg_O17F18_2B.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Guardar en Archivo
         */
        /*
        for (Trabajo trabajo : trabajos) {
            util.imprimirEnPantalla("Identificador: "+trabajo.getIdentificador()+" Descripción: "+trabajo.getDescripcion()+" Número de horas: "+trabajo.getHoras()+ " Estado del trabajo: "+trabajo.getEstado()+" Costo: "+trabajo.getCosto(), PRINTLN);
        }*/
        // Trabajo trabajo = new ReparacionesMecanicas(0, "Reparación 1", 0, "ABIERTO", 0, util.generarNumero());
        /*System.out.println(trabajo.getIdentificador());
        System.out.println(trabajo.getDescripcion());
        System.out.println(trabajo.getHoras());
        System.out.println(trabajo.getEstado());
        System.out.println(trabajo.getCosto());
        util.imprimirEnPantalla(trabajo.getIdentificador(), PRINTLN);
        util.imprimirEnPantalla("--------------", PRINTLN);*/
        ReparacionesMecanicas trabajo1 = new ReparacionesMecanicas(1, "Reparaación 2", "", 20, "CERRADO", 100, 50);
        util.imprimirEnPantalla(trabajo1.getIdentificador(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getDescripcion(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getHoras(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getEstado(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.calcularPrecioTotalRepMecanicas(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getPrecioMaterial(), PRINTLN);
        util.imprimirEnPantalla("Modificar Estado y precio material: ", PRINT);
        trabajo1.setPrecioMaterial(90);
        trabajo1.setEstado(Estado.FINALIZADO.toString());
        util.imprimirEnPantalla("Estado Cambiado", PRINT);
        util.imprimirEnPantalla(trabajo1.getEstado(), PRINTLN);
        util.imprimirEnPantalla(trabajo1.getPrecioMaterial(), PRINTLN);
        util.imprimirEnPantalla("Modificando Finalizado \n---------", PRINT);
        trabajo1.setPrecioMaterial(90);

    }

    static class Trabajo {

        private int identificador;
        private String descripcion;
        private String tipo;

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
        private int horas;
        private String estado;
        private double costo;

        public Trabajo() {

        }

        public Trabajo(int identificador, String descripcion, String tipo, int horas, String estado, double costo) {
            this.identificador = identificador;
            this.descripcion = descripcion;
            this.tipo = tipo;
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
            if (getEstado().equals(Estado.FINALIZADO.toString())) {
                System.out.println("No es posible actualizar, porque el trabajo ya finalizo");
            } else {
                this.descripcion = descripcion;
            }
        }

        public int getHoras() {
            return horas;
        }

        public void setHoras(int horas) {
            if (getEstado().equals(Estado.FINALIZADO.toString())) {
                System.out.println("No es posible actualizar, porque el trabajo ya finalizo");
            } else {
                this.horas = horas;
            }
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

        public Reparaciones(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo);
            this.precioMaterial = precioMaterial;
        }

        private Reparaciones() {
        }

        public double calcularPrecioTotal() {
            return getHoras() * PRECIO_FIJO;
        }

        public double getPrecioMaterial() {
            return precioMaterial;
        }

        public void setPrecioMaterial(double precioMaterial) {
            if (getEstado().equals("FINALIZADO")) {
                System.out.println("No es posible actualizar el Precio del Material, porque el trabajo ya finalizo");
            } else {
                this.precioMaterial = precioMaterial;
            }

        }

    }

    static class ReparacionesMecanicas extends Reparaciones {

        public ReparacionesMecanicas(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo, precioMaterial);
        }

        public double calcularPrecioTotalRepMecanicas() {
            return calcularPrecioTotal() + (getPrecioMaterial() / 3);
        }
    }

    static class ReparacionesChapasPintura extends Reparaciones {

        public ReparacionesChapasPintura(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo, precioMaterial);
        }

        public double calcularPrecioTotalRepPintura() {
            return calcularPrecioTotal() + (getPrecioMaterial() / 5);
        }
    }

    static class Revision extends Reparaciones {

        public Revision(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo, precioMaterial);
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
            return new Random().nextInt(1000);
        }

        /*
        * Utilidad para generar int recibiendo un parametro
         */
        int numeroAleatorio(int maximo) {
            return new Random().nextInt(maximo);
        }

        /*
        * Utilidad para generar un double
         */
        double generarDouble() {
            return (double) Math.round(new Random().nextDouble() * 100 * 100d) / 100d;
        }
    }

    /*
    * Estado del Trabajo:
    * Iniciado, detenido y Finalizado
     */
    enum Estado {
        INICIADO, DETENIDO, FINALIZADO
    }

    /*Tipo de Trabajo:
    * M: Mecánico, P: Pintura y R: Revisión
     */
    enum Tipo {
        M, P, R
    }
}
