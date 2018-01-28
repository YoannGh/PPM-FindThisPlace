package fr.upmc.m2sar.findthisplace.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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

    private final int permsRequestCode = 200;
    private boolean hasPermissions = false;

    private Button lvl0Btn;
    private Button lvl1Btn;
    private Button lvl2Btn;
    private Button scoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if(getApplication().getBaseContext().getFileStreamPath("scores.data").exists())
        //    getApplication().getBaseContext().getFileStreamPath("scores.data").delete();

        final String[] perms = { Manifest.permission.INTERNET };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermissions) {
            requestPermissions(perms, permsRequestCode);
        } else {
            hasPermissions = true;
        }

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
            startGameWithDifficultyAndModeIfPerms(GameDifficulty.NOVICE, GameMode.CLASSIC);
        }
        else if(v == lvl1Btn) {
            startGameWithDifficultyAndModeIfPerms(GameDifficulty.MEDIUM, GameMode.CLASSIC);
        }
        else if(v == lvl2Btn) {
            startGameWithDifficultyAndModeIfPerms(GameDifficulty.EXPERT, GameMode.CLASSIC);
        }
        else if(v == scoreBtn) {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        if(this.permsRequestCode == permsRequestCode) {
            boolean internet = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            this.hasPermissions = internet;
        }
    }

    private void startGameWithDifficultyAndModeIfPerms(GameDifficulty difficulty, GameMode mode) {
        if(hasPermissions) {
            Intent intent = new Intent(this, GameMapActivity.class);
            intent.putExtra(GameDifficulty.class.getName(), difficulty);
            intent.putExtra(GameMode.class.getName(), mode);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.no_internet_perm)
                    .setPositiveButton(R.string.OK, (DialogInterface dialog, int which) -> {
                        finish();
            }).show();
        }
    }

}
