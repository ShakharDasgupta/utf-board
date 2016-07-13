/*
 * Copyright (C) 2016 Shakhar Dasgupta <shakhardasgupta@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.shakhar.utfboard;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Shakhar Dasgupta <shakhardasgupta@gmail.com>
 */
public class UTFBoard extends Application {

    private ComboBox<String> blockComboBox;
    private Label rangeLabel;
    private TextArea textArea;
    private FlowPane keyboard;
    private UnicodeBlockFactory unicodeBlockFactory;

    @Override
    public void start(Stage primaryStage) {
        unicodeBlockFactory = UnicodeBlockFactory.newInstance();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        blockComboBox = new ComboBox<>();
        blockComboBox.getItems().addAll(unicodeBlockFactory.getBlockNames());
        blockComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setKeyboard();
                primaryStage.sizeToScene();
            }
        });

        grid.add(new Label("Unicode Block: "), 0, 0);
        grid.add(blockComboBox, 1, 0);

        rangeLabel = new Label();
        grid.add(rangeLabel, 0, 1);

        textArea = new TextArea();
        grid.add(textArea, 0, 2, 2, 1);

        keyboard = new FlowPane();
        keyboard.setOrientation(Orientation.HORIZONTAL);
        keyboard.setHgap(2);
        keyboard.setVgap(2);
        keyboard.setPrefWrapLength(300);
        grid.add(keyboard, 0, 3, 2, 1);

        Scene scene = new Scene(grid);
        primaryStage.setTitle("UTF Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setKeyboard() {
        keyboard.getChildren().clear();
        String blockName = blockComboBox.getSelectionModel().getSelectedItem();
        UnicodeBlock block = unicodeBlockFactory.getBlockByName(blockName);
        int min = block.getMinimum();
        int max = block.getMaximum();
        rangeLabel.setText("Range: U+" + Integer.toString(min, 16).toUpperCase() + " - U+" + Integer.toString(max, 16).toUpperCase());
        for (Integer codePoint : block.getCodePoints()) {
            String character = String.valueOf(Character.toChars(codePoint));
            Button button = new Button(character);
            button.setTooltip(new Tooltip("UTF+" + Integer.toString(codePoint, 16).toUpperCase()));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    textArea.appendText(character);
                }
            });
            keyboard.getChildren().add(button);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
