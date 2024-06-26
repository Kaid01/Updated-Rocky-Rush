package edu.sdccd.cisc191.Game.aFinalBossBattle;

import edu.sdccd.cisc191.Game.Scenes.ProgressScenes;
import edu.sdccd.cisc191.Game.Scenes.SceneController;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.Random;

public class FightingStage extends SceneController {

    private static int bossHealth = 450;
    private static ProgressBar bossHealthBar = new ProgressBar(1.0);
    private static Label bossText = new Label();;
    private static FadeTransition fadeIn, fadeOut;
    protected static RockyPlayer rockyPlayer = new RockyPlayer();
    private static ImageView rockyImage = new ImageView();
    private static ImageView elMonoImage = new ImageView();

    //buttons
    private static Button fightButton = new Button("FIGHT!");
    private static Button runButton = new Button("RUN...");
    private static GridPane mainMenu = new GridPane();
    private static GridPane skillsMenu = new GridPane();
    private static GridPane snacksMenu = new GridPane();

    private static PauseTransition pause = new PauseTransition(Duration.seconds(1));


    /**
     * creates the boss battle fighting stage
     */
    public void createFight() {
        Pane root = new Pane();
        root.setPrefSize(1000, 700);
        root.setBackground(ProgressScenes.getBackground());

        //el monos health bar
        bossHealthBar.setLayoutX(186);
        bossHealthBar.setLayoutY(61);
        bossHealthBar.setPrefSize(628, 20);
        bossHealthBar.setStyle("-fx-border-radius: 20%; -fx-control-inner-background: white; -fx-accent: #800020; -fx-border-color: #800020;");


        //boss name
        Label elMonoBoss = new Label("EL MONO");
        elMonoBoss.setFont(Font.font("Elephant", FontWeight.BOLD, 48));
        elMonoBoss.setTextFill(Color.BLACK);
        elMonoBoss.setAlignment(Pos.CENTER);
        elMonoBoss.setPrefSize(628, 69);
        elMonoBoss.setLayoutX(186);
        elMonoBoss.setLayoutY(2);

        //boss image
        elMonoImage = new ImageView(new Image("CharacterImages/elMonoSuper.png"));
        elMonoImage.setFitWidth(500);
        elMonoImage.setFitHeight(500);
        elMonoImage.setLayoutX(250);
        elMonoImage.setLayoutY(100);

        //boss text
        bossText = new Label("You may have defeated my domain...\nbut let's see how you fair up against \nme at my best.");
        bossText.setFont(Font.font("Elephant", 24));
        bossText.setTextFill(Color.WHITE);
        bossText.setPrefSize(468, 132);
        bossText.setAlignment(Pos.CENTER);
        bossText.setStyle("-fx-background-color: #4a6741;");
        bossText.setLayoutX(267);
        bossText.setLayoutY(352);

        //rocky image
        Rectangle rectangle1 = new Rectangle(200, 250, Color.web("#355e3b"));
        rectangle1.setLayoutX(14);
        rectangle1.setLayoutY(418);
        Rectangle rectangle2 = new Rectangle(169, 158, Color.WHITE);
        rectangle2.setLayoutX(30);
        rectangle2.setLayoutY(420);
        rockyImage = new ImageView(new Image("CharacterImages/rockyProfile.png"));
        rockyImage.setFitWidth(150);
        rockyImage.setFitHeight(200);
        rockyImage.setLayoutX(39);
        rockyImage.setLayoutY(378);

        ImageView heart = new ImageView(new Image("CharacterImages/heart.png"));
        heart.setFitWidth(37);
        heart.setFitHeight(37);
        heart.setLayoutX(22);
        heart.setLayoutY(584);

        ImageView manaImage = new ImageView(new Image("CharacterImages/mana.png"));
        manaImage.setFitWidth(22);
        manaImage.setFitHeight(31);
        manaImage.setLayoutX(29);
        manaImage.setLayoutY(630);

        //fight and run buttons
        runButton.setFont(Font.font("Elephant", FontWeight.BOLD, 36));
        runButton.setTextFill(Color.WHITE);
        runButton.setStyle("-fx-background-color: #355E3B;");
        runButton.setPrefSize(559, 69);
        runButton.setLayoutX(221);
        runButton.setLayoutY(598);
        runButton.setOnMouseClicked(e -> {
            fadeIn.play();
            fadeOut.play();
            bossText.setVisible(true);
            bossText.setText("Did you really think you could run? \nI won't let you escape. \nOnly one of us will make it out.");
        });

        fightButton.setFont(Font.font("Elephant", FontWeight.BOLD, 36));
        fightButton.setTextFill(Color.WHITE);
        fightButton.setStyle("-fx-background-color: #355E3B; -fx-border-radius: 20%;");
        fightButton.setPrefSize(559, 69);
        fightButton.setLayoutX(221);
        fightButton.setLayoutY(519);
        fightButton.setOnMouseClicked(e -> {
            fightButton.setVisible(false);
            runButton.setVisible(false);
            mainMenu.setVisible(true);
            mainMenu.setDisable(false);
        });

        //cool fade in and out animation for boss text
        fadeIn = new FadeTransition(Duration.seconds(1), bossText);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        fadeOut = new FadeTransition(Duration.seconds(1), bossText);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(3));
        fadeOut.setOnFinished(e -> bossText.setVisible(false));
        fadeOut.play();

