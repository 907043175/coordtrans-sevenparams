package com.ztesoft.gis;

public class EarthParamsMethod {

    public static EarthParams GetBJ54EarthParams() {
        EarthParams pEarthParams = new EarthParams();
        try {
            pEarthParams.set_Name("1954北京坐标系");
            pEarthParams.set_GeoName("GCS_Beijing_1954");
            pEarthParams.set_A(6378245.0);
            pEarthParams.set_B(6356863.0187730473);
            pEarthParams.set_F(298.3);
        } catch (Exception ex) {
            return null;
        }

        return pEarthParams;
    }

    public static EarthParams GetWGS84EarthParams() {
        EarthParams pEarthParams = new EarthParams();
        try {
            pEarthParams.set_Name("WGS84大地坐标系");
            pEarthParams.set_GeoName("GCS_WGS_1984");
            pEarthParams.set_A(6378137.0);
            pEarthParams.set_B(6356752.314245179);
            pEarthParams.set_F(298.257223563);
        } catch (Exception ex) {
            return null;
        }

        return pEarthParams;
    }

    public static EarthParams GetCGCS2000EarthParams() {
        EarthParams pEarthParams = new EarthParams();
        try {
            pEarthParams.set_Name("2000国家大地坐标系");
            pEarthParams.set_GeoName("GCS_China_Geodetic_Coordinate_System_2000");
            pEarthParams.set_A(6378137.0);
            pEarthParams.set_B(6356752.314140356);
            pEarthParams.set_F(298.257222101);
        } catch (Exception ex) {
            return null;
        }

        return pEarthParams;
    }

    public static EarthParams Xian80EarthParams() {
        EarthParams pEarthParams = new EarthParams();
        try {
            pEarthParams.set_Name("1980西安坐标系");
            pEarthParams.set_GeoName("GCS_Xian_1980");
            pEarthParams.set_A(6378140.0);
            pEarthParams.set_B(6356755.288157528);
            pEarthParams.set_F(298.257);
        } catch (Exception ex) {
            return null;
        }

        return pEarthParams;
    }

    public static EarthParams GetBDMCEarthParams() {
        EarthParams pEarthParams = new EarthParams();
        try {
            pEarthParams.set_Name("百度米坐标系");
            pEarthParams.set_GeoName("WGS_1984_Web_Mercator_Auxiliary_Sphere");
            pEarthParams.set_A(6378137.0);
            pEarthParams.set_B(6356752.314245179);
            pEarthParams.set_F(298.257223563);
        } catch (Exception ex) {
            return null;
        }

        return pEarthParams;
    }
}
