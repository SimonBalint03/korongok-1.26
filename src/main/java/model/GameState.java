package model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.tinylog.Logger;
import puzzle.TwoPhaseMoveState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents the of the game.
 */
@XmlRootElement
public class GameState implements TwoPhaseMoveState<Integer> {

    /**
     * The number of beams.
     */
    @XmlElement
    public static final int NUM_BEAM = 3;

    /**
     * The number of disks to place onto beams.
     */
    @XmlElement
    public static final int NUM_DISK = 5;

    /**
     * The maximum amount of disks a beam can have.
     */
    @XmlElement
    public static final int MAX_DISK = 16 ;

    /**
     *  The color code for the Blue color of the disks.
     */
    public static final String BLUE = "#3572EF";

    /**
     *  The color code for the Red color of the disks.
     */
    public static final String RED = "#FF0000";

    /**
     * Stores the contents of all beams.
     */
    private Beam[] contents;

    private static final int[][] possibleMoves = {
            {0, 1},
            {0, 2},
            {1, 0},
            {1, 2},
            {2, 0},
            {2, 1}
    };

    private int num_moves = 0;

    /**
     * The setup for beams at the start of a game.
     */
    public static final Beam[] START_BEAMS = new Beam[] {
            new Beam(0,NUM_DISK,RED,BLUE),
            new Beam(1,NUM_DISK,BLUE,RED),
            new Beam(2,0)
    };

    public GameState(){
        contents = START_BEAMS;
    }

    /**
     * Return the number of moves already made.
     *
     * @return The number of moves.
     */
    @XmlElement
    public int getNum_moves() {
        return num_moves;
    }

    /**
     * Return all the {@link Beam} objects.
     *
     * @return A {@link Beam} array.
     */
    @XmlElement
    public Beam[] getContents() {
        return contents;
    }

    public void setContents(Beam[] contents) {
        this.contents = contents;
    }

    public void setNum_moves(int num_moves) {
        this.num_moves = num_moves;
    }

    /**
     * Returns whether a beam contains any disks.
     *
     * @param beam The object to check for being empty.
     * @return True if beam contains 0 disks.
     */
    public boolean isEmpty(Beam beam){
        return contents[beam.getId()].getDisks().isEmpty();
    }

    /**
     * Returns whether it's legal to move from a beam.
     *
     * @param integer Representing the beam's id.
     * @return True if a move is legal.
     */
    @Override
    public boolean isLegalToMoveFrom(Integer integer) {
        return !contents[integer].getDisks().isEmpty();
    }

    /**
     * Checks if all the beams have the correct disks on them.
     *
     * @return True if the first beam is all red and second is all blue. False else.
     */
    @Override
    public boolean isSolved() {
        for (Disk disk : contents[0].getDisks()) {
            if (!Objects.equals(disk.getColor(), RED)) {
                return false;
            }
        }
        for (Disk disk : contents[1].getDisks()) {
            if (!Objects.equals(disk.getColor(), BLUE)) {
                return false;
            }
        }
        if (!contents[2].getDisks().isEmpty()){
            return false;
        }
        Logger.debug("IsSolved: true");
        return true;
    }

    /**
     *
     * Returns whether move from -> to is legal.
     *
     * @param integerTwoPhaseMove two integers representing the beam id's.
     * @return True if the move is legal, else false.
     */
    @Override
    public boolean isLegalMove(TwoPhaseMove<Integer> integerTwoPhaseMove) {
        var from = integerTwoPhaseMove.from();
        var to = integerTwoPhaseMove.to();
        if (Objects.equals(to, from)) return false;

        if (!isLegalToMoveFrom(from)) return false;

        if (contents[to].getDisks().isEmpty()) return true;

        if (contents[to].getDisks().size()+1 > MAX_DISK) return false;
        return contents[to].getDisks().getFirst().getSize() >= contents[from].getDisks().getFirst().getSize();
    }

    /**
     * Makes move from -> to if it is legal.
     *
     * @param integerTwoPhaseMove two integers representing the beam id's.
     */
    @Override
    public void makeMove(TwoPhaseMove<Integer> integerTwoPhaseMove) {
        if (isLegalMove(integerTwoPhaseMove)){
            setNum_moves(num_moves+1);
            var from = integerTwoPhaseMove.from();
            var to = integerTwoPhaseMove.to();

            //System.out.println("MOVE: " + contents[from].getDisks().getFirst() + " from: " + from + " to " + to);
            Logger.debug("MOVE: " + contents[from].getDisks().getFirst() + " from: " + from + " to " + to);
            contents[to].getDisks().addFirst(contents[from].getDisks().getFirst());
            contents[from].getDisks().removeFirst();
            Logger.debug("GameState: " + this);

        }

    }

    /**
     * Returns a {@link Set} containing all the possible moves in the current state.
     *
     * @return A {@link Set} of {@link puzzle.TwoPhaseMoveState.TwoPhaseMove} integers.
     */
    @Override
    public Set<TwoPhaseMove<Integer>> getLegalMoves() {
        Set<TwoPhaseMove<Integer>> legalMoves = new HashSet<>();
        for (int[] move : possibleMoves) {
            TwoPhaseMove<Integer> twoPhaseMove = new TwoPhaseMove<>(move[0], move[1]);
            if (isLegalMove(twoPhaseMove)) {
                legalMoves.add(twoPhaseMove);
            }
        }
        return legalMoves;
    }


    /**
     * Returns a {@link TwoPhaseMoveState} clone of the current {@link GameState}.
     *
     * @return A {@link TwoPhaseMoveState} clone of the current {@link GameState}.
     */
    @Override
    public TwoPhaseMoveState<Integer> clone() {
        try {
            return (TwoPhaseMoveState<Integer>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Resets the contents to their default state.
     */
    public void reset() {
        Logger.debug("RESET");
        contents = new Beam[] {
                new Beam(0,NUM_DISK,RED,BLUE),
                new Beam(1,NUM_DISK,BLUE,RED),
                new Beam(2,0)
        };
        num_moves = 0;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "contents=" + Arrays.toString(contents) +
                ", num_moves=" + num_moves +
                '}';
    }
}
