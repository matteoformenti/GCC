package GCC;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

public class Controller
{
    public AnchorPane topBar;
    public LineChart connectionsChart;
    public TextArea console;
    private boolean maximized = false;
    private double x;
    private double y;
    private NetworkListener networkListener = new NetworkListener();
    private Thread networkListenerThread;

    public MaterialDesignIconView resizeIcon;

    public void init()
    {
        console.setText("-----Gravity Control Center-----");
        topBar.setOnMousePressed((event) ->
        {
            if (event.getButton() == MouseButton.PRIMARY) {
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });

        topBar.setOnMouseDragged((event) ->
        {
            if (event.getButton() == MouseButton.PRIMARY) {
                Main.getStage().setX(event.getScreenX() - x);
                Main.getStage().setY(event.getScreenY() - y);
            }
        });
    }

    public void close(ActionEvent actionEvent)
    {
        System.exit(0);
    }

    public void resizeWindow(ActionEvent actionEvent)
    {
        if (!maximized)
        {
            Main.getStage().setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            Main.getStage().setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            Main.getStage().centerOnScreen();
            resizeIcon.setGlyphName("WINDOW_RESTORE");
        }
        else
        {
            Main.getStage().setWidth(800);
            Main.getStage().setHeight(600);
            Main.getStage().centerOnScreen();
            resizeIcon.setGlyphName("WINDOW_MAXIMIZE");
        }
        maximized = !maximized;
    }

    public void minimize(ActionEvent actionEvent)
    {
        Main.getStage().setIconified(true);
    }

    public void startStopServer(ActionEvent actionEvent)
    {
        JFXButton button = (JFXButton)actionEvent.getSource();

        if (!networkListener.isRunning())
        {
            networkListener = new NetworkListener();
            networkListenerThread = new Thread(networkListener);
            networkListenerThread.setName("Network Listener");
            networkListenerThread.start();
            button.setStyle("-fx-background-color: #F44336");
            button.setText("Kill server");
        }
        else
        {
            button.setText("Start server");
            networkListener.kill();
            networkListenerThread.interrupt();
            button.setStyle("-fx-background-color: #4CAF50");
        }
    }
}
