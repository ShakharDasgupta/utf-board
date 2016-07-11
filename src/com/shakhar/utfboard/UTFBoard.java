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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author Shakhar Dasgupta <shakhardasgupta@gmail.com>
 */
public class UTFBoard extends Application {

    private ComboBox<String> blockComboBox;
    private Label rangeLabel;
    private TextArea textArea;
    private FlowPane keyboard;
    private HashMap<String, Range> map;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        try {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            UnicodeHandler handler = new UnicodeHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(getClass().getResourceAsStream("/res/unicode.xml")));
            map = handler.getMap();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(UTFBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

        blockComboBox = new ComboBox<>();
        if (map != null) {
            ArrayList list = new ArrayList(map.keySet());
            Collections.sort(list);
            blockComboBox.getItems().addAll(list);
        }
        blockComboBox.setOnAction((ActionEvent e) -> {
            setKeyboard();
            primaryStage.sizeToScene();
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
        String block = blockComboBox.getSelectionModel().getSelectedItem();
        int start = map.get(block).getStart();
        int end = map.get(block).getEnd();
        rangeLabel.setText("Range: " + Integer.toString(start, 16).toUpperCase() + " - " + Integer.toString(end, 16).toUpperCase());
        for (int i = start; i <= end; i++) {
            if (Character.isDefined(i)) {
                String character = String.valueOf(Character.toChars(i));
                Button button = new Button(character);
                button.setOnAction((ActionEvent e) -> {
                    textArea.appendText(character);
                });
                keyboard.getChildren().add(button);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
