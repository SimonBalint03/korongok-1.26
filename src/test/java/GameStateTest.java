import model.Beam;
import model.GameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import puzzle.TwoPhaseMoveState;

public class GameStateTest {

    @Test
    public void testMove() {
        GameState gameState = new GameState();
        gameState.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(0,1));
        Assertions.assertEquals(gameState.getNum_moves(),1);
    }

    @Test
    public void testClone(){
        GameState gameState = new GameState();
        Assertions.assertTrue(gameState.clone() != gameState);
        Assertions.assertTrue(gameState.clone().getClass() == gameState.getClass());
    }

    @Test
    public void testBeam(){
        GameState gameState = new GameState();
        Beam b1 = new Beam(0,3,gameState.RED,gameState.BLUE);
        Beam b2 = new Beam(0,3,gameState.BLUE,gameState.RED);

        System.out.println(b1 + " : " + b2);
        Assertions.assertFalse(b1.equals(b2));
    }

}
