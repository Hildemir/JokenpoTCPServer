package Game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author almirpires
 */
public class Main extends Application {
    private static double w = 1500, h = 900;
    private Group root;
    private Scene scene;
    public static Menu menu;
    public static Instructions instructions;
    private static Status status = Status.MENU;
    private MouseEvent mouse;
    private KeyEvent key;
    private boolean keyPressed = false;
    public static TCPServer server = new TCPServer();
    Socket client;
    ObjectOutputStream outObject;
    ObjectInputStream inObject;
    boolean checkConexao = false;



    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("\033[0;0;32m\033[38;2;32;34;200m"); // set caracteres do terminal para cor azul
        root = new Group();
        scene = new Scene(root, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jokenpo!");
        primaryStage.setMaxWidth(w);
        primaryStage.setMaxHeight(h);
        Canvas canvas = new Canvas(w, h);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(0, canvas);
        menu = new Menu(gc, status, root);
        instructions = new Instructions(gc, status, root);
        Game gameScreen = new Game(gc, status, root);
        WaitToPlay waitToPlay = new WaitToPlay(gc, status, root);
        WaitingOpponent waitingOpponent = new WaitingOpponent(gc, status, root);
        RoundResult roundResult = new RoundResult(gc, status, root);
        //GameOver gameOver = new GameOver(gc);


        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouse = mouseEvent;
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY() + " " + mouseEvent.getEventType().getName());
            }
        });

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouse = mouseEvent;
//                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY() + " " + mouseEvent.getEventType().getName());
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    if (!keyPressed) {
                        key = keyEvent;
                        keyPressed = true;
                    }
                } else {
                    key = keyEvent;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                key = null;
                if (keyEvent.getCode() == KeyCode.SPACE)
                    keyPressed = false;
            }
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                if (status == Status.MENU) {
                    //primaryStage.setFullScreenExitHint(""); // deixa vazia a mensagem "clique esc para sair do modo tela cheia
                   // primaryStage.setFullScreen(true);
                    menu.drawing(mouse, key, root);
                    //if (mouse != null) {                //reseta o valor do mouse e assim qaundo o status for menu novamnete, ele vai ter q esperar outro clique
                      //  mouse = null;
                   // }
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if(status == Status.WAITTOPLAY){
                    waitToPlay.drawing(key, root);

                    try {
                        if(checkConexao == false) {
                            server.iniciandoServidor();
                            checkConexao = true;

                        }
//                        if(server.getConexaoCliente()){
//                            setStatus(Status.GAME);
//                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if(status == Status.GAME) {
                    gameScreen.drawing(key, root, waitToPlay.getServer());
                } else if(status == Status.WAITINGOPPONENT) {
                    waitingOpponent.drawing(key, root, gameScreen.getChoice());
                }else if(status == Status.ROUNDRESULT){
                    roundResult.drawing(key, root);
                } else if(status == Status.INSTRUCTIONS){
                    instructions.drawing(key, root);

                } else if (status == Status.CLOSE) {
                    primaryStage.close();
                }

            }

        };
        gameLoop.start();
        primaryStage.show();
    }


        public static void main(String[] args) {
        launch(args);
    }


    public static Status getStatus() {
        return status;
    }

    public static void setStatus(Status status) {
        Main.status = status;
    }
}

