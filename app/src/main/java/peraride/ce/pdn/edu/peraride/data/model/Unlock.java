package peraride.ce.pdn.edu.peraride.data.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;


public class Unlock implements Serializable {
    private String token;
    private String lockID;

    public Unlock() {
    }

    public Unlock(String token, String lockID) {
        this.token = token;
        this.lockID = lockID;
    }

    @JsonGetter("token")
    public String getJwtToken() {
        return token;
    }

    @JsonSetter("token")
    public void setJwtToken(String jwtToken) {
        this.token = token;
    }

    @JsonGetter("lock_id")
    public String getLockID() {
        return lockID;
    }

    @JsonSetter("lock_id")
    public void setLockID(String lockID) {
        this.lockID = lockID;
    }
}
