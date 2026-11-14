package estadisticas;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import modelo.Arbol;
import modelo.Lista;
import modelo.NodoArbol;
import modelo.NodoLista;
import modelo.Registro;
import modelo.TablaHash;
import persistencia.Lectura;

/**
 * Mide cuanto mejora guardar cada slot con arboles en vez de listas.
 *
 * Metodologia (pensada para que la comparacion sea justa y repetible):
 *
 *   1. Se calientan las CUATRO combinaciones (lista/arbol x division/multiplicacion)
 *      antes de medir ninguna. El JIT compila el codigo de Arbol.adicionar solo
 *      cuando ese codigo corre; si se mide la lista primero y el arbol despues,
 *      parte de la medicion del arbol cae mientras la JVM todavia esta compilando.
 *
 *   2. Las repeticiones se INTERCALAN: en cada vuelta se mide una vez cada
 *      combinacion. Asi un pico de GC o una compilacion del JIT afecta a todas
 *      por igual, en vez de castigar solo a la que se estaba midiendo.
 *
 *   3. Se resume con la MEDIANA de las repeticiones, no con el promedio: la
 *      mediana ignora los picos y no se deja arrastrar por una corrida mala.
 *
 *   4. Las dos estructuras reciben los mismos registros en el mismo orden y
 *      borran en el mismo orden (una sola barajada, con semilla fija).
 *
 * Una corrida es: insertar los N registros y despues borrarlos todos.
 * El cronometro va alrededor del bucle completo y se divide entre N.
 *
 * Esta clase NO imprime nada desde calcular(): devuelve un Informe y la vista
 * decide como mostrarlo. main() existe solo para sacar el informe por consola.
 *
 * @author W11
 */
public class Estadisticas {

    // Cuantas corridas completas se hacen de cada combinacion antes de medir.
    // Son bastantes a proposito: la primera vez que se abre la prueba en una JVM
    // el compilador del JIT todavia esta trabajando, y con poco calentamiento la
    // primera corrida sale distinta a las siguientes.
    private static final int CALENTAMIENTO = 50;

    // Cuantas veces se mide cada combinacion. Impar para que la mediana sea un
    // valor realmente observado, no un promedio de dos.
    private static final int REPETICIONES = 21;

    private static final long SEMILLA = 7L;

    // ------------------------------------------------------------------
    // resultados
    // ------------------------------------------------------------------

    /** Una fila de la tabla de tiempos: una operacion, en las dos estructuras. */
    public static class Fila {

        public final String metodo;
        public final String operacion;
        public final double lista;
        public final double arbol;

        public Fila(String metodo, String operacion, double lista, double arbol) {
            this.metodo = metodo;
            this.operacion = operacion;
            this.lista = lista;
            this.arbol = arbol;
        }

        /** Porcentaje de mejora del arbol frente a la lista. */
        public double mejora() {
            if (lista <= 0) return 0;
            return (lista - arbol) / lista * 100.0;
        }
    }

    /** Una fila de la tabla de estructura resultante. */
    public static class Estructura {

        public final String metodo;
        public final String tipo;
        public final int slots;
        public final double cadena;    // -1 si es arbol
        public final double altura;    // -1 si es lista
        public final int maximo;

        public Estructura(String metodo, String tipo, int slots,
                          double cadena, double altura, int maximo) {
            this.metodo = metodo;
            this.tipo = tipo;
            this.slots = slots;
            this.cadena = cadena;
            this.altura = altura;
            this.maximo = maximo;
        }
    }

    /** Todo el resultado de la prueba. */
    public static class Informe {

        public final String archivo;
        public final int registros;
        public final int calentamiento = CALENTAMIENTO;
        public final int repeticiones = REPETICIONES;
        public final List<Fila> filas = new ArrayList<>();
        public final List<Estructura> estructura = new ArrayList<>();

        // Datos de contexto para el informe escrito. La GUI no los usa, pero
        // explican por que el resultado sale como sale.
        public double factorCarga = 0;          // registros / m
        public int colisionesDivision = 0;
        public int colisionesMultiplicacion = 0;

        public Informe(String archivo, int registros) {
            this.archivo = archivo;
            this.registros = registros;
        }
    }

    // ------------------------------------------------------------------
    // prueba
    // ------------------------------------------------------------------

