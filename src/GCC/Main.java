package GCC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application
{
    public static void main(String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GCC.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.init();
        stage.setScene(new Scene(root, 800, 400));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();
    }

    private static Controller controller;
    private static Stage stage;

    public static Controller getController()
    {
        return controller;
    }

    public static void appendDebugText(String text)
    {
        getController().console.appendText("\n"+getDate()+": "+text);
    }

    private static String getDate()
    {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static Stage getStage()
    {
        return stage;
    }

    public static boolean test()
    {
        int[][] matrix =
                {
                        {0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0}
                };
        int x = 2;
        int y = 5;

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
            System.out.println("win h2");
            return true;
        }
        return false;
    }
}
