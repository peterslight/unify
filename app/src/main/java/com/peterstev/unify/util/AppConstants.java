package com.peterstev.unify.util;

public class AppConstants {

    public static final int LIST_VIEW = 0;
    public static final int GRID_VIEW = 1;

    public static Boolean isLIST = null;
    public static Boolean isGRID = null;

    public static Boolean setIsList() {
        isLIST = true;
        return isLIST;
    }

    public static Boolean setIsGrid() {
        isGRID = true;
        return isGRID;
    }

}
