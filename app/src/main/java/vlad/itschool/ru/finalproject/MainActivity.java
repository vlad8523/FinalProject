package vlad.itschool.ru.finalproject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BluetoothSocket clientSocket;
    final String ENABLE_BT = BluetoothAdapter.ACTION_REQUEST_ENABLE;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.forwardEngine):
                break;
            case (R.id.backEngine):
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivityForResult(new Intent(ENABLE_BT), 0);
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

//        try{
//
//            BluetoothDevice device = bluetooth.getRemoteDevice();
//
//            Method m = device.getClass().getMethod(
//                    "createRfcommSocket", new Class[] {int.class});
//
//            clientSocket = (BluetoothSocket) m.invoke(device, 1);
//            clientSocket.connect();
//        }
//        catch (Exception e){
//            Log.d(e.getMessage());
//        }

        Button forwardEngine = (Button)findViewById(R.id.forwardEngine);
        Button backEngine = (Button)findViewById(R.id.backEngine);

        forwardEngine.setOnClickListener(this);
        backEngine.setOnClickListener(this);
    }


}
