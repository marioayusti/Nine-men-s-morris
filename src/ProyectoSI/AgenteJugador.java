package ProyectoSI;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario A. Yusti
 */
public class AgenteJugador extends Agent {

    public AgenteJugador() {
    }

    @Override
    public void setup() {
        Jugar solucionador = new Jugar();
        addBehaviour(solucionador);
    }

    class Jugar extends CyclicBehaviour {

        private final Punto[] puntos;
        private final int[][] matrixLineas;
        private final int[][] matrixLineasPuntos;
        private final int[] arrayPuntos;
        private Tablero tablero;
        private ArrayList<String> nodosVisitadosJ1;
        private ArrayList<String> nodosVisitadosJ2;

        public Jugar() {

            this.arrayPuntos = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};

            this.puntos = new Punto[24];
            this.puntos[0] = new Punto(0);
            this.puntos[1] = new Punto(1);
            this.puntos[2] = new Punto(2);
            this.puntos[3] = new Punto(3);
            this.puntos[4] = new Punto(4);
            this.puntos[5] = new Punto(5);
            this.puntos[6] = new Punto(6);
            this.puntos[7] = new Punto(7);
            this.puntos[8] = new Punto(8);
            this.puntos[9] = new Punto(9);
            this.puntos[10] = new Punto(10);
            this.puntos[11] = new Punto(11);
            this.puntos[12] = new Punto(12);
            this.puntos[13] = new Punto(13);
            this.puntos[14] = new Punto(14);
            this.puntos[15] = new Punto(15);
            this.puntos[16] = new Punto(16);
            this.puntos[17] = new Punto(17);
            this.puntos[18] = new Punto(18);
            this.puntos[19] = new Punto(19);
            this.puntos[20] = new Punto(20);
            this.puntos[21] = new Punto(21);
            this.puntos[22] = new Punto(22);
            this.puntos[23] = new Punto(23);
            this.puntos[0].setNeighbours(new int[]{1, 7});
            this.puntos[1].setNeighbours(new int[]{0, 2, 9});
            this.puntos[2].setNeighbours(new int[]{1, 3});
            this.puntos[3].setNeighbours(new int[]{2, 4, 11});
            this.puntos[4].setNeighbours(new int[]{3, 5});
            this.puntos[5].setNeighbours(new int[]{4, 6, 13});
            this.puntos[6].setNeighbours(new int[]{5, 7});
            this.puntos[7].setNeighbours(new int[]{0, 6, 15});
            this.puntos[8].setNeighbours(new int[]{9, 15});
            this.puntos[9].setNeighbours(new int[]{1, 8, 10, 17});
            this.puntos[10].setNeighbours(new int[]{9, 11});
            this.puntos[11].setNeighbours(new int[]{3, 10, 12, 19});
            this.puntos[12].setNeighbours(new int[]{11, 13});
            this.puntos[13].setNeighbours(new int[]{5, 12, 14, 21});
            this.puntos[14].setNeighbours(new int[]{13, 15});
            this.puntos[15].setNeighbours(new int[]{7, 8, 14, 23});
            this.puntos[16].setNeighbours(new int[]{17, 23});
            this.puntos[17].setNeighbours(new int[]{9, 16, 18});
            this.puntos[18].setNeighbours(new int[]{17, 19});
            this.puntos[19].setNeighbours(new int[]{11, 18, 20});
            this.puntos[20].setNeighbours(new int[]{19, 21});
            this.puntos[21].setNeighbours(new int[]{13, 20, 22});
            this.puntos[22].setNeighbours(new int[]{21, 23});
            this.puntos[23].setNeighbours(new int[]{15, 16, 22});

