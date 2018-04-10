package vlad.itschool.ru.finalproject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Влад on 08.04.2018.
 */

public class BluetoothConnection {
    OutputStream outputStream;
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "20:16:09:26:90:13";
    BluetoothSocket clientSocket;
    Context context;
    StringBuilder sendData;
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();


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

    public void send(float x,float y){
        if(clientSocket.isConnected()){
            sendData = new StringBuilder(String.format("%.1f",x)+"/"+String.format("%.1f",y)+"/");
            try {
                outputStream.write(sendData.toString().getBytes());
            }
            catch (Exception e){
                Toast.makeText(context, "Ошибка2", Toast.LENGTH_SHORT).show();
                Log.e("123",e.getLocalizedMessage());
            }
        }
        else{
            try{
                clientSocket.connect();
                outputStream = clientSocket.getOutputStream();
            }
            catch (Exception e){
                Toast.makeText(context, "Ошибка2", Toast.LENGTH_SHORT).show();
                Log.e("123",e.getLocalizedMessage());
            }
        }
    }

    public void closeConnect(){
        try {
            clientSocket.close();
            outputStream.close();
        }
        catch (IOException e){
            Toast.makeText(context, "Ошибка3", Toast.LENGTH_SHORT).show();
        }
    }
}


