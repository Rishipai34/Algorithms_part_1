import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {

    private Board[] neighbours;
    private int[][] blocks;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.blocks = copy(tiles);
    }

    // to copy a two dimensional array
    private int[][] copy(int[][] arrayToCopy) {
        int[][] copy = new int[arrayToCopy.length][];
        for (int r = 0; r < arrayToCopy.length; r++) {
            copy[r] = arrayToCopy[r].clone();
        }
        return copy;
    }

    private void exchangeBlocks(int[][] tiles, int iF, int jF, int iS, int jS) {
        int first = tiles[iF][jF];
        tiles[iF][jF] = tiles[iS][jS];
        tiles[iS][jS] = first;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringVariable = new StringBuilder(blocks.length + "\n");
        for (int[] row : blocks)
            for (int block : row) {
                stringVariable.append(" ");
                stringVariable.append(block);
            }
        return stringVariable.toString();
    }

    // board dimension
    public int dimension() {
        return blocks.length;
    }

    // number of tiles out of place
    public int hamming() {
        int value = 0;
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != i * blocks.length + j + 1) value++;
            }
        return value;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int value = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int expectedValue = (i * blocks.length + j + 1);
                if (blocks[i][j] != expectedValue && blocks[i][j] != 0) {
                    int actualValue = blocks[i][j];
                    actualValue--;
                    int goalI = actualValue / dimension();
                    int goalJ = actualValue % dimension();
                    value += Math.abs(goalI - i) + Math.abs(goalJ - j);
                }
            }
        }
        return value;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.blocks.length != that.blocks.length) return false;
        for (int i = 0; i < blocks.length; i++) {
            if (this.blocks[i].length != that.blocks[i].length) return false;
            for (int j = 0; j < blocks[i].length; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }

        return true;
    }

    private void findNeighbours() {
        List<Board> foundNeighbours = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (blocks[i][j] != 0) {
            j++;
            if (j >= blocks.length) {
                j = 0;
                i++;
            }
        }
        if (i > 0) {
            int[][] neighborTiles = copy(blocks);
            exchangeBlocks(neighborTiles, i - 1, j, i, j);
            foundNeighbours.add(new Board(neighborTiles));
        }
        if (i < dimension() - 1) {
            int[][] neighborTiles = copy(blocks);
            exchangeBlocks(neighborTiles, i, j, i + 1, j);
            foundNeighbours.add(new Board(neighborTiles));
        }
        if (j > 0) {
            int[][] neighborTiles = copy(blocks);
            exchangeBlocks(neighborTiles, i, j - 1, i, j);
            foundNeighbours.add(new Board(neighborTiles));
        }
        if (j < dimension() - 1) {
            int[][] neighborTiles = copy(blocks);
            exchangeBlocks(neighborTiles, i, j, i, j + 1);
            foundNeighbours.add(new Board(neighborTiles));
        }
        neighbours = new Board[foundNeighbours.size()];
        neighbours = foundNeighbours.toArray(neighbours);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return () -> {
            if (neighbours == null) {
                findNeighbours();
            }
            return new NeighborIterator();
        };
    }

    private class NeighborIterator implements Iterator<Board> {

        int index = 0;

        @Override
        public boolean hasNext() {
            return index < neighbours.length;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return neighbours[index++];
            } else {
                throw new NoSuchElementException(" No next neighbour exists ");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(" Remove operation is not supported by this iterator ");
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinBlocks = copy(blocks);
        int i = 0;
        int j = 0;
        while (twinBlocks[i][j] == 0 || twinBlocks[i][j + 1] == 0) {
            j++;
            if (j >= twinBlocks.length) {
                i++;
                j = 0;
            }
        }
        exchangeBlocks(twinBlocks, i, j, i, j + 1);
        return new Board(twinBlocks);
    }
}
