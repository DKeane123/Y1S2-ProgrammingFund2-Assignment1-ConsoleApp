package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductivityAppTest {

    private ProductivityApp proAppBelowBoundary, proAppOnBoundary, proAppAboveBoundary, proAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        proAppBelowBoundary = new ProductivityApp(developerLego, "WeDo", 1, 1.0, 0);
        proAppOnBoundary = new ProductivityApp(developerLego, "Spike", 1000, 2.0, 1.99);
        proAppAboveBoundary = new ProductivityApp(developerLego, "EV3", 1001, 3.5, 2.99);
        proAppInvalidData = new ProductivityApp(developerLego, "", -1, 0, -1.00);
    }

    @AfterEach
    void tearDown() {
        proAppBelowBoundary = proAppOnBoundary = proAppAboveBoundary = proAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Test
    void testToString() {
        ProductivityApp proApp = setupProductivityApp(3, 4);
        String stringContents = proApp.toString();

        assertTrue(stringContents.contains(proApp.getAppName()));
        assertTrue(stringContents.contains("(Version " + proApp.getAppVersion()));
        assertTrue(stringContents.contains(proApp.getDeveloper().toString()));
        assertTrue(stringContents.contains(proApp.getAppSize() + "MB"));
        assertTrue(stringContents.contains("Cost: " + proApp.getAppCost()));
        assertTrue(stringContents.contains("Ratings (" + proApp.calculateRating()));

        //contains list of ratings too
        assertTrue(stringContents.contains("Billy Bob"));
        assertTrue(stringContents.contains("Very Good"));
        assertTrue(stringContents.contains("Bobby Bill"));
        assertTrue(stringContents.contains("Excellent"));
    }

    ProductivityApp setupProductivityApp(int rating1, int rating2) {
        ProductivityApp proApp = new ProductivityApp(developerLego, "WeDo", 1, 1.0, 1.99);
        proApp.addRating(new Rating(rating1, "Billy Bob", "Very Good"));
        proApp.addRating(new Rating(rating2, "Bobby Bill", "Excellent"));
        assertEquals(2, proApp.getRatings().size());
        assertEquals(1.99, proApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), proApp.calculateRating(), 0.01);

        return proApp;
    }

    @Nested
    class RecommendedApp {

        @Test
        void appIsNotRecommendedBelowPrice() {
            ProductivityApp proApp = setupProductivityApp(5, 5);
            proApp.setAppCost(1.98);
            assertFalse(proApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan3() {
            ProductivityApp proApp = setupProductivityApp(3, 2);
            assertFalse(proApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            ProductivityApp proApp = new ProductivityApp(developerLego, "WeDo", 1, 1.0, 1.99);
            assertFalse(proApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            ProductivityApp proApp = setupProductivityApp(3, 4);
            assertTrue(proApp.isRecommendedApp());
        }
    }
}
