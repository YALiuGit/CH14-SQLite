package tw.tcnr03.m1405;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class M1405browse extends AppCompatActivity implements View.OnClickListener {
    private TextView count_t;
    private Button b001, b002, b003, b004;
    private EditText e001, e002, e003, e004;

    private FriendDbHelper dbHper;
    private static final String DB_FILE = "friends.db";
    private static final String DB_TABLE = "member";
    private static final int DBversion = 1;
    private TextView t001;
    private TextView tvTitle;
    private Button btNext;
    private Button btPrev;
    private Button btTop;
    private Button btEnd;
    private int index =0;
    private ArrayList<String> recSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405browse);
        setupViewComponent();
    }

    private void initDB() {
        if(dbHper == null){
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
        recSet = dbHper.getRecSet();
        int a =0;
    }

    private void setupViewComponent() {
        tvTitle = (TextView)findViewById(R.id.tvIdTitle);
        e001 = (EditText) findViewById(R.id.edtName);
        e002 = (EditText) findViewById(R.id.edtGrp);
        e003 = (EditText) findViewById(R.id.edtAddr);
        count_t =(TextView)findViewById(R.id.count_t) ;

        btNext = (Button) findViewById(R.id.btIdNext);
        btPrev = (Button) findViewById(R.id.btIdPrev);
        btTop = (Button) findViewById(R.id.btIdtop);
        btEnd = (Button) findViewById(R.id.btIdend);

        btNext.setOnClickListener(this);
        btPrev.setOnClickListener(this);
        btTop.setOnClickListener(this);
        btEnd.setOnClickListener(this);

        //如果放在onCreate會與setupViewComponent()同時執行造成錯誤
        initDB();
        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");

        showRec(index);
    }

    private void showRec(int index) {
        int b=0;
        if (recSet.size() != 0)   {
            String stHead = "顯示資料：第 " + (index + 1) + " 筆 / 共 " + recSet.size() + " 筆";
            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.Blue));
            tvTitle.setText(stHead);
            //--------------------------
//            fld為field欄位的簡寫
            String[] fld = recSet.get(index).split("#");
            e001.setTextColor(ContextCompat.getColor(this, R.color.Red));
            e001.setText(fld[1]);
            e002.setText(fld[2]);
            e003.setText(fld[3]);
            //---------------------
        } else        {
            e001.setText("");
            e002.setText("");
            e003.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btIdtop:
                ctlFirst();
                index=0;
                break;
            case R.id.btIdNext:
                ctlNext();
                break;
            case R.id.btIdPrev:
                ctlPrev();
                break;
            case R.id.btIdend:
                ctlLast();
                index=recSet.size()-1;
                break;
        }
    }

    private void ctlFirst() {
        showRec(0);
    }

    private void ctlPrev() {
        index--;
        if (index < 0)
            index = recSet.size() - 1;
        showRec(index);
    }
    private void ctlNext() {
        index++;
        if (index >= recSet.size())
            index = 0;
        showRec(index);
    }

    private void ctlLast() {
        showRec(recSet.size()-1);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),getString(R.string.noback),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbHper == null)
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dbHper != null) {
            dbHper.close();
            dbHper = null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1405sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_return:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
