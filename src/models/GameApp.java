package models;

public class GameApp extends App {

    private boolean isMultiplayer;

    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        setMultiplayer(isMultiplayer);
    }

    public boolean isRecommendedApp() {
        return (isMultiplayer) && (super.calculateRating() >= 4.0);
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public String appSummary() {
        return "Is this multiplayer: " + isMultiplayer + ", " + super.appSummary();
    }

    public String toString() {
        return "Is this multiplayer: " + isMultiplayer + ", " + super.toString() + "," + listRatings();
    }
}
