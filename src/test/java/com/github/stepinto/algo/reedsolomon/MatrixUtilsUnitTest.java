package com.github.stepinto.algo.reedsolomon;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class MatrixUtilsUnitTest {

    private Random r = new Random();
    
    @Test
    public void testFindDispersalMatrix() {
        for (int i = 0; i < 100; i++) {
            int n = r.nextInt(50) + 1;
            int m = r.nextInt(50) + 1;
            doTestFindDispersalMatrix(n, m);
        }
    }
    
    private void doTestFindDispersalMatrix(int n, int m) {
        int[][] a = MatrixUtils.findDispersalMatrix(n, m);
        int[] p = new int[n + m];

        for (int i = 0; i < n + m; i++) {
            p[i] = i;
        }
        for (int i = 0; i < n + m; i++) {
            int k = r.nextInt(n + m - i) + i;
            int tmp = p[i];
            p[i] = p[k];
            p[k] = tmp;
        }

        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = a[p[i]][j];
            }
        }
        assertTrue(MatrixUtils.isInvertable(b, n));
    }

}
