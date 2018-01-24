package fr.upmc.m2sar.findthisplace.model;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    private static final String SCORE_FILENAME = "scores.data";
    private LiveData<List<Score>> scores;

    public ScoreViewModel(Application context) {
        super(context);
    }

    public void update() {
        if(this.scores != null) {
            return;
        }
        scores = getData();
    }

    public LiveData<List<Score>> getScores() {
        return scores;
    }

    public boolean addScore(Score s) {
        return scores != null && scores.getValue().add(s);
    }

    private LiveData<List<Score>> getData() {
        final MutableLiveData<List<Score>> data = new MutableLiveData<>();
        File f = getApplication().getBaseContext().getFileStreamPath(SCORE_FILENAME);
        if(!f.exists()) {
            data.setValue(new ArrayList<>());
            saveData();
        } else {
            data.setValue(loadData());
        }
        return data;
    }

    private LiveData<List<Score>> getDataa(Context context) {
        final MutableLiveData<List<Score>> data = new MutableLiveData<>();
        List<Score> scores = loadData();
        data.setValue(scores);
        return data;
    }

    private List<Score> loadData() {
        List<Score> scores = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = getApplication().openFileInput(SCORE_FILENAME);
            ois = new ObjectInputStream(fis);
            scores = (List<Score>) ois.readObject();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ois != null)
                    ois.close();
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scores;
    }

    public void saveData() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = getApplication().openFileOutput(SCORE_FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(scores.getValue());
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(oos != null)
                    oos.close();
                if(fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}