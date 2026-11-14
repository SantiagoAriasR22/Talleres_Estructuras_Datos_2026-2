package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

/**
 * Dibuja UNA fila de la vista de slots cuando los registros estan guardados en
 * listas enlazadas: la caja con el indice del slot a la izquierda y, a su
 * derecha, la cadena de registros con flechas entre ellos.
 *
 * Solo se muestra la placa de cada registro.
 *
 * @author W11
 */
public class PanelLista extends javax.swing.JPanel {

    // ---- medidas (en pixeles) ----
    private static final int MARGEN     = 8;
    private static final int SLOT_W     = 68;
    private static final int SLOT_H     = 44;
    private static final int CHIP_W     = 100;
    private static final int CHIP_H     = 30;
    private static final int FLECHA_W   = 28;
    private static final int SEPARACION = 20;

    // ---- colores (los mismos del resto de la interfaz) ----
    private static final Color SLOT_FONDO  = new Color(219, 234, 254);
    private static final Color SLOT_BORDE  = new Color(30, 58, 138);
    private static final Color CHIP_FONDO  = Color.WHITE;
    private static final Color CHIP_BORDE  = new Color(148, 163, 184);
    private static final Color TEXTO       = new Color(15, 23, 42);
    private static final Color TEXTO_SUAVE = new Color(71, 85, 105);
    private static final Color FLECHA      = new Color(100, 116, 139);

    private final int slot;
    private final List<String> placas;

    public PanelLista(int slot, List<String> placas) {
        initComponents();

        this.slot = slot;
        this.placas = placas;

        setOpaque(false);

        Dimension tam = new Dimension(anchoPreferido(), altoPreferido());
        setPreferredSize(tam);
        setMinimumSize(tam);
    }

    private int anchoPreferido() {
        int n = placas.size();
        return MARGEN + SLOT_W + SEPARACION + n * CHIP_W + (n - 1) * FLECHA_W + MARGEN;
    }

    private int altoPreferido() {
        return MARGEN * 2 + Math.max(SLOT_H, CHIP_H);
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
        int xSlot = MARGEN;
        int ySlot = centroY - SLOT_H / 2;

        dibujarCaja(g, xSlot, ySlot, SLOT_W, SLOT_H, SLOT_FONDO, SLOT_BORDE, 1.2f);

        g.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
        g.setColor(SLOT_BORDE);
        dibujarCentrado(g, String.valueOf(slot), xSlot, ySlot + 22, SLOT_W);

        g.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 10));
        g.setColor(TEXTO_SUAVE);
        dibujarCentrado(g, placas.size() + " rec.", xSlot, ySlot + 36, SLOT_W);

        // ---------- cadena de registros ----------
        int bordeDerechoAnterior = xSlot + SLOT_W;

        for (int i = 0; i < placas.size(); i++) {

            int xChip = xSlot + SLOT_W + SEPARACION + i * (CHIP_W + FLECHA_W);

            dibujarFlecha(g, bordeDerechoAnterior, centroY, xChip);

            int yChip = centroY - CHIP_H / 2;
            dibujarCaja(g, xChip, yChip, CHIP_W, CHIP_H, CHIP_FONDO, CHIP_BORDE, 1.0f);

            g.setFont(new Font("Consolas", Font.PLAIN, 12));
            g.setColor(TEXTO);
            dibujarCentradoVertical(g, placas.get(i), xChip, yChip, CHIP_W, CHIP_H);

            bordeDerechoAnterior = xChip + CHIP_W;
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

    /** Dibuja el texto centrado horizontalmente; yBase es la linea base. */
    private void dibujarCentrado(Graphics2D g, String texto, int x, int yBase, int ancho) {
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (ancho - fm.stringWidth(texto)) / 2;
        g.drawString(texto, tx, yBase);
    }

    /** Dibuja el texto centrado horizontal y verticalmente dentro de la caja. */
    private void dibujarCentradoVertical(Graphics2D g, String texto, int x, int y, int w, int h) {
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (w - fm.stringWidth(texto)) / 2;
        int ty = y + (h - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(texto, tx, ty);
    }

    private void dibujarFlecha(Graphics2D g, int xInicio, int y, int xFinChip) {
        int x1 = xInicio + 3;
        int x2 = xFinChip - 2;

        g.setColor(FLECHA);
        g.setStroke(new BasicStroke(1.4f));
        g.drawLine(x1, y, x2 - 7, y);

        Polygon punta = new Polygon(
                new int[]{x2, x2 - 8, x2 - 8},
                new int[]{y, y - 4, y + 4},
                3);
        g.fillPolygon(punta);
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
