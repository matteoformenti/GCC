package GCC;

import javafx.concurrent.Task;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NetworkListener extends Task<Void>
{
    private static boolean stop = false;

    @Override
    protected Void call() throws Exception
    {
        ExecutorService executors = new ThreadPoolExecutor(20, Integer.MAX_VALUE, 60L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        while (!stop)
        {
            ServerSocket serverSocket = new ServerSocket(6969);
            executors.execute(new Player(serverSocket.accept()));
            serverSocket.close();
        }
        return null;
    }

    public static void kill()
    {
        stop = true;
    }
}
