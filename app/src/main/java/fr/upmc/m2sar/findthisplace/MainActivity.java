package fr.upmc.m2sar.findthisplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button lvl0Btn;
    private Button lvl1Btn;
    private Button lvl2Btn;
    private Button scoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvl0Btn = (Button) findViewById(R.id.btnPlayLvl0);
        lvl1Btn = (Button) findViewById(R.id.btnPlayLvl1);
        lvl2Btn = (Button) findViewById(R.id.btnPlayLvl2);
        scoreBtn = (Button) findViewById(R.id.btnScores);

        lvl0Btn.setOnClickListener(this);
        lvl1Btn.setOnClickListener(this);
        lvl2Btn.setOnClickListener(this);
        scoreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == lvl0Btn) {
            Intent intent = new Intent(this, GameMapActivity.class);
            startActivity(intent);
        }
        else if(v == scoreBtn) {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
    }
}
