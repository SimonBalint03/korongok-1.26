package model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.scene.paint.Paint;

import java.util.Objects;

@XmlRootElement
public class Disk {

    @XmlElement
    public String color;

    private int size;

    public Disk(String color, int size) {
        this.color = color;
        this.size = size;
    }

    public Disk() {
    }

    public String getColor(){
        return color;
    }

    @XmlElement
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disk disk = (Disk) o;
        return size == disk.size && Objects.equals(color, disk.color);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(color);
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "size=" + size +
                ", color=" + color +
                '}';
    }
}
