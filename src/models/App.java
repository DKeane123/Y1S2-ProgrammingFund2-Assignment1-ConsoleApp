package models;

import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class App {

    private Developer developer;
    private String appName = "No app name";
    private double appSize = 0;
    private double appVersion = 1.0;
    private double appCost = 0;
    private List<Rating> ratings = new ArrayList<>();

    public App(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
    }

    public abstract boolean isRecommendedApp();

    public String appSummary() {
        return appName + "(V" + appVersion + ") by " + developer + ", €" + appCost + "Rating: " + calculateRating();
    }

    public boolean addRating(Rating rating) {
        return ratings.add(rating);
    }

    public String listRatings() {
        if (ratings.isEmpty()) {
            return "No ratings added yet";
        } else {
            String listRatings = "";
            for (int i = 0; i < ratings.size(); i++) {
                listRatings += ratings.get(i) + "\n";
            }
            return listRatings;
        }
    }

    public double calculateRating() {
        double sumOfRating = 0.0;
        double averageRating = 0.0;

        if (ratings.isEmpty()) {
            return 0;
        } else {
            for (int i = 0; i < ratings.size(); i++) {
                if (ratings.get(i).getNumberOfStars() > 0) {
                    sumOfRating += (ratings.get(i).getNumberOfStars());
                }
            }
            averageRating = sumOfRating / (ratings.size());
            return averageRating;
        }
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public double getAppSize() {
        return appSize;
    }

    public void setAppSize(double appSize) {
        if (Utilities.validRange(appSize, 1, 1000)) {
            this.appSize = appSize;
        }
    }

    //Setters//

    public double getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(double appVersion) {
        if (appVersion >= 1.0) {
            this.appVersion = appVersion;
        }
    }

    public double getAppCost() {
        return appCost;
    }

    public void setAppCost(double appCost) {
        if (appCost >= 0) {
            this.appCost = appCost;
        }
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    @Override
    public String toString() {
        return appName + " (Version " + appVersion + ") " + developer + ", "
                + appSize + "MB" + ", " + "Cost: " + appCost + ", " + "Ratings (" + calculateRating() + ")";
    }
}