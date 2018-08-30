package codeformas.com.miranda_wear.socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import codeformas.com.miranda_wear.util.ConstantsMiranda;

public class TCPCliente {
    private String serverMessage;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    private PrintWriter out = null;
    private BufferedReader in = null;

    /**
     *  Constructor de la clase. OnMessagedReceived Escucha los mensajes resibidos desde el servidor
     */
    public TCPCliente(final OnMessageReceived listener)
    {
        mMessageListener = listener;
    }

    /**
     * Enviamos los mensajes del cliente al servidor
     * @param message textos del cliente
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            Log.v("------>", "message: "+ message);
            byte[] str = new byte[10];
            //out.println(str);
            out.println(message);
            out.flush();
        }
    }

    public void stopClient(){
        mRun = false;
    }

    public void run() {

        mRun = true;

        try {
            //aqui pones la IP de tu computadora
            InetAddress serverAddr = InetAddress.getByName(ConstantsMiranda.SERVER_IP);

            Log.e("TCP SI Client", "SI: Connecting...");

            //Creamos un socket y hacemos la coneccion con el servidor
            Socket socket = new Socket(serverAddr, ConstantsMiranda.SERVER_PORT);
            try {

                //Enviamos los mensajes al servidor
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                //recibimos el mensaje que el servidor envía de vuelta
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Aquí, mientras que el cliente escucha los mensajes enviados por el servidor
                //Leemos las líneas
                while (mRun) {
                    serverMessage = in.readLine();

                    if (serverMessage != null && mMessageListener != null) {
                        //llamamos al metodo messageReceived de la clase MyActivity
                        mMessageListener.messageReceived(serverMessage);
                        Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
                    }
                    serverMessage = null;
                }
            }
            catch (Exception e)
            {
                Log.e("TCP SI Error", "SI: Error", e);
                e.printStackTrace();
            }
            finally
            {
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP SI Error", "SI: Error", e);

        }

    }

    public interface OnMessageReceived {
        void messageReceived(String message);
    }
}