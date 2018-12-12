package com.ztesoft.gis;

public class TCoordinate3D_7Params implements TCoordinate {

    private EnumTransFormModel _Model;
    private SevenParams _Params;
    private EnumTransFormType _type;
    private EnumCoordinateFormat _format;
    private EarthParams _earth_old;
    private EarthParams _earth_new;
    private double _dOldCentralMeridian;
    private double _dOldAddY;
    private double _dNewCentralMeridian;
    private double _dNewAddY;

    public TCoordinate3D_7Params(EnumTransFormType type,
                                  EnumCoordinateFormat format,
                                  EarthParams earth_old,
                                  EarthParams earth_new,
                                  EnumTransFormModel model,
                                  SevenParams Params,
                                  double dOldCentralMeridian,
                                  double dOldAddY,
                                  double dNewCentralMeridian,
                                  double dNewAddY) {
        _type = type;
        _format = format;
        _earth_old = earth_old;
        _earth_new = earth_new;
        _Model = model;
        _Params = Params;
        _dOldCentralMeridian = dOldCentralMeridian;
        _dOldAddY = dOldAddY;
        _dNewCentralMeridian = dNewCentralMeridian;
        _dNewAddY = dNewAddY;
    }

    /**
     * 七参数计算坐标点
     * @param point
     * @return
     */
    public CoordinatePoint Compute(CoordinatePoint point) {
        CoordinatePoint NewPnt = new CoordinatePoint();
        try {
            TCoordinate TClass = null;
            //// 高斯反算转换
            if (_type == EnumTransFormType.XYHtoBLH || _type == EnumTransFormType.XYHtoXYH) {
                TClass = new GaussTrans(_type, _format, _earth_old, _dOldCentralMeridian, _dOldAddY);
                point = TClass.Compute(point);
            }

            double[][] MatrixB = null, MatrixL = null;
            CoordinatePoint PointXYZ = null;

            if (_Model == EnumTransFormModel.Bursa || _Model == EnumTransFormModel.Molodensky) {
                //// 大地坐标转换成空间直角坐标
                PointXYZ = TransFormMethod.TransFormXYZ(_format, _earth_old, point);

                //// 七参数矩阵
                double[][] MatrixX = new double[][]{
                        {_Params.get_DX()},
                        {_Params.get_DY()},
                        {_Params.get_DZ()},
                        {_Params.get_ScaleK() / Math.pow(10, 6)},
                        {TransFormMethod.DegreeToArc(_Params.get_AngleX() / 3600.0)},
                        {TransFormMethod.DegreeToArc(_Params.get_AngleY() / 3600.0)},
                        {TransFormMethod.DegreeToArc(_Params.get_AngleZ() / 3600.0)}
                };

                //// 坐标矩阵
                if (_Model == EnumTransFormModel.Molodensky) {
                    MatrixB = TransFormMethod.GetMatrixB(PointXYZ, (MolodenskyParams) _Params);
                } else {
                    MatrixB = TransFormMethod.GetMatrixB(_earth_old, _earth_new, PointXYZ, _Model);
                }

                //// 新旧坐标差值矩阵
                MatrixL = TransFormMethod.MatrixMultiply(MatrixB, MatrixX);
                //// 新坐标系下的空间直角坐标
                CoordinatePoint NewPointXYZ = new CoordinatePoint(point.GetDH(), MatrixL[0][0] + PointXYZ.GetXorLat(), MatrixL[1][0] + PointXYZ.GetYorLon(), MatrixL[2][0] + PointXYZ.GetHorZ());
                //// 空间直角坐标转换成大地坐标
                NewPnt = TransFormMethod.TransFormBLH(_format, _earth_new, NewPointXYZ);
                NewPnt.setDh(point.GetDH());
            } else if (_Model == EnumTransFormModel._2D7Params || _Model == EnumTransFormModel._3D7Params) {
                if (_format == EnumCoordinateFormat.Du) {
                    PointXYZ = new CoordinatePoint("", TransFormMethod.DegreeToArc(point.GetXorLat()), TransFormMethod.DegreeToArc(point.GetYorLon()), point.GetHorZ());
                } else if (_format == EnumCoordinateFormat.ddmmss) {
                    PointXYZ = new CoordinatePoint("", TransFormMethod.DmsToArc(point.GetXorLat()), TransFormMethod.DmsToArc(point.GetYorLon()), point.GetHorZ());
                }

                //// 七参数矩阵
                double[][] MatrixX = new double[][]{
                        {_Params.get_DX()},
                        {_Params.get_DY()},
                        {_Params.get_DZ()},
                        {_Params.get_ScaleK() / Math.pow(10, 6)},
                        {_Params.get_AngleX()},
                        {_Params.get_AngleY()},
                        {_Params.get_AngleZ()}
                };

                //// 坐标矩阵
                MatrixB = TransFormMethod.GetMatrixB(_earth_old, _earth_new, PointXYZ, _Model);

                //// 新旧坐标差值矩阵
                MatrixL = TransFormMethod.MatrixMultiply(MatrixB, MatrixX);
                //// 新坐标系下的弧度坐标
                CoordinatePoint NewPointXYZ = GetNewPoint(MatrixL, PointXYZ);
                ;
                if (_format == EnumCoordinateFormat.Du) {
                    NewPnt = new CoordinatePoint(point.GetDH(), TransFormMethod.ArcToDegree(NewPointXYZ.GetXorLat()), TransFormMethod.ArcToDegree(NewPointXYZ.GetYorLon()), NewPointXYZ.GetHorZ());
                } else if (_format == EnumCoordinateFormat.ddmmss) {
                    NewPnt = new CoordinatePoint(point.GetDH(), TransFormMethod.ArcToDms(NewPointXYZ.GetXorLat()), TransFormMethod.ArcToDms(NewPointXYZ.GetYorLon()), NewPointXYZ.GetHorZ());
                }
            }
            //// 高斯正算
            if (_type == EnumTransFormType.BLHtoXYH || _type == EnumTransFormType.XYHtoXYH) {
                TClass = new GaussTrans(EnumTransFormType.BLHtoXYH, _format, _earth_new, _dNewCentralMeridian, _dNewAddY);
                NewPnt = TClass.Compute(NewPnt);
            }
        } catch (Exception ex) {
            return null;
        }
        return NewPnt;
    }

