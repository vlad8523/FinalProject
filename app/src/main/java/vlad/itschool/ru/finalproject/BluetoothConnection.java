package vlad.itschool.ru.finalproject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    private static String address = null;
    BluetoothSocket clientSocket;
    Context context;
    StringBuilder sendData = new StringBuilder("n/n/");
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    boolean isRunning = true;


    public BluetoothConnection(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        address = sp.getString("address","null");
        this.context = context;
        try {

            if (address!=null) {
                BluetoothDevice dev = adapter.getRemoteDevice(address);
                clientSocket = dev.createRfcommSocketToServiceRecord(MY_UUID);
                clientSocket.connect();
                outputStream = clientSocket.getOutputStream();
                Toast.makeText(context, "Присоединение", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"Введите адрес устройства", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Ошибка соединения", Toast.LENGTH_SHORT).show();
            Log.e("123",e.getLocalizedMessage());
        }
    }

    public void send(double x,double y){
        if (outputStream!=null) {
            sendData.delete(0, sendData.length());
            sendData.append(String.format("%.1f",x)+"/"+String.format("%.1f",y)+"/");
            Log.e("Send",sendData.toString());
            try {
                outputStream.write(sendData.toString().getBytes());
            } catch (Exception e) {
                Toast.makeText(context, "Ошибка отправки1", Toast.LENGTH_SHORT).show();
                Log.e("123", e.getLocalizedMessage());
            }
        }
        else {
//            Toast.makeText(context, "Ошибка соединения", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            if (!sendData.toString().equals("n/n/")) {
                try {
                    outputStream.write(sendData.toString().getBytes());
                } catch (Exception e) {
                    Toast.makeText(context, "Ошибка отправки2", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Ошибка разъединения", Toast.LENGTH_SHORT).show();
        }
    }
}


