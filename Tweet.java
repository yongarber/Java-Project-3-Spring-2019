public class Tweet{
    private long tweetid; // index[0]
    private String userid; // index[1]
    private String tweet_text; // index[12]
    private String tweet_time;// index[13]
    private long quote_count;// index[23]
    private long reply_count;// index[24]
    private long like_count; // index[25]
    private long retweet_count; // index[26]

    public Tweet(long tweetid, String userid,String tweet_text,String tweet_time,long quote_count,long reply_count,long like_count,long retweet_count){
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
    public long getquote_count(){
        return quote_count;
    }
    public long getreply_count(){
        return reply_count;
    }
    public long getlike_count(){
        return like_count;
    }
    public long getretweet_count(){
        return retweet_count;
    }
  }