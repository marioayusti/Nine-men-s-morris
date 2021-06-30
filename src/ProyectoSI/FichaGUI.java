package ProyectoSI;

/**
 *
 * @author Mario A. Yusti
 */
public class FichaGUI {

    private int x;
    private int y;
    private int idPunto;

    public FichaGUI(int _x, int _y, int _idPunto) {
        this.x = _x;
        this.y = _y;
        this.idPunto = _idPunto;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }
}
