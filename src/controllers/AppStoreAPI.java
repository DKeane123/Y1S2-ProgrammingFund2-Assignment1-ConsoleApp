package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI {


    private List<App> apps = new ArrayList<>();
    private Random randomGenerator = new Random();

    public boolean addApp(App app) {
        return apps.add(app);
    }

    public String listAllApps() {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listOfApps = "";
            for (int i = 0; i < apps.size(); i++) {
                listOfApps += i + ": " + apps.get(i) + "\n";
            }
            return listOfApps;
        }
    }

    public String listSummaryOfAllApps() {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listAppSummary = "";
            for (int i = 0; i < apps.size(); i++) {
                listAppSummary += i + ": " + apps.get(i).appSummary() + "\n";
            }
            return listAppSummary;
        }
    }

    public String listAllGameApps() {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listGameApps = "";
            for (App app : apps) {
                if (app instanceof GameApp) {
                    listGameApps += apps.indexOf(app) + ": " + app + "\n";
                }
            }
            if (listGameApps.isEmpty()) {
                return "No Game Apps";
            } else {
                return listGameApps;
            }
        }
    }

    public String listAllEducationApps() {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listEducationApps = "";
            for (App app : apps) {
                if (app instanceof EducationApp) {
                    listEducationApps += apps.indexOf(app) + ": " + app + "\n";
                }
            }
            if (listEducationApps.isEmpty()) {
                return "No Education Apps";
            } else {
                return listEducationApps;
            }
        }
    }

    public String listAllProductivityApps() {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listProductivityApps = "";
            for (App app : apps) {
                if (app instanceof ProductivityApp) {
                    listProductivityApps += apps.indexOf(app) + ": " + app + "\n";
                }
            }
            if (listProductivityApps.isEmpty()) {
                return "No Productivity Apps";
            } else {
                return listProductivityApps;
            }
        }
    }

    public String listAllAppsByName(String appName) {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listAppByName = "";
            for (App app : apps) {
                if (app.getAppName().toLowerCase().contains(appName.toLowerCase())) {
                    listAppByName += apps.indexOf(app) + ": " + app + "\n";
                }
            }
            if (listAppByName.isEmpty()) {
                return "No apps for name" + appName + "exists";
            } else {
                return listAppByName;
            }
        }
    }

    public String listAllAppsAboveOrEqualAGivenStarRating(int rating) {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listRatedApps = "";
            for (App app : apps) {
                if (app.calculateRating() >= rating) {
                    listRatedApps += apps.indexOf(app) + ": " + app.toString() + "\n";
                }
            }
            if (listRatedApps.isEmpty()) {
                return "No apps have a rating of " + rating + " or above";
            } else {
                return listRatedApps;
            }
        }
    }

    public String listAllRecommendedApps() {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listRecommendedApps = "";
            for (App app : apps) {
                if (app.isRecommendedApp()) {
                    listRecommendedApps += apps.indexOf(app) + ": " + app + "\n";
                }
            }
            if (listRecommendedApps.isEmpty()) {
                return "No recommended apps";
            } else {
                return listRecommendedApps;
            }
        }
    }

    public String listAllAppsByChosenDeveloper(Developer developer) {
        if (apps.isEmpty()) {
            return "No apps";
        } else {
            String listChosenDeveloper = "";
            for (App app : apps) {
                if (developer.equals(app.getDeveloper())) {
                    listChosenDeveloper += apps.indexOf(app) + ": " + app + "\n";
                }
            }
            if (listChosenDeveloper.isEmpty()) {
                return "No apps for developer: " + developer;
            } else {
                return listChosenDeveloper;
            }
        }
    }

    public int numberOfAppsByChosenDeveloper(Developer developer) {
        int numberChosenDeveloper = 0;
        for (App app : apps) {
            if (app.getDeveloper().equals(developer)) {
                numberChosenDeveloper++;
            }
        }
        return numberChosenDeveloper;
    }

    public App deleteAppByIndex(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return apps.remove(indexToDelete);
        }
        return null;
    }

    public App randomApp() {
        if (apps.isEmpty()) {
            return null;
        } else {
            int index = randomGenerator.nextInt(apps.size());
            return apps.get(index);
        }
    }

    public void simulateRatings() {
        for (App app : apps) {
            app.addRating(generateRandomRating());
        }
    }

    public boolean isValidAppName(String appName) {
        for (App app : apps) {
            if (app.getAppName().equalsIgnoreCase(appName)) {
                return true;
            }
        }
        return false;
    }

    public App getAppByName(String appName) {
        for (App app : apps) {
            if (app.getAppName().equalsIgnoreCase(appName)) {
                return app;
            }
        }
        return null;
    }

    public App getAppByIndex(int indexToFind) {
        if (isValidIndex(indexToFind)) {
            return apps.get(indexToFind);
        }
        return null;
    }

    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }

    public int numberOfApps() {
        return apps.size();
    }

    public void sortAppsByNameAscending() {
        for (int i = apps.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (apps.get(j).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0) {
                    highestIndex = j;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    private void swapApps(List<App> apps, int currentIndex, int highestIndex) {
        App currentApp = apps.get(currentIndex);
        App highestApp = apps.get(highestIndex);

        apps.set(currentIndex, highestApp);
        apps.set(highestIndex, currentApp);
    }

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName() {
        return "apps.xml";
    }

}