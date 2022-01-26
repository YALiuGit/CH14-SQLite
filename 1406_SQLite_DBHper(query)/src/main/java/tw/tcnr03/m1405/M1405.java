package tw.tcnr03.m1405;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class M1405 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),getString(R.string.noback),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.m1405, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
            Intent it = new Intent();
        switch (item.getItemId()){
            case R.id.m_add:
              it.setClass(M1405.this,M1405insert.class);
              startActivity(it);
                break;
            case R.id.m_query:
                it.setClass(M1405.this,M1405query.class);
                startActivity(it);
                break;
            case R.id.m_update:
                Toast.makeText(getApplicationContext(),getString(R.string.m_nofound),Toast.LENGTH_LONG).show();
                break;
            case R.id.m_delete:
                Toast.makeText(getApplicationContext(),getString(R.string.m_nofound),Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}