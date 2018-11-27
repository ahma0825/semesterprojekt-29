/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sdu.woz.gui;

import edu.sdu.woz.Direction;
import edu.sdu.woz.Game;
import edu.sdu.woz.IFacade;
import edu.sdu.woz.Item;
import edu.sdu.woz.room.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class FxFacade implements Initializable, IFacade {
    private Game game = null;
    private SpecialButtonController specialButtonController = null;

    @FXML
    private Button specialButton;
    @FXML
    private TextArea terminal;
    @FXML
    private Button goNorth;
    @FXML
    private Button goSouth;
    @FXML
    private Button goWest;
    @FXML
    private Button goEast;
    @FXML
    private Button take;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new Game(this);
        specialButtonController = new SpecialButtonController(specialButton, game);
    }    

    @FXML
    private void onSpecial(ActionEvent event) {
        specialButtonController.onClick();
    }

    @FXML
    private void onNorth(ActionEvent event) {
        game.go(Direction.NORTH);
    }

    @FXML
    private void onSouth(ActionEvent event) {
        game.go(Direction.SOUTH);
    }

    @FXML
    private void onWest(ActionEvent event) {
        game.go(Direction.WEST);
    }

    @FXML
    private void onEast(ActionEvent event) {
        game.go(Direction.EAST);
    }

    @FXML
    private void onTake(ActionEvent event) {
        Room room = game.getCurrentRoom();
        Item item = room.getItems().get(0);
        room.takeItem(item);
        println("Took " + item.getDescription());
        specialButtonController.update();
        take.setDisable(true);
    }

    @Override
    public void onRoomEnter(Room room) {
        println(room.examine());
        
        if (room.canGo(Direction.NORTH)) {
            goNorth.setDisable(false);
        } else {
            goNorth.setDisable(true);
        }
        
        if (room.canGo(Direction.SOUTH)) {
            goSouth.setDisable(false);
        } else {
            goSouth.setDisable(true);
        }
        
        if (room.canGo(Direction.WEST)) {
            goWest.setDisable(false);
        } else {
            goWest.setDisable(true);
        }
        
        if (room.canGo(Direction.EAST)) {
            goEast.setDisable(false);
        } else {
            goEast.setDisable(true);
        }

        take.setDisable(room.getItems().isEmpty());

        if (game != null) specialButtonController.update();
    }

    @Override
    public void onGameOver() {
        try {
            Runtime.getRuntime().exec("explorer http://sanger.dk").waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void println(String s) {
        terminal.setText(terminal.getText() + s + "\n");
    }
}
