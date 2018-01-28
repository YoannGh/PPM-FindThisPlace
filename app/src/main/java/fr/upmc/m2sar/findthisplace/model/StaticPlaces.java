package fr.upmc.m2sar.findthisplace.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.upmc.m2sar.findthisplace.game.GameDifficulty;

public class StaticPlaces {

    private static final Map<GameDifficulty, List<LatLng>> places;
    static {
        List<LatLng> sLvl0Places = new LinkedList<>();
        List<LatLng> sLvl1Places = new LinkedList<>();
        List<LatLng> sLvl2Places = new LinkedList<>();
        Map<GameDifficulty, List<LatLng>> sMap = new HashMap<>();

        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));
        sLvl0Places.add(new LatLng(48.85721, 2.34144));

        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));
        sLvl1Places.add(new LatLng(48.85721, 2.34144));

        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));
        sLvl2Places.add(new LatLng(48.85721, 2.34144));

        sMap.put(GameDifficulty.NOVICE, sLvl0Places);
        sMap.put(GameDifficulty.MEDIUM, sLvl1Places);
        sMap.put(GameDifficulty.EXPERT, sLvl2Places);

        places = Collections.unmodifiableMap(sMap);
    }

    public static List<LatLng> getRandomPlacesForDifficulty(GameDifficulty difficulty, int maxPlaces) {
        List<LatLng> res = new ArrayList<>();
        List<LatLng> difficultyPlaces = places.get(difficulty);
        Collections.shuffle(difficultyPlaces);

        int min = Math.min(maxPlaces, difficultyPlaces.size());
        for (int i = 0; i < min; i++) {
            res.add(difficultyPlaces.get(i));
        }
        return res;
    }

}
