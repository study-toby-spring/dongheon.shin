package com.webtoonscorp.spring.domain;

import com.webtoonscorp.spring.type.Level;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    private User user;

    @Before
    public void setup() {

        user = new User();
    }

    @Test
    public void upgradeLevel() {

        Level[] levels = Level.values();

        for (Level level : levels) {

            if (level.next() == null)
                continue;

            user.setLevel(level);
            user.upgradeLevel();

            assertThat(user.getLevel(), is(level.next()));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void cannotUpgradeLevel() {

        Level[] levels = Level.values();

        for (Level level : levels) {

            if (level.next() != null)
                continue;

            user.setLevel(level);
            user.upgradeLevel();
        }
    }
}