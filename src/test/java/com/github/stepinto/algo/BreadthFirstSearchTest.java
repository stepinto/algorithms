package com.github.stepinto.algo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BreadthFirstSearchTest {
    @Test
    public void test1() {
        String s =
            "s.....\n" +
            ".#####\n" +
            ".#...#\n" +
            "...#t#\n";
        doTest(s, 9);
    }

    @Test
    public void test2() {
        String s =
            "s.....\n" +
            ".#####\n" +
            ".#...#\n" +
            "..##t#\n";
        doTest(s, -1);
    }

    public void doTest(String s, int exp) {
        int w = 0;
        int h = 0;
        int sx = 0, sy = 0, tx = 0, ty = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '\n') {
                h++;
            }
        }
        w = s.length() / h - 1;
        assertEquals(s.length(), (w + 1) * h);
        boolean[][] walls = new boolean[w][h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                char ch = s.charAt(y * (w + 1) + x);
                if (ch == '#') {
                    walls[x][y] = true;
                } else if (ch == 's') {
                    sx = x;
                    sy = y;
                } else if (ch == 't') {
                    tx = x;
                    ty = y;
                }
            }
        }
        int ans = BreadthFirstSearch.bfs(w, h, walls, sx, sy, tx, ty);
        assertEquals(exp, ans);
    }
}
