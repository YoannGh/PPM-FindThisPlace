package fr.upmc.m2sar.findthisplace.data;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by Yo on 23/01/2018.
 */

public class ScoreViewModel extends ViewModel {

    private LiveData<List<Score>> scores;

    public void init() {
        if(this.scores != null) {
            return;
        }
        scores = loadData();
    }

    public LiveData<List<Score>> getScores() {
        return scores;
    }

    public boolean addScore(Score s) {
        if(scores != null)
            return scores.getValue().add(s);
        else
            return false;
    }

    private LiveData<List<Score>> loadData() {
        final MutableLiveData<List<Score>> data = new MutableLiveData<>();
        List<Score> scoresFromFile = null;
        // TODO load scoresFromFile from file
        data.setValue(scoresFromFile);
        return null;
    }
}
