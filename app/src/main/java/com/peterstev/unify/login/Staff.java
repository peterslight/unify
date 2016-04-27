package com.peterstev.unify.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Staff {

    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("full_name")
    @Expose
    private String fullName;


    /**
     *
     * @return
     * The schoolId
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     *
     * @param schoolId
     * The school_id
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The apiToken
     */
    public String getApiToken() {
        return apiToken;
    }

    /**
     *
     * @param apiToken
     * The api_token
     */
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}