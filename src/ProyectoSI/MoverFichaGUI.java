package ProyectoSI;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Mario A. Yusti
 */
public class MoverFichaGUI implements Runnable {

    private final PuntoGUI fin;
    private final FichaGUI ficha;
    private final JPanel panelJuego;

    public MoverFichaGUI(FichaGUI _ficha, PuntoGUI _puntoFinal, JPanel _panelJuego) {
        this.ficha = _ficha;
        this.fin = _puntoFinal;
        this.panelJuego = _panelJuego;
    }

    @Override
    public void run() {
        boolean moving = true;
        boolean refX = true;
        double mY, mX;
        double posX = this.ficha.getX();
        double posY = this.ficha.getY();
        int dX = this.fin.getX() - this.ficha.getX();
        int dY = this.fin.getY() - this.ficha.getY();
        int mRef, ms;
        if (dY != 0 && (dX != 0 && Math.abs(dY) < Math.abs(dX) || dX == 0)) {
            mRef = Math.abs(dY);
            refX = false;
        } else {
            mRef = Math.abs(dX);
        }
        mX = (double) dX / mRef;
        mY = (double) dY / mRef;
        ms = 500 / mRef;
        while (moving) {
            try {
                posX += mX;
                posY += mY;
                this.ficha.setX((int) posX);
                this.ficha.setY((int) posY);
                if (refX && this.ficha.getX() == this.fin.getX()) {
                    this.ficha.setY(this.fin.getY());
                    moving = false;
                } else if (!refX && this.ficha.getY() == this.fin.getY()) {
                    this.ficha.setX(this.fin.getX());
                    moving = false;
                }
                this.panelJuego.repaint();
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                Logger.getLogger(MoverFichaGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            finalize();
        } catch (Throwable ex) {
            Logger.getLogger(MoverFichaGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
