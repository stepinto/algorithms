package com.github.stepinto.algo.reedsolomon;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Stopwatch;

public class ReedSolomonPerfTest {

    private static final int CHUNK_SIZE = 64 * 1024 * 1024;
    private Random r = new Random();

    @Test
    public void testComputeParity() {
        doTestComputeParity(1, 2);
        doTestComputeParity(5, 3);
        doTestComputeParity(10, 3);
    }

    public void testRecover() {
    }

    private void doTestComputeParity(int n, int m) {
        final int ROUND_COUNT = 1;

        ReedSolomonEncoder encoder = new ReedSolomonEncoder(n, m);
        byte[] data = new byte[n];
        byte[] parity = new byte[m];
        for (int i = 0; i < n; i++) {
            data[i] = ByteUtils.uint2byte(r.nextInt(256));
        }

        Stopwatch watch = new Stopwatch();
        watch.start();
        for (int t = 0; t < ROUND_COUNT; t++) {
            for (int i = 0; i < CHUNK_SIZE; i++) {
                encoder.encode(data, parity);
            }
        }
        watch.stop();

        long runningTime = watch.elapsedTime(TimeUnit.MILLISECONDS);
        System.out.printf("Build %d parity chunk(s) from %d chunk(s)\n", m, n);
        System.out.printf("Time: %.2f s\n", runningTime / 1000.0 / ROUND_COUNT);
        System.out.printf("Throughput: %.2f MBps\n", m * CHUNK_SIZE * ROUND_COUNT * 1000.0
                / (runningTime * 1024.0 * 1024.0));
    }
}
