package com.usc.market.common;

public enum PickupLoc {

    Tommy_Trojan, Hecuba, Lyon_Center, SAL, USC_Bookstore, Leavey;

    public static boolean isValid(int loc) {
        return loc >= 0 && loc < PickupLoc.values().length;
    }
}
