import java.util.ArrayList;

public class User {
  private String userid;
  private ArrayList<Tweet> tweets;

  public User (String userid){
    this.userid = userid;
    tweets = new ArrayList<Tweet>();
  }

  public void addTweet(Tweet tweet){
    tweets.add(tweet);
  }

  public ArrayList<Tweet> getTweets(){
    return tweets;
  }

  public String getuserid(){
    return userid;
  }
}