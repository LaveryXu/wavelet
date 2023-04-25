/* implement a web server (like NumberServer.java) that tracks a list of strings.
 * It should support:
    1. a path for adding a new string to the list,
    2. a path for querying the list of strings,
    3. returning a list of all strings that have a given substring.
P.S. This file's code is the same as NumberServer.java, excluding the file name, some comments, and a different variable name etc..
*/
import java.io.IOException;
import java.net.URI;

class JunHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Jun's Number: %d", num); // format() prints out to the web page
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath()); // System.out.println() prints out to the terminal
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new JunHandler());
    }
}