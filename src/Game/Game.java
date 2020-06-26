package Game;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private GraphicsContext gc;
    private Status status;
    private Group root;
    private Image backgroundImg, yourTurn, rockImg, paperImg, scissorImg, circle, yourPoints;
    private static double w = 1500, h = 900;
    private MenuItem rock, paper, scissor;
    private int choice, points;
    private int opponentChoice;


    public Game(GraphicsContext gc, Status status, Group root) {
        this.gc = gc;
        this.status = status;
        this.root = root;
        this.choice = -1;
        this.points = 2;
        images();
        this.rock = new MenuItem(rockImg, 200,270,gc,root);     // x1= 200 x2= 200 + 300
        rock.removeFromView(root);
        this.paper = new MenuItem(paperImg, 600,270,gc,root);   // x1= 600 x2= 600 + 300
        paper.removeFromView(root);
        this.scissor = new MenuItem(scissorImg, 1000,270,gc,root);   // x1= 1000 x2= 1000 + 300
        scissor.removeFromView(root);
//        this.playButton = new MenuItem(play, 500,570,gc,root);
//        playButton.removeFromView(root);
    }


    private void images() {
        backgroundImg = new Image("/Resources/rays.jpg");
        yourTurn = new Image("/Resources/yourTurnImg.png");
        rockImg = new Image("/Resources/rock.png");
        paperImg = new Image("/Resources/paper.png");
        scissorImg = new Image("/Resources/scissor.png");
        circle = new Image("/Resources/circle.png");
        yourPoints = new Image("/Resources/yourPoints.png");
        //arrow = new Image("/Resources/arrowF.png");
        //play = new Image("/Resources/play.png");
    }

    public void drawing(KeyEvent key, Group root, TCPServer server){
        gc.drawImage(backgroundImg, 0,0,w,h);
        gc.drawImage(yourTurn, 330, 20, 800, 200);
       // gc.drawImage(perg, 150,-100,1200,1100);
        rock.addToView(root);
        paper.addToView(root);
        scissor.addToView(root);
        gc.drawImage(yourPoints, 0, 700, 250, 100);
//        if(points != 0){
//            if(points == 1){
//                gc.drawImage(circle, 50,770,80,80);
//            } else {
//                gc.drawImage(circle, 50,770,80,80);
//                gc.drawImage(circle, 160,770,80,80);
//            }
//
//        }
        //playButton.addToView(root);

        rock.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //remove carta
                root.getChildren().remove(1, root.getChildren().size());
                setChoice(0);
                try {
                    Main.server.getOutObject().writeInt(choice);
                    Main.server.getOutObject().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println("Jogador escolheu " + choice);
                // adiciona botoes do menu
//                for (MenuItem item: Main.menu.getItems()) {
//                    item.addToView(root);
//                }
                Main.setStatus(Status.WAITINGOPPONENT);

            }
        });

        paper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //remove carta
                root.getChildren().remove(1, root.getChildren().size());
                setChoice(1);
                try {
                    Main.server.getOutObject().writeInt(choice);
                    Main.server.getOutObject().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // System.out.println("Jogador escolheu " + choice);
                Main.setStatus(Status.WAITINGOPPONENT);

            }
        });

        scissor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //remove carta
                root.getChildren().remove(1, root.getChildren().size());
                setChoice(2);

                try {
                    Main.server.getOutObject().writeInt(choice);
                    Main.server.getOutObject().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println("Jogador escolheu " + choice);
                Main.setStatus(Status.WAITINGOPPONENT);

            }
        });

    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
}
