package fr.upmc.m2sar.findthisplace.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import fr.upmc.m2sar.findthisplace.R;

/**
 * Calcul du score renvoyant le score maximal si le bon pays est trouvé
 * 0 sinon
 */
public class CountryBasedScoreCalculator implements IScoreCalculatorStrategy {

    private Context context;
    private Geocoder geocoder;

    public CountryBasedScoreCalculator(Context context) {
        this.context = context;
        this.geocoder = new Geocoder(context);
    }

    @Override
    public long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty) {
        long score = 0;
        String countryName = "";
        try {
            List<Address> expAddr = geocoder.getFromLocation(expected.latitude, expected.longitude, 1);
            List<Address> guessAddr = geocoder.getFromLocation(guess.latitude, guess.longitude, 1);
            if(expAddr.size() == 0 || guessAddr.size() == 0) {
                score = 0;
            }
            else {
                String expCountryCode = expAddr.get(0).getCountryCode();
                String guessCountryCode = guessAddr.get(0).getCountryCode();
                if (expCountryCode == null || guessCountryCode == null
                     || expCountryCode.length() == 0 || guessCountryCode.length() ==  0 ) {
                    score = 0;
                } else {
                    if (expCountryCode.equalsIgnoreCase(guessCountryCode)) {
                        score = MAX_SCORE_LVL0  + (difficulty.ordinal()*DIFFICULTY_MULTIPLIER_BONUS);
                        countryName = expAddr.get(0).getCountryName();
                    } else {
                        score = 0;
                    }
                }
            }
        } catch (IOException e) {
            return 0;
        }

        String title;
        String message;

        // dialog affichant le nom du pays que l'utilisateur a trouvé
        if(score > 0) {
            title = context.getResources().getString(R.string.correct_country_dialog_title);
            message = context.getResources().getString(R.string.correct_country_dialog).replace("XXX", countryName);
        } else {
            title = context.getResources().getString(R.string.wrong_country_dialog_title);
            message = context.getResources().getString(R.string.wrong_country_dialog);
        }

        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.OK, (DialogInterface dialog, int which) -> {
                    dialog.dismiss();
        }).show();

        return score;
    }

}
