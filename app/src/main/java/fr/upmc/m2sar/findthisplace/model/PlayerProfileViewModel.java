package fr.upmc.m2sar.findthisplace.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayerProfileViewModel extends AndroidViewModel {

    private static final String TAG = "PlayerProfileViewModel";

    private static final String PLAYER_FILENAME = "players.data";
    private LiveData<List<PlayerProfile>> players;

    public PlayerProfileViewModel(Application context) {
        super(context);
    }

    public LiveData<List<PlayerProfile>> getPlayerProfiles() {
        if(players == null) {
            players = getData();
        }
        return players;
    }

    private LiveData<List<PlayerProfile>> getData() {
        final MutableLiveData<List<PlayerProfile>> data = new MutableLiveData<>();
        File f = getApplication().getBaseContext().getFileStreamPath(PLAYER_FILENAME);
        if(!f.exists()) {
            data.setValue(new ArrayList<>());
            players = data;
            saveData();
        } else {
            data.setValue(loadData());
        }
        return data;
    }

    private List<PlayerProfile> loadData() {
        List<PlayerProfile> players = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = getApplication().openFileInput(PLAYER_FILENAME);
            ois = new ObjectInputStream(fis);
            players = (ArrayList<PlayerProfile>) ois.readObject();
        } catch (IOException |ClassNotFoundException e) {
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
        return players;
    }

    public void saveData() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            getApplication().getBaseContext().getFileStreamPath(PLAYER_FILENAME).delete();
            fos = getApplication().openFileOutput(PLAYER_FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(players.getValue());
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
