package edu.sdccd.cisc191;

import edu.sdccd.cisc191.Game.SinglyLinkedList;
import edu.sdccd.cisc191.Leaderboard.sorting.BinarySearchTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchingSortingTest {

    BinarySearchTree bst = new BinarySearchTree();

    @Test
    public void testBinarySearchTree() {
        bst.root = bst.add(bst.root, "Rocky", "00:40");
        bst.add(bst.root, "Ekin", "00:34");
        bst.add(bst.root, "Kaden", "00:46");

        assertEquals(bst.root.getLeft().getPlayerName(), "Ekin");
        assertEquals(bst.root.getRight().getPlayerTime(), "00:46");
    } //end testLinkedList()
}
