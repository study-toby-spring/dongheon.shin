package com.webtoonscorp.spring.type;

public enum  Level {

    GOLD(3, null),
    SILVER(2, GOLD),
    BASIC(1, SILVER);

    private int level;
    private Level next;

    Level(int level, Level next) {
        this.level = level;
        this.next = next;
    }

    public int intValue() {
        return level;
    }

    public Level next() {
        return next;
    }

    public static Level valueOf(int level) {

        switch (level) {

            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;

            default: throw new IllegalStateException("Unknown value : " + level);
        }
    }
}
