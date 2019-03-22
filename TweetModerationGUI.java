import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TweetModerationGUI extends Application {
    ArrayList<Tweet> tweets;
    ArrayList<User> users;
    static long NumberUsers = 0;
    static long NumberTweets = 0;
    String parse = "All";  //parse can be "All" or "Eng" or "Other"

    protected BorderPane getBorderPane(ArrayList<User> users) {


        Slider slider = new Slider(1,800,40);
//        slider.setMin(0);
//        slider.setMax(800);
//        slider.setValue(40);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                NumberUsers= Math.round(newValue.doubleValue());
                System.out.println(NumberUsers);
            }
        });
        Slider slider2 = new Slider();
        slider2.setMin(1);
        slider2.setMax(1200000); // Need to make sure this part works!!! Why this part doesnt work and the users do work?
        slider2.setValue(40);
        slider2.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(10000);
        slider2.setMinorTickCount(5);
        slider2.setBlockIncrement(10);

        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                NumberTweets= Math.round(newValue.doubleValue());
                System.out.println(NumberTweets);
            }
        });

        RadioButton Eng =new RadioButton("English");
        RadioButton Other =new RadioButton("Other");
        RadioButton All =new RadioButton("All");
        HBox buttons = new HBox(Eng,Other, All);

        Button Cancel = new Button("Cancel");
        Cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Cancel");
                Platform.exit();
            }
        });

        Button Parse = new Button("Parse");
        Parse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Parse");
                parseUsersAndTweets();
            }
        });
        HBox buttons1 = new HBox(Parse, Cancel);

        ToggleGroup group = new ToggleGroup();
        Eng.setToggleGroup(group);
        Other.setToggleGroup(group);
        All.setToggleGroup(group);
        All.setSelected(true);


        Eng.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Eng");
                parse="Eng";
            }
        });

        Other.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Other");
                parse="Other";
            }
        });

        All.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("All");
                parse="All";
            }
        });



        VBox sliders = new VBox(20,slider, slider2,buttons, buttons1);

        BorderPane pane = new BorderPane();
        Text text = new Text("Loaded " + users.size() + " users; 12 tweets");
        ArrayList<Pane> content = new ArrayList<Pane>();
        content.add(new Pane());
        content.add(new Pane());
        content.add(new Pane());
        ListView<Pane> lv = new ListView<>(
            FXCollections.observableArrayList(content));
        pane.setTop(sliders);
        pane.setBottom(text);
        pane.setCenter(new ScrollPane(lv));
        return pane;
    }

    public void parseUsersAndTweets(){
        try{
            tweets = TweetParser.parseTweets("iranian_tweets_csv_hashed.csv");
            users = UserParser.parseUsers("iranian_users_csv_hashed.csv");
            System.out.println(tweets.size() + " tweets parsed");
            System.out.println(users.size() + " users parsed");
            for(Tweet tweet: tweets){
                for(User user: users)
                    if(tweet.getuserid().equals(user.getuserid())){
                        user.addTweet(tweet);
                        break;
                    }
            }
        }catch(Exception e){
            System.out.println("file error: "+ e.getClass().getCanonicalName());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        parseUsersAndTweets();
        Scene scene = new Scene(getBorderPane(users), 200, 200);
        primaryStage.setTitle("TweetModerator Demo");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}