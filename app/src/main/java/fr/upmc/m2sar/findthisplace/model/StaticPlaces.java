package fr.upmc.m2sar.findthisplace.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.upmc.m2sar.findthisplace.game.GameDifficulty;


/**
 * Liste statique des lieux à deviner en fonction de la difficulté
 */
public class StaticPlaces {

    private static final Map<GameDifficulty, List<LatLng>> places;
    static {
        List<LatLng> sLvl0Places = new LinkedList<>();
        List<LatLng> sLvl1Places = new LinkedList<>();
        List<LatLng> sLvl2Places = new LinkedList<>();
        Map<GameDifficulty, List<LatLng>> sMap = new HashMap<>();

        sLvl0Places.add(new LatLng(48.85721, 2.34144));         // Pont Neuf, Cité, Paris
        sLvl0Places.add(new LatLng(27.1746278, 78.0416659));    // Taj Mahal, View from Minar
        sLvl0Places.add(new LatLng(19.432567, -99.1330454));    // Ofrenda Día de Muertos Zócalo, Mexico
        sLvl0Places.add(new LatLng(55.7518282, 37.6133549));    // Kremlin
        sLvl0Places.add(new LatLng(-25.3658235, 131.0642449));  // Talinguru Nyakunytjaku – Uluru View, Australie
        sLvl0Places.add(new LatLng(-13.1650709, -72.5447154));  // Historic Sanctuary of Machu Picchu
        sLvl0Places.add(new LatLng(30.3223193, 35.4517788));    // Petra
        sLvl0Places.add(new LatLng(13.412283, 103.8667697));    // Angkor Wat
        sLvl0Places.add(new LatLng(-33.8573944, 151.2154351));  // Sydney Opera House
        sLvl0Places.add(new LatLng(29.9803885, 31.1329825));    // Pyramid of Khufu
        sLvl0Places.add(new LatLng(45.4328051, 12.3405832));    // Doge's Palace, Venise
        sLvl0Places.add(new LatLng(25.197184, 55.274378));      // Burj Khalifa View, Dubai
        sLvl0Places.add(new LatLng(37.0121949, 24.7122498));    // Agios Sostis church and mines, Grece
        sLvl0Places.add(new LatLng(48.8583734, 2.2943675));     // Tour Eiffel, France
        sLvl0Places.add(new LatLng(-22.9518225, -43.2103652));  // Cristo Redentor, Rio de Janeiro
        sLvl0Places.add(new LatLng(51.5005644, -0.1223387));    // Westminter, Londres
        sLvl0Places.add(new LatLng(41.4032857, 2.174673));      // Sagrada Familia, Barcelone, Espagne
        sLvl0Places.add(new LatLng(50.8464097, 4.3528549));     // Grand Place, Bruxelles, Belgique
        sLvl0Places.add(new LatLng(36.0659533, -112.1168492));  // Grand Canyon, CO, US
        sLvl0Places.add(new LatLng(41.0093889, 28.979319));     // Sainte-Sophie, Istanbul, Turquie

        sLvl1Places.add(new LatLng(47.5272818, 108.5517319));   // Outside a Mongolian yurt (Ger)
        sLvl1Places.add(new LatLng(23.177927, 80.0256708));     // Indian Institute of Information Technology, Design & Manufacturing, Jabalpur
        sLvl1Places.add(new LatLng(-34.6057175, -58.4359081));  // Parque Centenario Buenos Aires
        sLvl1Places.add(new LatLng(36.4226174, 9.2182043));     // Archaeological Site of Dougga, Tunisie
        sLvl1Places.add(new LatLng(29.6077829, 52.5525694));    // Maison Qavam, Iran
        sLvl1Places.add(new LatLng(43.8379625, 7.8292365));     // Sant’Egidio Church, Bussana Vecchia, Italie
        sLvl1Places.add(new LatLng(60.1329731, 6.7540072));     // Trolltunga, Norvege
        sLvl1Places.add(new LatLng(50.9572495, -1.7520579));    // Brook Lane, Woodgreen, UK
        sLvl1Places.add(new LatLng(14.6735222, -17.4491276));   // Omarienne Mosque, Dakar
        sLvl1Places.add(new LatLng(47.181402, 0.0517099));      // Fontevraud Royal Abbey, France
        sLvl1Places.add(new LatLng(35.3594829, 138.7312602));   // Mont Fuji, Japon
        sLvl1Places.add(new LatLng(47.6151872, -122.3559095));  // Olympic Sculpture Park, Seattle
        sLvl1Places.add(new LatLng(44.9966156, 7.6060196));     // Palazzina di Caccia di Stupinigi, Turin
        sLvl1Places.add(new LatLng(59.3258838, 18.0718926));    // Gamla stan, Suede
        sLvl1Places.add(new LatLng(68.5090469, 27.481808));     // Aurora Borealis, Finland
        sLvl1Places.add(new LatLng(35.7090719, 139.720969));    // Université Waseda, Japon
        sLvl1Places.add(new LatLng(37.2737157, -76.7020864));   // Colonial Williamsburg, Virginie, US
        sLvl1Places.add(new LatLng(30.0290007, 31.2596442));    // Citadelle du Caire, Egypte
        sLvl1Places.add(new LatLng(35.0127618, 135.7503382));   // Nijō-jō, Kyoto, Japon
        sLvl1Places.add(new LatLng(55.0223933, -2.3154889));    // Hadrian's Wall

        sLvl2Places.add(new LatLng(-2.6638703, -42.858229));    // Parque Nacional Lençóis Maranhenses - Lagoa Azul
        sLvl2Places.add(new LatLng(62.093947, -7.413442));      // Gasadalur, Faroe Islands
        sLvl2Places.add(new LatLng(-34.019529, 23.9036379));    // Garden Route National Park: Tsitsikamma
        sLvl2Places.add(new LatLng(-2.863632, 37.9038121));     // Outside a Maasai boma
        sLvl2Places.add(new LatLng(76.4207938, -82.8941163));   // Relocation Monument, Grise Fiord, NU
        sLvl2Places.add(new LatLng(37.8283609, -75.9948723));   // Wetlands Tangier
        sLvl2Places.add(new LatLng(-8.680658, 119.5568211));    // Padar Island, Indonesie
        sLvl2Places.add(new LatLng(35.7811681, 129.222871));    // Gyeongju National Park, Korea
        sLvl2Places.add(new LatLng(69.2079432, -51.1630068));   // Ilulissat Saavat - Ilulissat Waterfront, Groenland
        sLvl2Places.add(new LatLng(37.7292321, -119.6357516));  // Yosemite climbing, CA US
        sLvl2Places.add(new LatLng(-3.1377605, -60.4933549));   // Rio Negro, Amazonie
        sLvl2Places.add(new LatLng(51.7782918, -3.5549115));    // Waterfall Country Woodlands, Wales
        sLvl2Places.add(new LatLng(-21.1854322, 55.5295826));   // Grand Bassin, La Réunion
        sLvl2Places.add(new LatLng(57.3247028, -4.4403388));    // Loch Ness - Urquhart Castle, Ecosse
        sLvl2Places.add(new LatLng(-0.4436351, -91.0923774));   // Tortoises, Galapagos
        sLvl2Places.add(new LatLng(24.4131884, 54.4757479));    // Mosque Yard, Abou Dabi
        sLvl2Places.add(new LatLng(42.4338148, 143.4013077));   // Asahihama Pillbox, Bunker, Japon
        sLvl2Places.add(new LatLng(19.9216713, 99.0405333));    // Tea plantation, Thailande
        sLvl2Places.add(new LatLng(56.1592298, 10.1914215));    // The Old Town, Aarhus, Danemark
        sLvl2Places.add(new LatLng(19.4197508, -155.2882052));  // Hawaii Volcanoes National Park

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
