package tw.tcnr03.m1405;


import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class M1405 extends AppCompatActivity {
    private FriendDbHelper dbHper;
    private static final String DB_FILE = "friends.db";
    private static final String DB_TABLE = "member";
    private static final int DBversion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405);
        initDB();
    }

    private void initDB() {
        if(dbHper == null){
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
    }

    private String msg;
    private DialogInterface.OnClickListener aldBtListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case BUTTON_POSITIVE:
                    int rowsAffected = dbHper.clearRec();
                    msg = getString(R.string.msg1) + rowsAffected + " 筆";
                    break;
                case BUTTON_NEGATIVE:
                    msg = getString(R.string.msg2);
                    break;
            }
            Toast.makeText(M1405.this, msg, Toast.LENGTH_SHORT).show();
        }
    };

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
                it.setClass(M1405.this,M1405browse.class);
                startActivity(it);
                break;
            case R.id.m_delete:
                Toast.makeText(getApplicationContext(),getString(R.string.m_nofound),Toast.LENGTH_LONG).show();
                break;
            case R.id.m_batch://批次新增
                dbHper.createTB();  //--- new function
                long totrec=dbHper.RecCount();
                Toast.makeText(getApplicationContext(), "總計:"+totrec, Toast.LENGTH_LONG).show();
                break;
            case R.id.m_clear:
                MyAlertDialog aldDial = new MyAlertDialog(M1405.this);
                aldDial.setTitle(getString(R.string.m_clear));
                aldDial.setMessage(getString(R.string.m_message));
                aldDial.setIcon(android.R.drawable.ic_dialog_info);
                aldDial.setCancelable(false); //返回鍵關閉
                aldDial.setButton(BUTTON_POSITIVE, getString(R.string.m_btn_ok), aldBtListener);
                aldDial.setButton(BUTTON_NEGATIVE, getString(R.string.m_btn_cancel), aldBtListener);
                aldDial.show();

                break;
            case R.id.action_settings:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}