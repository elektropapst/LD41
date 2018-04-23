package de.elektropapst.ld41.input;

import com.badlogic.gdx.utils.Array;

import java.util.Objects;

public class Commands {

    public static final Commands MOVE_UP = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.UP, CommandKeys.UP, CommandKeys.COMMIT}
    );
    public static final Commands MOVE_DOWN = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.DOWN, CommandKeys.DOWN, CommandKeys.COMMIT}
    );
    public static final Commands MOVE_LEFT = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.LEFT, CommandKeys.LEFT, CommandKeys.COMMIT}
    );
    public static final Commands MOVE_RIGHT = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.RIGHT, CommandKeys.RIGHT, CommandKeys.COMMIT}
    );
    public static final Commands SHOOT_RIGHT = new Commands(
            new CommandKeys[]{CommandKeys.SHOOT, CommandKeys.RIGHT, CommandKeys.RIGHT, CommandKeys.COMMIT}
    );

    public static final Commands MOVE_RIGHT_UP = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.RIGHT, CommandKeys.UP, CommandKeys.COMMIT},
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.UP, CommandKeys.RIGHT, CommandKeys.COMMIT}
    );
    public static final Commands MOVE_RIGHT_DOWN = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.RIGHT, CommandKeys.DOWN, CommandKeys.COMMIT},
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.DOWN, CommandKeys.RIGHT, CommandKeys.COMMIT}
    );
    public static final Commands MOVE_LEFT_DOWN = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.LEFT, CommandKeys.DOWN, CommandKeys.COMMIT},
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.DOWN, CommandKeys.LEFT, CommandKeys.COMMIT}
    );
    public static final Commands MOVE_LEFT_UP = new Commands(
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.LEFT, CommandKeys.UP, CommandKeys.COMMIT},
            new CommandKeys[]{CommandKeys.MOVE, CommandKeys.UP, CommandKeys.LEFT, CommandKeys.COMMIT}
    );



    private Array<CommandKeys[]> registeredPatterns;

    public Commands(CommandKeys[]... keys) {
        registeredPatterns = new Array<>();
        for (CommandKeys[] ks : keys) {
            registeredPatterns.add(ks);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commands commands = (Commands) o;

        for (CommandKeys[] pattern : registeredPatterns) {
            for (CommandKeys[] oPattern : commands.registeredPatterns) {
                if (pattern[0] == oPattern[0] &&
                        pattern[1] == oPattern[1] &&
                        pattern[2] == oPattern[2] &&
                        pattern[3] == oPattern[3]) return true;
            }
        }

        return false;

    }

}
