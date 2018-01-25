package fr.upmc.m2sar.findthisplace.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import fr.upmc.m2sar.findthisplace.R;

public class ScoreActivity extends AppCompatActivity {

    private static final String TAG = "ScoreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // rendre la status bar transparente pour une meilleure visibilité
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
