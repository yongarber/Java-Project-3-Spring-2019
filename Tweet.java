public class Tweet{
    private long tweetid;
    private String userid;

    public Tweet(long tweetid, String userid){
      this.tweetid = tweetid;
      this.userid = userid;
    }

    /**
     * Get the content of this message as a String
     * @return The content of this message as a String
     */
    public String toString(){
      return null;
    }

    public String getuserid(){
      return userid;
    }
  }