package com.ztesoft.gis;

public class GaussTrans implements TCoordinate {
    private EnumTransFormType _Type;
    private EarthParams _Earth;
    private double _dCentralMeridian;
    private double _dAddY;
    private EnumCoordinateFormat _format;

    public GaussTrans(EnumTransFormType type,
                      EnumCoordinateFormat format,
                      EarthParams earth,
                      double dCentralMeridian,
                      double dAddY) {
        _Type = type;
        _format = format;
        _Earth = earth;
        _dCentralMeridian = dCentralMeridian;
        _dAddY = dAddY;
    }

    public CoordinatePoint Compute(CoordinatePoint point) {
        CoordinatePoint NewPoint = new CoordinatePoint();
        try {
            NewPoint.setDh(point.GetDH());
            NewPoint.SetHorZ(point.GetHorZ());
            String sX = Double.toString(point.GetXorLat());
            double dX = sX.equals("") || sX == null ? 0.0 : point.GetXorLat();
            String sY = Double.toString(point.GetYorLon());
            double dY = sY.equals("") || sY == null ? 0.0 : point.GetYorLon();
            if (_Type == EnumTransFormType.BLtoXY || _Type == EnumTransFormType.BLHtoXYH) {
                if (_format == EnumCoordinateFormat.ddmmss) {
                    dX = TransFormMethod.DmsToDegree(dX);
                    dY = TransFormMethod.DmsToDegree(dY);
                }
                ////高斯正算
                NewPoint.SetXorLat(TransFormMethod.GaussPositiveX(dX, dY, _dCentralMeridian, _Earth.getA(), _Earth.getB()));
                NewPoint.SetYorLon(TransFormMethod.GaussPositiveY(dX, dY, _dCentralMeridian, _Earth.getA(), _Earth.getB(), _dAddY));
            } else {
                //// 高斯反算
                double dValue_X = TransFormMethod.GaussNegativeB(dX, dY, _dCentralMeridian, _Earth.getA(), _Earth.getB(), _dAddY);
                double dValue_Y = TransFormMethod.GaussNegativeL(dX, dY, _dCentralMeridian, _Earth.getA(), _Earth.getB(), _dAddY);
                if (_format == EnumCoordinateFormat.ddmmss) {
                    dValue_X = TransFormMethod.DegreeToDms(dValue_X);
                    dValue_Y = TransFormMethod.DegreeToDms(dValue_Y);
                }

                NewPoint.SetXorLat(dValue_X);
                NewPoint.SetYorLon(dValue_Y);
            }
        } catch (Exception ex) {
            return null;
        }

        return NewPoint;
    }
}
