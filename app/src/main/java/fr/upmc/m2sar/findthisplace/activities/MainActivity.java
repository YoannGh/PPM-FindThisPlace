package fr.upmc.m2sar.findthisplace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.game.GameDifficulty;
import fr.upmc.m2sar.findthisplace.game.GameMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button lvl0Btn;
    private Button lvl1Btn;
    private Button lvl2Btn;
    private Button scoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getApplication().getBaseContext().getFileStreamPath("scores.data").exists())
            getApplication().getBaseContext().getFileStreamPath("scores.data").delete();

        // rendre la status bar transparente pour une meilleure visibilité
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        lvl0Btn = findViewById(R.id.btnPlayLvl0);
        lvl1Btn = findViewById(R.id.btnPlayLvl1);
        lvl2Btn = findViewById(R.id.btnPlayLvl2);
        scoreBtn = findViewById(R.id.btnScores);

        lvl0Btn.setOnClickListener(this);
        lvl1Btn.setOnClickListener(this);
        lvl2Btn.setOnClickListener(this);
        scoreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == lvl0Btn) {
            Intent intent = new Intent(this, GameMapActivity.class);
            intent.putExtra(GameDifficulty.class.getName(), GameDifficulty.NOVICE);
            intent.putExtra(GameMode.class.getName(), GameMode.CLASSIC);
            startActivity(intent);
        }
        else if(v == lvl1Btn) {
            Intent intent = new Intent(this, GameMapActivity.class);
            intent.putExtra(GameDifficulty.class.getName(), GameDifficulty.MEDIUM);
            intent.putExtra(GameMode.class.getName(), GameMode.CLASSIC);
            startActivity(intent);
        }
        else if(v == lvl2Btn) {
            Intent intent = new Intent(this, GameMapActivity.class);
            intent.putExtra(GameDifficulty.class.getName(), GameDifficulty.EXPERT);
            intent.putExtra(GameMode.class.getName(), GameMode.CLASSIC);
            startActivity(intent);
        }
        else if(v == scoreBtn) {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
    }

}
