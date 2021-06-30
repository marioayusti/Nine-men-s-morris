/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoSI;

/**
 *
 * @author Mario A. Yusti
 */
public class PuntoGUI {

    private final int id;
    private final int x;
    private final int y;

    public PuntoGUI(int _id, int _x, int _y) {
        this.id = _id;
        this.x = _x;
        this.y = _y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
