package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> A = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        for (int i = 0 ; i < 3 ; i ++) {
            A.addLast(i);
            B.addLast(i);
        }
        assertEquals(A.removeLast() , B.removeLast());
        assertEquals(A.removeLast(),B.removeLast());
        assertEquals(A.removeLast(),B.removeLast());
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> LB = new BuggyAList<>();
        int N = 50000;//should not be bigger than 500000 ,due to the hard-coded length of L (L.length == 1000)
                      //or an index 1000 out of bounds 1000 exception would occur
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                LB.addLast(randVal);
                //remove all print statements as requested
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int sizeB = LB.size();
                //remove all print statements as requested
                //System.out.println("size: Correct = " + size + " Broken:" + sizeB);
            }
            else if (operationNumber == 2) {
                if (L.size() != 0) {
                    int ip = L.removeLast();
                    int ib = LB.removeLast();
                    //remove all print statements as requested
                    //System.out.println("removeLast(): Correct: " + ip + " Broken: "+ib);
                    assertEquals(ip,ib);
                }
            }
            else if (operationNumber == 3) {
                if (L.size() != 0) {
                    int pi = L.getLast();
                    int bi = LB.getLast();
                    //remove all print statements as requested
                    //System.out.println("getLast(): Correct: " + pi+ " Broken: "+ bi);
                    assertEquals(pi,bi);
                }
            }
        }
    }
}
