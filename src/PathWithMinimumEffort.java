import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathWithMinimumEffort {
    
    public int minimumEffortPath(int[][] heights) {
        int r = heights.length;
        int c = heights[0].length;

        // Priority queue to process cells based on the effort (smallest effort first)
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Create an effort matrix initialized to max value (infinity)
        int[][] effort = new int[r][c];
        for (int[] row : effort) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Initialize starting point with effort 0
        pq.add(new int[]{0, 0, 0});  // {effort, row, col}
        effort[0][0] = 0;

        // Directions for moving up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Process the queue
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currEffort = current[0];
            int row = current[1];
            int col = current[2];

            // If we reach the bottom-right cell, return the effort
            if (row == r - 1 && col == c - 1) {
                return currEffort;
            }

            // Explore the four possible moves
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // Skip invalid moves
                if (newRow < 0 || newRow >= r || newCol < 0 || newCol >= c) {
                    continue;
                }

                // Calculate the effort to move to the new cell
                int newEffort = Math.max(currEffort, Math.abs(heights[row][col] - heights[newRow][newCol]));

                // If this new path has a smaller maximum effort, update and add to the queue
                if (newEffort < effort[newRow][newCol]) {
                    effort[newRow][newCol] = newEffort;
                    pq.add(new int[]{newEffort, newRow, newCol});
                }
            }
        }

        // If no path exists (this should never happen with valid inputs)
        return -1;
    }
}