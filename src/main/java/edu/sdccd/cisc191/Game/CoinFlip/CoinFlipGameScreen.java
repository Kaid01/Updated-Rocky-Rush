package edu.sdccd.cisc191.Game.CoinFlip;

import edu.sdccd.cisc191.Game.GameButton;
import edu.sdccd.cisc191.Game.GameLabel;
import edu.sdccd.cisc191.Game.Scenes.ProgressScenes;
import edu.sdccd.cisc191.Game.Scenes.SceneController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class CoinFlipGameScreen extends SceneController {

    //create coin sides
    protected static boolean heads = false, tails = false;
    protected static ImageView coinImage = new ImageView(new Image("coin.png"));
    protected static GameLabel titleLabel = new GameLabel("COIN FLIP", 462, 85, 48);
    protected static GameLabel rockyText = new GameLabel("What should we do!!", 247, 91, 18);
    protected static GameLabel businessText = new GameLabel("Foolish monkey!", 247, 91, 18);
    protected static GameButton exitButton = new GameButton("Exit", 136, 69, 24);

    /**
     * creates the screen for coin flip game
     */
    public void createCoinFlipScreen() {

        Pane root = new Pane();
        root.setPrefSize(1000, 700);
        root.setBackground(ProgressScenes.getBackground());

        titleLabel.setLayoutX(269);
        root.getChildren().add(titleLabel);

        ImageView rockyProfile = new ImageView(new Image("CharacterImages/rockyProfile.png"));
        rockyProfile.setFitWidth(275);
        rockyProfile.setFitHeight(336);
        rockyProfile.setLayoutY(364);
        root.getChildren().add(rockyProfile);

        rockyText.setLayoutX(14);
        rockyText.setLayoutY(305);
        root.getChildren().add(rockyText);

        ImageView businessmanImageView = new ImageView(new Image("CharacterImages/businessman.png"));
        businessmanImageView.setFitWidth(247);
        businessmanImageView.setFitHeight(297);
        businessmanImageView.setLayoutX(745);
        businessmanImageView.setLayoutY(403);
        root.getChildren().add(businessmanImageView);

        businessText.setLayoutX(745);
        businessText.setLayoutY(264);
        root.getChildren().add(businessText);

        exitButton.setLayoutX(429);
        exitButton.setLayoutY(630);
        exitButton.setVisible(false);
        exitButton.setOnAction(e -> {
            coinImage.setImage(new Image("coin.png"));
            createMainScreen();
            titleLabel.setText("COIN FLIP");
            heads = false;
            tails = false;
        });
        root.getChildren().add(exitButton);

        Button flipButton = new GameButton("FLIP", 136, 69, 24);
        flipButton.setDisable(true);
        flipButton.setLayoutX(429);
        flipButton.setLayoutY(560);
        flipButton.setOnAction(e -> {
            CoinFlipAnimation.flipCoin();
            flipButton.setDisable(true);
        });
        root.getChildren().add(flipButton);

        GameButton headsButton = new GameButton("HEADS", 136, 69, 24);
        GameButton tailsButton = new GameButton("TAILS", 136, 69, 24);

        headsButton.setLayoutX(289);
        headsButton.setLayoutY(476);
        headsButton.setOnMouseClicked(e -> {
            heads = true;
            flipButton.setDisable(false);
            headsButton.setDisable(true);
            tailsButton.setDisable(true);
            rockyText.setText("Alright! Let's go \nwith heads!");
            businessText.setText("Hah! Then I'll \ngo with tails!");
        });
        root.getChildren().add(headsButton);

        tailsButton.setLayoutX(559);
        tailsButton.setLayoutY(476);
        tailsButton.setOnMouseClicked(e -> {
            tails = true;
            flipButton.setDisable(false);
            tailsButton.setDisable(true);
            headsButton.setDisable(true);
            rockyText.setText("Alright! Let's go \nwith tails!");
            businessText.setText("Hah! Then I'll \ngo with heads!");
        });
        root.getChildren().add(tailsButton);

        Polygon leftArrow = new Polygon(-31, 31, -61, -10, 4.5, -10);
        leftArrow.setLayoutX(161);
        leftArrow.setLayoutY(406);
        leftArrow.setFill(Color.web("#4a6741"));
        leftArrow.setStroke(Color.web("#4a6741"));
        leftArrow.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        leftArrow.setStyle("-fx-background-radius: 20%");
        root.getChildren().add(leftArrow);

        coinImage.setFitWidth(390);
        coinImage.setFitHeight(354);
        coinImage.setLayoutX(305);
        coinImage.setLayoutY(98);
        root.getChildren().add(coinImage);

        Polygon rightArrow = new Polygon(-31, 31, -61, -10, 4.5, -10);
        rightArrow.setLayoutX(892);
        rightArrow.setLayoutY(365);
        rightArrow.setFill(Color.web("#4a6741"));
        rightArrow.setStroke(Color.web("#4a6741"));
        rightArrow.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rightArrow.setStyle("-fx-background-radius: 20%");
        root.getChildren().add(rightArrow);

        currentStage.setScene(new Scene(root));
    } //end createCoinFlipScreen()
}
