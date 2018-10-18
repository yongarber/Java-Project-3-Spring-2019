import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import java.util.ArrayList;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;

public class TweetModerationGUI extends Application {
    ArrayList<Tweet> tweets;
    ArrayList<User> users;

    protected BorderPane getBorderPane(ArrayList<User> users) {
        BorderPane pane = new BorderPane();
        Text text = new Text("Loaded " + users.size() + " users; 12 tweets");
        ArrayList<Pane> content = new ArrayList<Pane>();
        content.add(new Pane());
        content.add(new Pane());
        content.add(new Pane());
        ListView<Pane> lv = new ListView<>(
            FXCollections.observableArrayList(content));
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