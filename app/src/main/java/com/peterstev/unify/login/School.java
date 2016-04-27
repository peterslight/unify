package com.peterstev.unify.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class School {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("api_token")
    @Expose
    private String apiToken;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The apiToken
     */
    public String getApiToken() {
        return apiToken;
    }

    /**
     * @param apiToken The api_token
     */
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

}
