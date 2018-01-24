package fr.upmc.m2sar.findthisplace.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.adapters.ScoreAdapter;
import fr.upmc.m2sar.findthisplace.model.Score;
import fr.upmc.m2sar.findthisplace.model.ScoreViewModel;

public class ScoreFragment extends ListFragment {

    private ArrayAdapter<Score> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_list_score, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ScoreViewModel viewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        viewModel.update();

        adapter = new ScoreAdapter(getContext(), viewModel.getScores().getValue());
        setListAdapter(adapter);

        viewModel.getScores().observe(this, e -> {
            adapter.notifyDataSetChanged();
        });
    }
}
