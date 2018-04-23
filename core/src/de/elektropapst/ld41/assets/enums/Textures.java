package de.elektropapst.ld41.assets.enums;

public enum Textures {

    BTN_EMPTY("texture/btnEmpty.png"),
    BTN_EMPTY_LIGHT("texture/btnEmptyLight.png"),

    BTN_UP("texture/btnUp.png"),
    BTN_DOWN("texture/btnDown.png"),
    BTN_LEFT("texture/btnLeft.png"),
    BTN_RIGHT("texture/btnRight.png"),

    BTN_MOVE("texture/btnMove.png"),
    BTN_SHOOT("texture/btnShoot.png"),
    BTN_COMMIT("texture/btnCommit.png"),

    SHIP("texture/ship.png"),
    METEOR_01("texture/meteor01.png"),
    METEOR_02("texture/meteor02.png"),
    METEOR_03("texture/meteor03.png"),
    METEOR_04("texture/meteor04.png"),
    MISSILE_RED("texture/missileRed.png"),
    MISSILE_BLUE("texture/missileBlue.png"),
    MISSILE_GREEN("texture/missileGreen.png"),
    TILEMAP("texture/tilemap.png"),
    BG_SPARKLES("texture/backgroundSparkles2.png"),
    GRAY_SQUARE("texture/graySquare.png"),
    BLUE_SQUARE("texture/blueSquare.png"),
    RED_SQUARE("texture/redSquare.png"),
    ENEMY1("texture/enemy1.png"),
    ENEMY2("texture/enemy2.png");

    public String name;

    Textures(String name) {
        this.name = name;
    }
}
