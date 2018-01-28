package fr.upmc.m2sar.findthisplace.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.model.Score;

public class ScoreAdapter extends ArrayAdapter<Score> {

    private static final String TAG = "ScoreAdapter";

    private List<Score> scores;
    private SimpleDateFormat dateFormat;

    public ScoreAdapter(@NonNull Context context, @NonNull List<Score> scores) {
        super(context, 0, scores);
        this.scores = scores;
        dateFormat = new SimpleDateFormat(context.getString(R.string.date_format), Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            // on recycle une vue
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_score, parent, false);
        }

        TextView playerNameTV = convertView.findViewById(R.id.score_player_name);
        TextView dateTV = convertView.findViewById(R.id.score_date);
        TextView difficultyTV = convertView.findViewById(R.id.score_difficulty);
        TextView modeTV = convertView.findViewById(R.id.score_mode);
        TextView scoreTV = convertView.findViewById(R.id.score_score);

        Score score = getItem(position);

        if(score != null) {
            playerNameTV.setText(score.getPlayerName());
            dateTV.setText(dateFormat.format(score.getDate()));
            difficultyTV.setText(score.getDifficulty());
            modeTV.setText(score.getMode());
            scoreTV.setText(score.getScore() + "");
        }
        return convertView;
    }
}
