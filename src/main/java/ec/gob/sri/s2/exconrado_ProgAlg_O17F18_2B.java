package ec.gob.sri.s2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/**
 * @Descripcion La solución maneja los trabajos de un taller dividos en
 * Reparaciones Mecanicas, pintura y revisiones Los datos son autogenerados, asì
 * como las modificaciones. Al final se crea un csv externo que ademas se lo
 * presenta en pantalla
 * @author excz010715
 * @version 1.0 15/01/2018
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
        util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
        util.imprimirEnPantalla("\n--------------------------------- Sistema de Mecanica ---------------------------------------------", PRINTLN);
        util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
        util.imprimirEnPantalla("Registrar trabajo: ", PRINTLN);
        util.imprimirEnPantalla("\nIngresando registros...", PRINTLN);
        /*
        * Crear Listado de Trabajos por tipo
         */
        List<ReparacionesMecanicas> mecanicas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mecanicas.add(new ReparacionesMecanicas(identificador++, "Reparación_Mecánica_" + identificador, Tipo.R.toString(), 0, Estado.INICIADO.toString(), 0, 0));
        }
        List<ReparacionesChapasPintura> pinturas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pinturas.add(new ReparacionesChapasPintura(identificador++, "Chapas_Pintura_" + identificador, Tipo.M.toString(), 0, Estado.INICIADO.toString(), 0, 0));
        }
        List<Revision> revisiones = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            revisiones.add(new Revision(identificador++, "Revisión_" + identificador, Tipo.P.toString(), 0, Estado.INICIADO.toString(), 0, 0));
        }
        /*
        * Actualizar Datos
         */
        util.imprimirEnPantalla("\nActualizando registros...", PRINTLN);
        util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
        for (ReparacionesMecanicas mecanica : mecanicas) {
            util.imprimirEnPantalla("Hora inicial: " + mecanica.getHoras(), PRINT);
            mecanica.setHoras(util.generarNumero());
            util.imprimirEnPantalla(" Hora actualizada: " + mecanica.getHoras(), PRINTLN);
            util.imprimirEnPantalla("Precio inicial: " + mecanica.getPrecioMaterial(), PRINT);
            mecanica.setPrecioMaterial(util.generarDouble());
            util.imprimirEnPantalla(" Precio actualizado: " + mecanica.getPrecioMaterial(), PRINTLN);
            if (mecanica.getIdentificador() % 2 == 0) {
                util.imprimirEnPantalla("Estado inicial: " + mecanica.getEstado(), PRINT);
                mecanica.setEstado(Estado.FINALIZADO.toString());
                util.imprimirEnPantalla(" Estado actualizado: " + mecanica.getEstado(), PRINTLN);
            }
            if (mecanica.getEstado().equals(Estado.FINALIZADO.toString())) {
                mecanica.setCosto(mecanica.calcularPrecioTotalRepMecanicas());
            }
        }
        for (ReparacionesChapasPintura pintura : pinturas) {
            util.imprimirEnPantalla("Hora inicial: " + pintura.getHoras(), PRINT);
            pintura.setHoras(util.generarNumero());
            util.imprimirEnPantalla(" Hora actualizada: " + pintura.getHoras(), PRINTLN);
            util.imprimirEnPantalla("Precio inicial: " + pintura.getPrecioMaterial(), PRINT);
            pintura.setPrecioMaterial(util.generarDouble());
            util.imprimirEnPantalla(" Precio actualizado: " + pintura.getPrecioMaterial(), PRINTLN);
            if (!(pintura.getIdentificador() % 2 == 0)) {
                util.imprimirEnPantalla("Estado inicial: " + pintura.getEstado(), PRINT);
                pintura.setEstado(Estado.FINALIZADO.toString());
                util.imprimirEnPantalla(" Estado actualizado: " + pintura.getEstado(), PRINTLN);
            }
            if (pintura.getEstado().equals(Estado.FINALIZADO.toString())) {
                pintura.setCosto(pintura.calcularPrecioTotalRepPintura());
            }
        }
        for (Revision revision : revisiones) {
            revision.setHoras(util.generarNumero());
            if (revision.getIdentificador() % 2 == 0) {
                util.imprimirEnPantalla("Estado inicial: " + revision.getEstado(), PRINT);
                revision.setEstado(Estado.FINALIZADO.toString());
                util.imprimirEnPantalla(" Estado actualizado: " + revision.getEstado(), PRINTLN);
            }
            if (revision.getEstado().equals(Estado.FINALIZADO.toString())) {
                revision.setCosto(revision.calcularPrecioTotalRevision());
            }
        }
        util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
        util.imprimirEnPantalla("Fin de actualización", PRINTLN);
        util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
        /**
         * Guardar en Archivo
         */
        util.imprimirEnPantalla("\nGurdando registros...", PRINTLN);
        util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
        String plazo = null;
        try {
            Formatter outArchivo = new Formatter("exconrado_DatosSalida.csv");
            outArchivo.format("Identificador;");
            outArchivo.format("Descripción;");
            outArchivo.format("Tipo;");
            outArchivo.format("Horas;");
            outArchivo.format("Estado;");
            outArchivo.format("Precio_Material;");
            outArchivo.format("Costo_Total;");
            outArchivo.format("Plazo;\n");
            for (ReparacionesMecanicas mecanica : mecanicas) {
                int dias = mecanica.getHoras() / 24;
                if (dias > DIAS_LIMITE_REPARACION) {
                    plazo = "VENCIDO";
                } else {
                    plazo = "A_TIEMPO";
                }
                outArchivo.format("%d;%s;%s;%dh;%s;%f;%f;%s;\n", mecanica.getIdentificador(), mecanica.getDescripcion(),
                        mecanica.getTipo(), mecanica.getHoras(), mecanica.getEstado(), mecanica.getPrecioMaterial(), mecanica.getCosto(), plazo);
            }
            for (ReparacionesChapasPintura pintura : pinturas) {
                int dias = pintura.getHoras() / 24;
                if (dias > DIAS_LIMITE_PINTURA) {
                    plazo = "VENCIDO";
                } else {
                    plazo = "A_TIEMPO";
                }
                outArchivo.format("%d;%s;%s;%dh;%s;%f;%f;%s;\n", pintura.getIdentificador(), pintura.getDescripcion(),
                        pintura.getTipo(), pintura.getHoras(), pintura.getEstado(), pintura.getPrecioMaterial(), pintura.getCosto(), plazo);
            }
            for (Revision revision : revisiones) {
                int dias = revision.getHoras() / 24;
                if (dias > DIAS_LIMITE_REVISION) {
                    plazo = "VENCIDO";
                } else {
                    plazo = "A_TIEMPO";
                }
                outArchivo.format("%d;%s;%s;%dh;%s;%f;%f;%s;\n", revision.getIdentificador(), revision.getDescripcion(),
                        revision.getTipo(), revision.getHoras(), revision.getEstado(), revision.getPrecioMaterial(), revision.getCosto(), plazo);
            }
            outArchivo.close();

        } catch (FileNotFoundException ex) {
            util.imprimirEnPantalla(ex, ERROR);
        }
        util.imprimirEnPantalla("\nDesea presentar los datos guardados: S", PRINTLN);
        util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
        try {
            Scanner inArchivo = new Scanner(new File("exconrado_DatosSalida.csv"));
            String contenido = null;
            util.imprimirEnPantalla("\n---------------------------------------------------------------------------------------------------", PRINTLN);
            while (inArchivo.hasNext()) {
                contenido = inArchivo.next();
                String[] tokens = contenido.split(";");
                for (String token : tokens) {
                    util.imprimirEnPantalla(token + "\t||\t", PRINT);
                }
                util.imprimirEnPantalla("\n", PRINT);
            }
            inArchivo.close();
        } catch (FileNotFoundException ex) {
            util.imprimirEnPantalla(ex, ERROR);
        }
    }
    /**
     * Clase Trabajo, encargada de registrar los elementos minimos de una orden
     */
    static class Trabajo {
        private int identificador;
        private String descripcion;
        private String tipo;
        private int horas;
        private String estado;
        private double costo;
        public Trabajo() {

        }
        /**
         * Constructor principal
         *
         * @param identificador
         * @param descripcion
         * @param tipo
         * @param horas
         * @param estado
         * @param costo
         */
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
        /**
         * Registra la Descripcion si el estado es diferente de Finalizado
         *
         * @param descripcion
         */
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
        /**
         * Registra las horas si el estado es diferente de Finalizado
         *
         * @param horas
         */
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
        public String getTipo() {
            return tipo;
        }
        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }
    /**
     * Reparaciones hereda de Trabajo y crea la forma de calcular el costo total
     * general y añade sus propios atributos
     */
    static class Reparaciones extends Trabajo {

        private double precioMaterial;

        /**
         * Contructor principal de Reparaciones
         *
         * @param identificador
         * @param descripcion
         * @param tipo
         * @param horas
         * @param estado
         * @param costo
         * @param precioMaterial
         */
        public Reparaciones(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo);
            this.precioMaterial = precioMaterial;
        }

        /**
         * Constructor vacio de Reparaciones
         */
        private Reparaciones() {
        }

        /**
         * Calculo de Precio Total General
         *
         * @return double precio Total
         */
        public double calcularPrecioTotal() {
            return getHoras() * PRECIO_FIJO;
        }

        public double getPrecioMaterial() {
            return precioMaterial;
        }

        /**
         * Registra el precio de Material si el estado es diferente de
         * Finalizado
         *
         * @param precioMaterial
         */
        public void setPrecioMaterial(double precioMaterial) {
            if (getEstado().equals("FINALIZADO")) {
                System.out.println("No es posible actualizar el Precio del Material, porque el trabajo ya finalizo");
            } else {
                this.precioMaterial = precioMaterial;
            }

        }

    }
    /**
     * Reparacion Mecanica hereda de Reparaciones y crea su propia forma de
     * calcular el costo total
     */
    static class ReparacionesMecanicas extends Reparaciones {

        /**
         * Constructor principal de Reparaciones Mecanicas
         *
         * @param identificador
         * @param descripcion
         * @param tipo
         * @param horas
         * @param estado
         * @param costo
         * @param precioMaterial
         */
        public ReparacionesMecanicas(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo, precioMaterial);
        }

        /**
         * Calculo de precio Reparacion Mecanica
         *
         * @return double precio de Reparacion Mecanica
         */
        public double calcularPrecioTotalRepMecanicas() {
            return calcularPrecioTotal() + (getPrecioMaterial() / 3);
        }
    }
    /**
     * Reparacion de chapas y pintura hereda de Reparaciones y crea su propia
     * forma de calcular el costo total
     */
    static class ReparacionesChapasPintura extends Reparaciones {

        /**
         * Constructor principal de Reparaciones Pintura
         *
         * @param identificador
         * @param descripcion
         * @param tipo
         * @param horas
         * @param estado
         * @param costo
         * @param precioMaterial
         */
        public ReparacionesChapasPintura(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo, precioMaterial);
        }

        /**
         * Calculo de precio Reparacion Chapas y Pintura
         *
         * @return double precio de Reparacion chapas y pintura
         */
        public double calcularPrecioTotalRepPintura() {
            return calcularPrecioTotal() + (getPrecioMaterial() / 5);
        }
    }
    /**
     * Revision de Reparaciones y crea su propia forma de calcular precio
     */
    static class Revision extends Reparaciones {

        /**
         * Constructor principal de Revisiones
         *
         * @param identificador
         * @param descripcion
         * @param tipo
         * @param horas
         * @param estado
         * @param costo
         * @param precioMaterial
         */
        public Revision(int identificador, String descripcion, String tipo, int horas, String estado, double costo, double precioMaterial) {
            super(identificador, descripcion, tipo, horas, estado, costo, precioMaterial);
        }

        /**
         * Calcula el costo por revisión
         *
         * @return double calculo precio revision
         */
        public double calcularPrecioTotalRevision() {
            return PRECIO_FIJO + PRECIO_FIJO_REVISION;
        }
    }
    /**
     * Clase que aporta con métodos transversales al sistema Mecánica
     */
    public static class Utilidades {

        /**
         * Utilidad para imprimir en pantalla
         *
         * @param mensaje
         * @param tipo
         */
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

        /**
         * Crea un numero entero aleatorio
         *
         * @return int
         */
        int generarNumero() {
            return new Random().nextInt(1000);
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
/**
 * 
---------------------------------------------------------------------------------------------------

--------------------------------- Sistema de Mecanica ---------------------------------------------

---------------------------------------------------------------------------------------------------
Registrar trabajo: 

Ingresando registros...

Actualizando registros...

---------------------------------------------------------------------------------------------------
Hora inicial: 0 Hora actualizada: 312
Precio inicial: 0.0 Precio actualizado: 9.13
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 207
Precio inicial: 0.0 Precio actualizado: 35.42
Hora inicial: 0 Hora actualizada: 961
Precio inicial: 0.0 Precio actualizado: 98.35
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 890
Precio inicial: 0.0 Precio actualizado: 27.94
Hora inicial: 0 Hora actualizada: 580
Precio inicial: 0.0 Precio actualizado: 71.09
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 511
Precio inicial: 0.0 Precio actualizado: 88.56
Hora inicial: 0 Hora actualizada: 286
Precio inicial: 0.0 Precio actualizado: 97.41
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 796
Precio inicial: 0.0 Precio actualizado: 81.66
Hora inicial: 0 Hora actualizada: 315
Precio inicial: 0.0 Precio actualizado: 10.28
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 940
Precio inicial: 0.0 Precio actualizado: 94.31
Hora inicial: 0 Hora actualizada: 285
Precio inicial: 0.0 Precio actualizado: 4.38
Hora inicial: 0 Hora actualizada: 827
Precio inicial: 0.0 Precio actualizado: 26.29
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 924
Precio inicial: 0.0 Precio actualizado: 48.36
Hora inicial: 0 Hora actualizada: 155
Precio inicial: 0.0 Precio actualizado: 93.22
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 295
Precio inicial: 0.0 Precio actualizado: 13.47
Hora inicial: 0 Hora actualizada: 203
Precio inicial: 0.0 Precio actualizado: 44.75
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 786
Precio inicial: 0.0 Precio actualizado: 86.12
Hora inicial: 0 Hora actualizada: 733
Precio inicial: 0.0 Precio actualizado: 18.48
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Hora inicial: 0 Hora actualizada: 693
Precio inicial: 0.0 Precio actualizado: 28.02
Hora inicial: 0 Hora actualizada: 36
Precio inicial: 0.0 Precio actualizado: 2.05
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Estado inicial: INICIADO Estado actualizado: FINALIZADO
Estado inicial: INICIADO Estado actualizado: FINALIZADO

---------------------------------------------------------------------------------------------------
Fin de actualización

---------------------------------------------------------------------------------------------------

Gurdando registros...

---------------------------------------------------------------------------------------------------

Desea presentar los datos guardados: S

---------------------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------------
Identificador	||	Descripción	||	Tipo	||	Horas	||	Estado	||	Precio_Material	||	Costo_Total	||	Plazo	||	
0	||	Reparación_Mecánica_1	||	R	||	312h	||	FINALIZADO	||	9,130000	||	9363,043333	||	A_TIEMPO	||	
1	||	Reparación_Mecánica_2	||	R	||	207h	||	INICIADO	||	35,420000	||	0,000000	||	A_TIEMPO	||	
2	||	Reparación_Mecánica_3	||	R	||	961h	||	FINALIZADO	||	98,350000	||	28862,783333	||	VENCIDO	||	
3	||	Reparación_Mecánica_4	||	R	||	890h	||	INICIADO	||	27,940000	||	0,000000	||	VENCIDO	||	
4	||	Reparación_Mecánica_5	||	R	||	580h	||	FINALIZADO	||	71,090000	||	17423,696667	||	VENCIDO	||	
5	||	Reparación_Mecánica_6	||	R	||	511h	||	INICIADO	||	88,560000	||	0,000000	||	VENCIDO	||	
6	||	Reparación_Mecánica_7	||	R	||	286h	||	FINALIZADO	||	97,410000	||	8612,470000	||	A_TIEMPO	||	
7	||	Reparación_Mecánica_8	||	R	||	796h	||	INICIADO	||	81,660000	||	0,000000	||	VENCIDO	||	
8	||	Reparación_Mecánica_9	||	R	||	315h	||	FINALIZADO	||	10,280000	||	9453,426667	||	A_TIEMPO	||	
9	||	Reparación_Mecánica_10	||	R	||	940h	||	INICIADO	||	94,310000	||	0,000000	||	VENCIDO	||	
10	||	Chapas_Pintura_11	||	M	||	285h	||	INICIADO	||	4,380000	||	0,000000	||	A_TIEMPO	||	
11	||	Chapas_Pintura_12	||	M	||	827h	||	FINALIZADO	||	26,290000	||	24815,258000	||	VENCIDO	||	
12	||	Chapas_Pintura_13	||	M	||	924h	||	INICIADO	||	48,360000	||	0,000000	||	VENCIDO	||	
13	||	Chapas_Pintura_14	||	M	||	155h	||	FINALIZADO	||	93,220000	||	4668,644000	||	A_TIEMPO	||	
14	||	Chapas_Pintura_15	||	M	||	295h	||	INICIADO	||	13,470000	||	0,000000	||	A_TIEMPO	||	
15	||	Chapas_Pintura_16	||	M	||	203h	||	FINALIZADO	||	44,750000	||	6098,950000	||	A_TIEMPO	||	
16	||	Chapas_Pintura_17	||	M	||	786h	||	INICIADO	||	86,120000	||	0,000000	||	VENCIDO	||	
17	||	Chapas_Pintura_18	||	M	||	733h	||	FINALIZADO	||	18,480000	||	21993,696000	||	VENCIDO	||	
18	||	Chapas_Pintura_19	||	M	||	693h	||	INICIADO	||	28,020000	||	0,000000	||	VENCIDO	||	
19	||	Chapas_Pintura_20	||	M	||	36h	||	FINALIZADO	||	2,050000	||	1080,410000	||	A_TIEMPO	||	
20	||	Revisión_21	||	P	||	912h	||	FINALIZADO	||	0,000000	||	50,000000	||	VENCIDO	||	
21	||	Revisión_22	||	P	||	119h	||	INICIADO	||	0,000000	||	0,000000	||	A_TIEMPO	||	
22	||	Revisión_23	||	P	||	851h	||	FINALIZADO	||	0,000000	||	50,000000	||	VENCIDO	||	
23	||	Revisión_24	||	P	||	135h	||	INICIADO	||	0,000000	||	0,000000	||	A_TIEMPO	||	
24	||	Revisión_25	||	P	||	833h	||	FINALIZADO	||	0,000000	||	50,000000	||	VENCIDO	||	
25	||	Revisión_26	||	P	||	50h	||	INICIADO	||	0,000000	||	0,000000	||	A_TIEMPO	||	
26	||	Revisión_27	||	P	||	530h	||	FINALIZADO	||	0,000000	||	50,000000	||	VENCIDO	||	
27	||	Revisión_28	||	P	||	912h	||	INICIADO	||	0,000000	||	0,000000	||	VENCIDO	||	
28	||	Revisión_29	||	P	||	503h	||	FINALIZADO	||	0,000000	||	50,000000	||	A_TIEMPO	||	
29	||	Revisión_30	||	P	||	727h	||	INICIADO	||	0,000000	||	0,000000	||	VENCIDO	||	
 * 
 * */ 
