package peraride.ce.pdn.edu.peraride.data.model;



public class DockLocation {
    private double latitude;
    private double longitude;
    private String place;

    public DockLocation(double latitude, double longitude,String place){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setPlace(place);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
