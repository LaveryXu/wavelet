import java.io.IOException;
import java.net.URI;

/*
 * a web server called StringServer that supports the path and behavior described below.
 * It should keep track of a single string that gets added to by incoming requests.
 */

class StringHandler implements URLHandler {
    String x = ""; // "" is an empty String. An empty String "" is not equivalent to a null reference. QUESTIONS: 1. What kind of data or what data is stored in an empty String ""? Is it a referece? Is there nothing/no data stored in an empty String? 2. How does Java encode/interprete an empty String ""?
    int portNum;

    StringHandler(int portNum) { // a constructor that updates the field portNum as the port number the user enters
        this.portNum = portNum;
    }

    /*
     * Cases to consider:
     * - when the query is:
     *      - ?
     *      - null (without ?, the url is http://localhost:<portNum>/message)
     * - when the path is
     *      - null // the path of the url is printed as "/" by System.out.println(url.getPath()), when the url is http://localhost:<portNum>/ (w / at the end) or http://localhost:<portNum> (w/ / at the end)
     * - when there are more than one = or no = in the query
     *      - consecutive =
     *      - non-consecutive =
     *      - no =
     *      - etc.
     * - when there are more than one s or no s in the query
     *      - consecutive s
     *      - non-consecutive s
     *      - no s
     *      - etc.
     * - more cases and corner cases to consider...
     */

    public String handleRequest(URI url) throws NullPointerException{ // QUESTION: This method is not called in the main method below, how does this method process the input entered in the address box on the web page?
        // System.out.println("url:    " + url.toString());
        // System.out.println("host:   " + url.getHost());
        // System.out.println("path:   " + url.getPath());
        // System.out.println("query:  " + url.getQuery()); // QUESTION: After url, host, path, and query are printed, why is /favicon.ico and some other stuff printed?
        
        // The requests should look like this: add-message?s=<string>
        if (url.getPath().equals("/")) {
            return String.format("Copy '/add-message?s=' (excluding ''), paste it after this web page's url in the address box above, and enter the string you want to add.");
        }
        // else if(url.getQuery() == null) { // java.lang.NullPointerException: Cannot invoke "String.getBytes()" because "<local2>" is null, when the url is http://localhost:1111/message
        //     return null;
        // }
        else if(url.getQuery().contains("s")) {
            if(url.getQuery().contains("=")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s") && !(parameters.length < 2)) {
                    x += parameters[1]; // the + operator is applicable to String objects.
                    x += "\n";
                    return String.format(x);
                }
            }
        }

    return "404 NOT FOUND.\nThe url you entered is illegal. Make sure your url is in this format: http://localhost:" + portNum + "/add-message?s=<your String>\nThis web server does NOT support empty queries and you may NOT enter nothing after 'add-messages?s='.";
    }
}

public class StringServer {
    public static void main (String[] args) throws IOException { // TODO: Study IOException and use it to narrow the arguments to StringHandler(int portNum) down to int arguments only (make this constructor throw exceptions when the argument passed to it is non-int data).
        if(args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int portNum = Integer.parseInt(args[0]);
        Server.start(portNum, new StringHandler(portNum)); // The method, public static void main (String[] args) throws IOException, finishes executing after this line, because Server.start(portNum, new StringHandler(portNum)) returns void
    }
}

/* new stuff learned:
 * Regarding to this command: $ java <file01> ... <fileXX>, that's entered in a bash terminal, and all files are required to run the program w desired functionalities:
 *      - bash recognizes the first file name, <file01> as the file that has the method, public static void main (String[] args).
 *      - javac <file01> ... <fileXX> compiles all files (friles from file01 to fileXX) into one single file that's encoded in a machine language
 *          - => after changing one or more of the files, compile all files to ensure the one single file (encoded in a machine language) mentioned above is updated with the changes made in one or more of the files.
 */