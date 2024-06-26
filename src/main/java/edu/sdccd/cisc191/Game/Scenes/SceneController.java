package edu.sdccd.cisc191.Game.Scenes;

import edu.sdccd.cisc191.Game.GameButton;
import edu.sdccd.cisc191.Game.GameLabel;
import edu.sdccd.cisc191.RockyRush;
import edu.sdccd.cisc191.Game.aFinalBossBattle.ActOne;
import edu.sdccd.cisc191.Game.aFinalBossBattle.FightingStage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class SceneController extends RockyRush {

    private final static double sceneWidth = 1000.0, sceneHeight = 700.0;

    protected static GameButton gameEnd = new GameButton("You Have Won.", 500, 350, 42);

    protected static double original = 0.05;

    protected static ProgressBar progressBar = new ProgressBar(original);

    protected static int losses = 0;
    protected static ImageView heart1 = new ImageView(new Image("CharacterImages/heart.png")),
            heart2 = new ImageView(new Image("CharacterImages/heart.png")),
            heart3 = new ImageView(new Image("CharacterImages/heart.png"));


    /**
     * creates the intro of the game that asks player to choose class
     */
    public static void createIntroScreen() {
        //introduction
        Pane root = new Pane();

        ImageView background = new ImageView(new Image("Scenes/start.png"));
        background.setFitWidth(1000);
        background.setFitHeight(700);
        root.getChildren().add(background);

        GameButton playButton = new GameButton("Play", 172, 72, 18);
        playButton.setLayoutX(414);
        playButton.setLayoutY(430);
        playButton.setOnMouseClicked(e -> {
            createNamingScreen();
            timer.play();
        });
        root.getChildren().add(playButton);

        GameButton leaderboardButton = new GameButton("Leaderboard", 204, 72, 18);
        leaderboardButton.setLayoutX(398);
        leaderboardButton.setLayoutY(596);
        leaderboardButton.setOnAction(e -> createLeaderboardScreen());
        root.getChildren().add(leaderboardButton);

        GameButton exitButton = new GameButton("Exit", 172, 72, 18);
        exitButton.setLayoutX(414);
        exitButton.setLayoutY(513);
        exitButton.setOnMouseClicked(e -> Platform.exit());
        root.getChildren().add(exitButton);

        ImageView monkeyImage = new ImageView(new Image("CharacterImages/rocky.png"));
        monkeyImage.setFitWidth(375);
        monkeyImage.setFitHeight(331);
        monkeyImage.setLayoutY(369);
        root.getChildren().add(monkeyImage);

        Label titleLabel = new Label("Rocky Rush");
        titleLabel.setLayoutX(254);
        titleLabel.setLayoutY(23);
        titleLabel.setPrefSize(492, 100);
        titleLabel.setStyle("-fx-background-color: #4a6741; -fx-background-radius: 15%;");
        titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        titleLabel.setFont(new Font("Elephant", 64));
        titleLabel.setAlignment(javafx.geometry.Pos.CENTER);
        root.getChildren().add(titleLabel);

        currentStage.setScene(new Scene(root));
    } //end createIntroScreen

    /**
     * create the ask player name screen of the game
     */
    public static void createNamingScreen() {
        NamingScreen namingScreen = new NamingScreen();
        namingScreen.createScene();
    } //end createNamingScreen()

    /**
     * creates the main menu screen of the game
     */
    public static void createMainScreen() {
        if (backgrounds.head.next == null) {
            ActOne bossBattle = new ActOne();
            bossBattle.createScreen();
//        if (count == 0) {
//            FightingStage fightingStage = new FightingStage();
//            fightingStage.createFight();
        } else {
            Pane root = new Pane();
            root.setPrefSize(sceneWidth, sceneHeight);
            root.setBackground(ProgressScenes.getBackground());

            // ImageView 2
            ImageView imageView2 = new ImageView(new Image("CharacterImages/rockyProfile.png"));
            imageView2.setFitWidth(332.0);
            imageView2.setFitHeight(385.0);
            imageView2.setLayoutY(315.0);

            // Button
            GameButton progress = new GameButton("Progress", 223, 74, 24);
            progress.setLayoutX(389.0);
            progress.setLayoutY(377.0);
            progress.setOnMouseClicked(e -> {
                RandomEvent randomEvent = new RandomEvent();
                randomEvent.generateRandomEvent();
            });

            // ProgressBar
            progressBar.setPrefWidth(365.0);
            progressBar.setPrefHeight(39.0);
            progressBar.setLayoutX(318.0);
            progressBar.setLayoutY(14.0);
            progressBar.setStyle("-fx-border-radius: 30%; -fx-control-inner-background: #6F4E37; -fx-accent: #4a6741; -fx-border-color: #4a6741;");

            // ImageView 3
            ImageView imageView3 = new ImageView(new Image("goldenPalace.png"));
            imageView3.setFitWidth(113.0);
            imageView3.setFitHeight(93.0);
            imageView3.setLayoutX(677.0);
            imageView3.setLayoutY(-7.0);

            // ImageView 4
            ImageView imageView4 = new ImageView(new Image("bridge.png"));
            imageView4.setFitWidth(132.0);
            imageView4.setFitHeight(74.0);
            imageView4.setLayoutX(185.0);
            imageView4.setLayoutY(-3.0);

            gameEnd.setVisible(false);

            root.getChildren().addAll(imageView2, progress, progressBar, imageView3, imageView4, showLives(), gameEnd);
            currentStage.setScene(new Scene(root));

            if (losses == 3) {
                heart3.setImage(new Image("CharacterImages/brokenheart.png"));
                createGameOver();
            }
        }
    } //end createMainScreen()

    /**
     * show lives of player
     */
    public static GridPane showLives() {
        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(85.0);
        gridPane.setLayoutY(343.0);
        gridPane.setPrefHeight(50.0);
        gridPane.setPrefWidth(150.0);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col1.setMinWidth(10.0);
        col1.setPrefWidth(100.0);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col2.setMinWidth(10.0);
        col2.setPrefWidth(100.0);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        col3.setMinWidth(10.0);
        col3.setPrefWidth(100.0);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10.0);
        row1.setPrefHeight(30.0);
        row1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        gridPane.getRowConstraints().add(row1);

        heart1.setFitHeight(51.0);
        heart1.setFitWidth(51.0);
        heart1.setPreserveRatio(true);
        GridPane.setColumnIndex(heart1, 0);

        heart2.setFitHeight(51.0);
        heart2.setFitWidth(51.0);
        heart2.setPreserveRatio(true);
        GridPane.setColumnIndex(heart2, 1);

        heart3.setFitHeight(51.0);
        heart3.setFitWidth(51.0);
        heart3.setPreserveRatio(true);
        GridPane.setColumnIndex(heart3, 2);

        gridPane.getChildren().addAll(heart1, heart2, heart3);
        return gridPane;
    } //end showLives()

    /**
     * update player losses
     */
    public static void updateLosses() {
//        losses++;

        if (losses == 1) {
            heart1.setImage(new Image("CharacterImages/brokenheart.png"));
        }

        if (losses == 2) {
            heart2.setImage(new Image("CharacterImages/brokenheart.png"));
        }
    } //end updateLosses()

    public static void restartGame() {
        losses = 0;
        count = 0;
        createIntroScreen();
        timer.stop();
    }

    /**
     * game over screen once player loses 3 hearts
     */
    public static void createGameOver() {
        Pane root = new Pane();
        root.setPrefSize(1000, 700);
        BackgroundImage bgImage = new BackgroundImage(backgrounds.head.data, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1000, 700, false, false, false, false));
        root.setBackground(new Background(bgImage));

        ImageView imageView = new ImageView(new Image("CharacterImages/rockyDead.png"));
        imageView.setFitWidth(460);
        imageView.setFitHeight(427);
        imageView.setLayoutX(270);
        imageView.setLayoutY(129);
        root.getChildren().add(imageView);

        GameLabel label = new GameLabel("GAME OVER", 758, 122, 96);
        label.setLayoutX(121);
        label.setLayoutY(7);
        root.getChildren().add(label);

        GameButton restartButton = new GameButton("Restart", 262, 97, 48);
        restartButton.setLayoutX(223);
        restartButton.setLayoutY(556);
        restartButton.setOnMouseClicked(e -> {restartGame();});
        root.getChildren().add(restartButton);

        GameButton exitButton = new GameButton("Exit", 262, 97, 48);
        exitButton.setLayoutX(515);
        exitButton.setLayoutY(556);
        exitButton.setOnMouseClicked(e -> Platform.exit());
        root.getChildren().add(exitButton);

        currentStage.setScene(new Scene(root));
    } //end createGameOver()

    /**
     * creates ending screen of game
     */
    public static void createEndingScreen() {
        timer.stop();
        adventurer.setPlayerTime(timerLabel.getText());
        Pane root = new Pane();
        root.setPrefSize(1000, 700);
        root.setBackground(ProgressScenes.getBackground());

        ImageView rockyImage = new ImageView(new Image("CharacterImages/rockyProfile.png"));
        rockyImage.setFitWidth(360);
        rockyImage.setFitHeight(523);
        rockyImage.setLayoutX(140);
        rockyImage.setLayoutY(177);

        ImageView princessImage = new ImageView(new Image("CharacterImages/princess.png"));
        princessImage.setFitWidth(360);
        princessImage.setFitHeight(379);
        princessImage.setLayoutX(543);
        princessImage.setLayoutY(321);

        ImageView heartImage = new ImageView(new Image("CharacterImages/heart.png"));
        heartImage.setFitWidth(296);
        heartImage.setFitHeight(317);
        heartImage.setLayoutX(381);
        heartImage.setLayoutY(163);

        GameLabel thankYouLabel = new GameLabel("Thank you for playing!", 1000, 70, 64);

        GameLabel timeLabel = new GameLabel("Your finished time was: " + adventurer.getPlayerTime(), 1000, 70, 64);
        timeLabel.setLayoutY(70);

        GameButton exitButton = new GameButton("Exit", 92, 52, 12);
        exitButton.setLayoutY(340);
        exitButton.setMnemonicParsing(false);
        exitButton.setOnAction(event -> createIntroScreen());

        root.getChildren().addAll(rockyImage, princessImage, heartImage, thankYouLabel, timeLabel, exitButton);
        currentStage.setScene(new Scene(root));

        writeToLeaderboard();
    }

    public static void createLeaderboardScreen() {
        Pane root = new Pane();
        root.setBackground(ProgressScenes.getBackground());
        root.setPrefSize(1000, 700);

        ImageView rocky = new ImageView(new Image("CharacterImages/rockyProfile.png"));
        rocky.setFitHeight(442);
        rocky.setFitWidth(392);
        rocky.setLayoutX(50);
        rocky.setLayoutY(250);
        root.getChildren().add(rocky);

        GameButton backButton = new GameButton("Back", 240, 80, 36);
        backButton.setLayoutX(620);
        backButton.setLayoutY(608);
        backButton.setOnMouseClicked(e -> {
            createIntroScreen();
        });
        root.getChildren().add(backButton);

        GameLabel titleLabel = new GameLabel("Fastest Times", 517, 61, 48);
        titleLabel.setLayoutX(483);
        titleLabel.setLayoutY(0);
        root.getChildren().add(titleLabel);

        root.getChildren().add(readFromLeaderboard());

        currentStage.setScene(new Scene(root));
    }
}