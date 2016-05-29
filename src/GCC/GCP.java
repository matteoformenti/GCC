package GCC;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static GCC.GCP.Codes.err;

public class GCP
{
    public static String DELIMITER = "|";

    enum Codes
    {
        login,              //request for login
        login_ok,           //login ok
        login_usr,          //username not available
        matchmaker,         //start matchmaking
        matchmaker_queue,   //player in queue
        match,              //match started
        match_over,         //match finished
        match_error,        //opponent disconnection
        error,              //general error
        exit,               //disconnect from server
        move,               //any move in the game
        err,
        turn
    }

    public Message decodeIncoming(BufferedReader reader)
    {
        try
        {
            String in = reader.readLine();
            if (!in.equals("null"))
            {
                StringTokenizer tokenizer = new StringTokenizer(in, "//" + DELIMITER);
                Codes c = Codes.valueOf(tokenizer.nextToken());
                String s = "";
                while (tokenizer.hasMoreTokens())
                    s += tokenizer.nextToken() + DELIMITER;
                return new Message(c, s);
            }
            return new Message(err, "err");
        }
        catch (Exception e)
        {
            return new Message(err, "err");
        }
    }

    public String messageComposer(Codes code, String message)
    {
        return code.toString() + DELIMITER + message;
    }

    public class Message
    {
        GCP.Codes code;
        List<String> payload = new ArrayList<>();

        public Message(GCP.Codes code, String p)
        {
            this.code = code;
            StringTokenizer tokenizer = new StringTokenizer(p, GCP.DELIMITER);
            if (p!=null)
                while (tokenizer.hasMoreTokens())
                    this.payload.add(tokenizer.nextToken());
        }
    }
}
