import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Solver {
    private List<Board> solutionBoards = new ArrayList<>();
    private boolean solved;


    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        MinPQ<SolverStep> priorizedSteps = new MinPQ<>(new SolverStepComparator());
        priorizedSteps.insert(new SolverStep(initial, 0, null));

        MinPQ<SolverStep> priorizedStepsTwin = new MinPQ<>(new SolverStepComparator());
        priorizedStepsTwin.insert(new SolverStep(initial.twin(), 0, null));

        SolverStep step;
        while (!priorizedSteps.min().board.isGoal() && !priorizedStepsTwin.min().board.isGoal()) {
            step = priorizedSteps.delMin();
            for (Board neighbor : step.board.neighbors()) {
                if (!isAlreadyInSolutionPath(step, neighbor)) {
                    priorizedSteps.insert(new SolverStep(neighbor, step.moves + 1, step));
                }
            }

            SolverStep stepTwin = priorizedStepsTwin.delMin();
            for (Board neighbor : stepTwin.board.neighbors()) {
                if (!isAlreadyInSolutionPath(stepTwin, neighbor)) {
                    priorizedStepsTwin.insert(new SolverStep(neighbor, stepTwin.moves + 1, stepTwin));
                }
            }
        }
        step = priorizedSteps.delMin();
        solved = step.board.isGoal();

        solutionBoards.add(step.board);
        while ((step = step.previousStep) != null) {
            solutionBoards.add(0, step.board);
        }
    }

    private boolean isAlreadyInSolutionPath(SolverStep lastStep, Board board) {
        SolverStep previousStep = lastStep;
        while ((previousStep = previousStep.previousStep) != null) {
            if (previousStep.board.equals(board)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSolvable() {           // is the initial board solvable?
        return solved;
    }

    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        int moves;
        if (isSolvable()) {
            moves = solutionBoards.size() - 1;
        } else {
            moves = -1;
        }
        return moves;
    }

    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        Iterable<Board> iterable;
        if (isSolvable()) {
            iterable = new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
        } else {
            iterable = null;
        }
        return iterable;
    }


    private static class SolverStep {

        private int moves;
        private Board board;
        private SolverStep previousStep;

        private SolverStep(Board board, int moves, SolverStep previousStep) {
            this.board = board;
            this.moves = moves;
            this.previousStep = previousStep;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }
    }

    private class SolutionIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < solutionBoards.size();
        }

        @Override
        public Board next() {
            return solutionBoards.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("It is not supported to remove a board from the solution.");
        }
    }

    private static class SolverStepComparator implements Comparator<SolverStep> {

        @Override
        public int compare(SolverStep step1, SolverStep step2) {
            return step1.getPriority() - step2.getPriority();
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
