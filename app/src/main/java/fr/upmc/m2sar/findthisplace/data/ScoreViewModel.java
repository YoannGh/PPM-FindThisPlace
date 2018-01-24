package fr.upmc.m2sar.findthisplace.data;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yo on 23/01/2018.
 */

public class ScoreViewModel extends AndroidViewModel {

    public static final String SCORE_FILENAME = "scores.data";
    private LiveData<List<Score>> scores;

    public ScoreViewModel(Application context) {
        super(context);
    }

    public void init() {
        if(this.scores != null) {
            return;
        }
        scores = getData();
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

    private LiveData<List<Score>> getData() {
        final MutableLiveData<List<Score>> data = new MutableLiveData<>();
        List<Score> scoresFromFile = null;


        // TODO load scoresFromFile from file
        data.setValue(scoresFromFile);
        return null;
    }

    private void loadData() {
        List<Score> scores = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            File f = new File(SCORE_FILENAME);
            if(f.exists()) {
                fis = getApplication().openFileInput(SCORE_FILENAME);
                ois = new ObjectInputStream(fis);
                scores = (List<Score>) ois.readObject();
                if(scores == null)
                    scores = new ArrayList<Score>();
            } else {
                scores = new ArrayList<Score>();
                saveData();
            }
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
    }

    public void saveData() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(SCORE_FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(scores.getValue());
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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