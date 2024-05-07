package edu.sdccd.cisc191;

import edu.sdccd.cisc191.Game.Scenes.GameTimer;
import edu.sdccd.cisc191.Game.Scenes.ProgressScenes;
import edu.sdccd.cisc191.Game.Scenes.SceneController;
import edu.sdccd.cisc191.Game.SinglyLinkedList;
import edu.sdccd.cisc191.Leaderboard.database.LeaderboardRepo;
import edu.sdccd.cisc191.Leaderboard.database.Player;
import edu.sdccd.cisc191.Leaderboard.services.LeaderboardService;
import edu.sdccd.cisc191.Leaderboard.sorting.BinarySearchTree;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import static javafx.application.Application.launch;

@SpringBootApplication
public class RockyRush extends Application {

    public ConfigurableApplicationContext springContext;
    public static List<Player> players;
    private static BinarySearchTree bst = new BinarySearchTree();

    protected static edu.sdccd.cisc191.Game.Player adventurer = new edu.sdccd.cisc191.Game.Player();
    protected static Stage currentStage;
    protected static Label timerLabel = new Label();
    protected static Timeline timer;
    protected static ArrayList<String> games = new ArrayList<>();
    public static SinglyLinkedList<Image> backgrounds = new SinglyLinkedList();
    protected static int count = 0;

    @Override
    public void start(Stage primaryStage) {

        try {

            LeaderboardService leaderboardService = springContext.getBean(LeaderboardService.class);

            ProgressScenes.randomizeGameOrder();
            ProgressScenes.setBackground();
            currentStage = primaryStage;
            SceneController.createIntroScreen();
            currentStage.setResizable(false);
            currentStage.show();
            GameTimer timer = new GameTimer();
            timer.runTimer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
    }

    @Override
    public void init() throws Exception {
        springContext  = SpringApplication.run(RockyRush.class);

    }

    @Bean
    static CommandLineRunner commandLineRunner(LeaderboardRepo player) {
        return args -> {
            Player player1 = new Player(1, "Kaden", "3:23");
            Player player2 = new Player(2, "Ekin", "9:56");
            player.save(player1);
            player.save(player2);

            players = player.findAll();
        };
    }

    public static VBox readFromLeaderboard() {

        VBox fastestTimesBox = new VBox();
        fastestTimesBox.setLayoutX(483);
        fastestTimesBox.setLayoutY(60);
        fastestTimesBox.setPrefSize(517, 500);
        fastestTimesBox.setStyle("-fx-background-color: #4a6741;");
        fastestTimesBox.setSpacing(10);
        fastestTimesBox.setPadding(new Insets(10));

        bst = new BinarySearchTree();

        try {

            for(Player player : players) {

                String playerName = player.getPlayerName();
                String playerTime = player.getPlayerTime();

                // add player info to tree
                bst.root = bst.add(bst.root, playerName, playerTime);
            }


            bst.printInorder(bst.root, fastestTimesBox);

            return fastestTimesBox;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToLeaderboard() {

    }
}
