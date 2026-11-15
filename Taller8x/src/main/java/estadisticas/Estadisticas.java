package estadisticas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import modelo.Registro;
import modelo.TablaHash;

public class Estadisticas {

    private static final int ITERACIONES = 200;
    private static final int CALENTAMIENTO = 50;
    private static final int MEDICIONES = ITERACIONES - CALENTAMIENTO;
    private static final Locale EN = Locale.US;

    private Estadisticas() {
    }

    public static String generarReporte(ArrayList<Registro> registros) {

        if (registros == null || registros.isEmpty()) {
            return "No records to measure.\n"
                 + "Load a CSV file with data before running the test.";
        }

        Resultado division = medir(registros, true);
        Resultado multiplicacion = medir(registros, false);

        return formatear(registros.size(), division, multiplicacion);
    }

    private static Resultado medir(ArrayList<Registro> registros, boolean esDivision) {

        long sumaInsLista = 0;
        long sumaInsArbol = 0;
        long sumaDelLista = 0;
        long sumaDelArbol = 0;

        ArrayList<String> placasBase = new ArrayList<>();
        for (Registro r : registros) {
            placasBase.add(r.getPlate());
        }

        for (int iteracion = 0; iteracion < ITERACIONES; iteracion++) {

            TablaHash tablaLista = new TablaHash(true, esDivision);
            TablaHash tablaArbol = new TablaHash(false, esDivision);

            long insLista;
            long insArbol;

            //en las vueltas pares se mide primero la lista, en las impares primero el arbol
            if (iteracion % 2 == 0) {
                long t0 = System.nanoTime();
                for (Registro r : registros) {
                    tablaLista.insertarRegistro(r);
                }
                long t1 = System.nanoTime();

                long t2 = System.nanoTime();
                for (Registro r : registros) {
                    tablaArbol.insertarRegistro(r);
                }
                long t3 = System.nanoTime();

                insLista = t1 - t0;
                insArbol = t3 - t2;
            }
            else {
                long t0 = System.nanoTime();
                for (Registro r : registros) {
                    tablaArbol.insertarRegistro(r);
                }
                long t1 = System.nanoTime();

                long t2 = System.nanoTime();
                for (Registro r : registros) {
                    tablaLista.insertarRegistro(r);
                }
                long t3 = System.nanoTime();

                insArbol = t1 - t0;
                insLista = t3 - t2;
            }

            // La misma barajada para las dos estructuras: borran en el mismo
            // orden aleatorio y la comparacion es justa.
            ArrayList<String> placas = new ArrayList<>(placasBase);
            Collections.shuffle(placas, new Random(iteracion));

            long delLista;
            long delArbol;

            if (iteracion % 2 == 0) {
                long t0 = System.nanoTime();
                for (String plate : placas) {
                    tablaLista.eliminar(plate);
                }
                long t1 = System.nanoTime();

                long t2 = System.nanoTime();
                for (String plate : placas) {
                    tablaArbol.eliminar(plate);
                }
                long t3 = System.nanoTime();

                delLista = t1 - t0;
                delArbol = t3 - t2;
            }
            else {
                long t0 = System.nanoTime();
                for (String plate : placas) {
                    tablaArbol.eliminar(plate);
                }
                long t1 = System.nanoTime();

                long t2 = System.nanoTime();
                for (String plate : placas) {
                    tablaLista.eliminar(plate);
                }
                long t3 = System.nanoTime();

                delArbol = t1 - t0;
                delLista = t3 - t2;
            }

            // Las primeras 50 iteraciones son calentamiento: no se promedian.
            if (iteracion >= CALENTAMIENTO) {
                sumaInsLista += insLista;
                sumaInsArbol += insArbol;
                sumaDelLista += delLista;
                sumaDelArbol += delArbol;
            }
        }

        double insLista = sumaInsLista / (double) MEDICIONES / 1000.0;
        double insArbol = sumaInsArbol / (double) MEDICIONES / 1000.0;
        double delLista = sumaDelLista / (double) MEDICIONES / 1000.0;
        double delArbol = sumaDelArbol / (double) MEDICIONES / 1000.0;

        return new Resultado(insLista, insArbol, delLista, delArbol);
    }

    //porcentaje de mejora del arbol frente a la lista
    private static double mejora(double lista, double arbol) {
        if (lista <= 0) return 0.0;
        return ((lista - arbol) / lista) * 100.0;
    }

    private static String formatear(int n, Resultado division, Resultado multiplicacion) {

        StringBuilder sb = new StringBuilder();

        sb.append("<html><body><font face='Segoe UI' size='3'>");
        sb.append("<b>Hash Table Comparison: Linked List vs. Binary Tree</b><br>");
        sb.append("--------------------------------------------------------<br><br>");

        agregarBloque(sb, n, "Division", division);
        sb.append("<br>");
        agregarBloque(sb, n, "Multiplication", multiplicacion);

        sb.append("</font></body></html>");

        return sb.toString();
    }

    private static void agregarBloque(StringBuilder sb, int n, String metodo, Resultado r) {

        sb.append(String.format(EN,
                "Evaluated records: %d | Hash Function: %s<br><br>", n, metodo));

        sb.append("<b>1. INSERT Action:</b><br>");
        sb.append(String.format(EN,
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Linked List Time: %.3f ms<br>", r.insLista / 1000.0));
        sb.append(String.format(EN,
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Binary Tree Time: %.3f ms<br>", r.insArbol / 1000.0));
        sb.append(String.format(EN,
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Percentage improvement:&nbsp;&nbsp;%.2f %%<br><br>",
                mejora(r.insLista, r.insArbol)));

        sb.append("<b>2. DELETE Action:</b><br>");
        sb.append(String.format(EN,
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Linked List Time: %.3f ms<br>", r.delLista / 1000.0));
        sb.append(String.format(EN,
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Binary Tree Time: %.3f ms<br>", r.delArbol / 1000.0));
        sb.append(String.format(EN,
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Percentage improvement:&nbsp;&nbsp;%.2f %%<br><br>",
                mejora(r.delLista, r.delArbol)));

        double promedio = (mejora(r.insLista, r.insArbol) + mejora(r.delLista, r.delArbol)) / 2.0;
        sb.append(String.format(EN,
                "<b>Overall average improvement (Insert & Delete): %.2f %%</b><br>", promedio));
    }

    //resultado de un metodo hash: promedios en microsegundos
    private static class Resultado {

        final double insLista;
        final double insArbol;
        final double delLista;
        final double delArbol;

        Resultado(double insLista, double insArbol, double delLista, double delArbol) {
            this.insLista = insLista;
            this.insArbol = insArbol;
            this.delLista = delLista;
            this.delArbol = delArbol;
        }
    }
}
