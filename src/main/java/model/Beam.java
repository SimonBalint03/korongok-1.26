package model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.scene.paint.Color;

import java.util.ArrayList;

@XmlRootElement
public class Beam {

    private ArrayList<Disk> disks = new ArrayList<>();

    private int id;

    @XmlElement
    public int getId() {
        return id;
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

    @XmlElement
    public ArrayList<Disk> getDisks() {
        return disks;
    }

    @Override
    public String toString() {
        return "Beam{" +
                "disks=" + disks +
                '}';
    }
}
