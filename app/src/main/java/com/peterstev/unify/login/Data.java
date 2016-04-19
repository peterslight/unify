package com.peterstev.unify.login;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("staff")
@Expose
private Staff staff;
@SerializedName("schools")
@Expose
private List<String> schools = new ArrayList<String>();

/**
* 
* @return
* The staff
*/
public Staff getStaff() {
return staff;
}

/**
* 
* @param staff
* The staff
*/
public void setStaff(Staff staff) {
this.staff = staff;
}

/**
* 
* @return
* The schools
*/
public List<String> getSchools() {
return schools;
}

/**
* 
* @param schools
* The schools
*/
public void setSchools(List<String> schools) {
this.schools = schools;
}

    @Override
    public String toString() {
        return "Data{" +
                "staff=" + staff.getFullName() +
                ", schools=" + schools.toString() +
                '}';
    }
}