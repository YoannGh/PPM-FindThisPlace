package fr.upmc.m2sar.findthisplace;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreActivity extends ListActivity {

    private ArrayAdapter<Score> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Score> scores = new ArrayList<>();
        scores.add(new Score("toto", new Date(), "Dur", 100));
        scores.add(new Score("tata", new Date(), "Izi", 30000000));
        scores.add(new Score("titi", new Date(), "Moyen", 3));

        adapter = new ScoreAdapter(this, scores);

        setListAdapter(adapter);

    }
}
