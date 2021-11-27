package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
   public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int sizeL = L.size();
                int sizeB = B.size();
                assertEquals(sizeB,sizeL);
            }
            else if (L.size() != 0) {
                if (operationNumber == 2) {
                    //getLast
                    int l = L.getLast();
                    int b = B.getLast();
                    assertEquals(l,b);
                }
                if (operationNumber == 3) {
                    //removeLast
                    int l = L.removeLast();
                    int b = B.removeLast();
                    assertEquals(l,b);
                }
            }
        }
   }
}
