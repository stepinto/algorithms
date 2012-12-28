package com.github.stepinto.algo.reedsolomon;

public class ReedSolomonEncoder {

    private int n; // num of data words
    private int m; // num fo parity words
    private int[][] a; // parity = a * data

    ReedSolomonEncoder(int n, int m) {
        this.n = n;
        this.m = m;
        a = new int[m][n];
        int[][] tmp = MatrixUtils.findDispersalMatrix(n, m);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = tmp[i + n][j];
            }
        }
    }
    
    public void encode(byte[] data, byte[] parity) {
        assert data.length == n;
        assert parity.length == m;

        for (int i = 0; i < m; i++) {
            int sum = 0;
            for (int k = 0; k < n; k++) {
                sum = GaloisField.add(sum, GaloisField.mult(a[i][k], ByteUtils.byte2uint(data[k])));
            }
            parity[i] = ByteUtils.uint2byte(sum);
        }
    }

    public byte[] encode(byte[] data) {
        byte[] parity = new byte [m];
        encode(data, parity);
        return parity;
    }

}
