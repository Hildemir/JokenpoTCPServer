package Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {
    private Socket client;
    private int jogada;
    private int jogadaCliente;
    ObjectOutputStream outObject;
    ObjectInputStream inObject;
    private boolean conexaoCliente;

    public TCPServer() {
        this.jogada = -1;
        this.jogadaCliente = -1;
    }

    public void iniciandoServidor() throws IOException, ClassNotFoundException {


            new Thread() {

                @Override
                public void run() {
                    int i = 0;

                    while (true) {
                        try {
                            if(i == 0){
                                System.out.println("teste");
                                ServerSocket slisten = new ServerSocket(16868);
                                System.out.println("Aguardando Conexao...");
                                client = slisten.accept();
                                conexaoCliente = true;
                                // envia o true da conexao para o cliente
                                outObject = new ObjectOutputStream(client.getOutputStream());   //abre
                                inObject = new ObjectInputStream(client.getInputStream());
                                Main.setStatus(Status.GAME);
                                i++;

                            }
                            System.out.println("lendo cliente...");
                            jogadaCliente = inObject.read();
                            System.out.println("cliente jogou: " + jogadaCliente);
                            if(jogadaCliente != -1 /*&& jogada != -1*/){
                                Main.setStatus(Status.ROUNDRESULT);
                            }
                            //
                            // Main.setStatus(Status.ROUNDRESULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

    }

    public boolean getConexaoCliente() {
        return conexaoCliente;
    }

    public int getJogada() {
        return jogada;
    }

    public int getJogadaCliente() {
        return jogadaCliente;
    }

    public ObjectOutputStream getOutObject() {
        return outObject;
    }

    public ObjectInputStream getInObject() {
        return inObject;
    }

    public void getJogadaServer(int jogada){
        this.jogada = jogada;
    }
}
