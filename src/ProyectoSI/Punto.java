package ProyectoSI;

/**
 *
 * @author Mario A. Yusti
 */
public class Punto {

    private final int id;
    private int[] neighbours;

    public Punto(int _id) {
        this.id = _id;
    }

    public int getId() {
        return id;
    }

    public int[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(int[] neighbours) {
        this.neighbours = neighbours;
    }
}
