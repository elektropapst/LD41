package de.elektropapst.ld41.assets.enums;

public enum Sounds {

    SUCCESS("sound/beep.wav"),
    EXPLOSION("sound/spaceExplosion.wav"),
    ERROR("sound/error.wav");

    public String name;

    Sounds(String name) {
        this.name = name;
    }
}
