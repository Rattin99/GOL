package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Image a = new Image("Alive.png");
        Image d = new Image("Dead.png");

        Group root = new Group();
        Canvas canvas = new Canvas(1280,720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Sim sim = new Sim();
        gc.drawImage(a,10,10);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        AnimationTimer am = new AnimationTimer(){
            @Override
            public void handle(long now) {
                for(int i=0;i<1280;i+=10){
                    for(int j =0;j<720;j+=10){
                        if(sim.isAlive(i/10,j/10)){
                            gc.drawImage(a,i,j);
                        }else {

                            gc.drawImage(d,i,j);
                        }
                    }
                }

            }
        };

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch ( event.getCode()){
                    case SPACE:

                        am.start();
                        sim.simulate();
//                        sim.simulate_p();

                        break;
                    case R:
                        am.start();
                        sim.undo();

                        break;

                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {


                scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int x =(int) event.getX()/10;
                        int y = (int) event.getY()/10;
                       if(sim.isAlive(x,y)){
                           sim.kill(x,y, sim.grid);
                       }else{
                           sim.setALive(x,y, sim.grid);
                       }
                    }
                });
            }
        });


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
