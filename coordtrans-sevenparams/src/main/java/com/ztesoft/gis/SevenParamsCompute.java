package com.ztesoft.gis;

import java.util.ArrayList;
import java.util.List;

public class SevenParamsCompute {

    /**
     * 南通二维七参数计算入口
     *
     * @param sPoints
     * @return
     */
    public static SevenParams Compute7ParamsBy54ToBD_NT(String sPoints) {
        SevenParams pSevenParams = null;
        List<CoordinatePointPair> lstAll = null;

        try {
            lstAll = new ArrayList<CoordinatePointPair>();
            String[] sPnts = sPoints.split(";");
            if (sPnts == null || sPnts.length == 0) {
                return null;
            }

            for (int i = 0; i < sPnts.length; i++) {
                String[] sXY = sPnts[i].split(",");
                if (sXY != null && sXY.length > 0) {
                    CoordinatePointPair pCoordinatePointPair = new CoordinatePointPair();
                    pCoordinatePointPair.setX_lat_1(Double.parseDouble(sXY[0]));
                    pCoordinatePointPair.setY_lon_1(Double.parseDouble(sXY[1]));
                    pCoordinatePointPair.setH_z_1(0);
                    pCoordinatePointPair.setX_lat_2(Double.parseDouble(sXY[2]));
                    pCoordinatePointPair.setY_lon_2(Double.parseDouble(sXY[3]));
                    pCoordinatePointPair.setH_z_2(0);
                    lstAll.add(pCoordinatePointPair);
                }
            }

            if (lstAll == null || lstAll.size() == 0) {
                return null;
            }

            pSevenParams = ComputeSevenParams(lstAll,
                    117,
                    500000,
                    117,
                    500000,
                    EarthParamsMethod.GetBJ54EarthParams(),
                    EarthParamsMethod.GetWGS84EarthParams(),
                    EnumTransFormModel._2D7Params,
                    EnumCoordinateFormat.Du);
        } catch (Exception ex) {
            return null;
        }

        return pSevenParams;
    }

    /**
     * 南通二维七参数计算入口
     *
     * @param sPoints
     * @return
     */
    public static SevenParams Compute7ParamsByBDTo54_NT(String sPoints) {
        SevenParams pSevenParams = null;
        List<CoordinatePointPair> lstAll = null;

        try {
            lstAll = new ArrayList<CoordinatePointPair>();
            String[] sPnts = sPoints.split(";");
            if (sPnts == null || sPnts.length == 0) {
                return null;
            }

            for (int i = 0; i < sPnts.length; i++) {
                String[] sXY = sPnts[i].split(",");
                if (sXY != null && sXY.length > 0) {
                    CoordinatePointPair pCoordinatePointPair = new CoordinatePointPair();
                    pCoordinatePointPair.setX_lat_1(Double.parseDouble(sXY[2]));
                    pCoordinatePointPair.setY_lon_1(Double.parseDouble(sXY[3]));
                    pCoordinatePointPair.setH_z_1(0);
                    pCoordinatePointPair.setX_lat_2(Double.parseDouble(sXY[0]));
                    pCoordinatePointPair.setY_lon_2(Double.parseDouble(sXY[1]));
                    pCoordinatePointPair.setH_z_2(0);
                    lstAll.add(pCoordinatePointPair);
                }
            }

            if (lstAll == null || lstAll.size() == 0) {
                return null;
            }

            pSevenParams = ComputeSevenParamsBLtoXY(lstAll,
                    117,
                    500000,
                    117,
                    500000,
                    EarthParamsMethod.GetWGS84EarthParams(),
                    EarthParamsMethod.GetBJ54EarthParams(),
                    EnumTransFormModel._2D7Params,
                    EnumCoordinateFormat.Du);
        } catch (Exception ex) {
            return null;
        }

        return pSevenParams;
    }

    /**
     * 计算七参数
     *
     * @param lstAll
     * @param dOldCentralMeridian
     * @param dOldAddY
     * @param dNewCentralMeridian
     * @param dNewAddY
     * @param earth_old
     * @param earth_new
     * @param model
     * @param format
     * @return
     */
    public static SevenParams ComputeSevenParams(List<CoordinatePointPair> lstAll,
                                                 double dOldCentralMeridian,
                                                 double dOldAddY,
                                                 double dNewCentralMeridian,
                                                 double dNewAddY,
                                                 EarthParams earth_old,
                                                 EarthParams earth_new,
                                                 EnumTransFormModel model,
                                                 EnumCoordinateFormat format) {
        SevenParams pSevenParams = null;

        try {
            //// 计算七参数
            GaussTrans GaussOld = new GaussTrans(EnumTransFormType.XYtoBL,
                    format, earth_old, dOldCentralMeridian, dOldAddY);
            GaussTrans GaussNew = new GaussTrans(EnumTransFormType.XYtoBL,
                    format, earth_new, dNewCentralMeridian, dNewAddY);
            pSevenParams = StartComputeSevenParams(GaussOld, GaussNew,
                    earth_old, earth_new, EnumTransFormType.XYHtoBLH,
                    EnumTransFormType.BLHtoBLH, lstAll, model, format);
        } catch (Exception ex) {
            return null;
        }

        return pSevenParams;
    }

