package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.App;
import models.Developer;
import models.EducationApp;
import models.Rating;
import utils.ScannerInput;

public class Driver {

    //TODO Some skeleton code has been given to you.

    //     Familiarise yourself with the skeleton code...run the menu and then review the skeleton code.
    //     Then start working through the spec.

    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        EducationApp edApp = setupEducationAppWithRating(3, 4);
        System.out.println(edApp.toString());
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppManagement();
                //case 3 -> // TODO run the Reports Menu and the associated methods (your design here)
                case 4 -> searchAppsBySpecificCriteria();
                //case 5 -> // TODO Sort Apps by Name
                //case 6 -> // TODO print the recommended apps
                //case 7 -> // TODO print the random app of the day
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }


    //----------------------------------
    // App Menu
    //----------------------------------

    private int appMenu() {
        System.out.println("""
                 ---------------App Menu---------------
                |   1) Add an App                     |
                |   2) List All Apps                  |
                |   3) List App Summary               |
                |   4) List All Game Apps             |
                |   5) List All Education Apps        |
                |   6) List All Productivity Apps     |
                |   7) List All Recommended Apps      |
                |                                     |
                |   0) RETURN to main menu            |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppManagement() {
        int option = appMenu();
        while (option != 0) {
            switch (option) {
                //case 1 -> addApp;
                case 2 -> System.out.println(appStoreAPI.listAllApps());
                case 3 -> System.out.println(appStoreAPI.listSummaryOfAllApps());
                case 4 -> System.out.println(appStoreAPI.listAllGameApps());
                case 5 -> System.out.println(appStoreAPI.listAllEducationApps());
                case 6 -> System.out.println(appStoreAPI.listAllProductivityApps());
                case 7 -> System.out.println(appStoreAPI.listAllRecommendedApps());
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    //private App addApp() {
    //    String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
    //    String appName = ScannerInput.validNextLine("Please enter the app name: ");
    //    double appSize = ScannerInput.validNextDouble("Please enter the app size");
    //    double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
    //    double appVersion = ScannerInput.validNextDouble("Please enter the app version");
    //    boolean isAdded = appStoreAPI.addApp(new App(developerName, appName, appSize, appCost, appVersion) {
    //        @Override
    //        public boolean isRecommendedApp() {
    //            return false;
    //        }
    //    });
    //    return null;
    //}


    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            case 1 -> searchAppsByName();
            // case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
            // case 3 -> searchAppsEqualOrAboveAStarRating();
            default -> System.out.println("Invalid option");
        }
    }

    private App searchAppsByName() {
        App app = appStoreAPI.getAppByName("Enter the name you wish to find:   ");
        if (app != null) {
            return app;
        } else {
            System.out.println("Name Invalid");
        }
        return null;
    }


    private void simulateRatings() {
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            appStoreAPI.simulateRatings();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        try {
            appStoreAPI.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }


    private void loadAllData() {
        try {
            appStoreAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
    }


    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        Developer developerLego = new Developer("Lego", "www.lego.com");
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;

    }
}