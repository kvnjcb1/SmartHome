package com.example.kevinjacob.smarthome;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.UartDevice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {

    UartDevice device;
    byte[] bytearray;
    static String val;

    @Override
    protected void onDestroy()
    {
        System.out.println("Destroyed");
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManager pioManager = PeripheralManager.getInstance();
        try {
            List<String> deviceList = pioManager.getUartDeviceList();
            System.out.println(deviceList);


            //serial port commn
            device = pioManager.openUartDevice("UART0");
            System.out.println("Device ID = " + device);
            if(device == null)
            {
                System.out.println("Device Not found!!!");
            }
            device.setBaudrate(9600);
            device.setDataSize(8);
            device.setParity(UartDevice.PARITY_NONE);
            device.setStopBits(1);

        }
        catch (Exception e)
        {
            System.out.println(e);//do nothing
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref1= reference.child("light1");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if(dataSnapshot.getValue(Boolean.class))
                    {

                        //serial port commn
                        val = "1";
                        System.out.println(val);
                        bytearray = val.getBytes();

                        try {
                            device.write(bytearray, bytearray.length);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                    else
                    {
                        //serial port commn
                        val = "0";
                        bytearray = val.getBytes();
                        System.out.println(val);
                        try {
                            device.write(bytearray, bytearray.length);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }


                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);//dp nothing
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref2 = database.getReference("light2");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    if(dataSnapshot.getValue(Boolean.class))
                    {
                        //serial port commn
                        val = "2";
                        bytearray = val.getBytes();
                        try {
                            device.write(bytearray, bytearray.length);
                        }
                        catch (Exception e)
                        {}
                    }
                    else
                    {
                        //serial port commn
                        val = "3";
                        bytearray = val.getBytes();
                        try {
                            device.write(bytearray, bytearray.length);
                        }
                        catch (Exception e)
                        {}


                    }
                }
                catch (Exception e)
                {
                    //dp nothing
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*tb=findViewById(R.id.tB);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Boolean.class))
                {
                    //check output here
                    System.out.println(dataSnapshot.getValue(Boolean.class)+"------>>>>>>");
                    tb.setChecked(true);
                    val = "1";
                    bytearray = val.getBytes();
                    System.out.println("BYTEARRAY is" + bytearray);
                    System.out.println("Bytearray length is" + bytearray.length);
                    try {
//                        System.out.println("Kevin says 1, it is "+val);
                        device.write(bytearray, 1);
                        System.out.println("WRITTEN");
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }

                }
                else{
                    tb.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        //for light 2


        //end light 2

        /*tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ref1.setValue(isChecked);
            }
        });*/


    }
}
