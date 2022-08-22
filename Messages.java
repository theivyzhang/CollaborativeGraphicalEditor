import java.awt.*;
import java.util.ArrayList;

public class Messages {
    private Shape curr =null;
    private Sketch sketch;
    String[] clientMessage;

    private SketchServer server;			// handling communication for


    public Messages(){
        // empty constructor
    }

    /**
     * Constructor
     */
    public Messages(Sketch sketch){
        if (sketch == null) {
            System.out.println("fuck");
        } else {
            System.out.println("all good");
        }
        this.sketch = sketch;
    }

    /**
     * Method to handle the input from client (editor)
     * @param message client request
     * @param sketch the sketch
     */
    public synchronized void handleClientInput(String message, Sketch sketch) {
        if (message == null) {
            System.err.println("Invalid input.");
        }
        else {
            clientMessage = message.toLowerCase().split(" ");
            if (clientMessage.length < 2) {
                System.err.println("Invalid input: missing information.");
            }
            if (clientMessage[0].equals("draw")) {
                handleDrawCommand(message); // calls the handle draw helper method
            }
            else if (clientMessage[0].equals("move")) {
                handleMoveCommand(message); // calls the handle move helper method
            }
            else if (clientMessage[0].equals("recolor")) {
                handleRecolorCommand(message); // calls the handle recolor helper method
            }
            else if (clientMessage[0].equals("delete")) {
                handleDeleteCommand(message); // calls the handle delete helper method
            }
            else if (clientMessage[0].equals("clear")){
                sketch.clearSketch(); // clears sketch
            }
        }
    }

    /**
     * Helper method to handle "draw" request
     * @param message client message
     */
    public synchronized void handleDrawCommand(String message) {
        clientMessage = message.toLowerCase().split(" ");
        System.out.println("message is " + message);
        if (clientMessage.length<7){
            System.err.println("invalid input: missing information");
        }
        // handle different shapes
        if (clientMessage[1].equals("ellipse")){
            curr = new Ellipse(Integer.parseInt(clientMessage[2]), Integer.parseInt(clientMessage[3]),Integer.parseInt(clientMessage[4]),Integer.parseInt(clientMessage[5]),new Color(Integer.parseInt(clientMessage[6])));
            sketch.addShape(curr);
        }
        else if (clientMessage[1].equals("rectangle")){
            curr = new Rectangle(Integer.parseInt(clientMessage[2]), Integer.parseInt(clientMessage[3]),Integer.parseInt(clientMessage[4]),Integer.parseInt(clientMessage[5]),new Color(Integer.parseInt(clientMessage[6])));
            sketch.addShape(curr);
        }
        else if (clientMessage[1].equals("segment")){
            curr = new Segment(Integer.parseInt(clientMessage[2]), Integer.parseInt(clientMessage[3]),Integer.parseInt(clientMessage[4]),Integer.parseInt(clientMessage[5]),new Color(Integer.parseInt(clientMessage[6])));
            sketch.addShape(curr);
        }
        else if (clientMessage[1].equals("polyline")){
            String[] points = clientMessage[2].split(",");
            ArrayList<Point> pointsList = new ArrayList<>();

            for (int i = 0; i <= points.length-2; i += 2) {
                pointsList.add(new Point(Integer.parseInt(points[i]),Integer.parseInt(points[i+1])));
            }

            curr = new Polyline(pointsList, new Color(Integer.parseInt(clientMessage[3])));

            sketch.addShape(curr);
        }
    }

    /**
     * Helper method to handle move request
     * @param message client request
     */
    public synchronized void handleMoveCommand(String message) {
        clientMessage = message.toLowerCase().split(" ");
        if (clientMessage.length<4){
            return;
        }
        curr = sketch.getShape(Integer.parseInt(clientMessage[1]));
        if (curr!= null){
            curr.moveBy(Integer.parseInt(clientMessage[2]),Integer.parseInt(clientMessage[3]));
        }
    }

    /**
     * Helper method to handle recolor request
     * @param message client request
     */
    public synchronized void handleRecolorCommand(String message) {
        clientMessage = message.toLowerCase().split(" ");
        curr = sketch.getShape(Integer.parseInt(clientMessage[1]));
        curr.setColor(new Color(Integer.parseInt(clientMessage[2])));
    }

    /**
     * Helper method to handle delete request
     * @param message client request
     */
    public synchronized void handleDeleteCommand(String message) {
        clientMessage = message.toLowerCase().split(" ");
        curr = sketch.getShape(Integer.parseInt(clientMessage[1]));
        sketch.deleteShape(Integer.parseInt(clientMessage[1]));
    }
}

