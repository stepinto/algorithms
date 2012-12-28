package com.github.stepinto.algo.reedsolomon;

import static org.junit.Assert.*;

import org.junit.Test;

public class GaloisFieldUnitTest {

    @Test
    public void testAdd() {
        final int N = 256;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int sum = GaloisField.add(i, j);
                assertTrue(0 <= sum && sum < N);
            }
        }
    }

    @Test
    public void testMult() {
        // 0 * k = 0
        for (int i = 0; i < GaloisField.N; i++) {
            assertEquals(0, GaloisField.mult(0, i));
            assertEquals(0, GaloisField.mult(i, 0));
        }

        // 1 * k == k
        for (int i = 0; i < GaloisField.N; i++) {
            assertEquals(i, GaloisField.mult(1, i));
            assertEquals(i, GaloisField.mult(i, 1));
        }

        // Communicative law
        for (int i = 0; i < GaloisField.N; i++) {
            for (int j = 0; j < GaloisField.N; j++) {
                assertEquals(GaloisField.mult(i, j), GaloisField.mult(j, i));
            }
        }

        // Distributive law
        for (int i = 0; i < GaloisField.N; i++) {
            for (int j = 0; i < GaloisField.N; i++) {
                for (int k = 0; i < GaloisField.N; i++) {
                    assertEquals(GaloisField.mult(i, GaloisField.add(j, k)),
                            GaloisField.add(GaloisField.mult(i, j), GaloisField.mult(i, k)));
                }
            }
        }

        // Existance and uniqueniess of insverse
        for (int i = 1; i < GaloisField.N; i++) {
            int cnt = 0;
            for (int j = 0; j < GaloisField.N; j++) {
                if (GaloisField.mult(i, j) == 1) {
                    cnt++;
                }
            }
            assertEquals(1, cnt);
        }
    }

    @Test
    public void testDiv() {
        for (int i = 1; i < GaloisField.N; i++) {
            for (int j = 1; j < GaloisField.N; j++) {
                int c = GaloisField.mult(i, j);
                // System.out.println("i=" + i + " j=" + j + " c=" + c);
                assertEquals(j, GaloisField.div(c, i));
                assertEquals(i, GaloisField.div(c, j));
            }
        }
    }

}
