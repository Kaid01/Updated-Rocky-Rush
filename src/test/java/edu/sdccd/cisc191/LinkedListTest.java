package edu.sdccd.cisc191;
import edu.sdccd.cisc191.Game.Scenes.ProgressScenes;
import edu.sdccd.cisc191.Game.SinglyLinkedList;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    static int number;

    @BeforeAll
    public static void setUp() {
        number = 10;
    }

    @Test
    public void testLinkedList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(11);
        list.add(number);

        assertEquals(list.head.next.data, number);
    } //end testLinkedList()
}
