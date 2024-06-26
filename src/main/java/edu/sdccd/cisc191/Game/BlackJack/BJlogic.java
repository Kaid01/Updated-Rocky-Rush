package edu.sdccd.cisc191.Game.BlackJack;

import edu.sdccd.cisc191.Game.GameButton;
import edu.sdccd.cisc191.Game.Scenes.ProgressScenes;
import edu.sdccd.cisc191.Game.Scenes.SceneController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class BJlogic extends SceneController {

    private CardsDeck deck = new CardsDeck();
    private Hand dealer, player;
    private Text message = new Text();
    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
    private HBox dealerCards = new HBox(20);
    private HBox playerCards = new HBox(20);
    private GameButton exitButton = new GameButton("Exit", 135, 70, 36);
    private GameButton btnPlay = new GameButton("Play", 157, 70, 36);


    /**
     * creates the main content of the game that appears on the screen
     */
    public void createContent() {
        dealer = new Hand(dealerCards.getChildren());
        player = new Hand(playerCards.getChildren());

        Pane root = new Pane();
        root.setPrefSize(1000, 700);
        root.setBackground(ProgressScenes.getBackground());

        Label titleLabel = new Label("Blackjack");
        titleLabel.setStyle("-fx-background-color: #4a6741; -fx-background-radius: 0 0 50% 50%;");
        titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setFont(new Font("Elephant", 48));
        titleLabel.setPrefWidth(574);
        titleLabel.setPrefHeight(85);
        titleLabel.setLayoutX(213);

        Text dealerScore = new Text("Dealer: ");
        dealerScore.setStyle("-fx-text-fill: white; -fx-font-family: Elephant; -fx-font-size: 18");
        Text playerScore = new Text("Player: ");
        playerScore.setStyle("-fx-text-fill: white; -fx-font-family: Elephant; -fx-font-size: 18");
        message.setStyle(("-fx-text-fill: white; -fx-font-family: Elephant; -fx-font-size: 18"));

        VBox cardsHolder = new VBox();
        cardsHolder.setAlignment(Pos.CENTER);
        cardsHolder.getChildren().addAll(dealerCards, playerCards);
        cardsHolder.setPrefSize(500, 175);
        cardsHolder.setPadding(new Insets(0, 0, 0, 200));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutX(146);
        vBox.setLayoutY(288);
        vBox.setPrefWidth(709);
        vBox.setPrefHeight(327);
        vBox.setStyle("-fx-border-color: #6F4E37; -fx-border-width: 20px; -fx-border-radius: 14%; -fx-background-radius: 29%; -fx-background-color: #4a6741;");
        vBox.getChildren().addAll(dealerScore, cardsHolder, message, playerScore);

        btnPlay.setLayoutX(213);
        btnPlay.setLayoutY(623);
        btnPlay.setOnAction(event -> {
            startNewGame();
        });

        GameButton btnHit = new GameButton("Hit", 157, 70, 36);
        btnHit.setLayoutX(422);
        btnHit.setLayoutY(623);
        btnHit.setOnAction(event -> {
            player.takeCard(deck.drawCard());
        });

        GameButton btnStand = new GameButton("Stand", 157, 70, 36);
        btnStand.setLayoutX(630);
        btnStand.setLayoutY(623);
        btnStand.setOnAction(event -> {
            while (dealer.valueProperty().get() < 17) {
                dealer.takeCard(deck.drawCard());
            }
            endGame();
        });

        exitButton.setVisible(false);
        exitButton.setLayoutX(850);
        exitButton.setLayoutY(623);
        exitButton.setOnAction(e -> {
            createMainScreen();
        });

        ImageView imageView = new ImageView(new Image("CharacterImages/gambler.png"));
        imageView.setLayoutX(309);
        imageView.setLayoutY(43);
        imageView.setFitWidth(383);
        imageView.setFitHeight(265);

        // BIND PROPERTIES
        btnPlay.disableProperty().bind(playable);
        btnHit.disableProperty().bind(playable.not());
        btnStand.disableProperty().bind(playable.not());
        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));

        player.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });

        dealer.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });

        root.getChildren().addAll(titleLabel, vBox, btnStand, btnPlay, btnHit, exitButton, imageView);
        currentStage.setScene(new Scene(root));

    }

    /**
     * code to start new game of blackjack
     */
    private void startNewGame() {
        playable.set(true);
        message.setText("");

        deck.refill();

        dealer.reset();
        player.reset();

        dealer.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
    } //end startNewGame()

    /**
     * code to end blackjack game when someone wins
     */
    private void endGame() {
        playable.set(false);

        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();
        String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;

        // the order of checking is important
        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue || (dealerValue < 21 && dealerValue > playerValue)) {
            winner = "DEALER";
            exitButton.setVisible(true);
            btnPlay.disableProperty().bind(playable.not());
            btnPlay.setOpacity(0.5);
            updateLosses();
        } else {
            winner = "PLAYER";
            exitButton.setVisible(true);
            btnPlay.disableProperty().bind(playable.not());
            btnPlay.setOpacity(0.5);
            ProgressScenes.changeScene();
        }

        message.setText(winner + " WON");
    } //end endGame()
}

