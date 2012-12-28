package com.github.stepinto.algo;

import com.google.common.collect.Lists;

import java.util.Queue;

public class BreadthFirstSearch {
    public static int bfs(int w, int h, boolean[][] walls, int sx, int sy, int tx, int ty) {
        class State { int x, y, t; };
        int[] dx = new int[] {1, -1, 0, 0};
        int[] dy = new int[] {0, 0, 1, -1};
        boolean[][] hash = new boolean[w][h];

        State init = new State();
        init.x = sx;
        init.y = sy;
        init.t = 0;
        Queue<State> queue = Lists.newLinkedList();
        queue.offer(init);
        hash[sx][sy] = true;
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.x == tx && current.y == ty) {
                return current.t;
            }
            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                if (0 <= nx && nx < w && 0 <= ny && ny < h && !walls[nx][ny] && !hash[nx][ny]) {
                    State next = new State();
                    next.x = nx;
                    next.y = ny;
                    next.t = current.t + 1;
                    queue.offer(next);
                    hash[nx][ny] = true;
                }
            }
        }
        return -1;
    }
}
