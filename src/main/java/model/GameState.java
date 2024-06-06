package model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.scene.paint.Color;
import puzzle.TwoPhaseMoveState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


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
    public static final int NUM_DISK = 8;

    /**
     * The maximum amount of disks a beam can have.
     */
    @XmlElement
    public static final int MAX_DISK = 10 ;

    public static final String BLUE = "#2c29ff";
    public static final String RED = "#ff2945";

    /**
     * Stores the contents of all beams.
     */
    private Beam[] contents;


    private int num_moves;

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
        num_moves = 0;
    }

    @XmlElement
    public int getNum_moves() {
        return num_moves;
    }

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

    public boolean isEmpty(Beam beam){
        return contents[beam.getId()].getDisks().isEmpty();
    }

    @Override
    public boolean isLegalToMoveFrom(Integer integer) {
        if (contents[integer].getDisks().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSolved() {
        for (Disk disk : contents[0].getDisks()) {
            if (disk.getColor() != RED) {
                return false;
            }
        }
        for (Disk disk : contents[1].getDisks()) {
            if (disk.getColor() != BLUE) {
                return false;
            }
        }
        if (!contents[2].getDisks().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public boolean isLegalMove(TwoPhaseMove<Integer> integerTwoPhaseMove) {
        var from = integerTwoPhaseMove.from();
        var to = integerTwoPhaseMove.to();
        if (Objects.equals(to, from)) return false;

        if (!isLegalToMoveFrom(from)) return false;

        if (contents[to].getDisks().isEmpty()) return true;

        if (contents[to].getDisks().size()+1 > MAX_DISK) return false;
        if (contents[to].getDisks().getFirst().getSize() < contents[from].getDisks().getFirst().getSize()){
            return false;
        }
        return true;
    }

    @Override
    public void makeMove(TwoPhaseMove<Integer> integerTwoPhaseMove) {
        if (isLegalMove(integerTwoPhaseMove)){
            var from = integerTwoPhaseMove.from();
            var to = integerTwoPhaseMove.to();

            System.out.println("MOVE: " + contents[from].getDisks().getFirst() + " from: " + from + " to " + to);

            contents[to].getDisks().addFirst(contents[from].getDisks().get(0));
            contents[from].getDisks().remove(0);

            num_moves++;

        }

    }

    @Override
    public Set<TwoPhaseMove<Integer>> getLegalMoves() {
        return Set.of();
    }

    @Override
    public TwoPhaseMoveState<Integer> clone() {
        return null;
    }

    public void reset() {
        contents = new Beam[] {
                new Beam(0,NUM_DISK,RED,BLUE),
                new Beam(1,NUM_DISK,BLUE,RED),
                new Beam(2,0)
        };
        num_moves = 0;
    }
}
