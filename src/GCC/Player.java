package GCC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable
{
    private Socket socket;
    private String username;
    private String color;
    private BufferedReader reader;
    private PrintWriter writer;
    private Match match;
    public GCP gcp = new GCP();

    public Player(Socket accept)
    {
        this.socket = accept;
    }

    @Override
    public void run()
    {
        try
        {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(),true);
            GCP.Message msg;
            do
            {
                msg = gcp.decodeIncoming(reader);
                if (msg != null)
                switch (msg.code)
                {
                    case login:
                        username = msg.payload.get(0);
                        color = msg.payload.get(1);
                        if (!Storage.usernameExists(username))
                        {
                            writer.println(gcp.messageComposer(GCP.Codes.login_ok, "null"));
                            Storage.players.add(this);
                            Main.appendDebugText("Player "+username+" connected, color: "+color);
                        } else
                            writer.println(gcp.messageComposer(GCP.Codes.login_usr, "null"));
                        break;
                    case matchmaker:
                        Main.appendDebugText("Match request from "+username);
                        Storage.initMatchmaking(this);
                        break;
                    case move:
                        int posx = Integer.parseInt(msg.payload.get(0));
                        int posy = Integer.parseInt(msg.payload.get(1));
                        match.markTable(posx, posy, this);
                        break;
                    case exit:
                        Storage.players.remove(this);
                        Storage.playersQueue.remove(this);
                        Main.appendDebugText("Player "+username+" disconnected");
                        break;
                }
            }
            while (msg == null || !msg.code.equals(GCP.Codes.exit) || !msg.code.equals(GCP.Codes.err));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getUsername()
    {
        return username;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public String getColor()
    {
        return color;
    }

    public BufferedReader getReader()
    {
        return reader;
    }

    public PrintWriter getWriter()
    {
        return writer;
    }

    public void notifyMatch(Match m)
    {
        this.match = m;
        writer.println(gcp.messageComposer(GCP.Codes.match, match.getOpponent(this).getUsername()+GCP.DELIMITER+match.getOpponent(this).getColor()));
        writer.println(gcp.messageComposer(GCP.Codes.turn, match.player1.getUsername()));
    }

    public void setMatch(Match m)
    {
        this.match = m;
    }
}
