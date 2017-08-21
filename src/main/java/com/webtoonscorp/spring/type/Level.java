package com.webtoonscorp.spring.type;

import sun.plugin.dom.exception.InvalidStateException;

public enum  Level {

    BASIC(1),
    SILVER(2),
    GOLD(3);

    private int level;

    Level(int level) {
        this.level = level;
    }

    public int intValue() {
        return level;
    }

    public static Level valueOf(int level) {

        switch (level) {

            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;

            default: throw new InvalidStateException("Unknown value : " + level);
        }
    }
}
