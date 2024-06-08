import model.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiskTest {

    private Disk disk1;
    private Disk disk2;

    @BeforeEach
    public void setUp() {
        disk1 = new Disk("red", 5);
        disk2 = new Disk("blue", 3);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("red", disk1.getColor());
        assertEquals(5, disk1.getSize());
        assertEquals("blue", disk2.getColor());
        assertEquals(3, disk2.getSize());
    }

    @Test
    public void testDefaultConstructor() {
        Disk disk = new Disk();
        assertNull(disk.getColor());
        assertEquals(0, disk.getSize());
    }

    @Test
    public void testSetSize() {
        disk1.setSize(10);
        assertEquals(10, disk1.getSize());
    }

    @Test
    public void testEqualsAndHashCode() {
        Disk disk3 = new Disk("red", 5);
        Disk disk4 = new Disk("blue", 3);
        Disk disk5 = new Disk("red", 6);

        assertTrue(disk1.equals(disk3));
        assertFalse(disk1.equals(disk2));
        assertFalse(disk1.equals(disk5));

        assertEquals(disk1.hashCode(), disk3.hashCode());
        assertNotEquals(disk1.hashCode(), disk2.hashCode());
        assertNotEquals(disk1.hashCode(), disk5.hashCode());
    }
}
