package vlad.itschool.ru.finalproject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.OutputStream;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    BluetoothSocket clientSocket;
    ToggleButton LED1, LED2;
    final String ENABLE_BT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
    private static String address = "20:16:09:26:90:13";
    BluetoothAdapter bluetooth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SurfView(this));

//        startActivityForResult(new Intent(ENABLE_BT), 1);
//        bluetooth = BluetoothAdapter.getDefaultAdapter();

//        LED1 = (ToggleButton) findViewById(R.id.toggleButtonLED1);
//        LED2 = (ToggleButton) findViewById(R.id.toggleButtonLED2);

//        LED1.setOnCheckedChangeListener(this);
//        LED2.setOnCheckedChangeListener(this);

//        try {
//            BluetoothDevice dev = bluetooth.getRemoteDevice(address);
//            Method m = dev.getClass().getMethod(
//                    "createRfcommSocket", new Class[] {int.class});

//            clientSocket = (BluetoothSocket) m.invoke(dev, 1);
//            clientSocket.connect();
//            Toast.makeText(this, "Присоединение", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
//        }
//    }


//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        String sentString = "";
//        switch (compoundButton.getId()) {
//            case (R.id.toggleButtonLED1):
//                sentString += 1;
//                break;
//            case (R.id.toggleButtonLED2):
//                sentString += 2;
//                break;
//        }
//        if (b) {
//            sentString += 1;
//        } else {
//            sentString += 0;
//        }
//        if(clientSocket!=null){
//        try {
//            OutputStream outputStream = clientSocket.getOutputStream();
//            outputStream.write(sentString.getBytes());
//        } catch (Exception e) {
//            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
//        }
//        }
//        else Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
    }
}
