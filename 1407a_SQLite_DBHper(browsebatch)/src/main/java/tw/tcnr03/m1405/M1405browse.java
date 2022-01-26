package tw.tcnr03.m1405;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float range =350; //設定touchEvent的距離
    private int ran =50;
    private Vibrator myVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1405browse);
        setupViewComponent();
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

        // 宣告震動
        myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);

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

    private void initDB() {
        if(dbHper == null){
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        }
        recSet = dbHper.getRecSet();
        int a =0;
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

//    ----------------------------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: //按下
                x1 = event.getX(); //按下去取得的x
                y1 = event.getY(); //按下去取得的y
                break;
            case MotionEvent.ACTION_MOVE: //移動
                myVibrator.vibrate(10000); // 震動五秒
                break;
            case MotionEvent.ACTION_UP: //放開
                x2 = event.getX(); //按下去取得的x
                y2 = event.getY(); //按下去取得的y
                //                ========================================
                // 判斷左右的方法，因為屏幕的左上角是：0，0 點右下角是max,max
                // 並且移動距離需大於 > range
                float xbar = Math.abs(x2 - x1);
                float ybar = Math.abs(y2 - y1);
                double z = Math.sqrt(xbar * xbar + ybar * ybar);
                int angle = Math.round((float) (Math.asin(ybar / z) / Math.PI * 180));// 角度
                if (x1 != 0 && y1 != 0) {
                    if (x1 - x2 > range) { // 向左滑動
                        ctlPrev();
                    }
                    if (x1 - x2 <  range) { // 向右滑動
                        ctlNext();
                        // t001.setText("向右滑動\n" + "滑動參值x1=" + x1 + " x2=" + x2 + "
                        // r=" + (x2 - x1)+"\n"+"ang="+angle);
                    }
                    if (y2 - y1 > range && angle > ran) { // 向下滑動
                        // 往下角度需大於50
                        // 最後一筆
                        ctlLast();
                        index=recSet.size()-1;
                    }
                    if (y1 - y2 > range && angle > ran) { // 向上滑動
                        // 往上角度需大於50
                        ctlFirst();// 第一筆
                        index=0;
                    }
                }
                myVibrator.cancel();
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),getString(R.string.noback),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dbHper != null)
        {
            dbHper.close();
            dbHper = null;
        }
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
        if (myVibrator != null)
            myVibrator.cancel();
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
