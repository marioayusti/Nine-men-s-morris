package ProyectoSI;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mario A. Yusti
 */
public class AgenteTablero extends GuiAgent {

    private int comandoGUI = -1;
    transient protected GUIJuego GUIJuego;
    private ArrayList<FichaGUI> fichasJugador1;
    private ArrayList<FichaGUI> fichasJugador2;
    private boolean turnoJ1;
    private boolean jugador1Conectado;
    private boolean jugador2Conectado;
    private Behaviour comportamientoActual;

    public AgenteTablero() {
    }

    @Override
    public void setup() {
        this.fichasJugador1 = new ArrayList<>();
        this.fichasJugador2 = new ArrayList<>();
        this.turnoJ1 = true;
        this.GUIJuego = new GUIJuego(this);
        this.GUIJuego.setVisible(true);
    }

    @Override
    protected void onGuiEvent(GuiEvent ge) {
        this.comandoGUI = ge.getType();
        switch (this.comandoGUI) {
            case 1:
                intentarConectar(1);
                break;
            case 2:
                intentarConectar(2);
                break;
            case 3:
                empezarJuego();
                break;
            case 4:
                resetJuego();
                break;
        }
    }

    public void cambiarEstadoJugador(int idJugador, String nombreJugador, boolean estado) {
        if (idJugador == 1) {
            this.jugador1Conectado = estado;
            this.GUIJuego.setEstadoJ1(estado);
        } else {
            this.jugador2Conectado = estado;
            this.GUIJuego.setEstadoJ2(estado);
        }
        if (estado) {
            this.GUIJuego.addLog(nombreJugador + " está conectado!");
        } else {
            this.GUIJuego.addLog("¡Error al conectar con el agente \"" + nombreJugador + "\"!");
        }
    }

    public void intentarConectar(int JugadorId) {
        String nombreJugador;
        if (JugadorId == 1) {
            nombreJugador = this.GUIJuego.getJugador1Name();
        } else {
            nombreJugador = this.GUIJuego.getJugador2Name();
        }
        AID id = new AID();
        id.setLocalName(nombreJugador);
        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
        mensaje.setSender(getAID());
        mensaje.setLanguage("Español");
        mensaje.addReceiver(id);
        try {
            mensaje.setContentObject(new Tablero('C', null, null));
        } catch (IOException ex) {
            Logger.getLogger(AgenteTablero.class.getName()).log(Level.SEVERE, null, ex);
        }
        send(mensaje);
        this.comportamientoActual = new esperarRespuestaConexion(JugadorId, nombreJugador, this);
        addBehaviour(this.comportamientoActual);
    }

    public boolean isJugador1Conectado() {
        return jugador1Conectado;
    }

    public boolean isJugador2Conectado() {
        return jugador2Conectado;
    }

    public boolean isTurnoJ1() {
        return turnoJ1;
    }

    public void setTurnoJ1(boolean turnoJ1) {
        this.turnoJ1 = turnoJ1;
    }

    public ArrayList<FichaGUI> getFichasJugador1() {
        return fichasJugador1;
    }

    public ArrayList<FichaGUI> getFichasJugador2() {
        return fichasJugador2;
    }

    public int getNumeroFichasJ1() {
        return this.getFichasJugador1().size();
    }

    public int getNumeroFichasJ2() {
        return this.getFichasJugador2().size();
    }

    public int[] getPuntosFichasJ1() {
        int[] fichas = new int[this.getFichasJugador1().size()];
        for (int i = 0; i < this.getFichasJugador1().size(); i++) {
            fichas[i] = this.getFichasJugador1().get(i).getIdPunto();
        }
        return fichas;
    }

    public int[] getPuntosFichasJ2() {
        int[] fichas = new int[this.getFichasJugador2().size()];
        for (int i = 0; i < this.getFichasJugador2().size(); i++) {
            fichas[i] = this.getFichasJugador2().get(i).getIdPunto();
        }
        return fichas;
    }

    public void capturarFicha(int idFicha) {
        if (turnoJ1) {
            this.fichasJugador2.remove(idFicha);
        } else {
            this.fichasJugador1.remove(idFicha);
        }
        this.GUIJuego.actualizarGUI();
    }

    class esperarRespuestaConexion extends SimpleBehaviour {

        boolean done = false;
        int jugadorId;
        AgenteTablero agente;
        String nombreJugador;

        public esperarRespuestaConexion(int id, String _nombreJugador, AgenteTablero _agente) {
            this.jugadorId = id;
            this.agente = _agente;
            this.nombreJugador = _nombreJugador;
        }

        @Override
        public void action() {
            try {
                done = true;
                ACLMessage mensajeRespuesta = blockingReceive();
                Accion AccionRespuesta = (Accion) mensajeRespuesta.getContentObject();
                if (mensajeRespuesta != null && AccionRespuesta.getIdAccion() == 'C') {
                    this.agente.cambiarEstadoJugador(this.jugadorId, this.nombreJugador, true);
                    return;
                }
            } catch (UnreadableException ex) {
                //Logger.getLogger(AgenteTablero.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.agente.cambiarEstadoJugador(this.jugadorId, this.nombreJugador, false);
        }

        @Override
        public boolean done() {
            return done;
        }
    }