    /**
     * 计算七参数
     *
     * @param lstAll
     * @param dOldCentralMeridian
     * @param dOldAddY
     * @param dNewCentralMeridian
     * @param dNewAddY
     * @param earth_old
     * @param earth_new
     * @param model
     * @param format
     * @return
     */
    public static SevenParams ComputeSevenParamsBLtoXY(List<CoordinatePointPair> lstAll,
                                                 double dOldCentralMeridian,
                                                 double dOldAddY,
                                                 double dNewCentralMeridian,
                                                 double dNewAddY,
                                                 EarthParams earth_old,
                                                 EarthParams earth_new,
                                                 EnumTransFormModel model,
                                                 EnumCoordinateFormat format) {
        SevenParams pSevenParams = null;

        try {
            //// 计算七参数
            GaussTrans GaussOld = new GaussTrans(EnumTransFormType.XYtoBL,
                    format, earth_old, dOldCentralMeridian, dOldAddY);
            GaussTrans GaussNew = new GaussTrans(EnumTransFormType.XYtoBL,
                    format, earth_new, dNewCentralMeridian, dNewAddY);
            pSevenParams = StartComputeSevenParams(GaussOld, GaussNew,
                    earth_old, earth_new, EnumTransFormType.BLHtoBLH,
                    EnumTransFormType.XYHtoBLH, lstAll, model, format);
        } catch (Exception ex) {
            return null;
        }

        return pSevenParams;
    }

    /**
     * 计算七参数
     *
     * @param GaussOld
     * @param GaussNew
     * @param earth_old
     * @param earth_new
     * @param pTypeOld
     * @param pTypeNew
     * @param lstAll
     * @param model
     * @param format
     * @return
     */
    private static SevenParams StartComputeSevenParams(GaussTrans GaussOld,
                                                       GaussTrans GaussNew,
                                                       EarthParams earth_old,
                                                       EarthParams earth_new,
                                                       EnumTransFormType pTypeOld,
                                                       EnumTransFormType pTypeNew,
                                                       List<CoordinatePointPair> lstAll,
                                                       EnumTransFormModel model,
                                                       EnumCoordinateFormat format) {
        SevenParams pSevenParams = null;
        try {
            //// 获取参与计算的公共坐标点对（BLH）
            List<CoordinatePointPair> pairs = new ArrayList<CoordinatePointPair>();
            CoordinatePoint sPointXYZ = null, tPointXYZ = null;
            for (int i = 0; i < lstAll.size(); i++) {
                CoordinatePointPair pair = lstAll.get(i);
                //// 坐标点对
                CoordinatePoint source = new CoordinatePoint("", pair.getX_lat_1(),
                        pair.getY_lon_1(), pair.getH_z_1());
                CoordinatePoint target = new CoordinatePoint("", pair.getX_lat_2(),
                        pair.getY_lon_2(), pair.getH_z_2());
                ////将平面坐标转为大地坐标
                if (pTypeOld == EnumTransFormType.XYHtoBLH) {
                    source = GaussOld.Compute(source);
                }

                if (pTypeNew == EnumTransFormType.XYHtoBLH) {
                    target = GaussNew.Compute(target);
                }

                //// 根据转换类型将大地坐标转为转换坐标类型（弧度、XYZ）
                if (model == EnumTransFormModel._2D7Params || model == EnumTransFormModel._3D7Params) {
                    if (format == EnumCoordinateFormat.Du) {
                        sPointXYZ = new CoordinatePoint("", TransFormMethod.DegreeToArc(source.GetXorLat()),
                                TransFormMethod.DegreeToArc(source.GetYorLon()), source.GetHorZ());
                        tPointXYZ = new CoordinatePoint("", TransFormMethod.DegreeToArc(target.GetXorLat()),
                                TransFormMethod.DegreeToArc(target.GetYorLon()), target.GetHorZ());
                    } else if (format == EnumCoordinateFormat.ddmmss) {
                        sPointXYZ = new CoordinatePoint("", TransFormMethod.DmsToArc(source.GetXorLat()),
                                TransFormMethod.DmsToArc(source.GetYorLon()), source.GetHorZ());
                        tPointXYZ = new CoordinatePoint("", TransFormMethod.DmsToArc(target.GetXorLat()),
                                TransFormMethod.DmsToArc(target.GetYorLon()), target.GetHorZ());
                    }
                } else {
                    //// 大地坐标转换成空间直角坐标
                    sPointXYZ = TransFormMethod.TransFormXYZ(format, earth_old, source);
                    tPointXYZ = TransFormMethod.TransFormXYZ(format, earth_new, target);
                }

                //// 组成新的坐标点对（XYZ）
                CoordinatePointPair newPair = new CoordinatePointPair(sPointXYZ.GetXorLat(),
                        sPointXYZ.GetYorLon(), sPointXYZ.GetHorZ(), tPointXYZ.GetXorLat(),
                        tPointXYZ.GetYorLon(), tPointXYZ.GetHorZ());
                pairs.add(newPair);
            }

            //// 计算七参数
            pSevenParams = TransFormMethod.Compute7Params(earth_old, earth_new, pairs, model);
        } catch (Exception ex) {
            return null;
        }
        return pSevenParams;
    }
}