            this.matrixLineas = new int[16][3];
            this.matrixLineas[0][0] = 0;
            this.matrixLineas[0][1] = 1;
            this.matrixLineas[0][2] = 2;
            this.matrixLineas[1][0] = 2;
            this.matrixLineas[1][1] = 3;
            this.matrixLineas[1][2] = 4;
            this.matrixLineas[2][0] = 4;
            this.matrixLineas[2][1] = 5;
            this.matrixLineas[2][2] = 6;
            this.matrixLineas[3][0] = 6;
            this.matrixLineas[3][1] = 7;
            this.matrixLineas[3][2] = 0;
            this.matrixLineas[4][0] = 8;
            this.matrixLineas[4][1] = 9;
            this.matrixLineas[4][2] = 10;
            this.matrixLineas[5][0] = 10;
            this.matrixLineas[5][1] = 11;
            this.matrixLineas[5][2] = 12;
            this.matrixLineas[6][0] = 12;
            this.matrixLineas[6][1] = 13;
            this.matrixLineas[6][2] = 14;
            this.matrixLineas[7][0] = 14;
            this.matrixLineas[7][1] = 15;
            this.matrixLineas[7][2] = 8;
            this.matrixLineas[8][0] = 16;
            this.matrixLineas[8][1] = 17;
            this.matrixLineas[8][2] = 18;
            this.matrixLineas[9][0] = 18;
            this.matrixLineas[9][1] = 19;
            this.matrixLineas[9][2] = 20;
            this.matrixLineas[10][0] = 20;
            this.matrixLineas[10][1] = 21;
            this.matrixLineas[10][2] = 22;
            this.matrixLineas[11][0] = 22;
            this.matrixLineas[11][1] = 23;
            this.matrixLineas[11][2] = 16;
            this.matrixLineas[12][0] = 1;
            this.matrixLineas[12][1] = 9;
            this.matrixLineas[12][2] = 17;
            this.matrixLineas[13][0] = 3;
            this.matrixLineas[13][1] = 11;
            this.matrixLineas[13][2] = 19;
            this.matrixLineas[14][0] = 5;
            this.matrixLineas[14][1] = 13;
            this.matrixLineas[14][2] = 21;
            this.matrixLineas[15][0] = 7;
            this.matrixLineas[15][1] = 15;
            this.matrixLineas[15][2] = 23;

            this.matrixLineasPuntos = new int[24][2];
            this.matrixLineasPuntos[0][0] = 0;
            this.matrixLineasPuntos[0][1] = 3;
            this.matrixLineasPuntos[1][0] = 0;
            this.matrixLineasPuntos[1][1] = 12;
            this.matrixLineasPuntos[2][0] = 0;
            this.matrixLineasPuntos[2][1] = 1;
            this.matrixLineasPuntos[3][0] = 1;
            this.matrixLineasPuntos[3][1] = 13;
            this.matrixLineasPuntos[4][0] = 1;
            this.matrixLineasPuntos[4][1] = 2;
            this.matrixLineasPuntos[5][0] = 2;
            this.matrixLineasPuntos[5][1] = 14;
            this.matrixLineasPuntos[6][0] = 2;
            this.matrixLineasPuntos[6][1] = 3;
            this.matrixLineasPuntos[7][0] = 3;
            this.matrixLineasPuntos[7][1] = 15;
            this.matrixLineasPuntos[8][0] = 4;
            this.matrixLineasPuntos[8][1] = 7;
            this.matrixLineasPuntos[9][0] = 4;
            this.matrixLineasPuntos[9][1] = 12;
            this.matrixLineasPuntos[10][0] = 4;
            this.matrixLineasPuntos[10][1] = 5;
            this.matrixLineasPuntos[11][0] = 5;
            this.matrixLineasPuntos[11][1] = 13;
            this.matrixLineasPuntos[12][0] = 5;
            this.matrixLineasPuntos[12][1] = 6;
            this.matrixLineasPuntos[13][0] = 6;
            this.matrixLineasPuntos[13][1] = 14;
            this.matrixLineasPuntos[14][0] = 6;
            this.matrixLineasPuntos[14][1] = 7;
            this.matrixLineasPuntos[15][0] = 7;
            this.matrixLineasPuntos[15][1] = 15;
            this.matrixLineasPuntos[16][0] = 8;
            this.matrixLineasPuntos[16][1] = 11;
            this.matrixLineasPuntos[17][0] = 8;
            this.matrixLineasPuntos[17][1] = 12;
            this.matrixLineasPuntos[18][0] = 8;
            this.matrixLineasPuntos[18][1] = 9;
            this.matrixLineasPuntos[19][0] = 9;
            this.matrixLineasPuntos[19][1] = 13;
            this.matrixLineasPuntos[20][0] = 9;
            this.matrixLineasPuntos[20][1] = 10;
            this.matrixLineasPuntos[21][0] = 10;
            this.matrixLineasPuntos[21][1] = 14;
            this.matrixLineasPuntos[22][0] = 10;
            this.matrixLineasPuntos[22][1] = 11;
            this.matrixLineasPuntos[23][0] = 11;
            this.matrixLineasPuntos[23][1] = 15;
        }

