package com.github.stepinto.algo.reedsolomon;

import static com.github.stepinto.algo.reedsolomon.GaloisField.add;
import static com.github.stepinto.algo.reedsolomon.GaloisField.div;
import static com.github.stepinto.algo.reedsolomon.GaloisField.mult;
import static com.github.stepinto.algo.reedsolomon.GaloisField.pow;
import static com.github.stepinto.algo.reedsolomon.GaloisField.sub;

public final class MatrixUtils {

    private MatrixUtils() {}

    public static String toString(int[][] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (j > 0) {
                    sb.append(" ");
                }
                sb.append(a[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Finds the dispersal matrix of n data words and m parity words
     * 
     * @param n
     * @param m
     * @return a n+m by n matrix
     */
    public static int[][] findDispersalMatrix(int n, int m) {
        int[][] a = new int[n + m][n];

        // Start from Vandermonde matrix
        for (int i = 0; i < n + m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = pow(i + 1, j);
            }
        }

        assert isInvertable(a, n);

        // Reduce the top n by n matrix to identity by Guassian elimination
        for (int k = 0; k < n; k++) {
            assert a[k][k] > 0;

            // Scale column k so that a[k][k] = 1
            for (int i = k + 1; i < n + m; i++) {
                a[i][k] = div(a[i][k], a[k][k]);
            }
            a[k][k] = 1;

            // For each column j, column j -= a[k][j] * column k
            for (int j = k + 1; j < n; j++) {
                int val = a[k][j];
                for (int i = k; i < n + m; i++) {
                    a[i][j] = sub(a[i][j], mult(val, a[i][k]));
                }
                assert a[k][j] == 0;
            }
        }
        for (int j = n - 1; j >= 0; j--) {
            for (int k = j + 1; k < n; k++) {
                int val = a[k][j];
                for (int i = 0; i < n + m; i++) {
                    a[i][j] = sub(a[i][j], mult(val, a[i][k]));
                }
            }
        }
        // System.out.println("finally dispersal matrix:\n" + toString(a));
        return a;
    }

    /**
     * Finds the inversion of a matrix by Gaussian elimination
     * 
     * @param a
     *            the matrix
     * @param n
     *            dimension
     * @return the inversion of the matrix
     */
    public static int[][] inverse(int[][] a, int n) {
        int[][] org = new int[n][n];
        int[][] inv = new int[n][n];

        // System.out.println("a=\n" + MatrixUtils.toString(a));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assert 0 <= a[i][j] && a[i][j] < 256;
                org[i][j] = a[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            inv[i][i] = 1;
        }

        for (int k = 0; k < n; k++) {
            // System.out.println("k=" + k + ":\n" + MatrixUtils.toString(org));
            // Find some row x such that org[x][k] != 0
            int x = -1;
            for (int i = k; i < n; i++) {
                if (org[i][k] != 0) {
                    x = i;
                    break;
                }
            }
            if (x == -1) {
                return null; // Singular!
            }

            // Swap row x and row k
            for (int j = k; j < n; j++) {
                int tmp = org[x][j];
                org[x][j] = org[k][j];
                org[k][j] = tmp;
            }
            for (int j = 0; j < n; j++) {
                int tmp = inv[x][j];
                inv[x][j] = inv[k][j];
                inv[k][j] = tmp;
            }
            assert org[k][k] > 0;
            // System.out.println("After swap:\n" + MatrixUtils.toString(org));

            // Scale row k so that org[k][k] == 1
            for (int j = k + 1; j < n; j++) {
                org[k][j] = div(org[k][j], org[k][k]);
            }
            for (int j = 0; j < n; j++) {
                inv[k][j] = div(inv[k][j], org[k][k]);
            }
            org[k][k] = 1;

            // For each row i, row i -= org[i][k] * row k
            for (int i = k + 1; i < n; i++) {
                int val = org[i][k];
                for (int j = k; j < n; j++) {
                    org[i][j] = sub(org[i][j], mult(val, org[k][j]));
                }
                for (int j = 0; j < n; j++) {
                    inv[i][j] = sub(inv[i][j], mult(val, inv[k][j]));
                }
                assert org[i][k] == 0;
            }
        }
        // System.out.println("now we have:\n" + MatrixUtils.toString(a));

        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                for (int k = 0; k < n; k++) {
                    inv[i][k] = sub(inv[i][k], mult(org[i][j], inv[j][k]));
                }
                for (int k = 0; k < n; k++) {
                    org[i][k] = sub(org[i][k], mult(org[i][j], org[j][k]));
                }
            }
        }
        // System.out.println("finally:\n" + MatrixUtils.toString(org));
        // System.out.println("inv:\n" + MatrixUtils.toString(inv));
        assert testInv(a, inv, n);
        return inv;
    }

    public static boolean isInvertable(int[][] a, int n) {
        return inverse(a, n) != null;
    }

    private static boolean testInv(int a[][], int b[][], int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum = add(sum, mult(a[i][k], b[k][j]));
                }
                if (i == j && sum != 1) {
                    return false;
                }
                if (i != j && sum != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
