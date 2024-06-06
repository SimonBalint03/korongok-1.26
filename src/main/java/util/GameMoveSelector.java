package util;

import model.Beam;
import model.GameState;
import puzzle.TwoPhaseMoveState;

public class GameMoveSelector {

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

    public Phase getPhase() {
        return phase;
    }
    public void setPhase(Phase phase) {
        this.phase = phase;
    }
    public boolean isReadyToMove() {
        return phase == Phase.READY_TO_MOVE;
    }

    public void select(Beam beam){
        switch (phase){
            case SELECT_FROM -> selectFrom(beam);
            case SELECT_TO -> selectTo(beam);
            case READY_TO_MOVE -> {}// Ignored
        }
    }

    public void selectFrom(Beam beam){
        from = beam;
        setPhase(Phase.SELECT_TO);

    }
    public void selectTo(Beam beam){
        to = beam;
        setPhase(Phase.READY_TO_MOVE);
    }

    public void makeMove(){
        gameState.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(from.getId(), to.getId()));
        reset();
    }

    public void reset(){
        from = null;
        to = null;
        phase = Phase.SELECT_FROM;
    }

}
