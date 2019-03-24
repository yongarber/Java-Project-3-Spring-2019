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
    static ArrayList<Tweet> tweets;
    static ArrayList<User> users;
    static long NumberUsers = 0;
    static long NumberTweets = 0;
    String parse = "All";  //parse can be "All" or "Eng" or "Other"
    Stage stageTweets = new Stage();
    Stage stage = new Stage();

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
        slider2.setValue(1);
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
         Label nameslider = new Label("Users Slider");
         Label nameslider2 = new Label("Tweets Slider");


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
                getBorderPane(users);
            }
        });
        Button ShowUser = new Button("Show Users");
        ShowUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Show Users");
                stage.show();
            }
        });
        HBox buttons1 = new HBox(Parse, Cancel,ShowUser);

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

        // the pane for the users
        Pane canvasTweets = new Pane();
        canvasTweets.setPrefSize(200,200);


        //new stage for users
        stageTweets.setTitle("Tweets");
        stageTweets.setScene(new Scene(canvasTweets, 350, 450));


        //pane for each user
        Pane EachUser = new Pane();
        //Label userId = new Label(User.getuserid());


        // the pane for the users
        ScrollPane canvas = new ScrollPane();
        canvas.setPrefSize(200,200);
        Label userspane = new Label (NumberUsers +"  users");
        VBox UsersPane  = new VBox();
        UsersPane.getChildren().add(userspane);
        for(int i=0; i<= NumberUsers; i++){
            Label userspane1 = new Label (i +"  users");
            Button Tweets = new Button("Show Tweets");
            UsersPane.getChildren().addAll(userspane1, Tweets);
            Tweets.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Show Tweets");
                    stageTweets.show();
                }
            });
        }
        canvas.setContent(UsersPane);
        //new stage for users
        stage.setTitle("Users");
        stage.setScene(new Scene(canvas, 350, 450));




        VBox sliders = new VBox(20,nameslider,slider,nameslider2, slider2,buttons, buttons1);

        BorderPane pane = new BorderPane();
        Text text = new Text("Loaded " + users.size() + " users; " +tweets.size()+" tweets");
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



    @Override
    public void start(Stage primaryStage) {
        parseUsersAndTweets();
        Scene scene = new Scene(getBorderPane(users), 500, 350);
        primaryStage.setTitle("TweetModerator Demo");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}