package tw.tcnr03.m1401;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {
    private Button mBtnLaunchAct;
    private TextView mTxtResult;
    final private int LAUNCH_GAME=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupViewComponent();
    }

    private void setupViewComponent() {
        mBtnLaunchAct = (Button)findViewById(R.id.btnLaunchAct);
        mTxtResult = (TextView)findViewById(R.id.txtResult);
        mBtnLaunchAct.setOnClickListener(btnLaunchActOnClickLis);
    }



    private Button.OnClickListener btnLaunchActOnClickLis = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(Main.this, M1401.class);
            startActivityForResult(it,LAUNCH_GAME);
//            intent.setClass(Main.this,M1401.class);
//                    ActivityManager.getInstance().addActivity(Main.this);
//                    startActivity(intent);
            //--
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //intent有加入回傳值
        if (requestCode != LAUNCH_GAME) return;
        switch (resultCode) {
            case RESULT_OK:
                Bundle bundle = data.getExtras();


                int iCountSet = bundle.getInt("KEY_COUNT_SET");
                int iCountPlayerWin = bundle.getInt("KEY_COUNT_PLAYER_WIN");
                int iCountComWin = bundle.getInt("KEY_COUNT_COM_WIN");
                int iCountDraw = bundle.getInt("KEY_COUNT_DRAW");

                String s =getString(R.string.m1401_result) + iCountSet +getString(R.string.m1401_table)+" "+
                        getString(R.string.m1401_PlayerWin) + iCountPlayerWin +getString(R.string.m1401_table)+" "+
                        getString(R.string.m1401_comWin)+ iCountComWin +getString(R.string.m1401_table)+" "+
                        getString(R.string.m1401_draw)+ iCountDraw +getString(R.string.m1401_table);
                mTxtResult.setText(s);
                break;
            case RESULT_CANCELED:
                mTxtResult.setText("你選擇取消遊戲。");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}