    /** Corre la prueba sobre el archivo indicado y devuelve los resultados. */
    public static Informe calcular(File archivo) {

        List<Registro> registros = new Lectura().leer(archivo);

        if (registros == null) {
            registros = new ArrayList<>();
        }

        Informe informe = new Informe(archivo.getName(), registros.size());

        if (registros.isEmpty()) {
            return informe;
        }

        informe.factorCarga = registros.size() / (double) new TablaHash(true, true).getM();
        informe.colisionesDivision = colisiones(registros, true);
        informe.colisionesMultiplicacion = colisiones(registros, false);

        // Una sola barajada: las dos estructuras borran exactamente en este orden.
        List<Registro> ordenBorrado = new ArrayList<>(registros);
        Collections.shuffle(ordenBorrado, new Random(SEMILLA));

        Caso[] casos = {
            new Caso(true, true),      // Division, lista
            new Caso(false, true),     // Division, arbol
            new Caso(true, false),     // Multiplication, lista
            new Caso(false, false)     // Multiplication, arbol
        };

        // 1) Calentar TODAS las combinaciones antes de medir ninguna.
        for (int i = 0; i < CALENTAMIENTO; i++) {
            for (Caso c : casos) {
                unaCorrida(registros, ordenBorrado, c.esLista, c.esDivision);
            }
        }

        // Una vuelta mas que se descarta: la primera medicion de una JVM puede
        // agarrar la CPU todavia arrancando. No entra en la mediana.
        for (Caso c : casos) {
            unaCorrida(registros, ordenBorrado, c.esLista, c.esDivision);
        }

        // 2) Medir intercalado: en cada vuelta se mide una vez cada combinacion.
        //    El orden se baraja en cada vuelta para que ninguna combinacion quede
        //    siempre justo despues de otra (eso podria cargarle el GC de la anterior).
        List<Caso> orden = new ArrayList<>();
        Collections.addAll(orden, casos);
        Random barajador = new Random(SEMILLA + 1);

        for (int i = 0; i < REPETICIONES; i++) {
            Collections.shuffle(orden, barajador);
            for (Caso c : orden) {
                double[] r = unaCorrida(registros, ordenBorrado, c.esLista, c.esDivision);
                c.inserciones.add(r[0]);
                c.borrados.add(r[1]);
            }
        }

        // 3) Resumir cada combinacion con la mediana de sus repeticiones.
        double[] divLista = medidas(casos[0]);
        double[] divArbol = medidas(casos[1]);
        double[] mulLista = medidas(casos[2]);
        double[] mulArbol = medidas(casos[3]);

        informe.filas.add(new Fila("Division", "Insert", divLista[0], divArbol[0]));
        informe.filas.add(new Fila("Division", "Delete", divLista[1], divArbol[1]));
        informe.filas.add(new Fila("Multiplication", "Insert", mulLista[0], mulArbol[0]));
        informe.filas.add(new Fila("Multiplication", "Delete", mulLista[1], mulArbol[1]));

        // El taller pide UN porcentaje promedio de las dos acciones: esta fila
        // lo deja a la vista sin tocar la vista.
        double promedioLista = (divLista[0] + divLista[1] + mulLista[0] + mulLista[1]) / 4.0;
        double promedioArbol = (divArbol[0] + divArbol[1] + mulArbol[0] + mulArbol[1]) / 4.0;
        informe.filas.add(new Fila("Average", "Insert + Delete", promedioLista, promedioArbol));

        informe.estructura.add(estructura(registros, true, true, "Division", "Lists"));
        informe.estructura.add(estructura(registros, false, true, "Division", "Trees"));
        informe.estructura.add(estructura(registros, true, false, "Multiplication", "Lists"));
        informe.estructura.add(estructura(registros, false, false, "Multiplication", "Trees"));

        return informe;
    }

    /**
     * Una corrida completa: crear tabla, cronometrar insertar todos,
     * y despues cronometrar borrar todos en el orden ya barajado.
     *
     * @return { nanosegundos por insercion, nanosegundos por borrado }
     */
    private static double[] unaCorrida(List<Registro> registros, List<Registro> ordenBorrado,
                                       boolean esLista, boolean esDivision) {

        TablaHash tabla = new TablaHash(esLista, esDivision);

        long t0 = System.nanoTime();

        for (Registro r : registros) {
            tabla.insertarRegistro(r);
        }

        long t1 = System.nanoTime();

        for (Registro r : ordenBorrado) {
            tabla.eliminar(r.getPlate());
        }

        long t2 = System.nanoTime();

        int n = registros.size();

        return new double[]{
            (double) (t1 - t0) / n,
            (double) (t2 - t1) / n
        };
    }

    /** { mediana de inserciones, mediana de borrados } de un caso. */
    private static double[] medidas(Caso c) {
        return new double[]{ mediana(c.inserciones), mediana(c.borrados) };
    }

    private static double mediana(List<Double> valores) {
        List<Double> copia = new ArrayList<>(valores);
        Collections.sort(copia);
        return copia.get(copia.size() / 2);
    }

    /** Cuantos objetos comparten slot con otro, para el metodo hash indicado. */
    private static int colisiones(List<Registro> registros, boolean esDivision) {
        TablaHash tabla = new TablaHash(true, esDivision);
        for (Registro r : registros) {
            tabla.insertarRegistro(r);
        }
        return tabla.getColisiones();
    }

