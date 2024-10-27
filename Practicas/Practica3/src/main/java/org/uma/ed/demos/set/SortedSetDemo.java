package org.uma.ed.demos.set;

import org.uma.ed.datastructures.set.Set;
import org.uma.ed.datastructures.set.SortedLinkedSet;
import org.uma.ed.datastructures.set.SortedSet;

/**
 * Simple SortedSet demo.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedSetDemo {
    public static void main(String[] args) {
        Set<Integer> sortedSet1 = SortedLinkedSet.of(5, 3, 2, 1, 2, 0, 4, 3, 5, 1);

        SortedSet<Integer> sortedSet2 = SortedLinkedSet.of(5, 3, 2, 1, 2, 0, 4, 3, 5, 1);

        SortedSet<Integer> sortedSet3 = SortedLinkedSet.of(8, 5, 3, 2, 1, 2, 0, 4, 3, 5, 1);

        System.out.println(sortedSet1);
        System.out.println(sortedSet2);
        System.out.println(sortedSet3);

        System.out.println(sortedSet1.equals(sortedSet2));
        System.out.println(sortedSet1.equals(sortedSet3));
        System.out.println(sortedSet2.equals(sortedSet3));

        sortedSet3.delete(8);
        System.out.println(sortedSet3);
        System.out.println(sortedSet2.equals(sortedSet3));
        System.out.println(sortedSet3.size());
    }
}