    /// <summary>
    /// 根据矩阵L，获取转换后的新点
    /// </summary>
    /// <param name="MatrixL"></param>
    /// <param name="point"></param>
    /// <returns></returns>
    private CoordinatePoint GetNewPoint(double[][] MatrixL,
                                         CoordinatePoint point) {
        CoordinatePoint NewPoint = new CoordinatePoint();
        try {
            double p = 180.0 * 3600 / TransFormMethod.L_AREACAL_PI;             //// 弧度秒 1.0   180.0 * 3600 / L_AREACAL_PI    七参数是弧度时(三维七参数除外)，p=1，如果是秒时，p=180.0 * 3600 / L_AREACAL_PI
            double dA = _earth_new.getA() - _earth_old.getA();
            double dF = (1 / _earth_new.getF()) - (1 / _earth_old.getF());
            double E2 = TransFormMethod.GetE2(_earth_old.getA(), _earth_old.getB());
            double Lat1 = point.GetXorLat();

            double W = Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat1), 2));
            double N = _earth_old.getA() / W;                //// A/根号下（1-e2*sinB*sinB）   卯酉圈曲率半径N
            double M = _earth_old.getA() * (1 - E2) / Math.pow(W, 3);                       //// 子午圈曲率半径M

            double num1 = N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * p * dA / (M * _earth_old.getA())
                    + (2 - E2 * Math.pow(Math.sin(Lat1), 2)) * Math.sin(Lat1) * Math.cos(Lat1) * p * dF / (1 - 1 / _earth_old.getF());

            double num2 = -W * dA + _earth_old.getA() * (1 - E2) * Math.pow(Math.sin(Lat1), 2) * dF / ((1 - _earth_old.getA()) * W);

            NewPoint.SetXorLat(point.GetXorLat() + (num1 + MatrixL[1][0]) / p);
            NewPoint.SetYorLon(point.GetYorLon() + MatrixL[0][0] / p);

            if (_Model == EnumTransFormModel._2D7Params) {
                NewPoint.SetHorZ(point.GetHorZ());
            } else if (_Model == EnumTransFormModel._3D7Params) {
                NewPoint.SetHorZ(point.GetHorZ() + num2 + MatrixL[2][0]);
            }
        } catch (Exception ex) {
            return null;
        }
        return NewPoint;
    }
}
