package fr.upmc.m2sar.findthisplace.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.adapters.ScoreAdapter;
import fr.upmc.m2sar.findthisplace.model.Score;
import fr.upmc.m2sar.findthisplace.model.ScoreViewModel;

/*
 * Fragment contenant la ListeView des Scores
 */
public class ScoreFragment extends ListFragment {

    private static final String TAG = "ScoreFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_list_score, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // on recupère le model qui existe déjà si l'activité est recréée après un changement de configuration
        // sinon une nouvelle instance est créée
        final ScoreViewModel scoresModel = ViewModelProviders.of(getActivity()).get(ScoreViewModel.class);

        ScoreAdapter adapter = new ScoreAdapter(getActivity(), scoresModel.getScores().getValue());
        setListAdapter(adapter);
    }

}
