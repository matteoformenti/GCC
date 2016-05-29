package GCC;

public class Match
{
    public Player player1;
    public Player player2;
    private String lastMove = "null";
    private int counter = 0;

    private int[][] matrix = new int[8][8];

    public Match(Player p1, Player p2)
    {
        this.player1 = p1;
        this.player2 = p2;
        player1.notifyMatch(this);
        player2.notifyMatch(this);
    }

    public boolean checkWinner(Player p, int x, int y)
    {
        //VERTICALE
        int tmp = 0;
        for (int i = 0; i < 4; i++)
        {
            if (x+i > 7)
                break;
            if (matrix[x][y] != matrix[x+i][y])
                break;
            tmp++;
        }
        for (int i = 1; i < 4; i++)
        {
            if (x-i < 0)
                break;
            if (matrix[x][y] != matrix[x-i][y])
                break;
            tmp++;
        }
        if (tmp >= 4)
            return true;
        //ORIZZONTALE
        tmp = 0;
        for (int i = 0; i < 4; i++)
        {
            if (y+i > 7)
                break;
            if (matrix[x][y] != matrix[x][y+i])
                break;
            tmp++;
        }
        for (int i = 1; i < 4; i++)
        {
            if (y-i < 0)
                break;
            if (matrix[x][y] != matrix[x][y-i])
                break;
            tmp++;
        }
        if (tmp >= 4)
            return true;
        //OBLIQUA 1
        tmp = 0;
        for (int i = 0; i < 4; i++)
        {
            if (y+i > 7 || x+i > 7)
                break;
            if (matrix[x][y] != matrix[x+i][y+i])
                break;
            tmp++;
        }
        for (int i = 1; i < 4; i++)
        {
            if (y-i < 0 || x-i < 0)
                break;
            if (matrix[x][y] != matrix[x-i][y-i])
                break;
            tmp++;
        }
        if (tmp >= 4)
            return true;
        //OBLIQUA 1
        tmp = 0;
        for (int i = 0; i < 4; i++)
        {
            if (y+i > 7 || x-i < 0)
                break;
            if (matrix[x][y] != matrix[x-i][y+i])
                break;
            tmp++;
        }
        for (int i = 1; i < 4; i++)
        {
            if (y-i < 0 || x+i > 7)
                break;
            if (matrix[x][y] != matrix[x+i][y-i])
                break;
            tmp++;
        }
        if (tmp >= 4)
        {
            return true;
        }
        return false;
    }

    public void markTable(int x, int y, Player player)
    {
        matrix[x][y] = (player==player1)?1:2;
        getOpponent(player).getWriter().println(getOpponent(player).gcp.messageComposer(GCP.Codes.move, x+GCP.DELIMITER+y));
        if (checkWinner(player, x, y))
        {
            player1.getWriter().println(player1.gcp.messageComposer(GCP.Codes.match_over, player.getUsername()));
            player2.getWriter().println(player2.gcp.messageComposer(GCP.Codes.match_over, player.getUsername()));
            Main.appendDebugText(player.getUsername() + " won!");
            player1.setMatch(null);
        }
    }

    public Player getOpponent(Player p)
    {
        return (p==player1)?player2:player1;
    }
}
