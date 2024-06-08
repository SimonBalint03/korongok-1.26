import model.Beam;
import model.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BeamTest {

    private Beam beam1;
    private Beam beam2;

    @BeforeEach
    public void setUp() {
        beam1 = new Beam(1, 3, "red", "blue");
        beam2 = new Beam(2, 0);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, beam1.getId());
        assertEquals(3, beam1.getDisks().size());
        assertEquals("red", beam1.getDisks().get(0).getColor());
        assertEquals(20, beam1.getDisks().get(0).getSize());
        assertEquals("blue", beam1.getDisks().get(1).getColor());
        assertEquals(40, beam1.getDisks().get(1).getSize());
        assertEquals("red", beam1.getDisks().get(2).getColor());
        assertEquals(60, beam1.getDisks().get(2).getSize());

        assertEquals(2, beam2.getId());
        assertEquals(0, beam2.getDisks().size());
    }

    @Test
    public void testDefaultConstructor() {
        Beam beam = new Beam();
        assertEquals(0, beam.getId());
        assertEquals(0, beam.getDisks().size());
    }

    @Test
    public void testSetters() {
        ArrayList<Disk> disks = new ArrayList<>();
        disks.add(new Disk("green", 50));
        beam2.setDisks(disks);
        beam2.setId(3);

        assertEquals(3, beam2.getId());
        assertEquals(1, beam2.getDisks().size());
        assertEquals("green", beam2.getDisks().getFirst().getColor());
        assertEquals(50, beam2.getDisks().getFirst().getSize());
    }

    @Test
    public void testEqualsAndHashCode() {
        Beam beam3 = new Beam(1, 3, "red", "blue");
        Beam beam4 = new Beam(2, 0);
        Beam beam5 = new Beam(1, 2, "red", "blue");

        assertEquals(beam1, beam3);
        assertNotEquals(beam1, beam2);
        assertNotEquals(beam1, beam5);

        assertEquals(beam1.hashCode(), beam3.hashCode());
        assertNotEquals(beam1.hashCode(), beam2.hashCode());
        assertNotEquals(beam1.hashCode(), beam5.hashCode());
    }
}
