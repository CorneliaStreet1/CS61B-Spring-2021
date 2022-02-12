package bstmap;
import jh61b.junit.In;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;
public class SelfTests {
    @Test
    public void TestBasicFunction() {
        BSTMap<Integer, Integer> bstMap1 = new BSTMap<>();
        bstMap1.put(10,10);
        bstMap1.put(2,2);
        bstMap1.put(16,16);
        bstMap1.put(15,15);
        bstMap1.put(17,17);
        bstMap1.put(1,1);
        bstMap1.put(4,4);
        bstMap1.put(0,0);
        Set<Integer> keys = bstMap1.keySet();
        for (int i: keys) {
            assertTrue(bstMap1.containsKey(i));
            assertEquals((long)i, (long)bstMap1.get(i));
        }
        assertFalse(bstMap1.containsKey(114));
        assertEquals(8, bstMap1.size());
        bstMap1.remove(16, 15);
        assertTrue(bstMap1.containsKey(16));
        bstMap1.remove(16);
        assertFalse(bstMap1.containsKey(16));
    }
    @Test
    public void TestRemove() {
        BSTMap<Integer, Integer> b1 = new BSTMap<>();
        BSTMap<Integer, Integer> b2 = new BSTMap<>();
        BSTMap<Integer, Integer> b3 = new BSTMap<>();
        //测试移除的节点没有子节点
        b1.put(1, 1);
        b1.remove(1);
        b1.put(1, 1);
        b1.put(2,2);
        b1.put(0, 0);
        b1.remove(0);
        b1.remove(2);
        b1.clear();
        //测试移除的节点有一个子节点
        b2.put(3,3);
        b2.put(1,1);
        b2.put(0,0);
        b2.put(2,2);
        b2.remove(3);
        b2.clear();
        //测试移除的节点有两个子节点
        b3.put(10,10);
        b3.put(2,2);
        b3.put(16,16);
        b3.put(1,1);
        b3.put(4,4);
        b3.put(15,15);
        b3.put(17,17);
        b3.put(0,0);
        b3.put(-1,-1);
        b3.remove(2);
        b3.remove(10);
    }
}
