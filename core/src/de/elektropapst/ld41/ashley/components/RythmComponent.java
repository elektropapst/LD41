package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import de.elektropapst.ld41.input.CommandKeys;

public class RythmComponent implements Component, Pool.Poolable {

    public RythmComponent() {
        for (int i = 0; i < keyChain.length; i++) {
            keyChain[i] = CommandKeys.EMPTY;
        }
    }

    @Override
    public void reset() {
        for (int i = 0; i < keyChain.length; i++) {
            keyChain[i] = CommandKeys.EMPTY;
        }

        musicBpm = 140f;
        crotchet = 60f / musicBpm;
        musicPosition = 0;
        lastBeat = 0;

        numBeats = 4;
        currentBeat = 0;

        state = States.RECORDING;
    }

    public enum States {
        RECORDING,
        EXECUTING
    }

    public float musicBpm = 140f;
    //    public float musicBpm = 150f;
//    public float musicBpm = 157f;
//    public float musicBpm = 135f;
    public float crotchet = 60f / musicBpm;
    public float musicPosition;
    public float lastBeat;

    public int numBeats = 4;
    public int currentBeat = 0;

    public States state = States.RECORDING;

    //    public int combo = 0;
    public CommandKeys[] keyChain = new CommandKeys[numBeats];
}
