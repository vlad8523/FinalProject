package vlad.itschool.ru.finalproject;

import android.bluetooth.*;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Влад on 08.04.2018.
 */

public class BluetoothConnection extends Thread {
    OutputStream outputStream;
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "20:16:09:26:90:13";
    BluetoothSocket clientSocket;
    Context context;
    StringBuilder sendData = new StringBuilder("n/n/");
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    boolean isRunning = true;


    public BluetoothConnection(Context context){
        this.context = context;
        try {
            BluetoothDevice dev = adapter.getRemoteDevice(address);
            clientSocket = dev.createRfcommSocketToServiceRecord(MY_UUID);
            clientSocket.connect();
            outputStream = clientSocket.getOutputStream();
            Toast.makeText(context, "Присоединение", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Ошибка1", Toast.LENGTH_SHORT).show();
            Log.e("123",e.getLocalizedMessage());
        }
    }

    public void send(double x,double y){
        sendData.delete(0, sendData.length());
        sendData.append(String.format("%.1f",x)+"/"+String.format("%.1f",y)+"/");
        Log.e("Send",sendData.toString());
        try {
            outputStream.write(sendData.toString().getBytes());
        } catch (Exception e) {
            Toast.makeText(context, "Ошибка2", Toast.LENGTH_SHORT).show();
            Log.e("123", e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            if (!sendData.toString().equals("n/n/")) {
                try {
                    outputStream.write(sendData.toString().getBytes());
                } catch (Exception e) {
                    Toast.makeText(context, "Ошибка2", Toast.LENGTH_SHORT).show();
                    Log.e("123", e.getLocalizedMessage());
                }
            }
            sendData.delete(0, sendData.length());
            sendData.append("n/n/");
        }
    }

    public void closeConnect(){
        isRunning = false;
        try {if(clientSocket!=null){
                clientSocket.close();
            }
            if(outputStream!=null) {
                outputStream.close();
            }
        }
        catch (IOException e){
            Toast.makeText(context, "Ошибка3", Toast.LENGTH_SHORT).show();
        }
    }
}


