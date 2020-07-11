import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {

    private Board[] neighbours;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = copy(tiles);
    }

    // to copy a two dimensional array
    private int[][] copy(int[][] arrayToCopy) {
        int[][] copy = new int[arrayToCopy.length][];
        for (int row = 0; row < arrayToCopy.length; row++) {
            copy[row] = arrayToCopy[row].clone();
        }
        return copy;
    }

    private void exchangeBlocks(int[][] tiles2, int iF, int jF, int iS, int jS) {
        int first = tiles2[iF][jF];
        tiles2[iF][jF] = tiles2[iS][jS];
        tiles2[iS][jS] = first;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringVariable = new StringBuilder(tiles.length + "\n");
        String stringVariable2;
        for (int[] row : tiles) {
            for (int block : row) {

                stringVariable.append("    ");
                stringVariable2 = Integer.toString(block);
                int n = stringVariable2.length();
                if (stringVariable2.length() > 1) {
                    for (int i = 4; i > n; i--) {
                        stringVariable.append(" ");
                    }
                    stringVariable.append(block);
                } else {
                    stringVariable.append("   ");
                    stringVariable.append(block);
                }
            }
            stringVariable.append("\n\n");
        }
        return stringVariable.toString();
    }

    // board dimension
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int val = -1;
        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != (i * tiles.length + j + 1)) val++;
            }
        return val;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int value = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int expectedValue = (i * tiles.length + j + 1);
                if (tiles[i][j] != expectedValue && tiles[i][j] != 0) {
                    int actualValue = tiles[i][j];
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
    public boolean equals(Object given) {
        if (this == given) return true;
        if (given == null || getClass() != given.getClass()) return false;

        Board that = (Board) given;
        if (this.tiles.length != that.tiles.length) return false;
        for (int i = 0; i < tiles.length; i++) {
            if (this.tiles[i].length != that.tiles[i].length) return false;
            for (int j = 0; j < tiles[i].length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    private void findNeighbours() {
        List<Board> NeighboursFound = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (tiles[i][j] != 0) {
            j++;
            if (j >= tiles.length) {
                j = 0;
                i++;
            }
        }
        if (i > 0) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i - 1, j, i, j);
            NeighboursFound.add(new Board(neighborTiles));
        }
        if (i < dimension() - 1) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i, j, i + 1, j);
            NeighboursFound.add(new Board(neighborTiles));
        }
        if (j > 0) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i, j - 1, i, j);
            NeighboursFound.add(new Board(neighborTiles));
        }
        if (j < dimension() - 1) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i, j, i, j + 1);
            NeighboursFound.add(new Board(neighborTiles));
        }
        neighbours = new Board[NeighboursFound.size()];
        neighbours = NeighboursFound.toArray(neighbours);
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
        int[][] twinBlocks = copy(tiles);
        int i = 0;
        int j = 0;
        while (twinBlocks[i][j] == 0 || twinBlocks[i][j + 1] == 0) {
            j++;
            if (j >= twinBlocks.length - 1) {
                i++;
                j = 0;
            }
        }
        exchangeBlocks(twinBlocks, i, j, i, j + 1);
        return new Board(twinBlocks);
    }
}
