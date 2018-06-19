package peraride.ce.pdn.edu.peraride.data.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;


public class Login implements Serializable {

    private String regNo;
    private String password;

    public Login() {
    }

    public Login(String regNo, String password) {
        this.regNo = regNo;
        this.password = password;
    }

    @JsonGetter("rider_regNo")
    public String getRegNo() {
        return regNo;
    }

    @JsonSetter("rider_regNo")
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    @JsonGetter("rider_password")
    public String getPassword() {
        return password;
    }

    @JsonSetter("rider_password")
    public void setPassword(String password) {
        this.password = password;
    }
}
