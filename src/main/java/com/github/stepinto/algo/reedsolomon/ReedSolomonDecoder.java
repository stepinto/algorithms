package com.github.stepinto.algo.reedsolomon;

public class ReedSolomonDecoder {

    private int n;
    private int[][] aInv;

    public ReedSolomonDecoder(int n, int m, int[] p) {
        assert p.length >= n;

        this.n = n;
        int[][] tmp = MatrixUtils.findDispersalMatrix(n, m);

        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0 ;j < n; j++) {
                a[i][j] = tmp[p[i]][j];
            }
        }
        aInv = MatrixUtils.inverse(a, n);
        assert aInv != null;
    }

    /**
     * Recovers data from at least n data or parity words
     * 
     * @param remain
     *            data or parity words
     * @return data words
     */
    public byte[] recover(byte[] remain) {
        assert remain.length >= n;

        byte[] data = new byte[n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int k = 0; k < n; k++) {
                sum = GaloisField.add(sum, GaloisField.mult(aInv[i][k], ByteUtils.byte2uint(remain[k])));
            }
            assert 0 <= sum && sum < 256;
            data[i] = ByteUtils.uint2byte(sum);
        }
        return data;
    }
}
