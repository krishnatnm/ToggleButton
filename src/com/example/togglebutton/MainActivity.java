package com.example.togglebutton;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	ToggleButton flight;
	CheckBox btcheck;
	final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        flight = (ToggleButton) findViewById(R.id.flightbutton);
        btcheck = (CheckBox) findViewById(R.id.bluetooth);
        
        flight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean state = isAirplaneMode();
				toggleAirplaneMode(state);
				if (btcheck.isChecked()){
					Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(in, 0);
				}
				else{
					bluetooth.disable();
				}
			}
		});
        
    }
    
    public void toggleAirplaneMode (boolean state){
    	Settings.System.putInt(this.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, state ? 0 : 1);
    	
    	Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
    	intent.putExtra("state", !state);
    	sendBroadcast(intent);
    }
    
        public boolean isAirplaneMode() {
		return Settings.System.getInt(this.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1; 
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
