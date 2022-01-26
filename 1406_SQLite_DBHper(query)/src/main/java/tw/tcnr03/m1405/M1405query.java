package tw.tcnr03.m1405;

import androidx.appcompat.app.AppCompatActivity;

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

public class M1405query extends AppCompatActivity implements View.OnClickListener {
    private TextView count_t;
    private Button b001, b002, b003, b004;
    private EditText e001, e002, e003, e004;

    private FriendDbHelper dbHper;
    private static final String DB_FILE = "friends.db";
    private static final String DB_TABLE = "member";
    private static final int DBversion = 1;
    private TextView t001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405query);
        setupViewComponent();
        initDB();
        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
//        count_t.setText("共計:5筆");
    }

    private void initDB() {
        if(dbHper == null){
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
    }

    private void setupViewComponent() {
        e001 = (EditText) findViewById(R.id.edtName);
        e002 = (EditText) findViewById(R.id.edtGrp);
        e003 = (EditText) findViewById(R.id.edtAddr);
        b002 = (Button) findViewById(R.id.btnquery);
        t001 = (TextView) findViewById(R.id.m_ans);
        count_t =(TextView)findViewById(R.id.count_t) ;


        b002.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String result = null;
        // 查詢name跟在e001上打得是否有有此筆資料
        String tname = e001.getText().toString().trim();
        if (tname.length() != 0)  {
            String rec = dbHper.FindRec(tname); //run sql
            if (rec != null)     {
                result = "找到的資料為 ：\n" + rec;
            } else     {
                result = "找不到指定的編號：" + tname;
            }
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
//                    .show();
            // 獲取輸入法打開的狀態-------------------------------
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = imm.isActive();
            // isOpen若返回true，則表示輸入法打開
            if (isOpen == true) {// 如果輸入法打開則關閉，如果沒打開則打開
                InputMethodManager m=(InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
            t001.setText(result);
        }
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
