package com.ztesoft.gis;

import java.math.BigDecimal;

public class CoordinatePointPair {

    private String isValid = "";
    private double x_lat_1 = 0.0;
    private double y_lon_1 = 0.0;
    private double h_z_1 = 0.0;
    private double x_lat_2 = 0.0;
    private double y_lon_2 = 0.0;
    private double h_z_2 = 0.0;
    private String guid = "";
    private BigDecimal residualX = new BigDecimal(0.0);
    private BigDecimal residualY = new BigDecimal(0.0);
    private BigDecimal residualZ = new BigDecimal(0.0);

    /**
     * 是否采用
     *
     * @return
     */
    public String getIsValid() {
        return isValid;
    }

    public String getGUID() {
        return guid;
    }

    public BigDecimal getResidualX() {
        return residualX;
    }

    public BigDecimal getResidualY() {
        return residualY;
    }

    public BigDecimal getResidualZ() {
        return residualZ;
    }

    public double getH_z_1() {
        return h_z_1;
    }

    public double getH_z_2() {
        return h_z_2;
    }

    public double getX_lat_1() {
        return x_lat_1;
    }

    public double getX_lat_2() {
        return x_lat_2;
    }

    public double getY_lon_1() {
        return y_lon_1;
    }

    public double getY_lon_2() {
        return y_lon_2;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public void setGUID(String sGUID) {
        this.guid = sGUID;
    }

    public void setH_z_1(double h_z_1) {
        this.h_z_1 = h_z_1;
    }

    public void setH_z_2(double h_z_2) {
        this.h_z_2 = h_z_2;
    }

    public void setResidualX(BigDecimal residualX) {
        this.residualX = residualX;
    }

    public void setResidualY(BigDecimal residualY) {
        this.residualY = residualY;
    }

    public void setResidualZ(BigDecimal residualZ) {
        this.residualZ = residualZ;
    }

    public void setX_lat_1(double x_lat_1) {
        this.x_lat_1 = x_lat_1;
    }

    public void setX_lat_2(double x_lat_2) {
        this.x_lat_2 = x_lat_2;
    }

    public void setY_lon_1(double y_lon_1) {
        this.y_lon_1 = y_lon_1;
    }

    public void setY_lon_2(double y_lon_2) {
        this.y_lon_2 = y_lon_2;
    }

    public CoordinatePointPair() {

    }

    public CoordinatePointPair(double dX1,
                               double dY1,
                               double dX2,
                               double dY2) {
        x_lat_1 = dX1;
        x_lat_2 = dX2;
        y_lon_1 = dY1;
        y_lon_2 = dY2;
    }

    public CoordinatePointPair(double dX1,
                               double dY1,
                               double dH1,
                               double dX2,
                               double dY2,
                               double dH2) {
        x_lat_1 = dX1;
        x_lat_2 = dX2;
        y_lon_1 = dY1;
        y_lon_2 = dY2;
        h_z_1 = dH1;
        h_z_2 = dH2;
    }
}
