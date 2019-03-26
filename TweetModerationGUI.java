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
    ArrayList<Tweet> tweets;   // Here I add all the variables to which I want to have a bigger scope.
    ArrayList<User> users;
    //ArrayList<User> Users;
    static long NumberUsers = 0; // Those two static long are the variables that hold the value from the slider.
    static long NumberTweets = 0;
    String parse = "All";  //parse can be "All" or "Eng" or "Other"
    Stage stageTweets = new Stage();// Those two stages initiate the stages for the users window and the tweets window.
    Stage stage = new Stage();

    public void parseUsersAndTweets(){
        try{
            tweets = TweetParser.parseTweets("iranian_tweets_csv_hashed.csv");
            users = UserParser.parseUsers("iranian_users_csv_hashed.csv");
            //Users = UserParser.parseUsers("iranian_users_csv_hashed.csv");
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


        Slider slider = new Slider(1,800,40); // here I make the slider for the users, between 1 to 800 with initial value of 1.
//        slider.setMin(0);
//        slider.setMax(800);
//        slider.setValue(40);
        slider.setValue(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        slider.valueProperty().addListener(new ChangeListener<Number>() { //this is the handler for the slider which returns the rounded value we get from the slider.
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                NumberUsers= Math.round(newValue.doubleValue());
                System.out.println(NumberUsers);
            }
        });
        Slider slider2 = new Slider();  // here I make the slider for the users, between 1 to 1200000 with initial value of 1.
        slider2.setMin(1);
        slider2.setMax(1200000);
        slider2.setValue(1);
        slider2.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(10000);
        slider2.setMinorTickCount(5);
        slider2.setBlockIncrement(10);

        slider2.valueProperty().addListener(new ChangeListener<Number>() {  //this is the handler for the tweets slider which returns the rounded value we get from the slider.
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                NumberTweets= Math.round(newValue.doubleValue());
                System.out.println(NumberTweets);
            }
        });
         Label nameslider = new Label("Users Slider"); // I add lables for the sliders in the window
         Label nameslider2 = new Label("Tweets Slider");


        RadioButton Eng =new RadioButton("English"); // Here I make the radio buttons for the language and put them in a HBox.
        RadioButton Other =new RadioButton("Other");
        RadioButton All =new RadioButton("All");
        HBox buttons = new HBox(Eng,Other, All);

        Button Cancel = new Button("Cancel"); // cancel button with its handler that terminates the code.
        Cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Cancel");
                Platform.exit();
            }
        });
        Button Parse = new Button("Parse");// parse button with its handler that parse the users and the tweets based on the slider.
        Parse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Parse");
                parseUsersAndTweets();
                getBorderPane(users); // I added this line to refresh the users - it didnt run without it.
            }
        });
        Button ShowUser = new Button("Show Users"); // Show users button with its handler that show the users stage/window with the users info in it.
        ShowUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Show Users");
                stage.show();
            }
        });
        HBox buttons1 = new HBox(Parse, Cancel,ShowUser); // put those buttons in a HBox.

        ToggleGroup group = new ToggleGroup(); // put all the radio button in a togglegroup so only one can be picked at a time.
        Eng.setToggleGroup(group);
        Other.setToggleGroup(group);
        All.setToggleGroup(group);
        All.setSelected(true);


        Eng.setOnAction(new EventHandler<ActionEvent>() {  // the three handlers under here are the radio button handlers ready to be used when parsing based on the language.
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

        // the pane for the tweets
        Pane canvasTweets = new Pane(); // this is the template for the tweets pane.
        canvasTweets.setPrefSize(200,200);


        //new stage for tweets
        stageTweets.setTitle("Tweets"); // this is the title and size of the window.
        stageTweets.setScene(new Scene(canvasTweets, 350, 450));


        //pane for each user
        Pane EachUser = new Pane();// this is the template for the users pane.
        //Label userId = new Label(User.getuserid());


        // the pane for the users
        ScrollPane canvas = new ScrollPane(); // adding the number of users at the top and adding a label and a button for each user. because of the issue with the users arraylist I coudnt add the getters without getting a compile error.
        canvas.setPrefSize(200,200);
        Label userspane = new Label (NumberUsers +"  users");
        VBox UsersPane  = new VBox();
        UsersPane.getChildren().add(userspane);
        for(int i=0; i<= NumberUsers; i++){
            Label userspane1 = new Label (i +"  users");
            Button Tweets = new Button(i + "  Show Tweets");
            //Label location = new Label ("Location: "+ users.get(i).user_reported_location());
            Tweets.setId(i+"");
            UsersPane.getChildren().addAll(userspane1, Tweets);
            Tweets.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) { // this is the handler for the show tweets button that opens the tweets stage/window
                    System.out.println("Show Tweets");
                    System.out.println(Tweets.getId());
                    stageTweets.show();
                }
            });
        }
        canvas.setContent(UsersPane);
        //new stage for users
        stage.setTitle("Users");//setting up the stage for users.
        stage.setScene(new Scene(canvas, 350, 450));




        VBox sliders = new VBox(20,nameslider,slider,nameslider2, slider2,buttons, buttons1);// I put all the nodes in a VBox for the first window.

        BorderPane pane = new BorderPane();
        Text text = new Text("Loaded " + users.size() + " users; " +tweets.size()+" tweets");
        ArrayList<Pane> content = new ArrayList<Pane>();
        content.add(new Pane());
        content.add(new Pane());
        content.add(new Pane());
        ListView<Pane> lv = new ListView<>(
            FXCollections.observableArrayList(content));
        pane.setTop(sliders);// I put the VBox in the top pane for display.
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