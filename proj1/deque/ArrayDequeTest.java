package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void Testget() {
        ArrayDeque<Integer> AD = new ArrayDeque<>();
        for (int i = 0 ; i < 10000000; i ++) {
            AD.addLast(i);
        }
        for (int i = 0 ; i < 1000000; i ++) {
            AD.removeLast();
            int index = StdRandom.uniform(0, AD.size());
            AD.get(index);
        }
    }
    @Test
    public void  TestResize() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for (int i = 0 ; i < 32 ; i ++) {
            A.addLast(i);
        }
        A.printDeque();
        for (int i = 0 ; i < 32 ; i ++) {
            A.removeLast();
            A.printDeque();
            System.out.println();
        }
    }
    @Test
    public void TestForeachLoop() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        java.util.ArrayDeque<Integer> JL = new java.util.ArrayDeque<>();
        for (int c = 0 ; c < 10 ; c ++) {
            L.addLast(c);
            JL.addLast(c);
        }
        for (int c:L) {
            System.out.print(c);
        }
       /* Iterator<Integer> aseer = L.iterator();
        while (aseer.hasNext()) {
            System.out.println(aseer.next());
        }*/
        System.out.println();
        for (int c:JL) {
            System.out.print(c);
        }
        assertEquals(L.removeFirst(),JL.removeFirst());
    }
    @Test
    public void RandomizedTest() {
        ArrayDeque<Integer> LSD = new ArrayDeque<>();
        java.util.ArrayDeque<Integer> LS = new java.util.ArrayDeque<>();
        int N = 1000000000;
        for (int i = 0 ; i < N ; i ++) {
            int operationNumber = StdRandom.uniform(0,6);
            if (operationNumber == 0) {
                int RandomValue = StdRandom.uniform(0,100);
                LSD.addFirst(RandomValue);
                LS.addFirst(RandomValue);
                //System.out.println("addFirst: " + RandomValue);
            }
            if (operationNumber == 1) {
                int RandomValue = StdRandom.uniform(0,100);
                LSD.addLast(RandomValue);
                LS.addLast(RandomValue);
                //System.out.println("addLast: " + RandomValue);
            }
           if (operationNumber == 2) {
                if (LS.size() > 0) {
                    int RandomValue = StdRandom.uniform(0, LSD.size());
                    Object[] Array = LS.toArray();
                    int broken = LSD.get(RandomValue);
                    int correct = (int) Array[RandomValue];
                    //int Recursive = LSD.getRecursive(RandomValue);
                    //System.out.println("get(index): Correct: " + correct + " Broken: " + broken + "Recursive: " + Recursive);
                    assertEquals(correct, broken);
                    //assertEquals(broken, Recursive);
                    //assertEquals(correct, Recursive);
                }
            }
            if (operationNumber == 3) {
                if (LS.size() > 0) {
                    int correct = LS.removeFirst();
                    int broken = LSD.removeFirst();
                    assertEquals(correct, broken);
                }
            }
            if (operationNumber == 4) {
                if (LS.size() > 0) {
                    int correct = LS.removeLast();
                    int broken = LSD.removeLast();
                    assertEquals(correct, broken);
                }
            }
           if (operationNumber == 5) {
                int c = LS.size();
                int b = LSD.size();
                assertEquals(c,b);
            }
            if (operationNumber == 6) {
                assertEquals(LS.isEmpty(),LSD.isEmpty());
            }
           if (operationNumber == 7) {
                LinkedListDeque<Integer> ls1 = new LinkedListDeque<>();
                LinkedListDeque<Integer> ls2 = new LinkedListDeque<>();
                for (int i1 = 0 ; i1 < 10 ; i1 ++) {
                    ls1.addLast(i1);
                    ls2.addLast(i1);
                }
                //等价关系：传递性、对称性、自反
                boolean b1 = ls1.equals(ls2);//对称
                boolean b2 = ls2.equals(ls1);//对称
                boolean b3 = ls1.equals(null);
                boolean b4 = ls1.equals(ls1);//自反
                assertEquals(b1,b2);
                assertEquals(true,b4);
                assertEquals(false,b3);
            }
            if (operationNumber == 8) {
                System.out.println("----------------------------Test printDeque:");
                System.out.println("Printing Deque:");
                LSD.printDeque();
                System.out.println("Printing List:");
                System.out.println(LS);
                System.out.println("-----------------------------");
            }
            if (operationNumber == 9) {
                if (!LSD.isEmpty()) {
                    System.out.println("---------------Test for-each loop:");
                    for (int c : LSD) {
                        System.out.print(c);
                    }
                    /*Iterator<Integer> aseer = LSD.iterator();
                    while (aseer.hasNext()) {
                        System.out.println(aseer.next());
                    }*/
                    System.out.println();
                    for (int d : LS) {
                        System.out.print(d);
                    }
                    System.out.println("-------------------------");
                }
            }
        }
    }
    @Test
    public void Test_toString() {
        ArrayDeque<String> a1 = new ArrayDeque<>();
        a1.addLast("Hey");
        a1.addLast("I'm");
        a1.addLast("Here !");
        System.out.println(a1.toString());
        ArrayDeque<String> a2 = new ArrayDeque<>();
        System.out.println(a2.toString());
    }
    @Test
    public void Test_of() {
        ArrayDeque<String> a1 = ArrayDeque.of("Hey","I'm","Here!");
        ArrayDeque<Integer> a2 = ArrayDeque.of(1,1,4,5,1,4);
        ArrayDeque<String> a3 = ArrayDeque.of("哼", "哼", "哼", "啊啊啊啊啊啊啊啊啊啊");
        assertEquals("[Hey,I'm,Here!]",a1.toString());
        assertEquals("[1,1,4,5,1,4]",a2.toString());
        assertEquals("[哼,哼,哼,啊啊啊啊啊啊啊啊啊啊]",a3.toString());
    }
}
