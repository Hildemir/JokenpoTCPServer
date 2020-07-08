package Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private Socket client;
    private int jogada;
    private int jogadaCliente;
    ObjectOutputStream outObject;
    ObjectInputStream inObject;

    // [Inicia conexao com cliente]
    public void iniciandoServidor(RoundResult roundResult) throws IOException, ClassNotFoundException {
            new Thread() {

                @Override
                public void run() {
                    int i = 0;

                    while (true) {
                        try {
                            if(i == 0){
                                // [aqui eh criada a conexao com o cliente]
                                ServerSocket slisten = new ServerSocket(16868);
                                System.out.println("Aguardando Conexao...");
                                client = slisten.accept();
                                outObject = new ObjectOutputStream(client.getOutputStream());
                                inObject = new ObjectInputStream(client.getInputStream());
                                Main.setStatus(Status.GAME);
                                i++;
                            }

                            System.out.println("lendo cliente...");
                            jogadaCliente = inObject.read();
                            System.out.println("cliente jogou: " + jogadaCliente);

                            if(jogadaCliente > 0 && jogada > 0){
                                Main.setStatus(Status.ROUNDRESULT);
                                roundResult.setButtonsOn(false);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

    }

    // [Getters e setters]
    public int getJogadaCliente() {
        return jogadaCliente;
    }

    public void setJogadaCliente(int jogadaCliente) {
        this.jogadaCliente = jogadaCliente;
    }

    public int getJogada() {
        return jogada;
    }

    public void setJogada(int jogada) {
        this.jogada = jogada;
    }

    public ObjectOutputStream getOutObject() {
        return outObject;
    }
}
