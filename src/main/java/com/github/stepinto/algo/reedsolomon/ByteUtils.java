package com.github.stepinto.algo.reedsolomon;

public final class ByteUtils {

    private ByteUtils() {}

    public static int byte2uint(byte b) {
        int n = (int) b;
        if (n < 0) {
            n += 256;
        }
        assert 0 <= n && n < 256;
        return n;
    }

    public static byte uint2byte(int n) {
        assert 0 <= n && n < 256;
        if (n >= 128) {
            n -= 256;
        }
        return (byte) n;
    }

}
