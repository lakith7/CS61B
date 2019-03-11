package hw3.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        double q = oomages.size();

        Map<Integer, Integer> holder = new HashMap<>(oomages.size());
        for (int n = 0; n < oomages.size(); n += 1) {
            int bucketNum = ((oomages.get(n).hashCode() & 0x7FFFFFFF) % M);
            if (holder.containsKey(bucketNum)) {
                holder.replace(bucketNum, holder.get(bucketNum) + 1);
            } else {
                holder.put(bucketNum, 1);
            }
        }

        ArrayList<Integer> tester = new ArrayList<>();
        for (Integer each: holder.keySet()) {
            tester.add(holder.get(each));
        }

        double upperLimit = q / 50;
        double lowerLimit = q / 2.5;
        for (int i = 0; i < tester.size(); i += 1) {
            if (tester.get(i) == 0) {
                return true;
            }
            if (tester.get(i) < upperLimit) {
                return false;
            } else if (tester.get(i) > lowerLimit) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        System.out.println(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }
}
