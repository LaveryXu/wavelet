import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/*
 * a web server called StringServer that supports the path and behavior described below.
 * It should keep track of a single string that gets added to by incoming requests.
 */

class StringHandler implements URLHandler {
    String x = ""; // "" is an empty String, an empty String is not equivalent to a null reference. QUESTION: What's stored in an empty String like ""? Is it a referece or what?
    
    public String handleRequest(URI url) {
        // The requests should look like this: add-message?s=<string>
        if (url.getPath().equals("/")) {
            return String.format("Copy '/add-message?s=' (excluding ''), paste it after this web page's url's port number in the address box above, delete any other input after '/add-message?s=', and then enter the string you wanna add.");
        }
        else if (url.getPath().equals("/add-message")) {
            if(url.getQuery().contains("s")) { // Because now I have no time to test wether url.getQuery() returns url's query with or without ?, I use contains() instead. TODO: 日后优化
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    x += parameters[1];
                    x += "\n";
                    return String.format(x);
                }
            }
        }

        // System.out.println("Your illegal url: " + url.toString()); // 日后优化
        return "404 NOT FOUND.";
    }
}

public class StringServer {
    public static void main (String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int portNum = Integer.parseInt(args[0]);
        Server.start(portNum, new StringHandler());

        // System.out.println(new URI().toString()); //用于日后探索Java的URI class。
    }
}

/* new stuff learned:
 * bash terminal recognize the first file name entered after `java` as the file that has the main method.
 * compile all files that depend on each other to run everytime you change one of the file and run the file with main method in it
 */