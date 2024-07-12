package com.example.text2speachbot;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;

public class textToSpeechGui extends Application {

    private static final int APP_WIDTH = 475;
    private static final int APP_HEIGHT = 575;

    private TextArea textArea;
    private ComboBox<String> voices, rates, volumes;
    private ProgressIndicator progressIndicator;

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("moe._.Talk's APP");
        stage.setScene(scene);
        stage.show();
    }

    private Scene createScene() {
        VBox box = new VBox();
        box.getStyleClass().add("body");

        Label textToSpeachLabel = new Label("Welcome to Moe._.talks");
        textToSpeachLabel.getStyleClass().add("text-to-speach-label");
        textToSpeachLabel.setMaxWidth(Double.MAX_VALUE);
        textToSpeachLabel.setAlignment(Pos.CENTER);
        box.getChildren().add(textToSpeachLabel);

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.getStyleClass().add("text-area");

        StackPane textAreaPane = new StackPane();
        // Add 'margin' around left and right of text area
        textAreaPane.setPadding(new Insets(0, 15, 0, 15));
        textAreaPane.getChildren().add(textArea);
        box.getChildren().add(textAreaPane);

        GridPane settingsPane = createSettingComponents();
        box.getChildren().add(settingsPane);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        StackPane progressPane = new StackPane(progressIndicator);
        progressPane.setAlignment(Pos.CENTER);
        box.getChildren().add(progressPane);

        Button speakButton = createImageButton();
        speakButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String msg = textArea.getText();
                String voice = voices.getValue();
                String rate = rates.getValue();
                String volume = volumes.getValue();

                progressIndicator.setVisible(true);
                new Thread(() -> {
                    textToSpeechController.speak(msg, voice, rate, volume);
                    progressIndicator.setVisible(false);
                }).start();
            }
        });
        StackPane speakButtonPane = new StackPane();
        speakButtonPane.setPadding(new Insets(60, 20, 20, 20));
        speakButtonPane.getChildren().add(speakButton);

        box.getChildren().add(speakButtonPane);

        return new Scene(box, APP_WIDTH, APP_HEIGHT);
    }

    private Button createImageButton() {
        Button button = new Button("YAP!");
        button.getStyleClass().add("speak-btn");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER);

        // Add image to button
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("speak.png")));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        button.setGraphic(imageView);

        return button;
    }

    private GridPane createSettingComponents() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setPadding(new Insets(10, 0, 0, 0));

        Label voiceLabel = new Label("Voice");
        voiceLabel.getStyleClass().add("setting-label");

        Label rateLabel = new Label("Rate");
        rateLabel.getStyleClass().add("setting-label");

        Label volumeLabel = new Label("Volume");
        volumeLabel.getStyleClass().add("setting-label");

        gridPane.add(voiceLabel, 0, 0);
        gridPane.add(rateLabel, 1, 0);
        gridPane.add(volumeLabel, 2, 0);

        // Centers Labels
        GridPane.setHalignment(voiceLabel, HPos.CENTER);
        GridPane.setHalignment(rateLabel, HPos.CENTER);
        GridPane.setHalignment(volumeLabel, HPos.CENTER);

        voices = new ComboBox<>();
        voices.getItems().addAll(
                textToSpeechController.getVoices()
        );
        voices.setValue(voices.getItems().get(0));
        voices.getStyleClass().add("setting-combo-box");

        rates = new ComboBox<>();
        rates.getItems().addAll(
                textToSpeechController.getSpeedRates()
        );
        rates.setValue(rates.getItems().get(0));
        rates.getStyleClass().add("setting-combo-box");

        volumes = new ComboBox<>();
        volumes.getItems().addAll(
                textToSpeechController.getVolumeLevels()
        );
        volumes.setValue(volumes.getItems().get(0));
        volumes.getStyleClass().add("setting-combo-box");

        gridPane.add(voices, 0, 1);
        gridPane.add(rates, 1, 1);
        gridPane.add(volumes, 2, 1);

        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    public static void main(String[] args) {
        // Print working directory and programmer's name
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Working Directory: " + currentDirectory);
        System.out.println("Programmer: Mamoudou T. Bah");
        // Print current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy @ HH:mm");
        String formattedDateTime = now.format(formatter);
        System.out.println("Moe's Text2SpeachBOT: " + formattedDateTime + '\n');

        launch();
    }
}



// *                            UML Diagram
// *                     +--------------------------+
// *                     |   textToSpeechController |
// *                     +--------------------------+
// *                     | - voiceManager: VoiceManager
// *                     +--------------------------+
// *                     | + getVoices(): ArrayList <String>
// *                     | + getSpeedRates(): ArrayList<String>
// *                     | + getVolumeLevels(): ArrayList<String>
// *                     | + speak(String, String, String, String)
// *                     +--------------------------+
// *                               ^
// *                               | uses
// *                     +--------------------------+
// *                     |    VoiceManager          |
// *                     +--------------------------+
// *                     | + getInstance(): VoiceManager
// *                     | + getVoices(): Voice[]
// *                     +--------------------------+
// *                     +--------------------------+
// *                     |          Voice            |
// *                     +--------------------------+
// *                     | + getName(): String
// *                     | + allocate(): void
// *                     | + setRate(int): void
// *                     | + setVolume(int): void
// *                     | + speak(String): void
// *                     | + deallocate(): void
// *                     +--------------------------+
// *
// *
// *                     +--------------------------+
// *                     |       textToSpeechGui    |
// *                     +--------------------------+
// *                     | - APP_WIDTH: int
// *                     | - APP_HEIGHT: int
// *                     | - textArea: TextArea
// *                     | - voices: ComboBox<String>
// *                     | - rates: ComboBox<String>
// *                     | - volumes: ComboBox<String>
// *                     | - progressIndicator: ProgressIndicator
// *                     +--------------------------+
// *                     | + start(Stage): void
// *                     | + createScene(): Scene
// *                     | + createImageButton(): Button
// *                     | + createSettingComponents(): GridPane
// *                     | + loadTextFromFile(Stage): void
// *                     | + saveTextToFile(Stage): void
// *                     | + main(String[]): void
// *                     +--------------------------+
// *                               ^
// *                               | uses
// *                     +--------------------------+
// *                     |    javafx.application    |
// *                     |        Application       |
// *                     +--------------------------+
// *                               ^
// *                               | calls
// *                     |   textToSpeechController |
// *                     +--------------------------+
// *
//
