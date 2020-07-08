package Game;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class WaitingOpponent {
    private GraphicsContext gc;
    private Status status;
    private Group root;
    private Image backgroundImg, waitingOpponent, rockImg, paperImg, scissorImg, loading, circle, yourPoints;
    private static double w = 1500, h = 900;
    private int choice, points;

    // [Construtor]
    public WaitingOpponent(GraphicsContext gc, Status status, Group root) {
        this.gc = gc;
        this.status = status;
        this.root = root;
       // this.points = 0;
        images();
    }

    // [Carrega imagens]
    private void images() {
        backgroundImg = new Image("/Resources/rays.jpg");
        waitingOpponent = new Image("/Resources/waitingOpponent.png");
        rockImg = new Image("/Resources/rock.png");
        paperImg = new Image("/Resources/paper.png");
        scissorImg = new Image("/Resources/scissor.png");
        loading = new Image("/Resources/loadingTransparent.gif");
      //  circle = new Image("/Resources/circle.png");
        //yourPoints = new Image("/Resources/yourPoints.png");
    }

    // [Desenha tela WaitingOpponent]
    public void drawing(KeyEvent key, Group root, TCPServer server, RoundResult roundResult) {
        gc.drawImage(backgroundImg, 0, 0, w, h);
        gc.drawImage(waitingOpponent, 330, 20, 800, 200);
        gc.drawImage(loading, 550, 300, 400, 300);
        if(server.getJogada() == 1){
            gc.drawImage(rockImg, 250, 270);
        } else if(server.getJogada() == 2){
            gc.drawImage(paperImg, 250, 270);
        }else if(server.getJogada() == 3){
            gc.drawImage(scissorImg, 250, 270);
        }

        if(server.getJogada() > 0 && server.getJogadaCliente() > 0){
            Main.setStatus(Status.ROUNDRESULT);
            roundResult.setButtonsOn(false);
        }
    }
}
