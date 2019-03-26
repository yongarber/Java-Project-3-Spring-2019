import java.math.BigInteger;

public class Tweet{
    private long tweetid; // index[0]     //I added the data fields based on the tweets diagram in stage 6. I added the constractors and the getters as well.
    private String userid; // index[1]
    private String tweet_text; // index[12]
    private String tweet_time;// index[13]
    private String quote_count;// index[23]
    private String reply_count;// index[24]
    private String like_count; // index[25]
    private String retweet_count; // index[26]

    public Tweet(long tweetid, String userid,String tweet_text,String tweet_time,String quote_count,String reply_count,String like_count,String retweet_count){
      this.tweetid = tweetid;
      this.userid = userid;
      this.tweet_text = tweet_text;
      this.tweet_time = tweet_time;
      this.quote_count = quote_count;
      this.reply_count = reply_count;
      this.like_count = like_count;
      this.retweet_count = retweet_count;
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

    public String gettweet_text(){
        return tweet_text;
    }
    public String gettweet_time(){
        return tweet_time;
    }
    public String getquote_count(){
        return quote_count;
    }
    public String getreply_count(){
        return reply_count;
    }
    public String getlike_count(){
        return like_count;
    }
    public String getretweet_count(){
        return retweet_count;
    }
  }