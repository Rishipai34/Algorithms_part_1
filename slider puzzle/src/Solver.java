import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Solver {

    private List<Board> solutionTiles = new ArrayList<>();
    private boolean solved;
    private SolverStep finalStep;

    private static class SolverStep {
        private int moves;
        private Board board;
        private SolverStep PreviousStep;

        public SolverStep(int moves, Board board, SolverStep PreviousStep) {
            this.board = board;
            this.moves = moves;
            this.PreviousStep = PreviousStep;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solved = false;
        Comparator<SolverStep> solverStepComparator = Comparator.comparingInt(SolverStep::getPriority);
        MinPQ<SolverStep> impostep = new MinPQ<>(solverStepComparator);
        impostep.insert(new SolverStep(0, initial, null));

        MinPQ<SolverStep> impotwin = new MinPQ<>(solverStepComparator);
        impotwin.insert(new SolverStep(0, initial.twin(), null));
        SolverStep step1 = impostep.delMin();
        SolverStep step1twin = impotwin.delMin();

        while ((!step1.board.isGoal()) && (!step1twin.board.isGoal())) {
            for (Board first : step1.board.neighbors()) {
                if (!(counted(step1, first))) {
                    impostep.insert(new SolverStep(step1.moves + 1, first, step1));
                }
            }
            for (Board second : step1twin.board.neighbors()) {
                if (!(counted(step1, second))) {
                    impostep.insert(new SolverStep(step1twin.moves + 1, second, step1twin));
                }
            }
        }
        step1 = impostep.delMin();
        if (step1.board.isGoal()) {
            finalStep = step1;
            solved = true;
        }
    }

    // to check if the neighbour has already been included in the solution sequence
    private boolean counted(SolverStep lastStep, Board newBoard) {
        SolverStep PreviousStep = lastStep.PreviousStep;
        while (PreviousStep != null) {
            if (PreviousStep.board.equals(newBoard)) {
                return true;
            }
            PreviousStep = PreviousStep.PreviousStep;
        }
        return false;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solved;
    }

    // min number of moves to solve initial board
    public int moves() {
        int num;
        if (isSolvable()) {
            num = solutionTiles.size() - 1;
        } else {
            num = -1;
        }
        return num;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!solved) {
            return null;
        }
        SolverStep current = finalStep;
        Stack<Board> solution = new Stack<>();
        solution.push(current.board);
        while (current.PreviousStep != null) {
            solution.push(current.PreviousStep.board);
            current = current.PreviousStep;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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