        @Override
        public void action() {
            ACLMessage mensaje = receive();
            if (mensaje != null) {
                try {
                    this.tablero = (Tablero) mensaje.getContentObject();
                    Accion accionRespuesta;
                    switch (this.tablero.getIdMensaje()) {
                        case 'C':
                            accionRespuesta = new Accion('C');
                            break;
                        case 'F':
                            int punto;
                            if (this.tablero.getFichasJugador2().length < 2) {
                                punto = getPuntoColocacionRandom(this.tablero.getFichasJugador1(), this.tablero.getFichasJugador2());
                                accionRespuesta = new Accion('F', 0, punto);
                            } else {
                                accionRespuesta = colocarPunto();
                            }
                            break;
                        case 'M':
                            if (jugadorPuedeMoverse(this.tablero.getFichasJugador1(), this.tablero.getFichasJugador2())) {
                                accionRespuesta = calcularMejorMovimiento();
                            } else if (jugadorPuedeMoverse(this.tablero.getFichasJugador2(), this.tablero.getFichasJugador1())) {
                                accionRespuesta = new Accion('-');
                            } else {
                                accionRespuesta = new Accion('E');
                            }
                            break;
                        default:
                            accionRespuesta = new Accion('0');
                            break;
                    }
                    ACLMessage respuesta = mensaje.createReply();
                    respuesta.setPerformative(ACLMessage.INFORM);
                    respuesta.setContentObject(accionRespuesta);
                    send(respuesta);
                } catch (UnreadableException | IOException ex) {
                    try {
                        ACLMessage respuesta = mensaje.createReply();
                        respuesta.setPerformative(ACLMessage.INFORM);
                        respuesta.setContentObject(new Accion('0'));
                        send(respuesta);
                    } catch (IOException ex1) {
                        Logger.getLogger(AgenteJugador.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

        public int getPuntoColocacionRandom(int[] fichasJ1, int[] fichasJ2) {
            int punto;
            do {
                punto = getRandomNumber(0, 23);
            } while (!puntoEstaLibre(punto, fichasJ1, fichasJ2));
            return punto;
        }

        public Accion colocarPunto() {
            int idPunto = -1;
            for (int i = 0; i < 24; i++) {
                if (puntoEstaLibre(i, this.tablero.getFichasJugador1(), this.tablero.getFichasJugador2())) {
                    if (checkLineaDeTres(i, this.tablero.getFichasJugador1())) {
                        return new Accion('J', 0, i, buscarMejorCaptura(this.tablero.getFichasJugador1(), this.tablero.getFichasJugador2()));
                    } else if (checkLineaDeTres(i, this.tablero.getFichasJugador2())) {
                        idPunto = i;
                    }
                }
            }
            if (idPunto == -1) {
                idPunto = getPuntoColocacionRandom(this.tablero.getFichasJugador1(), this.tablero.getFichasJugador2());
            }
            return new Accion('F', 0, idPunto);
        }

        public boolean puntoEstaLibre(int idPunto, int[] fichasJ1, int[] fichasJ2) {
            for (int i = 0; i < fichasJ1.length; i++) {
                if (fichasJ1[i] == idPunto) {
                    return false;
                }
            }
            for (int i = 0; i < fichasJ2.length; i++) {
                if (fichasJ2[i] == idPunto) {
                    return false;
                }
            }
            return true;
        }

//        public boolean checkEvitaCaptura(int idPunto, int[] fichasOJ) {
//            return checkLineaDeTres(idPunto, fichasOJ) && tieneVecinoAmigo(idPunto, fichasOJ);
//        }
//
//        public boolean tieneVecinoAmigo(int idPunto, int[] fichasOJ) {
//            int[] vecinos = this.puntos[idPunto].getNeighbours();
//            for (int i = 0; i < vecinos.length; i++) {
//                if (listaTienePunto(vecinos[i], fichasOJ)) {
//                    return true;
//                }
//            }
//            return false;
//        }

        public boolean checkLineaDeTres(int idPunto, int[] listaFichas) {
            return (checkLineaIndividual(idPunto, this.matrixLineasPuntos[idPunto][0], listaFichas) || checkLineaIndividual(idPunto, this.matrixLineasPuntos[idPunto][1], listaFichas));
        }

        public boolean checkLineaIndividual(int idPunto, int idLinea, int[] listaFichas) {
            for (int j = 0; j < 3; j++) {
                if (idPunto != this.matrixLineas[idLinea][j] && !listaTienePunto(this.matrixLineas[idLinea][j], listaFichas)) {
                    return false;
                }
            }
            return true;
        }

        public boolean listaTienePunto(int idPunto, int[] listaFichas) {
            for (int i = 0; i < listaFichas.length; i++) {
                if (listaFichas[i] == idPunto) {
                    return true;
                }
            }
            return false;
        }

        public boolean jugadorPuedeMoverse(int[] fichasJT, int[] fichasOJ) {
            if (fichasJT.length == 3) {
                return true;
            } else if (fichasJT.length < 3) {
                return false;
            } else {
                for (int i = 0; i < fichasJT.length; i++) {
                    if (puntoPuedeMoverse(fichasJT[i], fichasJT, fichasOJ)) {
                        return true;
                    }
                }
                return false;
            }
        }

        public boolean puntoPuedeMoverse(int idPunto, int[] fichasJ1, int[] fichasJ2) {
            int[] vecinos = this.puntos[idPunto].getNeighbours();
            for (int i = 0; i < vecinos.length; i++) {
                if (puntoEstaLibre(vecinos[i], fichasJ1, fichasJ2)) {
                    return true;
                }
            }
            return false;
        }

        public int getRandomNumber(int min, int max) {
            int range = (max - min) + 1;
            return (int) (Math.random() * range) + min;
        }

        public int buscarMejorCaptura(int[] fichasJTurno, int[] fichasACapturar) {
            int puntoJ2;
            for (int i = 0; i < fichasACapturar.length; i++) {
                puntoJ2 = fichasACapturar[i];
                if (checkLineaDeTres(puntoJ2, fichasJTurno)) {
                    return i;
                }
            }
            return getRandomNumber(0, (fichasACapturar.length - 1));
        }

        public int[] nuevoEstadoFichasMovimiento(int idFicha, int idPunto, int[] fichas) {
            int[] newFichas = fichas.clone();
            newFichas[idFicha] = idPunto;
            return newFichas;
        }

        public int[] nuevoEstadoFichasCaptura(int idFicha, int[] fichas) {
            int[] newFichas = new int[fichas.length - 1];
            int k = 0;
            for (int i = 0; i < fichas.length; i++) {
                if (i != idFicha) {
                    newFichas[k] = fichas[i];
                    k++;
                }
            }
            return newFichas;
        }

        public boolean isNewState(int[] fichasJT, int[] fichasOJ, boolean jugador1) {
            int[] fichasJ1, fichasJ2;
            ArrayList<String> nodosVisitados;
            if (jugador1) {
                fichasJ1 = fichasJT;
                fichasJ2 = fichasOJ;
                nodosVisitados = this.nodosVisitadosJ1;
            } else {
                fichasJ1 = fichasOJ;
                fichasJ2 = fichasJT;
                nodosVisitados = this.nodosVisitadosJ2;
            }
            String newState;
            int auxNum = 0;
            for (int i = 0; i < fichasJ1.length; i++) {
                auxNum += Math.pow(2, fichasJ1[i]);
            }
            newState = "J1:" + auxNum;
            auxNum = 0;
            for (int i = 0; i < fichasJ2.length; i++) {
                auxNum += Math.pow(2, fichasJ2[i]);
            }
            newState += "_J2:" + auxNum;
            for (String nodosVisitado : nodosVisitados) {
                if (nodosVisitado.equals(newState)) {
                    return false;
                }
            }
            nodosVisitados.add(newState);
            return true;
        }

        public Accion calcularMejorMovimiento() {
            ArrayList<Accion> accionesPosibles = new ArrayList<>();
            Accion accionAux;
            int[] posiblesPuntos, newFichasJ1, newFichasJ2;
            int beneficioJugada, punto, idCapturada;
            int maxBeneficio = -9;
            boolean quedanTres = this.tablero.getFichasJugador1().length == 3;
            this.nodosVisitadosJ1 = new ArrayList<>();
            this.nodosVisitadosJ2 = new ArrayList<>();

            for (int f = 0; f < this.tablero.getFichasJugador1().length; f++) {
                if (quedanTres) {
                    posiblesPuntos = this.arrayPuntos;
                } else {
                    posiblesPuntos = this.puntos[this.tablero.getFichasJugador1()[f]].getNeighbours();
                }
                for (int j = 0; j < posiblesPuntos.length; j++) {
                    punto = posiblesPuntos[j];
                    if (puntoEstaLibre(punto, this.tablero.getFichasJugador1(), this.tablero.getFichasJugador2())) {
                        newFichasJ1 = nuevoEstadoFichasMovimiento(f, punto, this.tablero.getFichasJugador1());
                        if (checkLineaDeTres(punto, newFichasJ1)) {
                            beneficioJugada = 2;
                            idCapturada = buscarMejorCaptura(newFichasJ1, this.tablero.getFichasJugador2());
                            newFichasJ2 = nuevoEstadoFichasCaptura(idCapturada, this.tablero.getFichasJugador2());
                            accionAux = new Accion('P', f, punto, idCapturada);
                        } else {
                            newFichasJ2 = this.tablero.getFichasJugador2();
                            accionAux = new Accion('M', f, punto);
                            if (checkLineaDeTres(punto, newFichasJ2)) {
                                beneficioJugada = 1;
                            } else {
                                beneficioJugada = 0;
                            }
                        }
                        beneficioJugada -= beneficioProximaJugada(newFichasJ2, newFichasJ1, false, 6);
                        if (beneficioJugada > maxBeneficio) {
                            maxBeneficio = beneficioJugada;
                            accionesPosibles.clear();
                            accionesPosibles.add(accionAux);
                        } else if (beneficioJugada == maxBeneficio) {
                            accionesPosibles.add(accionAux);
                        }
                    }
                }
            }
            if (accionesPosibles.size() > 1) {
                return accionesPosibles.get(getRandomNumber(0, accionesPosibles.size() - 1));
            }
            return accionesPosibles.get(0);
        }

        public int beneficioProximaJugada(int[] fichasJT, int[] fichasOJ, boolean jugador1, int profundidad) {
            if (profundidad == 0 || !isNewState(fichasJT, fichasOJ, jugador1)) {
                return 0;
            }
            int maxBeneficio = -9;
            if (jugadorPuedeMoverse(fichasJT, fichasOJ)) {
                if (!jugadorPuedeMoverse(fichasOJ, fichasJT)) {
                    return 9;
                }

                int[] posiblesPuntos, newFichasJT, newFichasOJ;
                int beneficioSJugada, punto, idCapturada;
                boolean quedanTres = fichasJT.length == 3;

                for (int i = 0; i < fichasJT.length; i++) {
                    if (quedanTres) {
                        posiblesPuntos = this.arrayPuntos;
                    } else {
                        posiblesPuntos = this.puntos[fichasJT[i]].getNeighbours();
                    }
                    for (int j = 0; j < posiblesPuntos.length; j++) {
                        punto = posiblesPuntos[j];
                        if (puntoEstaLibre(punto, fichasJT, fichasOJ)) {
                            newFichasJT = nuevoEstadoFichasMovimiento(i, punto, fichasJT);
                            if (checkLineaDeTres(punto, newFichasJT)) {
                                idCapturada = buscarMejorCaptura(newFichasJT, fichasOJ);
                                newFichasOJ = nuevoEstadoFichasCaptura(idCapturada, fichasOJ);
                                beneficioSJugada = 2;
                            } else {
                                newFichasOJ = fichasOJ;
                                if (checkLineaDeTres(punto, newFichasOJ)) {
                                    beneficioSJugada = 1;
                                } else {
                                    beneficioSJugada = 0;
                                }
                            }
                            beneficioSJugada -= beneficioProximaJugada(newFichasOJ, newFichasJT, !jugador1, profundidad - 1);
                            if (beneficioSJugada > maxBeneficio) {
                                maxBeneficio = beneficioSJugada;
                            }
                        }
                    }
                }
            } else if (!jugadorPuedeMoverse(fichasOJ, fichasJT)) {
                return 0;
            }
            return maxBeneficio;
        }
    }
}
