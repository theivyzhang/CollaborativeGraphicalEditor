import java.awt.*;
import java.util.TreeMap;

/**
 * A Sketch object class to hold the current shape list
 */
public class Sketch {
    public Integer shapeID = 0; // ids of the shapes
    public TreeMap<Integer, Shape> shapes; // treemap mapping ids to the shapes

    /**
     * Constructor: initializes a new sketch of shapes
     */
    public Sketch(){
        shapes = new TreeMap<Integer, Shape>();
    }

    /**
     * Add shape and id into the tree map
     * @param newShape the new shape
     */
    public void addShape(Shape newShape){
        synchronized (shapeID) {
            shapes.put(shapeID, newShape);
            shapeID++;
        }
    }

    /**
     * Get the id of a shape
     * @param shape the shape we want its number
     */
    public int getShapeID(Shape shape){
        if (shapes.containsValue(shape)){
            for (int id: shapes.keySet()){
                if (shapes.get(id).equals(shape)){
                    return id;
                }
            }
        }
        return -1; // if shape is not already in the tree map
    }

    /**
     * Method to get the shape mapped from the id
     * @param id the shape's id
     */
    public Shape getShape(int id){
        if (shapes.containsKey(id)){
            return shapes.get(id);
        }
        return null;
    }

    /**
     * Method to delete the shape
     * @param id shape id
     */
    public void deleteShape(int id){
        if (!shapes.containsKey(id)){
            System.err.println("the id does not exist in the system");
        }
        shapes.remove(id, shapes.get(id));
    }

    /**
     * Method to access the treemap
     * @return the tree map
     */
    public TreeMap<Integer, Shape> getMap(){
        return shapes;
    }

    public void clearSketch(){
        shapes = new TreeMap<Integer, Shape>();
    }
}
