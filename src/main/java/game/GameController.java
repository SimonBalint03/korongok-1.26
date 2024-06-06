package game;

import model.Disk;
import model.GameState;
import util.GameMoveSelector;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Arrays;

public class GameController {

    private GameState gameState = LoadXML();

    private GameMoveSelector selector = new GameMoveSelector(gameState);

    //private BreadthFirstSearch solver = new BreadthFirstSearch();

    @FXML
    private GridPane poles;

    @FXML
    private Button restart;

    @FXML
    private Text moves;

    @FXML
    private void initialize(){

        for (int i = 0; i < gameState.START_BEAMS.length; i++) {
            for (int j = 0; j < gameState.getContents()[i].getDisks().size(); j++) {
                var disk = createDisk(gameState.getContents()[i].getDisks().get(j));
                poles.add(disk, i, j+(gameState.MAX_DISK-gameState.NUM_DISK));
            }
        }
        poles.setOnMouseClicked(this::handleMouseClick);
        restart.setOnAction(this::resetButtonClicked);

        RefreshGrid();
    }

    private void resetButtonClicked(ActionEvent actionEvent) {
        System.out.println("EVENT: New game started.");
        gameState.reset();
        selector.reset();
        RefreshGrid();
        CreateXML();
    }



    private StackPane createDisk(Disk disk){
        var diskPane = new StackPane();
        var piece = new Rectangle(disk.getSize(),20, Color.BLACK);
        piece.setFill(Color.valueOf(disk.getColor()));
        piece.setStroke(Color.BLACK);
        piece.getStyleClass().add("disk");
        diskPane.getChildren().add(piece);
        return diskPane;
    }


    private void handleMouseClick(MouseEvent event) {
        ClickPosition result = getCalculatedClickPosition(event);

        var row = result.row;
        var col = result.column;
        System.out.printf("EVENT: Clicked on beam (%d)%n", col);
        selector.select(gameState.getContents()[col]);
        if (selector.isReadyToMove()){
            selector.makeMove();
            CreateXML();
        }

        RefreshGrid();
        showSelectionPhaseChange(row,col);
    }

    private void RefreshGrid() {
        poles.getChildren().clear();
        for (int i = 0; i < gameState.getContents().length; i++) {
            int j = GameState.MAX_DISK -1;
            for (Disk d : gameState.getContents()[i].getDisks().reversed()){
                try {
                    var disk = createDisk(d);
                    poles.add(disk, i, j); // i: col , j: row
                    j--;
                }catch (IndexOutOfBoundsException ex){
                    // Ignored
                }
            }
        }

        moves.setText("Moves: " + gameState.getNum_moves());

    }

    private void showSelectionPhaseChange(int row, int col){
        switch (selector.getPhase()){
            case SELECT_FROM -> {}
            case SELECT_TO -> { showSelection(row,col);}
            case READY_TO_MOVE -> { hideSelection(row,col);}
        }
    }


    private void showSelection(int row,int col) {
        for (Node child : poles.getChildren()) {
            if (GridPane.getRowIndex(child) == (GameState.MAX_DISK) - (gameState.getContents()[col].getDisks().size()) && GridPane.getColumnIndex(child) == col) {
                for (var y : ((StackPane) child).getChildren()){
                    y.getStyleClass().add("selected");
                }
            }
        }
    }

    private void hideSelection(int row, int col){
        for (Node child : poles.getChildren()) {
            if (GridPane.getRowIndex(child) == (GameState.MAX_DISK) - (gameState.getContents()[col].getDisks().size())&& GridPane.getColumnIndex(child) == col) {
                for (var y : ((StackPane) child).getChildren()){
                    y.getStyleClass().remove("selected");
                }
            }
        }
    }

    private ClickPosition getCalculatedClickPosition(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        double cellWidth = poles.getPrefWidth() / poles.getColumnCount();
        double cellHeight = poles.getPrefHeight() / poles.getRowCount();

        int row = (int) Math.floor(y / cellHeight);
        int column = (int) Math.floor(x / cellWidth);
        ClickPosition result = new ClickPosition(row, column);
        return result;
    }

    private record ClickPosition(int row, int column) {
    }

    private GameState LoadXML(){
        try {
            // Create JAXB context
            JAXBContext context = JAXBContext.newInstance(GameState.class);

            // Create unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Unmarshal the XML file to a state object
            GameState state = (GameState) unmarshaller.unmarshal(new File("gamestate.xml"));
            System.out.println("Succefully loaded gamestate.xml");
            return state;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GameState();
    }

    private void CreateXML(){
        try {

            // Create JAXB context
            JAXBContext context = JAXBContext.newInstance(GameState.class);

            // Create marshaller
            Marshaller marshaller = context.createMarshaller();

            // To format XML
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Marshal the person object to a file
            marshaller.marshal(gameState, new File("gamestate.xml"));

            // You can also marshal to a StringWriter, OutputStream, etc.
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
