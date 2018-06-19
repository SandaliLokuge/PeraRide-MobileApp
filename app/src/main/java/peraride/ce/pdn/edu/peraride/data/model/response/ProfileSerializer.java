package peraride.ce.pdn.edu.peraride.data.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by user on 5/27/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileSerializer implements Serializable{
    private String rider_firstName;
    private String rider_lastName;
    private String rider_phone;
    private String rider_regNo;
    private String rider_email;
    private  String nic;

    public ProfileSerializer() {
    }

    public ProfileSerializer(String rider_firstName, String rider_lastName, String rider_phone, String rider_regNo, String rider_email, String nic) {
        this.setRider_firstName(rider_firstName);
        this.setRider_lastName(rider_lastName);
        this.setRider_phone(rider_phone);
        this.setRider_regNo(rider_regNo);
        this.setRider_email(rider_email);
        this.setNic(nic);
    }

    public String getRider_firstName() {
        return rider_firstName;
    }

    public void setRider_firstName(String rider_firstName) {
        this.rider_firstName = rider_firstName;
    }

    public String getRider_lastName() {
        return rider_lastName;
    }

    public void setRider_lastName(String rider_lastName) {
        this.rider_lastName = rider_lastName;
    }

    public String getRider_phone() {
        return rider_phone;
    }

    public void setRider_phone(String rider_phone) {
        this.rider_phone = rider_phone;
    }

    public String getRider_regNo() {
        return rider_regNo;
    }

    public void setRider_regNo(String rider_regNo) {
        this.rider_regNo = rider_regNo;
    }

    public String getRider_email() {
        return rider_email;
    }

    public void setRider_email(String rider_email) {
        this.rider_email = rider_email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
