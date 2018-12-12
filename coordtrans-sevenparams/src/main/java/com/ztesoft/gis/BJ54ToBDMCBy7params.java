package com.ztesoft.gis;

public class BJ54ToBDMCBy7params {

    /**
     * 南通二维七参数转换（单个坐标点入口）
     *
     * @param X
     * @param Y
     * @param pSevenParams
     * @return
     */
    public static String TraformBy2D_7Params_NT(Double X,
                                                Double Y,
                                                SevenParams pSevenParams) {
        CoordinatePoint pPnt = new CoordinatePoint();
        pPnt.SetXorLat(X);
        pPnt.SetYorLon(Y);

        CoordinatePoint NewPnt = null;
        TCoordinate TClass = null;

        try {
            TClass = new TCoordinate3D_7Params(EnumTransFormType.XYHtoBLH,
                    EnumCoordinateFormat.Du,
                    EarthParamsMethod.GetBJ54EarthParams(),
                    EarthParamsMethod.GetWGS84EarthParams(),
                    EnumTransFormModel._2D7Params,
                    pSevenParams,
                    117.0,
                    500000,
                    117.0,
                    500000);

            NewPnt = TraformBy2D_7Params(TClass, pPnt);
        } catch (Exception ex) {
            return null;
        }

        return NewPnt.GetXorLat() + "," + NewPnt.GetYorLon();
    }

    /**
     * 南通二维七参数转换（单个坐标点入口）
     *
     * @param X
     * @param Y
     * @param pSevenParams
     * @return
     */
    public static String TraformBy2D_7Params_84to54_NT(Double X,
                                                Double Y,
                                                SevenParams pSevenParams) {
        CoordinatePoint pPnt = new CoordinatePoint();
        pPnt.SetXorLat(X);
        pPnt.SetYorLon(Y);

        CoordinatePoint NewPnt = null;
        TCoordinate TClass = null;

        try {
            TClass = new TCoordinate3D_7Params(EnumTransFormType.BLHtoXYH,
                    EnumCoordinateFormat.Du,
                    EarthParamsMethod.GetWGS84EarthParams(),
                    EarthParamsMethod.GetBJ54EarthParams(),
                    EnumTransFormModel._2D7Params,
                    pSevenParams,
                    117.0,
                    500000,
                    117.0,
                    500000);

            NewPnt = TraformBy2D_7Params(TClass, pPnt);
        } catch (Exception ex) {
            return null;
        }

        return NewPnt.GetXorLat() + "," + NewPnt.GetYorLon();
    }

    /**
     * 七参数计算坐标点
     *
     * @param TClass
     * @param pOldPnt
     * @return
     */
    private static CoordinatePoint TraformBy2D_7Params(TCoordinate TClass,
                                                       CoordinatePoint pOldPnt) {

        CoordinatePoint NewPnt = null;
        CoordinatePoint pPnt = null;
        try {
            double dZ = Double.isNaN(pOldPnt.GetHorZ()) ? 0.0 : pOldPnt.GetHorZ();
            pPnt = new CoordinatePoint("", pOldPnt.GetXorLat(), pOldPnt.GetYorLon(), dZ);
            NewPnt = TClass.Compute(pPnt);
        } catch (Exception ex) {
            return null;
        }

        return NewPnt;
    }

    /**
     * 获取南通七参数（临时测试）
     *
     * @return
     */
    protected static SevenParams GetSevenParamsByNT() {
        SevenParams pSevenParams = new SevenParams();
        try {
            pSevenParams.set_DX(-2630465.312500000000000000);
            pSevenParams.set_DY(1837301.000000000000000000);
            pSevenParams.set_DZ(-4632593.500000000000000000);
            pSevenParams.set_AngleX(-141325.271484375000000000);
            pSevenParams.set_AngleY(-110953.859375000000000000);
            pSevenParams.set_AngleZ(35900.947753906300900000);
            pSevenParams.set_ScaleK(543918.848037719727000000);
        } catch (Exception ex) {
            return null;
        }

        return pSevenParams;
    }
    /**
     * 南通二维七参数转换（单个坐标点入口）
     * 为了打成jar包提供给存储过程用
     * 存储过程传入的是七参数字符串
     * @param params 字符串，英文半角逗号分割，纬度、经度、dx dy dz anglex angley anglez scalek
     * @return
     */
    public static String TraformByStrParams(String params) {
        String[] paramsArr = params.split(",");
        SevenParams pSevenParams = new SevenParams();
        pSevenParams.set_DX(Double.parseDouble(paramsArr[2]));
        pSevenParams.set_DY(Double.parseDouble(paramsArr[3]));
        pSevenParams.set_DZ(Double.parseDouble(paramsArr[4]));
        pSevenParams.set_AngleX(Double.parseDouble(paramsArr[5]));
        pSevenParams.set_AngleY(Double.parseDouble(paramsArr[6]));
        pSevenParams.set_AngleZ(Double.parseDouble(paramsArr[7]));
        pSevenParams.set_ScaleK(Double.parseDouble(paramsArr[8]));

        String result = TraformBy2D_7Params_NT(Double.parseDouble(paramsArr[1]),
                Double.parseDouble(paramsArr[0]),pSevenParams);
        return result;
    }

    /**
     * 南通二维七参数转换（单个坐标点入口）（反向WGS84->BJ54）
     * 为了打成jar包提供给存储过程用
     * 存储过程传入的是七参数字符串
     * @param params 字符串，英文半角逗号分割，经度、纬度、dx dy dz anglex angley anglez scalek
     * @return
     */
    public static String TraformByStrParamsReverse(String params) {
        String[] paramsArr = params.split(",");
        SevenParams pSevenParams = new SevenParams();
        pSevenParams.set_DX(Double.parseDouble(paramsArr[2]));
        pSevenParams.set_DY(Double.parseDouble(paramsArr[3]));
        pSevenParams.set_DZ(Double.parseDouble(paramsArr[4]));
        pSevenParams.set_AngleX(Double.parseDouble(paramsArr[5]));
        pSevenParams.set_AngleY(Double.parseDouble(paramsArr[6]));
        pSevenParams.set_AngleZ(Double.parseDouble(paramsArr[7]));
        pSevenParams.set_ScaleK(Double.parseDouble(paramsArr[8]));

        String result = TraformBy2D_7Params_84to54_NT(Double.parseDouble(paramsArr[1]),
                Double.parseDouble(paramsArr[0]),pSevenParams);
        return result;
    }
}