    // ------------------------------------------------------------------
    // estructura resultante
    // ------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    private static Estructura estructura(List<Registro> registros, boolean esLista,
                                         boolean esDivision, String metodo, String tipo) {

        TablaHash tabla = new TablaHash(esLista, esDivision);
        for (Registro r : registros) tabla.insertarRegistro(r);

        int ocupados = 0;
        int totalNodos = 0;
        int maxCadena = 0;
        int sumaAlturas = 0;
        int maxAltura = 0;

        for (int i = 0; i < tabla.getM(); i++) {

            Object slot = tabla.getTablaHash().get(i);

            if (esLista) {

                Lista<Registro> lista = (Lista<Registro>) slot;

                int largo = 0;
                for (NodoLista<Registro> x = lista.getHead(); x != null; x = x.getNext()) largo++;

                if (largo > 0) ocupados++;
                totalNodos += largo;
                if (largo > maxCadena) maxCadena = largo;

            } else {

                Arbol<Registro> arbol = (Arbol<Registro>) slot;
                NodoArbol<Registro> raiz = (NodoArbol<Registro>) arbol.getRaiz();

                int alto = altura(raiz);

                if (raiz != null) ocupados++;
                totalNodos += nodos(raiz);
                sumaAlturas += alto;
                if (alto > maxAltura) maxAltura = alto;
            }
        }

        if (ocupados == 0) {
            return new Estructura(metodo, tipo, 0, 0, 0, 0);
        }

        if (esLista) {
            return new Estructura(metodo, tipo, ocupados,
                    (double) totalNodos / ocupados, -1, maxCadena);
        }

        return new Estructura(metodo, tipo, ocupados,
                -1, (double) sumaAlturas / ocupados, maxAltura);
    }

    private static int nodos(NodoArbol<Registro> n) {
        if (n == null) return 0;
        return 1 + nodos((NodoArbol<Registro>) n.getIzq())
                 + nodos((NodoArbol<Registro>) n.getDer());
    }

    private static int altura(NodoArbol<Registro> n) {
        if (n == null) return 0;
        return 1 + Math.max(altura((NodoArbol<Registro>) n.getIzq()),
                            altura((NodoArbol<Registro>) n.getDer()));
    }

    // ------------------------------------------------------------------
    // consola (opcional, para sacar el informe del escrito)
    // ------------------------------------------------------------------

    public static void main(String[] args) {

        File archivo = new File(args.length > 0 ? args[0] : "MOCK_DATA.csv");

        Informe inf = calcular(archivo);

        System.out.println("Performance test - " + inf.archivo);
        System.out.printf(Locale.US,
                "Records: %d | load factor: %.2f | warm-up: %d | repetitions: %d (median)%n",
                inf.registros, inf.factorCarga, inf.calentamiento, inf.repeticiones);
        System.out.printf(Locale.US, "Collisions: division=%d, multiplication=%d%n%n",
                inf.colisionesDivision, inf.colisionesMultiplicacion);

        System.out.printf(Locale.US, "%-15s %-17s %12s %12s %12s%n",
                "Method", "Operation", "List (ns)", "Tree (ns)", "Improvement");
        System.out.println("---------------------------------------------------------------"
                + "-----------------");
        for (Fila f : inf.filas) {
            System.out.printf(Locale.US, "%-15s %-17s %12.1f %12.1f %+11.1f%%%n",
                    f.metodo, f.operacion, f.lista, f.arbol, f.mejora());
        }

        System.out.printf(Locale.US, "%n%-15s %-6s %6s %12s %12s %6s%n",
                "Method", "Type", "Slots", "Avg chain", "Avg height", "Max");
        System.out.println("---------------------------------------------------------------"
                + "-----------------");
        for (Estructura e : inf.estructura) {
            System.out.printf(Locale.US, "%-15s %-6s %6d %12s %12s %6d%n",
                    e.metodo, e.tipo, e.slots,
                    e.cadena < 0 ? "-" : String.format(Locale.US, "%.2f", e.cadena),
                    e.altura < 0 ? "-" : String.format(Locale.US, "%.2f", e.altura),
                    e.maximo);
        }
    }

    // ------------------------------------------------------------------
    // auxiliar
    // ------------------------------------------------------------------

    /** Una combinacion (estructura, metodo hash) con sus repeticiones. */
    private static final class Caso {

        final boolean esLista;
        final boolean esDivision;
        final List<Double> inserciones = new ArrayList<>();
        final List<Double> borrados = new ArrayList<>();

        Caso(boolean esLista, boolean esDivision) {
            this.esLista = esLista;
            this.esDivision = esDivision;
        }
    }
}
