package com.github.stepinto.algo.reedsolomon;

public final class GaloisField {

    static final int W = 8;
    static final int N = 1 << W;
    static final int[] gflog = new int[N];
    static final int[] gfilog = new int[N];
    static final int[] gfmult = new int[N * N];
    static final int[] gfdiv = new int[N * N];

    static {
        int primPoly = 0435;
        int b = 1;
        for (int i = 0; i < N; i++) {
            assert 0 <= b && b < N;
            gflog[b] = i;
            gfilog[i] = b;
            b = b << 1;
            if ((b & N) != 0) {
                b = b ^ primPoly;
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gfmult[(i << W) | j] = doMult(i, j);
                if (j > 0) {
                    gfdiv[(i << W) | j] = doDiv(i, j);
                }
            }
        }
    }

    private GaloisField() {}

    public static int add(int a, int b) {
        assert 0 <= a && a < N;
        assert 0 <= b && b < N;
        return a ^ b;
    }

    public static int sub(int a, int b) {
        return add(a, b);
    }

    public static int mult(int a, int b) {
        assert 0 <= a && a < N;
        assert 0 <= b && b < N;
        return gfmult[(a << W) | b];
    }

    public static int div(int a, int b) {
        assert 0 <= a && a < N;
        assert 0 < b && b < N;
        return gfdiv[(a << W) | b];
    }

    private static int doMult(int a, int b) {
        assert 0 <= a && a < N;
        assert 0 <= b && b < N;

        if (a == 0 || b == 0) {
            return 0;
        }

        int sumLog = gflog[a] + gflog[b];
        if (sumLog >= N - 1) {
            sumLog -= N - 1;
        }
        return gfilog[sumLog];
    }

    public static int pow(int a, int b) {
        /*
         * int s = 1; for (int i = 0; i < b; i++) { s = mult(s, a); } return s;
         */
        assert 0 <= a && a < N;
        return gfilog[gflog[a] * b % (N - 1)];
    }

    public static int doDiv(int a, int b) {
        assert 0 <= a && a < N;
        assert 0 < b && b < N;

        if (a == 0) {
            return 0;
        }

        int diffLog = gflog[a] - gflog[b];
        if (diffLog < 0) {
            diffLog += N - 1;
        }
        return gfilog[diffLog];
    }
}
