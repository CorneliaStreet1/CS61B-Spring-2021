package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>();//N的次数
        AList<Double> times = new AList<>();//时间
        AList<Integer> opCounts = new AList<>();//运算次数

        for (int i = 1000; i <= 128000; i = 2 * i) {
            Ns.addLast(i);
            opCounts.addLast(1000);
            SLList<Integer> A = new SLList<>();

            for (int p = 0; p < i; p++) {
                A.addLast(p);
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < 1000; j++) {
                A.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }
        printTimingTable(Ns,times,opCounts);
    }
}
