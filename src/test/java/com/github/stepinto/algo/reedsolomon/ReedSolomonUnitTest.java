package com.github.stepinto.algo.reedsolomon;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class ReedSolomonUnitTest {

    private Random r = new Random();

    @Test
    public void testRaid5() {
        doTest(2, 1);
    }

    @Test
    public void testRaid6() {
        doTest(3, 2);
    }
    
    @Test
    public void testBig() {
        for (int i = 0; i < 100; i++) {
            int n = r.nextInt(50) + 1;
            int m = r.nextInt(50) + 1;
            doTest(n, m);
        }
    }

    private void doTest(int n, int m) {
        ReedSolomonEncoder encoder = new ReedSolomonEncoder(n, m);
        byte[] data = new byte[n];
        for (int i = 0; i < n; i++) {
            data[i] = ByteUtils.uint2byte(r.nextInt(256));
        }
        byte[] parity = encoder.encode(data);
        int p[] = new int[n + m];
        for (int i = 0; i < n + m; i++) {
            p[i] = i;
        }
        for (int i = 0; i < n + m; i++) {
            int k = r.nextInt(n + m - i) + i;
            int tmp = p[i];
            p[i] = p[k];
            p[k] = tmp;
        }

        byte[] remain = new byte[n];
        for (int i = 0; i < n; i++) {
            if (p[i] < n) {
                remain[i] = data[p[i]];
            } else {
                remain[i] = parity[p[i] - n];
            }
        }
        ReedSolomonDecoder decoder = new ReedSolomonDecoder(n, m, p);
        byte[] recovered = decoder.recover(remain);
        assertArrayEquals(data, recovered);
    }

}
