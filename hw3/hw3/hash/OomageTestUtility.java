package hw3.hash;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */

        double q = oomages.size();

        Map<Integer, Integer> holder = new HashMap<>();
        for (int n = 0; n < oomages.size(); n += 1) {
            int bucketNum = (oomages.get(0).hashCode() & 0x7FFFFFFF) % M;
            if (holder.containsKey(bucketNum)) {
                holder.replace(bucketNum, (int) holder.get(bucketNum) + 1);
            } else {
                holder.put(bucketNum, 1);
            }
        }

        Object[] tester = holder.values().toArray();
        double upperLimit = q/50;
        double lowerLimit = q/2.5;
        for (int i = 0; i < tester.length; i += 1) {
            if ((int) tester[i] > upperLimit) {
                return false;
            } else if ((int) tester[i] < lowerLimit) {
                return false;
            }
        }

        return true;
    }
}
