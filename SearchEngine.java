import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Anish's Search Engine");
        } 
        else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("word")) {
                String word = parameters[1];
                strings.add(0, word);
                return String.format("Your word %s was added to the list of words!", word);
            }
            return "404 Not Found!";
        } 
        else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("word")) {
                String substring = parameters[1];
                String wordsContaining = "";
                for (int i = 0; i < strings.size(); i++) {
                    if (strings.get(i).indexOf(substring) != -1) {
                        wordsContaining += " ";
                        wordsContaining += strings.get(i);
                    }
                }
                return wordsContaining;
            }
            return "404 Not Found!";
        } 
        else {
            System.out.println("Path: " + url.getPath());
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
