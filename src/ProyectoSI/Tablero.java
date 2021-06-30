package ProyectoSI;

import java.io.Serializable;

/**
 *
 * @author Mario A. Yusti
 */
public class Tablero implements Serializable {

    private final char idMensaje;
    private final int[] fichasJugador1;
    private final int[] fichasJugador2;

    public Tablero(char _idMensaje, int[] _fichasJugador1, int[] _fichasJugador2) {
        this.idMensaje = _idMensaje;
        this.fichasJugador1 = _fichasJugador1;
        this.fichasJugador2 = _fichasJugador2;
    }

    public int[] getFichasJugador1() {
        return fichasJugador1;
    }

    public int[] getFichasJugador2() {
        return fichasJugador2;
    }

    public char getIdMensaje() {
        return idMensaje;
    }
}
