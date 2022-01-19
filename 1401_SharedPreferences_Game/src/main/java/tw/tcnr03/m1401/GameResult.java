package tw.tcnr03.m1401;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class GameResult extends AppCompatActivity {

    private EditText mEdtCountSet,
            mEdtCountPlayerWin,
            mEdtCountComWin,
            mEdtCountDraw;
    private Button btnBackToGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_result);
        setupViewComponent();
        showResult();
    }

    private void setupViewComponent() {
        mEdtCountSet = (EditText)findViewById(R.id.edtCountSet);
        mEdtCountPlayerWin = (EditText)findViewById(R.id.edtCountPlayerWin);
        mEdtCountComWin = (EditText)findViewById(R.id.edtCountComWin);
        mEdtCountDraw = (EditText)findViewById(R.id.edtCountDraw);
        btnBackToGame = (Button)findViewById(R.id.btnBackToGame);

        btnBackToGame.setOnClickListener(btnBackToGameLis);
    }
    private Button.OnClickListener btnBackToGameLis = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

            GameResult.this.finish();
        }
    };
    private void showResult() {
        // 從 bundle 中取出資料
        Bundle bundle = this.getIntent().getExtras();

        int iCountSet = bundle.getInt("KEY_COUNT_SET");
        int iCountPlayerWin = bundle.getInt("KEY_COUNT_PLAYER_WIN");
        int iCountComWin = bundle.getInt("KEY_COUNT_COM_WIN");
        int iCountDraw = bundle.getInt("KEY_COUNT_DRAW");

        mEdtCountSet.setText(Integer.toString(iCountSet));
        mEdtCountPlayerWin.setText(Integer.toString(iCountPlayerWin));
        mEdtCountComWin.setText(Integer.toString(iCountComWin));
        mEdtCountDraw.setText(Integer.toString(iCountDraw));
    }
}