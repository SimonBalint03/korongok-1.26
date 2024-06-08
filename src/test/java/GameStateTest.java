import model.GameState;
import model.Beam;
import model.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puzzle.TwoPhaseMoveState.TwoPhaseMove;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class GameStateTest {

    private GameState gameState;

    @BeforeEach
    public void setUp() {
        gameState = new GameState();
        gameState.reset();
    }

    @Test
    public void testInitialNumMoves() {
        assertEquals(0, gameState.getNum_moves());
    }

    @Test
    public void testInitialContents() {
        Beam[] contents = gameState.getContents();
        assertEquals(GameState.NUM_BEAM, contents.length);
        assertEquals(GameState.NUM_DISK, contents[0].getDisks().size());
        assertEquals(GameState.NUM_DISK, contents[1].getDisks().size());
        assertEquals(0, contents[2].getDisks().size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(gameState.isEmpty(gameState.getContents()[0]));
        assertFalse(gameState.isEmpty(gameState.getContents()[1]));
        assertTrue(gameState.isEmpty(gameState.getContents()[2]));
    }

    @Test
    public void testIsLegalToMoveFrom() {
        assertTrue(gameState.isLegalToMoveFrom(0));
        assertTrue(gameState.isLegalToMoveFrom(1));
        assertFalse(gameState.isLegalToMoveFrom(2));
    }

    @Test
    public void testIsSolved() {
        assertFalse(gameState.isSolved());
        gameState.setContents(new Beam[]{
                new Beam(0,gameState.NUM_DISK,gameState.RED,gameState.RED),
                new Beam(1,gameState.NUM_DISK,gameState.BLUE,gameState.BLUE),
                new Beam(2,0),
        });
        assertTrue(gameState.isSolved());
    }

    @Test
    public void testIsLegalMove() {
        TwoPhaseMove<Integer> move1 = new TwoPhaseMove<>(0, 2);
        TwoPhaseMove<Integer> move2 = new TwoPhaseMove<>(1, 2);
        TwoPhaseMove<Integer> move3 = new TwoPhaseMove<>(2, 1);

        assertTrue(gameState.isLegalMove(move1));
        assertTrue(gameState.isLegalMove(move2));
        assertFalse(gameState.isLegalMove(move3));
    }

    @Test
    public void testMakeMove() {
        TwoPhaseMove<Integer> move = new TwoPhaseMove<>(0, 2);
        assertTrue(gameState.isLegalMove(move));
        gameState.makeMove(move);
        assertEquals(1, gameState.getNum_moves());
        assertEquals(GameState.NUM_DISK - 1, gameState.getContents()[0].getDisks().size());
        assertEquals(1, gameState.getContents()[2].getDisks().size());
    }

    @Test
    public void testGetLegalMoves() {
        Set<TwoPhaseMove<Integer>> legalMoves = gameState.getLegalMoves();
        System.out.println(legalMoves);
        assertEquals(4, legalMoves.size());
        assertTrue(legalMoves.contains(new TwoPhaseMove<>(0, 2)));
        assertTrue(legalMoves.contains(new TwoPhaseMove<>(1, 2)));
    }

    @Test
    public void testClone() {
        GameState clone = (GameState) gameState.clone();
        assertNotSame(gameState, clone);
        assertEquals(gameState.getNum_moves(), clone.getNum_moves());
        assertArrayEquals(gameState.getContents(), clone.getContents());
    }

    @Test
    public void testReset() {
        gameState.setNum_moves(5);
        gameState.makeMove(new TwoPhaseMove<>(0, 2));
        gameState.reset();
        assertEquals(0, gameState.getNum_moves());
        assertEquals(GameState.NUM_DISK, gameState.getContents()[0].getDisks().size());
        assertEquals(GameState.NUM_DISK, gameState.getContents()[1].getDisks().size());
        assertEquals(0, gameState.getContents()[2].getDisks().size());
    }

}
