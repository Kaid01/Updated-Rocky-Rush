package edu.sdccd.cisc191.Game.MOT;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MOTLogic {

    Deck motDeck = new Deck();
    Card middleCard;
    public String middleColor;
    ArrayList<Card> playerHand = new ArrayList<>();
    ArrayList<Card> npcHand = new ArrayList<>();
    Random rand = new Random();

    public MOTLogic() {

        // Sets the middleCard
        middleCard = motDeck.deck.get(0);
        motDeck.deck.remove(0);

        // Adds 4 cards to playerHand from deck
        for (int i = 0; i < 4; i++) {
            playerHand.add(motDeck.deck.get(0));
            motDeck.deck.remove(0);
        }

        // Adds 4 cards to npcHand from deck
        for (int i = 0; i < 4; i++) {
            npcHand.add(motDeck.deck.get(0));
            motDeck.deck.remove(0);
        }
    }

    public void drawPlayerCard() {
        if (playerHand.size() < 7) {
            playerHand.add(motDeck.deck.get(0));
            motDeck.deck.remove(0);
        }
        else {
            //AlertBox.display("Card out of bounds", "You cannot draw a card when at max.");
        }
    }

    public void drawNPCCard() {
        if (npcHand.size() < 7) {
            npcHand.add(motDeck.deck.get(0));
            motDeck.deck.remove(0);
        }
        else {
            //AlertBox.display("Card out of bounds", "the NPC brokey ;(");
        }
    }

    public void playCard(int i) {

        try {
            if (playerHand.get(i).value.contains("wild")) {
                switch (playerHand.get(i).value) {
                    case "wild":
                        replaceMiddleCard(i, "player");
                        playWild();
                        npcMove();
                        break;
                    case "wildDraw4":
                        replaceMiddleCard(i, "player");
                        playWild();
                        drawNPCCard();
                        drawNPCCard();
                        drawNPCCard();
                        drawNPCCard();
                        npcMove();
                        break;
                }
                return;
            }


            if (playerHand.get(i).color.equals(middleColor) || playerHand.get(i).value.equals(middleCard.value)) {
                switch (playerHand.get(i).value) {
                    case "draw2":
                        replaceMiddleCard(i, "player");
                        drawNPCCard();
                        drawNPCCard();
                        npcMove();
                        break;
                    case "skip":
                        replaceMiddleCard(i, "player");
                        break;
                    case "reverse":
                        replaceMiddleCard(i, "player");
                        break;
                    default:
                        replaceMiddleCard(i, "player");
                        npcMove();
                        break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print("");
        }
    }

    public void replaceMiddleCard(int i, String person) {

        motDeck.deck.add(middleCard);
        Collections.shuffle(motDeck.deck);

        if (person.equals("player")) {
            middleCard = playerHand.get(i);
            playerHand.remove(i);
        } else {
            middleCard = npcHand.get(i);
            npcHand.remove(i);
        }

        middleColor = middleCard.color;
    }

    public void playWild() {

        System.out.println("wildCard Played");

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(100);
        window.setMinHeight(100);
        window.setResizable(false);

        Button red = new Button();
        red.setLayoutX(0.0);
        red.setLayoutY(0.0);
        red.setMnemonicParsing(false);
        red.setPrefHeight(50.0);
        red.setPrefWidth(50.0);
        red.setGraphic(new ImageView(new Image("MOTCards/red_card.png")));
        red.setOnAction(event -> {middleColor = "red"; window.close();});

        Button yellow = new Button();
        yellow.setLayoutX(50.0);
        yellow.setLayoutY(0.0);
        yellow.setMnemonicParsing(false);
        yellow.setPrefHeight(50.0);
        yellow.setPrefWidth(50.0);
        yellow.setGraphic(new ImageView(new Image("MOTCards/yellow_card.png")));
        yellow.setOnAction(event -> {middleColor = "yellow"; window.close();});

        Button green = new Button();
        green.setLayoutX(0.0);
        green.setLayoutY(50.0);
        green.setMnemonicParsing(false);
        green.setPrefHeight(50.0);
        green.setPrefWidth(50.0);
        green.setGraphic(new ImageView(new Image("MOTCards/green_card.png")));
        green.setOnAction(event -> {middleColor = "green"; window.close();});

        Button blue = new Button();
        blue.setLayoutX(50.0);
        blue.setLayoutY(50.0);
        blue.setMnemonicParsing(false);
        blue.setPrefHeight(50.0);
        blue.setPrefWidth(50.0);
        blue.setGraphic(new ImageView(new Image("MOTCards/blue_card.png")));
        blue.setOnAction(event -> {middleColor = "blue"; window.close();});

        BorderPane layout = new BorderPane();
        HBox close = new HBox();
        close.getChildren().addAll(red, yellow, green, blue);
        close.setAlignment(Pos.CENTER);
        layout.setBottom(close);
        layout.setStyle("-fx-background-color: #6F4E37");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void npcLogic() {
        for (int i = 0; i < npcHand.size(); i++) {
            npcMove();
        }
    }

    public void npcMove() {

        boolean checkPossible = false;

        for(int i = 0; i < npcHand.size(); i++) {

            try {
                if (npcHand.get(i).value.contains("wild")) {
                    switch (npcHand.get(i).value) {
                        case "wild":
                            npcUseWild();
                            replaceMiddleCard(i, "npc");
                            break;
                        case "wildDraw4":
                            npcUseWild();
                            replaceMiddleCard(i, "npc");
                            drawPlayerCard();
                            drawPlayerCard();
                            drawPlayerCard();
                            drawPlayerCard();
                            break;
                    }
                    checkPossible = true;
                    return;
                }

                if (npcHand.get(i).color.equals(middleColor) || npcHand.get(i).value.equals(middleCard.value)) {
                    switch (npcHand.get(i).value) {
                        case "draw2":
                            replaceMiddleCard(i, "npc");
                            drawPlayerCard();
                            drawPlayerCard();
                            break;
                        case "skip":
                            replaceMiddleCard(i, "npc");
                            npcMove();
                            break;
                        case "reverse":
                            replaceMiddleCard(i, "npc");
                            npcMove();
                            break;
                        default:
                            replaceMiddleCard(i, "npc");
                            break;
                    }
                    checkPossible = true;
                    replaceMiddleCard(i, "npc");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.print("");
            }

            if (!checkPossible) {
                drawNPCCard();
            }

            // Test code
            System.out.println("npc play" + i + checkPossible);
        }
    }

    public void npcUseWild() {
        String[] colors = {"blue", "green", "red", "yellow"};
        middleColor = colors[rand.nextInt(4)];
    }
}
