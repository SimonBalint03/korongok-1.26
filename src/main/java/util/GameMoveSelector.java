package util;

import model.Beam;
import model.GameState;
import puzzle.TwoPhaseMoveState;

/**
 * Manages the state of the selector used to move disks during gameplay.
 */
public class GameMoveSelector {

    /**
     * Enum to store all possible states of the selector.
     */
    public enum Phase {
        SELECT_FROM,
        SELECT_TO,
        READY_TO_MOVE
    }

    private GameState gameState;
    private Phase phase = Phase.SELECT_FROM;
    private Beam from;
    private Beam to;


    public GameMoveSelector(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the current {@link Phase} of the selector.
     * @return The current {@link Phase}.
     */
    public Phase getPhase() {
        return phase;
    }
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /**
     * Returns whether the current {@link Phase} is equal to READY_TO_MOVE.
     * @return True if the current {@link Phase} == READY_TO_MOVE.
     */
    public boolean isReadyToMove() {
        return phase == Phase.READY_TO_MOVE;
    }

    /**
     * Iterates between the phases when called.
     * @param beam The {@link Beam} selected.
     */
    public void select(Beam beam){
        switch (phase){
            case SELECT_FROM -> selectFrom(beam);
            case SELECT_TO -> selectTo(beam);
            case READY_TO_MOVE -> {}// Ignored
        }
    }

    /**
     * Iterates to the SELECT_TO phase.
     * @param beam The {@link Beam} selected.
     */
    public void selectFrom(Beam beam){
        from = beam;
        setPhase(Phase.SELECT_TO);

    }

    /**
     * Iterates to the SELECT_FROM phase.
     * @param beam The {@link Beam} selected.
     */
    public void selectTo(Beam beam){
        to = beam;
        setPhase(Phase.READY_TO_MOVE);
    }

    /**
     * Calls the gameState to makeMove.
     * Resets the selector.
     */
    public void makeMove(){
        gameState.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(from.getId(), to.getId()));
        reset();
    }

    /**
     * Resets the selector.
     */
    public void reset(){
        from = null;
        to = null;
        phase = Phase.SELECT_FROM;
    }

}
