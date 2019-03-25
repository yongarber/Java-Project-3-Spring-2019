import java.util.ArrayList;

public class User {
  private String userid; //index[0]
  private ArrayList<Tweet> tweets;
  private String user_display_name;//index[1]
  private String user_reported_location; //index[3]
  private String user_profile_description; //index[4]
  private String user_profile_url; //index[5]
  private String follower_count; //index[6]
  private String following_count; //index[7]


  public User (String userid,String user_display_name,String user_reported_location,String user_profile_description,String user_profile_url,String follower_count,String following_count){
    this.userid = userid;
    tweets = new ArrayList<Tweet>();
    this.user_display_name = user_display_name;
    this.user_reported_location = user_reported_location;
    this.user_profile_description = user_profile_description;
    this.user_profile_url = user_profile_url;
    this.follower_count = follower_count;
    this.following_count = following_count;
  }

  public void addTweet(Tweet tweet){
    tweets.add(tweet);
  }

  public ArrayList<Tweet> getTweets(){
    return tweets;
  }

  public String user_reported_location(){
    return user_reported_location;
  }

  public String getuser_display_name(){
    return user_display_name;
  }

  public String getuser_profile_description(){
    return user_profile_description;
  }

  public String getuser_profile_url(){
    return user_profile_url;
  }

  public String getfollower_count(){
    return follower_count;
  }

  public String getfollowing_count(){
    return following_count;
  }

  public String getuserid(){
    return userid;
  }
  public String toString(){return "not implemented";}
}