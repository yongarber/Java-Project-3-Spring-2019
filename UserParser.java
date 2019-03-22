import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class UserParser{
  public static ArrayList<User> parseUsers (String filename) throws Exception{
    ArrayList<User> users = new ArrayList<User>();

    Scanner scanner = new Scanner(new File(filename), "UTF-8");
    String line = scanner.nextLine();//the first line is field headers, we do not want that.
    System.out.println(line);
    int i = 0;
    while(scanner.hasNextLine() && i < TweetModerationGUI.NumberUsers){ //this i < 100 is just to help you test, so that it doesn't take too long to run each time. you should eventually move towards reading in the entire file.
      line = scanner.nextLine();
      String[] fields = line.split("\",\"");
      fields[0] = fields[0].substring(1);
      try{
//        int followerCount = Integer.parseInt(fields[6]);
//        int followingCount = Integer.parseInt(fields[7]);
        users.add(new User(fields[0], fields[1],fields[3],fields[4],fields[5], fields[6], fields[7]));
        i++;
      }
      catch(Exception e){
        System.out.println("file error: "+ e.getClass().getCanonicalName());
            System.out.println(e.getMessage());
      }
    }

    return users;
  }
}