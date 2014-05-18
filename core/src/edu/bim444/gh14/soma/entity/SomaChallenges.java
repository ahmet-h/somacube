package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.soma.Assets;

import java.util.HashMap;

public class SomaChallenges {

    private static final String DATA_FILE = "challenges.dat";

    public static final int[] SOMA_CUBE = new int[]{
            0, 0, 0,
            0, 0, 1,
            0, 0, 2,
            0, 1, 0,
            0, 1, 1,
            0, 1, 2,
            0, 2, 0,
            0, 2, 1,
            0, 2, 2,
            1, 0, 0,
            1, 0, 1,
            1, 0, 2,
            1, 1, 0,
            1, 1, 1,
            1, 1, 2,
            1, 2, 0,
            1, 2, 1,
            1, 2, 2,
            2, 0, 0,
            2, 0, 1,
            2, 0, 2,
            2, 1, 0,
            2, 1, 1,
            2, 1, 2,
            2, 2, 0,
            2, 2, 1,
            2, 2, 2};

    private HashMap<Integer, SomaChallenge> challenges;
    private Array<Integer> challengeIds;

    public SomaChallenges() {
        challenges = new HashMap<>();
        challengeIds = new Array<>();

        put(new SomaChallenge(1, "Soma Cube", Assets.placeholder, SOMA_CUBE));
        put(new SomaChallenge(2, "Soma Cube", Assets.placeholder, SOMA_CUBE));
        put(new SomaChallenge(3, "Soma Cube", Assets.placeholder, SOMA_CUBE));
        put(new SomaChallenge(4, "Soma Cube", Assets.placeholder, SOMA_CUBE));
        put(new SomaChallenge(5, "Soma Cube", Assets.placeholder, SOMA_CUBE));
        put(new SomaChallenge(6, "Soma Cube", Assets.placeholder, SOMA_CUBE));
    }

    public SomaChallenge getChallengeById(int id) {
        return challenges.get(id);
    }

    public SomaChallenge getChallengeByIndex(int index) {
        return challenges.get(challengeIds.get(index));
    }

    public int getSize() {
        return challengeIds.size;
    }

    private void put(SomaChallenge challenge) {
        challenges.put(challenge.getId(), challenge);
        challengeIds.add(challenge.getId());
    }

    public void dispose() {
        challenges.clear();
        challengeIds.clear();
        challenges = null;
        challengeIds = null;
    }

    public void load() {
        FileHandle dataFile = Gdx.files.local(DATA_FILE);
        if(!dataFile.exists()) {
            dataFile.writeString("0", false);
            return;
        }

        // Get user best times...
    }

    public void save() {

    }

}
