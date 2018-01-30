package fr.upmc.m2sar.findthisplace.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.upmc.m2sar.findthisplace.R;
import fr.upmc.m2sar.findthisplace.game.GameDifficulty;
import fr.upmc.m2sar.findthisplace.game.GameMode;
import fr.upmc.m2sar.findthisplace.model.PlacesViewModel;
import fr.upmc.m2sar.findthisplace.model.PlayerProfile;
import fr.upmc.m2sar.findthisplace.model.PlayerProfileViewModel;

/**
 * Activity de démarrage, elle permet de choisir/créer un profil de joueur,
 * d'accéder à l'activité listant les scores et de commencer une partie
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private final int permsRequestCode = 200;

    // clé pour la sauvegarde dans le savedInstanceState
    private static final String STATE_PERMS = "hasPermissions";
    private boolean hasPermissions = false;

    // clé pour la sauvegarde dans le savedInstanceState
    private static final String STATE_VALID_PLAYER_PROFILE = "validPlayerProfile";
    private boolean validPlayerProfile = false;

    private TextView playerNameTV;
    private Button pickPlayerProfile;
    private Button lvl0Btn;
    private Button lvl1Btn;
    private Button lvl2Btn;
    private Button scoreBtn;

    private PlayerProfileViewModel playersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] perms = { Manifest.permission.INTERNET };

        // demande des permissions
        // certainement inutile car l'API Google Maps est inclue dans les Google Services
        // qui requiert déjà cette permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermissions) {
            requestPermissions(perms, permsRequestCode);
        } else {
            hasPermissions = true;
        }

        playerNameTV = findViewById(R.id.playername);
        pickPlayerProfile = findViewById(R.id.btnChangePlayer);
        lvl0Btn = findViewById(R.id.btnPlayLvl0);
        lvl1Btn = findViewById(R.id.btnPlayLvl1);
        lvl2Btn = findViewById(R.id.btnPlayLvl2);
        scoreBtn = findViewById(R.id.btnScores);

        pickPlayerProfile.setOnClickListener(this);
        lvl0Btn.setOnClickListener(this);
        lvl1Btn.setOnClickListener(this);
        lvl2Btn.setOnClickListener(this);
        scoreBtn.setOnClickListener(this);

        // les attributs model permettent de garder en mémoire les données
        // malgré les changements de configuration (rotation de l'écran)
        playersModel = ViewModelProviders.of(this).get(PlayerProfileViewModel.class);

        // on récupère le dernier profil de joueur utilisé
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String lastPlayerName = sharedPref.getString(PlayerProfile.class.getName(), null);
        if(lastPlayerName != null) {
            playerNameTV.setText(lastPlayerName);
            validPlayerProfile = true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        if(this.permsRequestCode == permsRequestCode) {
            boolean internet = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            this.hasPermissions = internet;
        }
    }

    @Override
    public void onClick(View v) {
        if(v == pickPlayerProfile) {
            pickPlayerProfileDialog();
        }
        else if(v == lvl0Btn) {
            chooseGamemodeWithDifficulty(GameDifficulty.NOVICE);
        }
        else if(v == lvl1Btn) {
            chooseGamemodeWithDifficulty(GameDifficulty.MEDIUM);
        }
        else if(v == lvl2Btn) {
            chooseGamemodeWithDifficulty(GameDifficulty.EXPERT);
        }
        else if(v == scoreBtn) {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
    }

    // sauvegarde des attributs qui ne sont pas contenus dans un model
    // méthode appelée lors d'un changement de configuration
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putBoolean(STATE_PERMS, hasPermissions);
        savedInstanceState.putBoolean(STATE_VALID_PLAYER_PROFILE, validPlayerProfile);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    // restauration des attributs qui ne sont pas contenus dans un model
    // méthode appelée lors arpès un changement de configuration
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        hasPermissions = savedInstanceState.getBoolean(STATE_PERMS);
        validPlayerProfile = savedInstanceState.getBoolean(STATE_VALID_PLAYER_PROFILE);
    }

    // si l'utilisateur a choisi un profil de joueur,
    // on lui demande à quel mode de jeu il souhaite jouer
    // sinon on lui demande de choisir un profil de joueur
    private void chooseGamemodeWithDifficulty(GameDifficulty difficulty) {
        if(validPlayerProfile) {
            String[] items = new String[GameMode.values().length];
            items[0] = GameMode.CLASSIC.name() + " " + getResources().getString(R.string.desc_mode_classic);
            items[1] = GameMode.COUNTRY.name() + " " + getResources().getString(R.string.desc_mode_country);
            items[2] = GameMode.REVERSE.name() + " " + getResources().getString(R.string.desc_mode_reverse);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_gamemode_title)
                    .setItems(items, (DialogInterface dialog, int which) -> {
                        startGameWithDifficultyAndModeIfPerms(difficulty, GameMode.values()[which]);
            }).show();
        }
        else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.dialog_no_playerprofile)
                    .setPositiveButton(R.string.OK, (DialogInterface dialog, int which) -> {
                        dialog.dismiss();
            }).show();
        }

    }

    // si l'utilisateur a accordé les permissions (INTERNET seulement)
    // on lance l'activité de jeu avec la configuration choisie
    // sinon on lui signale que l'application a besoin des permissions pour fonctionner
    private void startGameWithDifficultyAndModeIfPerms(GameDifficulty difficulty, GameMode mode) {
        if(hasPermissions) {
            Intent intent = new Intent(this, GameMapActivity.class);
            intent.putExtra(PlayerProfile.class.getName(), playerNameTV.getText());
            intent.putExtra(GameDifficulty.class.getName(), difficulty);
            intent.putExtra(GameMode.class.getName(), mode);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.no_internet_perm)
                    .setPositiveButton(R.string.OK, (DialogInterface dialog, int which) -> {
                        finish();
            }).show();
        }
    }

    // dialog permettant de choisir un profil de joueur
    private void pickPlayerProfileDialog() {
        int numPlayer = playersModel.getPlayerProfiles().getValue().size();
        String[] players = new String[numPlayer];
        PlayerProfile pp;
        // création de la liste des profils qui existent déjà
        for(int i = 0; i < numPlayer; i++) {
            pp = playersModel.getPlayerProfiles().getValue().get(i);
            players[i] = pp.getFirstName() + " " + pp.getLastName();
        }

        // dialog affichant la liste des profils de joueurs déjà créés
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_pick_player_title)
                .setItems(players, (DialogInterface dialog, int which) -> {
                    // lorsque l'utilisateur choisi un élément de la liste
                    // mise à jour de la TextView
                    playerNameTV.setText(players[which]);
                    validPlayerProfile = true;
                    // sauvegarde du choix dans les SharedPreferences
                    saveLastUsedPlayerName(players[which]);
                })
                .setPositiveButton(R.string.dialog_create_new_player, (DialogInterface dialog, int which) -> {
                    // Création de la vue de dialog permettant de créer un nouveau profil
                    LayoutInflater inflater = getLayoutInflater();
                    View createPlayerView = inflater.inflate(R.layout.dialog_create_playerprofile, null);
                    new AlertDialog.Builder(this)
                            .setView(createPlayerView)
                            .setPositiveButton(R.string.OK, (DialogInterface dialogCreate, int whichCreate) -> {
                                String firstName = ((EditText) createPlayerView.findViewById(R.id.create_playerFirstname)).getText().toString();
                                String lastName = ((EditText) createPlayerView.findViewById(R.id.create_playerLastname)).getText().toString();
                                if(firstName != null && firstName.length() > 0
                                        && lastName != null && lastName.length() > 0) {
                                    // sauvegarde du nouveau profil
                                    playersModel.getPlayerProfiles().getValue().add(new PlayerProfile(firstName, lastName));
                                    playersModel.saveData();
                                    String playerName = firstName + " " + lastName;
                                    // mise à jour de la TextView
                                    playerNameTV.setText(playerName);
                                    validPlayerProfile = true;
                                    // sauvegarde du choix dans les SharedPreferences
                                    saveLastUsedPlayerName(playerName);
                                }
                            })
                            .setNegativeButton(R.string.dialog_cancel, (DialogInterface dialogCreate, int whichCreate) -> {
                                // annulation de la création de nouveau profil
                                dialog.dismiss();
                    }).show();
                })
                .setNegativeButton(R.string.dialog_cancel, (DialogInterface dialog, int which) -> {
                    // annulation du choix de profil
                    dialog.dismiss();
                })
        .show();
    }

    // sauvegarde du dernier profil de joueur utilisé dans les SharedPreferences
    private void saveLastUsedPlayerName(String playerName) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PlayerProfile.class.getName(), playerName);
        editor.commit();
    }

}
