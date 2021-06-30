package ProyectoSI;

import java.io.Serializable;

/**
 *
 * @author Mario A. Yusti
 */
public class Accion implements Serializable {

    private final char idAccion;
    private int idFicha;
    private int idPunto;
    private int idFichaCapturada;

    public Accion(char _idAccion) {
        this.idAccion = _idAccion;
    }

    public Accion(char _idAccion, int _idFicha, int _idPunto) {
        this.idAccion = _idAccion;
        this.idFicha = _idFicha;
        this.idPunto = _idPunto;
    }

    public Accion(char _idAccion, int _idFicha, int _idPunto, int _idFichaCapturada) {
        this.idAccion = _idAccion;
        this.idFicha = _idFicha;
        this.idPunto = _idPunto;
        this.idFichaCapturada = _idFichaCapturada;
    }

    public char getIdAccion() {
        return idAccion;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public int getIdFichaCapturada() {
        return idFichaCapturada;
    }
}
