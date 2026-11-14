package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import modelo.NodoArbol;
import modelo.Registro;

/**
 * Dibuja UNA fila de la vista de slots cuando los registros estan guardados en
 * arboles binarios de busqueda: la caja con el indice del slot a la izquierda y,
 * a su derecha, el arbol dibujado con sus aristas.
 *
 * La posicion horizontal de cada nodo se asigna con un recorrido IN-ORDEN: asi
 * los subarboles ocupan rangos de x disjuntos y nunca se solapan.
 * La altura (y) es la profundidad del nodo.
 *
 * Solo se muestra la placa de cada registro.
 *
 * @author W11
 */
public class PanelArbol extends javax.swing.JPanel {

    // ---- medidas (en pixeles) ----
    private static final int MARGEN     = 8;
    private static final int SLOT_W     = 68;
    private static final int SLOT_H     = 44;
    private static final int NODO_W     = 96;
    private static final int NODO_H     = 26;
    private static final int H_GAP      = 12;
    private static final int V_GAP      = 28;
    private static final int SEPARACION = 22;

    // ---- colores ----
    private static final Color SLOT_FONDO  = new Color(219, 234, 254);
    private static final Color SLOT_BORDE  = new Color(30, 58, 138);
    private static final Color NODO_FONDO  = Color.WHITE;
    private static final Color NODO_BORDE  = new Color(148, 163, 184);
    private static final Color TEXTO       = new Color(15, 23, 42);
    private static final Color TEXTO_SUAVE = new Color(71, 85, 105);
    private static final Color ARISTA      = new Color(148, 163, 184);

    private final int slot;
    private final NodoArbol<Registro> raiz;

    // Un elemento por nodo, en el orden en que se asignaron (que es in-orden).
    // El indice en la lista es tambien la posicion horizontal.
    private final List<String> etiquetas = new ArrayList<>();
    private final List<Integer> profundidades = new ArrayList<>();
    private final List<int[]> aristas = new ArrayList<>();   // {padre, hijo}

    private int profundidadMaxima = 0;

    public PanelArbol(int slot, NodoArbol<Registro> raiz) {
        initComponents();

        this.slot = slot;
        this.raiz = raiz;

        if (raiz != null) {
            ubicarNodos(raiz, 0);
        }

        setOpaque(false);

        Dimension tam = new Dimension(anchoPreferido(), altoPreferido());
        setPreferredSize(tam);
        setMinimumSize(tam);
    }

    // ------------------------------------------------------------------
    // calculo de la posicion de cada nodo
    // ------------------------------------------------------------------

    /**
     * Recorre el arbol in-orden y le asigna a cada nodo un indice consecutivo.
     * Devuelve el indice asignado a este nodo.
     */
    @SuppressWarnings("unchecked")
    private int ubicarNodos(NodoArbol<Registro> nodo, int profundidad) {

        int idIzquierdo = -1;
        if (nodo.getIzq() != null) {
            idIzquierdo = ubicarNodos((NodoArbol<Registro>) nodo.getIzq(), profundidad + 1);
        }

        int id = etiquetas.size();
        Registro registro = (Registro) nodo.getInformacion();
        etiquetas.add(registro == null ? "" : registro.getPlate());
        profundidades.add(profundidad);

        if (profundidad > profundidadMaxima) {
            profundidadMaxima = profundidad;
        }

        if (idIzquierdo >= 0) {
            aristas.add(new int[]{id, idIzquierdo});
        }

        if (nodo.getDer() != null) {
            int idDerecho = ubicarNodos((NodoArbol<Registro>) nodo.getDer(), profundidad + 1);
            aristas.add(new int[]{id, idDerecho});
        }

        return id;
    }

    private int anchoPreferido() {
        int n = etiquetas.size();
        return MARGEN + SLOT_W + SEPARACION + n * (NODO_W + H_GAP) + MARGEN;
    }

    private int altoArbol() {
        return (profundidadMaxima + 1) * NODO_H + profundidadMaxima * V_GAP;
    }

    private int altoPreferido() {
        return MARGEN * 2 + Math.max(altoArbol(), SLOT_H);
    }

    private int xNodo(int id) {
        return MARGEN + SLOT_W + SEPARACION + id * (NODO_W + H_GAP);
    }

    private int yNodo(int id) {
        return MARGEN + profundidades.get(id) * (NODO_H + V_GAP);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g0) {

        super.paintComponent(g0);

        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int centroY = getHeight() / 2;

        // ---------- caja del slot ----------
        // Se alinea arriba (a la altura de la raiz) y no al centro: en un arbol
        // profundo quedaría flotando muy abajo, lejos de su propio arbol.
        int xSlot = MARGEN;
        int ySlot = MARGEN;
        if (altoArbol() < SLOT_H) {
            ySlot = centroY - SLOT_H / 2;
        }

        dibujarCaja(g, xSlot, ySlot, SLOT_W, SLOT_H, SLOT_FONDO, SLOT_BORDE, 1.2f);

        g.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
        g.setColor(SLOT_BORDE);
        dibujarCentrado(g, String.valueOf(slot), xSlot, ySlot + 22, SLOT_W);

        g.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 10));
        g.setColor(TEXTO_SUAVE);
        dibujarCentrado(g, etiquetas.size() + " rec.", xSlot, ySlot + 36, SLOT_W);

        // ---------- aristas (se dibujan antes para que los nodos las tapen) ----------
        g.setColor(ARISTA);
        g.setStroke(new BasicStroke(1.3f));

        for (int[] arista : aristas) {
            int padre = arista[0];
            int hijo = arista[1];

            int x1 = xNodo(padre) + NODO_W / 2;
            int y1 = yNodo(padre) + NODO_H;
            int x2 = xNodo(hijo) + NODO_W / 2;
            int y2 = yNodo(hijo);

            g.drawLine(x1, y1, x2, y2);
        }

        // ---------- nodos ----------
        g.setFont(new Font("Consolas", Font.PLAIN, 12));

        for (int id = 0; id < etiquetas.size(); id++) {

            int x = xNodo(id);
            int y = yNodo(id);

            dibujarCaja(g, x, y, NODO_W, NODO_H, NODO_FONDO, NODO_BORDE, 1.0f);

            g.setColor(TEXTO);
            dibujarCentradoVertical(g, etiquetas.get(id), x, y, NODO_W, NODO_H);
        }

        g.dispose();
    }

    // ------------------------------------------------------------------
    // utilidades de dibujo
    // ------------------------------------------------------------------

    private void dibujarCaja(Graphics2D g, int x, int y, int w, int h, Color fondo, Color borde, float grosor) {
        g.setColor(fondo);
        g.fill(new RoundRectangle2D.Float(x, y, w, h, 10, 10));

        g.setColor(borde);
        g.setStroke(new BasicStroke(grosor));
        g.draw(new RoundRectangle2D.Float(x, y, w, h, 10, 10));
    }

    private void dibujarCentrado(Graphics2D g, String texto, int x, int yBase, int ancho) {
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (ancho - fm.stringWidth(texto)) / 2;
        g.drawString(texto, tx, yBase);
    }

    private void dibujarCentradoVertical(Graphics2D g, String texto, int x, int y, int w, int h) {
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (w - fm.stringWidth(texto)) / 2;
        int ty = y + (h - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(texto, tx, ty);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
