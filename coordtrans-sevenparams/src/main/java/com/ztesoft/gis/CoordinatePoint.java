package com.ztesoft.gis;

public class CoordinatePoint {
    private String dh = "";
    private double x_lat = 0.0;
    private double y_lon = 0.0;
    private double h_z = 0.0;

    public String GetDH() {
        return this.dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public double GetXorLat() {
        return x_lat;
    }

    public void SetXorLat(double x_lat) {
        this.x_lat = x_lat;
    }

    public double GetYorLon() {
        return y_lon;
    }

    public void SetYorLon(double y_lon) {
        this.y_lon = y_lon;
    }

    public double GetHorZ() {
        return h_z;
    }

    public void SetHorZ(double h_z) {
        this.h_z = h_z;
    }

    public CoordinatePoint() {

    }

    public CoordinatePoint(String sDH, double dX, double dY, double dH) {
        dh = sDH;
        x_lat = dX;
        y_lon = dY;
        h_z = dH;
    }
}