        rockyPlayer.updateBarsAndLabels();
        mainMenu = createCombatMenus("MainMenu");
        skillsMenu = createCombatMenus("Skills");
        snacksMenu = createCombatMenus("Snacks");

        //adding all children to scene
        root.getChildren().addAll(bossHealthBar, elMonoBoss, elMonoImage, bossText,
                                  rectangle1, rectangle2, fightButton, runButton,
                                  rockyImage, heart, manaImage,
                                    rockyPlayer.getRockyHealthBar(), rockyPlayer.getRockyManaBar(),
                                  rockyPlayer.getRockyHealthLabel(), rockyPlayer.getRockyManaLabel(),
                                  mainMenu, skillsMenu, snacksMenu);
        currentStage.setScene(new Scene(root));
    } //end createFight()

    /**
     * creates the combat menus for the boss battle
     * @param text takes in what combat menu to create
     * @return the combat menu as a grid pane
     */
    private GridPane createCombatMenus(String text) {
        //basic setup
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setLayoutX(221);
        gridPane.setLayoutY(519);
        gridPane.setPrefSize(559, 158);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setDisable(true);
        gridPane.setVisible(false);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setHgrow(Priority.SOMETIMES);
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setVgrow(Priority.SOMETIMES);
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setVgrow(Priority.SOMETIMES);
        gridPane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        gridPane.getRowConstraints().addAll(rowConstraints1, rowConstraints2);

        //adding buttons to grid pane
        if (text.equalsIgnoreCase("MainMenu")) {
            CombatButton attackButton = new CombatButton("Attack", 24);
            attackButton.setOnMouseClicked(e -> {
                Random rand = new Random();
                int attackDmg = rand.nextInt(5) + 10;

                bossHealth -= attackDmg;
                bossHealthBar.setProgress((double) bossHealth / 450);
                if (bossHealth < 0) {
                    bossHealth = 0;
                    bossHealthBar.setProgress((double) bossHealth / 450);
                }

                ImageView hitEffect = new ImageView("hiteffect.png");
                hitEffect.setLayoutX(elMonoImage.getLayoutX() - 50);
                hitEffect.setLayoutY(elMonoImage.getLayoutY() - 40);
                ((Pane) elMonoImage.getParent()).getChildren().add(hitEffect);

                mainMenu.setDisable(true);
                pause.play();
                pause.setOnFinished(event -> {
                    ((Pane) elMonoImage.getParent()).getChildren().remove(hitEffect);
                    pause.play();
                    pause.setOnFinished(event1 -> {
                        createBossAttack();
                        rockyPlayer.updateBarsAndLabels();
                        mainMenu.setDisable(false);
                        rockyPlayer.setMana(rockyPlayer.getMana() + 3);
                        if (rockyPlayer.getMana() > 30) {
                            rockyPlayer.setMana(30);
                        }
                        rockyPlayer.updateBarsAndLabels();
                    });
                });
            });

            CombatButton skillButton = new CombatButton("Skill", 24);
            skillButton.setOnMouseClicked(e -> {
                skillsMenu.setVisible(true);
                skillsMenu.setDisable(false);
                mainMenu.setVisible(false);
            });

            CombatButton snackButton = new CombatButton("Snack", 24);
            snackButton.setOnMouseClicked(e -> {
                snacksMenu.setVisible(true);
                snacksMenu.setDisable(false);
                mainMenu.setVisible(false);
            });

            CombatButton backButton = new CombatButton("Back ➪", 24);
            backButton.setOnMouseClicked(e -> {
                mainMenu.setVisible(false);
                mainMenu.setDisable(true);
                fightButton.setVisible(true);
                runButton.setVisible(true);
            });

            gridPane.add(attackButton, 1, 1);
            gridPane.add(skillButton, 2, 1);
            gridPane.add(snackButton, 1, 2);
            gridPane.add(backButton, 2, 2);
        }

        if (text.equalsIgnoreCase("Skills")) {
            CombatButton bananaBarrage = new CombatButton("Banana Barrage", 16);
            ToolTipController.createSkillTooltip(bananaBarrage, "Banana Barrage", "Hurls a series of explosive\nbananas at their enemies,\ndealing great damage.", 10);
            bananaBarrage.setOnMouseClicked(e -> {
                if (rockyPlayer.getMana() >= 10) {
                     bossHealth -= bananaBarrage.createSkillButtonUse("Banana Barrage", rockyPlayer, elMonoImage, bossHealth, pause, gridPane);
                     bossHealthBar.setProgress((double) bossHealth / 450);
                    if (bossHealth < 0) {
                        bossHealth = 0;
                        bossHealthBar.setProgress((double) bossHealth / 450);
                    }
                }
            });

            CombatButton spikyShield = new CombatButton("Spiky Shield", 16);
            ToolTipController.createSkillTooltip(spikyShield, "Spiky Shield","Creates a barrier.\nWhen used, blocks any\ndamage dealt by the\nenemy, dealing damage in\nreturn to the foe.", 5);
            spikyShield.setOnMouseClicked(e -> {
                if (rockyPlayer.getMana() >= 5) {
                    bossHealth -= spikyShield.createSkillButtonUse("Spiky Shield", rockyPlayer, elMonoImage, bossHealth, pause, gridPane);
                    bossHealthBar.setProgress((double) bossHealth / 450);
                    if (bossHealth < 0) {
                        bossHealth = 0;
                        bossHealthBar.setProgress((double) bossHealth / 450);
                    }
                }
            });

            CombatButton bananaBalm = new CombatButton("Banana Balm", 16);
            ToolTipController.createSkillTooltip(bananaBalm, "Banana Balm", "A healing skill that\nharnesses the soothing\nproperties of bananas.\nUsing banana extracts,\nheals the user for 100.", 25);
            bananaBalm.setOnMouseClicked(e -> {
                if (rockyPlayer.getMana() >= 25) {
                    bananaBalm.createSkillButtonUse("Banana Balm", rockyPlayer, elMonoImage, bossHealth, pause, gridPane);
                }
            });

            CombatButton backButton = new CombatButton("Back ➪", 16);
            backButton.setOnMouseClicked(e -> {
                skillsMenu.setVisible(false);
                skillsMenu.setDisable(true);
                mainMenu.setVisible(true);
                mainMenu.setDisable(false);
            });

            gridPane.add(bananaBarrage, 1, 1);
            gridPane.add(spikyShield, 2, 1);
            gridPane.add(bananaBalm, 1, 2);
            gridPane.add(backButton, 2, 2);
        }

        if (text.equalsIgnoreCase("Snacks")) {
            Item bananaBliss = new Item("Banana Bliss", "A delicious treat, healing \nthe player for 30 health.", 2);
            Item bananaElixir = new Item("Banana Elixir", "Crafted from the rarest\ngolden bananas, this\npotent elixir restores\n60 health to the user.", 1);
            Item mysticMelon = new Item("Mystic Melon", "A mystical fruit\nblessed by the ancient\nspirits of monkeys,\nrestoring 10 mana\nto the user.", 2);

            CombatButton bananaBlissButton = new CombatButton(bananaBliss.getName() + " x" + bananaBliss.getAmount(), 16);
            ToolTipController.createSnacksToolTip(bananaBlissButton, bananaBliss.getName(), bananaBliss.getDescription());
            bananaBlissButton.createSnackButtonUse(bananaBliss, rockyPlayer, pause, gridPane);

            CombatButton bananaElixirButton = new CombatButton(bananaElixir.getName() +" x" + bananaElixir.getAmount(), 16);
            ToolTipController.createSnacksToolTip(bananaElixirButton, bananaElixir.getName(), bananaElixir.getDescription());
            bananaElixirButton.createSnackButtonUse(bananaElixir, rockyPlayer, pause, gridPane);

            CombatButton mysticMelonButton = new CombatButton(mysticMelon.getName() + " x" + mysticMelon.getAmount(), 16);
            ToolTipController.createSnacksToolTip(mysticMelonButton, mysticMelon.getName(), mysticMelon.getDescription());
            mysticMelonButton.createSnackButtonUse(mysticMelon, rockyPlayer, pause, gridPane);

            CombatButton backButton = new CombatButton("Back ➪", 16);
            backButton.setOnMouseClicked(e -> {
                snacksMenu.setVisible(false);
                snacksMenu.setDisable(true);
                mainMenu.setVisible(true);
                mainMenu.setDisable(false);
            });

            gridPane.add(bananaBlissButton, 1, 1);
            gridPane.add(bananaElixirButton, 2, 1);
            gridPane.add(mysticMelonButton, 1, 2);
            gridPane.add(backButton, 2, 2);
        }

        return gridPane;
    } //end createCombatMenus()


    public static int createBossAttack() {
        Random random = new Random();
        int damage = random.nextInt(10) + 10;

        if (bossHealth > 0) {
            rockyPlayer.setHealth(rockyPlayer.getHealth() - damage);

            //create red hit effect
            Rectangle redOverlay = new Rectangle(rockyImage.getFitWidth() + 20, rockyImage.getFitHeight() - 40, Color.rgb(255, 0, 0, 0.5));
            redOverlay.setTranslateX(rockyImage.getLayoutX() - 10);
            redOverlay.setTranslateY(rockyImage.getLayoutY() + 40);
            ((Pane) rockyImage.getParent()).getChildren().add(redOverlay);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), redOverlay);
            fadeOut.setFromValue(0.5);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> ((Pane) rockyImage.getParent()).getChildren().remove(redOverlay));
            fadeOut.play();

        } else {
            fadeIn.play();
            bossText.setVisible(true);
            bossText.setText("No...How is this possible?\nI've been defeated?");
            fadeIn.setOnFinished(e -> {
                mainMenu.setDisable(true);
                snacksMenu.setDisable(true);
                skillsMenu.setDisable(true);
            });
            fadeOut.play();
            fadeOut.setOnFinished(event -> bossText.setVisible(false));

            pause = new PauseTransition(Duration.seconds(4));
            pause.play();
            pause.setOnFinished(e -> {
                fadeIn.play();
                bossText.setVisible(true);
                bossText.setText("Perhaps I've misjudged you, Rocky.\nI'm sorry. You're truly strong.\nYou can go see your lover. ");
            });

            pause = new PauseTransition(Duration.seconds(10));
            pause.play();
            pause.setOnFinished(e -> {
                createEndingScreen();
            });
        }
        return damage;
    } //end createBossAttack()
}
