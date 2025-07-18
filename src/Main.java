import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        int[] arr=new int[20];
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
        	// Random between 50-550 to fit in the canvas
            arr[i] = rand.nextInt(500) + 50; 
        }
        

        // Initial draw (unsorted)
        drawArray(gc, arr, -1, -1);
        
       // Button to start sorting
        Button startButton = new Button("Start Sorting");
        startButton.setOnAction(e -> {
            new Thread(() -> {
                try {
                    bubbleSortVisual(arr, gc);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.getChildren().addAll(canvas, startButton);
       
        
        Scene scene = new Scene(root, 800, 650);
        primaryStage.setTitle("Algorithm Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
           

    }
    
    private void drawArray(GraphicsContext gc, int[] arr, int highlightedIndex1, int highlightedIndex2) {
    	gc.clearRect(0,0,800,600);
    	gc.setFill(Color.LIGHTGRAY);
        double barWidth=800.0/arr.length;
         
         for (int i = 0; i < arr.length; i++) {
        	if (i == highlightedIndex1 || i == highlightedIndex2) {
        	        gc.setFill(Color.RED);  // Highlight the bars being compared
        	 } else {
        	        gc.setFill(Color.LIGHTGRAY);
        	}
         	double x= i* barWidth;
         	double y= 600-arr[i];
         	double height=arr[i];
         	gc.fillRect(x, y,barWidth, height);
         	gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(arr[i]), x + barWidth / 4, y - 5);
         }
    }
    
    private void bubbleSortVisual(int[] arr, GraphicsContext gc) {
    	for (int i = 0; i < arr.length - 1; i++) {
    	    for (int j = 0; j < arr.length - i - 1; j++) {
    	        if (arr[j] > arr[j + 1]) {
    	            // Swap
    	            int temp = arr[j];
    	            arr[j] = arr[j + 1];
    	            arr[j + 1] = temp;

    	            // Redraw bars after swap
    	            drawArray(gc, arr,j,j+1);
    	            try {
    	            	Thread.sleep(300); // Pause so we can see the change
    	            } catch(Exception e) {
    	            	e.printStackTrace();
    	            }
    	            	
    	            }
    	    }
    	}
    	drawArray(gc,arr,-1,-1);

    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}