    public void empezarJuego() {
        if (isJugador1Conectado() && isJugador2Conectado()) {
            this.GUIJuego.goToJuego();
            removeBehaviour(this.comportamientoActual);
            this.comportamientoActual = new Juego(this.GUIJuego.getJugador1Name(), this.GUIJuego.getJugador2Name());
            addBehaviour(this.comportamientoActual);
        } else {
            this.GUIJuego.addLog("¡Todos los jugadores deben estar conectados para empezar el juego!");
        }
    }

    public void resetJuego() {
        this.getFichasJugador1().clear();
        this.getFichasJugador2().clear();
        removeBehaviour(this.comportamientoActual);
    }

    class Juego extends CyclicBehaviour {

        private boolean faseJuego;
        private final String nombreJugador1;
        private final String nombreJugador2;
        private int fichasColocadasJugador1;
        private int fichasColocadasJugador2;

        public Juego(String _nombreJugador1, String _nombreJugador2) {
            this.faseJuego = false;
            setTurnoJ1((((int) (Math.random() * 10)) % 2) == 1);
            this.nombreJugador1 = _nombreJugador1;
            this.nombreJugador2 = _nombreJugador2;
            this.fichasColocadasJugador1 = 0;
            this.fichasColocadasJugador2 = 0;
        }

        @Override
        public void action() {
            doWait(600);
            if (faseJuego) {
                enviarEstadoJuego('M');
            } else {
                enviarEstadoJuego('F');
                if (fichasColocadasJugador1 == 9 && fichasColocadasJugador2 == 9) {
                    faseJuego = true;
                }
            }
        }

        public void enviarEstadoJuego(char mensaje) {
            AID id = new AID();
            Tablero tablero;
            if (isTurnoJ1()) {
                id.setLocalName(this.nombreJugador1);
                tablero = new Tablero(mensaje, getPuntosFichasJ1(), getPuntosFichasJ2());
            } else {
                id.setLocalName(this.nombreJugador2);
                tablero = new Tablero(mensaje, getPuntosFichasJ2(), getPuntosFichasJ1());
            }
            ACLMessage estado = new ACLMessage(ACLMessage.REQUEST);
            estado.setSender(getAID());
            estado.setLanguage("Español");
            estado.addReceiver(id);
            try {
                estado.setContentObject(tablero);
            } catch (Exception e) {
            }
            send(estado);
            ACLMessage accionRespuesta = blockingReceive();
            try {
                Accion accion = (Accion) accionRespuesta.getContentObject();
                ejecutarAccion(accion);
            } catch (UnreadableException ex) {
                Logger.getLogger(AgenteTablero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void ejecutarAccion(Accion accion) {
            switch (accion.getIdAccion()) {
                case 'F':
                    GUIJuego.colocarFicha(isTurnoJ1(), accion.getIdPunto());
                    contarColocacionFicha();
                    break;
                case 'J':
                    GUIJuego.colocarFicha(isTurnoJ1(), accion.getIdPunto());
                    contarColocacionFicha();
                    capturarFicha(accion.getIdFichaCapturada());
                    GUIJuego.addLog("Jugador " + (isTurnoJ1() ? 1 : 2) + " - Captura la ficha " + accion.getIdFichaCapturada() + " del Jugador " + (isTurnoJ1() ? 2 : 1));
                    break;
                case 'M':
                    GUIJuego.moverFicha(isTurnoJ1(), accion.getIdFicha(), accion.getIdPunto());
                    break;
                case 'P':
                    GUIJuego.moverFicha(isTurnoJ1(), accion.getIdFicha(), accion.getIdPunto());
                    capturarFicha(accion.getIdFichaCapturada());
                    GUIJuego.addLog("Jugador " + (isTurnoJ1() ? 1 : 2) + " - Captura la ficha " + accion.getIdFichaCapturada() + " del Jugador " + (isTurnoJ1() ? 2 : 1));
                    break;
                case '-':
                    if (turnoJ1) {
                        JOptionPane.showMessageDialog(null, "¡Ha ganado el Jugador 2!", "¡Juegó Terminado!", JOptionPane.INFORMATION_MESSAGE);
                        GUIJuego.addLog("¡Ganó el jugador 2!");
                    } else {
                        JOptionPane.showMessageDialog(null, "¡Ha ganado el Jugador 1!", "¡Juegó Terminado!", JOptionPane.INFORMATION_MESSAGE);
                        GUIJuego.addLog("¡Ganó el jugador 1!");
                    }
                    removeBehaviour(comportamientoActual);
                    break;
                case 'E':
                    JOptionPane.showMessageDialog(null, "¡El juego acabó en empate!", "¡Juegó Terminado!", JOptionPane.INFORMATION_MESSAGE);
                    GUIJuego.addLog("¡El juego acabó en empate!");
                    removeBehaviour(comportamientoActual);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "No se reconoce el comando del Jugador " + (isTurnoJ1() ? 1 : 2), "¡Error de Comunicación!", JOptionPane.ERROR_MESSAGE);
                    GUIJuego.addLog("No se reconoce el comando del Jugador " + (isTurnoJ1() ? 1 : 2));
                    return;
            }
            turnoJ1 = !turnoJ1;
            GUIJuego.actualizarGUI();
        }

        public void contarColocacionFicha() {
            if (isTurnoJ1()) {
                this.fichasColocadasJugador1++;
            } else {
                this.fichasColocadasJugador2++;
            }
        }
    }
}
