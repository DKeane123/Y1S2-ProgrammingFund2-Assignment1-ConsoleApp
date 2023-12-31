package models;

import utils.Utilities;

public class EducationApp extends App {

    private int level = 0;

    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        super(developer, appName, appSize, appVersion, appCost);
        setLevel(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (Utilities.validRange(level, 1, 10)) {
            this.level = level;
        }
    }

    public boolean isRecommendedApp() {
        if ((super.getAppCost() > 0.99) && (super.calculateRating() >= 3.5) && (level >= 3)) {
            return true;
        } else {
            return false;
        }
    }

    public String appSummary() {
        return "level " + level + ", " + super.appSummary();
    }

    @Override
    public String toString() {
        return super.toString() + " Level: " + level + ", " + listRatings();
    }
}

