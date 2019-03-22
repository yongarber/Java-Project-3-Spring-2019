import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.math.BigInteger;

public class TweetParser{
  /**
   * Take the name of a comma-separated value (.csv) file in the local directory and parse the contents for tweets. The tweets have an expected structure of "Topic", "Sentiment", "TweetId", "TweetDate", "TweetText"
   * @param  filename the name of a comma-separated value (.csv) file in the local directory
   * @return          An array of parsed Message objects
   */
  public static ArrayList<Tweet> parseTweets(String filename) throws Exception {
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    Scanner scanner = new Scanner(new File(filename), "UTF-8");
    String line = scanner.nextLine();//the first line is field headers, we do not want that.
    System.out.println(line);
    int i = 0;
    while(scanner.hasNextLine() && i < TweetModerationGUI.NumberTweets){ //this i < 10000 is just to help you test, so that it doesn't take too long to run each time. you should eventually move towards reading in the entire file.
      line = scanner.nextLine();
      String[] fields = line.split("\",\"");
      fields[0] = fields[0].substring(1);
      try{
        long l = Long.parseLong(fields[0]);
//        BigInteger Qcount = new BigInteger(fields[23]);
//        BigInteger Rcount = new BigInteger(fields[24]);
//        BigInteger Lcount = new BigInteger(fields[25]);
//        BigInteger Recount = new BigInteger(fields[26]);
        tweets.add(new Tweet(l, fields[1], fields[12],fields[13], fields[23], fields[24], fields[25], fields[26]));
        i++; // I checked and it prints nulls for every tweet it adds??? question for the professor.
      }
      catch(Exception e){
        System.out.println("file error: "+ e.getClass().getCanonicalName());
            System.out.println(e.getMessage());
      }
    }
    return tweets;
  }
}