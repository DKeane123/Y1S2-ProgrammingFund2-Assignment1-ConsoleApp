package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameAppTest {

    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    @BeforeEach
    void setUp() {
        gameAppBelowBoundary = new GameApp(developerLego, "WeDo", 1, 1.0, 0, true);
        gameAppOnBoundary = new GameApp(developerLego, "Spike", 1000, 2.0, 1.99, true);
        gameAppAboveBoundary = new GameApp(developerLego, "EV3", 1001, 3.5, 2.99, false);
        gameAppInvalidData = new GameApp(developerLego, "", -1, 0, -1.00, false);
    }

    @AfterEach
    void tearDown() {
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Test
    void isMultiplayer() {
        assertTrue(gameAppOnBoundary.isMultiplayer());
        assertFalse(gameAppAboveBoundary.isMultiplayer());
    }

    @Test
    void testAppSummary() {
        GameApp gameApp = setupGameApp(5, 4);
        String stringContents = gameApp.appSummary();

        assertTrue(stringContents.contains("Is this multiplayer: " + gameApp.isMultiplayer()));
        assertTrue(stringContents.contains(gameApp.getAppName() + "(V" + gameApp.getAppVersion()));
        assertTrue(stringContents.contains(gameApp.getDeveloper().toString()));
        assertTrue(stringContents.contains("€" + gameApp.getAppCost()));
        assertTrue(stringContents.contains("Rating: " + gameApp.calculateRating()));
    }

    @Test
    void testToString() {
        GameApp gameApp = setupGameApp(5, 4);
        String stringContents = gameApp.toString();

        assertTrue(stringContents.contains("Is this multiplayer: " + gameApp.isMultiplayer()));
        assertTrue(stringContents.contains(gameApp.getAppName()));
        assertTrue(stringContents.contains("(Version " + gameApp.getAppVersion()));
        assertTrue(stringContents.contains(gameApp.getDeveloper().toString()));
        assertTrue(stringContents.contains(gameApp.getAppSize() + "MB"));
        assertTrue(stringContents.contains("Cost: " + gameApp.getAppCost()));
        assertTrue(stringContents.contains("Ratings (" + gameApp.calculateRating()));

        //contains list of ratings too
        assertTrue(stringContents.contains("Billy Bob"));
        assertTrue(stringContents.contains("Very Good"));
        assertTrue(stringContents.contains("Bobby Bill"));
        assertTrue(stringContents.contains("Excellent"));
    }

    GameApp setupGameApp(int rating1, int rating2) {
        GameApp gameApp = new GameApp(developerLego, "WeDo", 1, 1.0, 1.99, true);
        gameApp.addRating(new Rating(rating1, "Billy Bob", "Very Good"));
        gameApp.addRating(new Rating(rating2, "Bobby Bill", "Excellent"));
        assertEquals(2, gameApp.getRatings().size());
        assertEquals(1.99, gameApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), gameApp.calculateRating(), 0.01);

        return gameApp;
    }

    @Nested
    class IsRecommendedApp {

        @Test
        void appIsNotRecommendedWhenGameIsNotMultiplayer() {
            GameApp gameApp = setupGameApp(5, 4);
            gameApp.setMultiplayer(false);
            assertFalse(gameApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan4() {
            GameApp gameApp = setupGameApp(3, 4);
            assertFalse(gameApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            GameApp gameApp = new GameApp(developerLego, "WeDo", 1, 1.0, 1.99, true);
            assertFalse(gameApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            GameApp gameApp = setupGameApp(5, 4);
            assertTrue(gameApp.isRecommendedApp());
        }
    }

}