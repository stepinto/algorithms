package com.github.stepinto.algo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class BinarySearchTest {

    private static final int MAX_N = 1000;
    private static final Random RANDOM = new Random();

    @Test
    public void testLowerBound() {
        for (int i = 0; i < 10000; i++) {
            int[] a = randomArray();
            Arrays.sort(a);
            int k = randInt();
            int res1 = naiveLowerBound(a, k);
            int res2 = BinarySearch.lowerBound(a, k);
            assertEquals("a=" + Arrays.toString(a) + ", k=" + k, res1, res2);
        }
    }

    @Test
    public void testUpperBound() {
        for (int i = 0; i < 10000; i++) {
            int[] a = randomArray();
            Arrays.sort(a);
            int k = randInt();
            int res1 = naiveUpperBound(a, k);
            int res2 = BinarySearch.upperBound(a, k);
            assertEquals(res1, res2);
        }
    }

    private int[] randomArray() {
        int n = RANDOM.nextInt(MAX_N);
        int[] a = new int [n];
        for (int i = 0; i < n; i++) {
            a[i] = randInt();
        }
        return a;
    }

    private int randInt() {
        return RANDOM.nextInt(100);
    }

    public static int naiveLowerBound(int[] a, int k) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= k) {
                return i;
            }
        }
        return a.length;
    }

    public static int naiveUpperBound(int[] a, int k) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] > k) {
                return i;
            }
        }
        return a.length;
    }
}
