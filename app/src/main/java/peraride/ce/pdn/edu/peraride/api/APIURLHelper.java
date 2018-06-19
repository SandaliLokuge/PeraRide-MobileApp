package peraride.ce.pdn.edu.peraride.api;

public class APIURLHelper {

    private static final String API_BASE_URL = "https://159.89.238.212/PeraRide/v1";

    public static String getLoginUrl() {
        return API_BASE_URL.concat("/user/login");
    }

    public static String getUnlockUrl() {
        return API_BASE_URL.concat("/user/unlock");
    }

    public static String getProfileUrl(){return API_BASE_URL.concat("/user/getinfo");}

    public  static String getLogoutURL(){return API_BASE_URL.concat("/user/logout");}

    public  static String getChgPasswordURL(){return API_BASE_URL.concat("/user/changePass");}

    public  static String getStationsURL(){return API_BASE_URL.concat("/user/fetchstations");}

    public  static String getStationDataURL(){return API_BASE_URL.concat("/user/stationdata");}

}
