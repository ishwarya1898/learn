package Project5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstproject.MainActivity2;
import com.example.firstproject.R;

import java.util.ArrayList;
import java.util.Set;

import javax.security.auth.DestroyFailedException;

public class Bluetooth extends AppCompatActivity {
    Button enable,disable,pair,discover;
    ListView lv;
    RecyclerView rv;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    MyBroadcastReceiver broadcastreceiver;
    ArrayList<String> list1=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    DeviceAdapter adapter;
    ArrayList<String> dmac=new ArrayList<>();
    ArrayList<String> dname=new ArrayList<>();
    ArrayList list = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        enable=findViewById(R.id.btnenable);
        disable=findViewById(R.id.btndisable);
        pair=findViewById(R.id.btnpair);
        discover=findViewById(R.id.btndiscover);
        lv=findViewById(R.id.lvdevice);
        adapter=new DeviceAdapter();
        rv=findViewById(R.id.devicelist);
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }else{Toast.makeText(getApplicationContext(), "Bluetooth Al-Ready Enable", Toast.LENGTH_LONG).show();}

            }
        });
        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.disable();
                } Toast.makeText(getApplicationContext(), "Bluetooth Al-Ready Disable", Toast.LENGTH_LONG).show();

            }
        });
        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
                }
                else{
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {

                        for (BluetoothDevice device : pairedDevices) {
                            String deviceName = device.getName();
                            String MACAddress = device.getAddress();
                            list.add("Name: "+deviceName+"MAC ADDRESS:"+MACAddress);
                            Toast.makeText(Bluetooth.this, "Showing paired devices", Toast.LENGTH_SHORT).show();

                        }
                        final ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1,list);
                        lv.setAdapter(adapter);

                    }
                }
            }
        });
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothAdapter.startDiscovery();
                broadcastreceiver=new MyBroadcastReceiver();
                IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(broadcastreceiver,intentFilter);
                isduplicate(list1);
                adapter.notifyDataSetChanged();
                isduplicate(list2);
                adapter.notifyDataSetChanged();



            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(broadcastreceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            final ArrayAdapter[] adapter = new ArrayAdapter[1];
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String derp = device.getName() + " - " + device.getAddress();
                Toast.makeText(context, derp, Toast.LENGTH_SHORT);

            }

        }
    }

    public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewholder> {
        @NonNull
        @Override
        public DeviceAdapter.DeviceViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blue, parent, false);
            return new DeviceViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceAdapter.DeviceViewholder holder, int position) {
            holder.tvmac.setText(dmac.get(position));
            holder.tvname.setText(dmac.get(position));


        }

        @Override
        public int getItemCount() {
            return dmac.size();
        }

        public class DeviceViewholder extends RecyclerView.ViewHolder {
            TextView tvmac, tvname;

            public DeviceViewholder(@NonNull View itemView) {
                super(itemView);
                tvmac = itemView.findViewById(R.id.tvmac);
                tvname = itemView.findViewById(R.id.tvdevicename);
            }
        }

    }
    public ArrayList<String>isduplicate(ArrayList<String>list){
    for (String elementmac:list){
        if (!dname.contains(elementmac)){
            dname.add(elementmac);
        }
    }
    return dname;
    }
        public ArrayList<String>isduplicatemac(ArrayList<String>list){
            for (String elementmac:list){
                if (!dmac.contains(elementmac)){
                    dmac.add(elementmac);
                }
            }
            return dmac;
        }
    }
