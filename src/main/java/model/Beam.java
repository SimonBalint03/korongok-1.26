package model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a column of the playable grid.
 * Contains every disk on the column.
 */
@XmlRootElement
public class Beam {

    private ArrayList<Disk> disks = new ArrayList<>();

    private int id;

    /**
     * Returns the id of the beams representing the column on the grid.
     *
     * @return The id representing the column on the grid.
     */
    @XmlElement
    public int getId() {
        return id;
    }

    /**
     * Returns an ArrayList of Disk objects that are on this beam.
     *
     * @return An ArrayList of Disk objects
     */
    @XmlElement
    public ArrayList<Disk> getDisks() {
        return disks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDisks(ArrayList<Disk> disks) {
        this.disks = disks;
    }

    public Beam(int id, int num_of_disks, String firstColor, String secondColor){
        for (int i = 1; i < num_of_disks+1; i++) {
            if (i % 2 != 0) {
                disks.add(new Disk(firstColor,20*i));
            }else{
                disks.add(new Disk(secondColor,20*i));
            }
        }
        this.id = id;
    }

    public Beam(int id, int num_of_disks){
        this.disks = new ArrayList<>();
        this.id = id;
    }

    public Beam() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beam beam = (Beam) o;
        return id == beam.id && Objects.equals(disks, beam.disks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(disks);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Beam{" +
                "disks=" + disks +
                '}';
    }
}
