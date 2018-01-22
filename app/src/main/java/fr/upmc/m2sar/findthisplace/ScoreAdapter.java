package fr.upmc.m2sar.findthisplace;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by m2sar on 22/01/2018.
 */

public class ScoreAdapter extends ArrayAdapter<Score> {

    private List<Score> scores;
    private Context context;
    private SimpleDateFormat dateFormat;

    public ScoreAdapter(@NonNull Context context, @NonNull List<Score> scores) {
        super(context, 0, scores);
        this.scores = scores;
        dateFormat = new SimpleDateFormat(context.getString(R.string.date_format));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO

        if(convertView == null) {
            // on recycle une vue
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_score, parent, false);
        }

        TextView playerNameTV = (TextView) convertView.findViewById(R.id.score_player_name);
        TextView dateTV = (TextView) convertView.findViewById(R.id.score_date);
        TextView difficultyTV = (TextView) convertView.findViewById(R.id.score_difficulty);
        TextView scoreTV = (TextView) convertView.findViewById(R.id.score_score);

        Score score = getItem(position);

        playerNameTV.setText(score.getPlayerName());
        dateTV.setText(dateFormat.format(score.getDate()));
        difficultyTV.setText(score.getDifficulty());
        scoreTV.setText(score.getScore() + "");

        return convertView;
    }
}
