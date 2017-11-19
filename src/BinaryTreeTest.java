import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    @org.junit.Test
    public void remove() throws Exception {
        BinaryTree tree = new BinaryTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(7);
        tree.remove(10);
        tree.remove(5);
        tree.remove(7);
        tree.remove(1);
        assertEquals(0, tree.getSize());
        tree.add(3);
        tree.add(1);
        tree.add(4);
        tree.remove(3);
        assertEquals(2, tree.getSize());
        tree.add(8);
        tree.add(15);
        tree.add(20);
        tree.remove(15);
        tree.remove(20);
        tree.remove(8);
        assertEquals(2, tree.getSize());
    }

    @org.junit.Test
    public void headSet() throws Exception {
        BinaryTree tree = new BinaryTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(7);
        tree.add(3);
        tree.add(1);
        tree.add(4);
        tree.add(8);
        tree.add(15);
        tree.add(30);
        tree.add(20);
        tree.remove(30);
        SortedSet abc = tree.headSet(5);
        TreeSet<Integer> setabc = new TreeSet<>();
        setabc.add(1);
        setabc.add(3);
        setabc.add(4);
        assertEquals((SortedSet) setabc, abc);
        setabc.add(5);
        setabc.add(7);
        setabc.add(8);
        abc = tree.headSet(10);
        assertEquals((SortedSet) setabc, abc);
        setabc.add(10);
        setabc.add(15);
        abc = tree.headSet(20);
        assertEquals((SortedSet) setabc, abc);
    }


}
