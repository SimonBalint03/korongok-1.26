package game;

import javafx.application.Application;
import model.GameState;
import puzzle.solver.BreadthFirstSearch;

public class Main {

    public static void main(String[] args) {
        Application.launch(GameApplication.class, args);
    }

}