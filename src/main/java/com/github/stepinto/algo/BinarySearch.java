package com.github.stepinto.algo;

public class BinarySearch {

    private BinarySearch() {}

    /** Finds smallest i such that a[i] >= k in a sorted array. */
    public static int lowerBound(int[] a, int k) {
        if (a.length == 0) {
            return 0;
        }
        if (a[a.length - 1] < k) {
            return a.length;
        }

        int l = 0;
        int r = a.length - 1;

        while (l < r) {
            int mid = (l + r) / 2;
            if (a[mid] >= k) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    /** Finds smallest i such that a[i] > k in a sorted array. */
    public static int upperBound(int[] a, int k) {
        if (a.length == 0) {
            return 0;
        }
        if (a[a.length - 1] <= k) {
            return a.length;
        }

        int l = 0;
        int r = a.length - 1;

        while (l < r) {
            int mid = (l + r) / 2;
            if (a[mid] > k) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}
