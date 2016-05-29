package GCC;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Storage
{
    public static List<Player> players = new ArrayList<>();
    public static Queue<Player> playersQueue = new PriorityQueue<>();

    public static boolean usernameExists(String user)
    {
        for (Player p : players)
        if (p.getUsername().equals(user))
            return true;
        return false;
    }

    public static void initMatchmaking(Player player)
    {
        if (playersQueue.size() == 0)
        {
            playersQueue.add(player);
            player.getWriter().println(player.gcp.messageComposer(GCP.Codes.matchmaker_queue, "null"));
        }
        else
        {
            Match m = new Match(player, playersQueue.poll());
            Main.appendDebugText("New match created, "+m.player1.getUsername()+" vs "+m.player2.getUsername());
        }
    }
}
