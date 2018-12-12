package com.ztesoft.gis;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 转换算法
 */
public class TransFormMethod {
    /// <summary>
    /// PI 常量
    /// </summary>
    public static final double L_AREACAL_PI = 3.14159265358979;

    /// <summary>
    /// 度转弧度
    /// </summary>
    /// <param name="D">度</param>
    /// <returns>弧度</returns>
    public static double DegreeToArc(double D) {
        return D / 180 * L_AREACAL_PI;
    }

    /// <summary>
    /// 弧度转度
    /// </summary>
    /// <param name="A"></param>
    /// <returns></returns>
    public static double ArcToDegree(double A) {
        return A * 180 / L_AREACAL_PI;
    }

    /// <summary>
    /// 度转度分秒
    /// </summary>
    /// <param name="Degree"></param>
    /// <returns></returns>
    public static double DegreeToDms(double Degree) {
        BigDecimal num = new BigDecimal((int) Degree);
        BigDecimal bDegree = new BigDecimal(Degree);
        BigDecimal num1 = new BigDecimal(60.0);
        BigDecimal num100 = new BigDecimal(100.0);
        BigDecimal num10000 = new BigDecimal(10000.0);
        BigDecimal num2 = new BigDecimal(Integer.parseInt(((bDegree.subtract(num)).multiply(num1)).toString()));
        BigDecimal num4 = new BigDecimal(Integer.parseInt(((bDegree.subtract(num)).multiply(num1)).toString()));
        BigDecimal num5 = (bDegree.subtract(num)).multiply(num1);
        BigDecimal num3 = (num5.subtract(num4)).multiply(num1);
        return Double.parseDouble(((num.add(num2.divide(num100))).add((num3.divide(num10000)))).toString());
    }

    /// <summary>
    /// 度分秒转度
    /// </summary>
    /// <param name="Dms"></param>
    /// <returns></returns>
    public static double DmsToDegree(double Dms) {
        return (double) (DmsToSecond(Dms) / 3600.0);
    }

    /// <summary>
    /// 度分秒转秒
    /// </summary>
    /// <param name="dms"></param>
    /// <returns></returns>
    public static double DmsToSecond(double dms) {
        BigDecimal num = new BigDecimal(dms);
        BigDecimal num2 = new BigDecimal((int) (((dms - ((int) dms)) * 100.0) + 0.001));
        BigDecimal num5 = new BigDecimal(dms * 100.0);
        BigDecimal num100 = new BigDecimal(100.0);
        String snum5 = new DecimalFormat("#.###").format(num5);
        String str = snum5.equals("") || snum5 == null ? "0.0" : snum5;
        BigDecimal num4 = new BigDecimal(dms * 100.0);
        BigDecimal num6 = new BigDecimal(Double.parseDouble(str));
        BigDecimal num3 = (num4.subtract(num6)).multiply(num100);
        BigDecimal num3600 = new BigDecimal(3600.0);
        BigDecimal num60 = new BigDecimal(60.0);
        return Double.parseDouble((((num.multiply(num3600)).add(num2.multiply(num60))).add(num3)).toString());
    }

    /// <summary>
    /// 度转秒
    /// </summary>
    /// <param name="D"></param>
    /// <returns></returns>
    public static double DegreeToSecond(double D) {
        return D * 3600.0;
    }

    /// <summary>
    /// 秒转度
    /// </summary>
    /// <param name="D"></param>
    /// <returns></returns>
    public static double SecondToDegree(double S) {
        return S / 3600.0;
    }

    /// <summary>
    /// 度分秒转弧度
    /// </summary>
    /// <param name="Dms"></param>
    /// <returns></returns>
    public static double DmsToArc(double Dms) {
        double D = DmsToDegree(Dms);
        return DegreeToArc(D);
    }

    /// <summary>
    /// 弧度转度分秒
    /// </summary>
    /// <param name="A"></param>
    /// <returns></returns>
    public static double ArcToDms(double A) {
        double D = ArcToDegree(A);
        return DegreeToDms(D);
    }

    /// <summary>
    /// 秒转度分秒
    /// </summary>
    /// <param name="S"></param>
    /// <returns></returns>
    public static double SecondToDms(double S) {
        double D = SecondToDegree(S);
        return DegreeToDms(D);
    }

    /// <summary>
    /// 获取椭球参数短半轴B
    /// </summary>
    /// <param name="A"></param>
    /// <param name="F"></param>
    /// <returns></returns>
    public static double GetB(double A, double F) {
        double B = 0.0;
        try {
            if (A == 0 || F == 0) {
                return B;
            }
            B = A * (1 - 1.0 / F);
        } catch (Exception ex) {
            return 0.0;
        }
        return B;
    }

    /// <summary>
    /// 椭球第一偏心率的平方
    /// </summary>
    /// <param name="A"></param>
    /// <param name="B"></param>
    /// <returns></returns>
    public static double GetE2(double A, double B) {
        double E2 = 0.0;
        try {
            if (A == 0 || B == 0) {
                return E2;
            }
            E2 = (Math.pow(A, 2.0) - Math.pow(B, 2.0)) / Math.pow(A, 2.0);
        } catch (Exception ex) {
            return 0.0;
        }
        return E2;
    }

    public static double GetK1(double A, double E2) {
        double dValue = 0.0;
        try {
            if (A == 0.0) {
                return dValue;
            }
            double num = A * (1 - E2);
            double ParamA = GetParamA(E2);
            dValue = num * ParamA;
            double dd = dValue * L_AREACAL_PI / 180.0;
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    public static double GetK2(double A, double E2) {
        double dValue = 0.0;
        try {
            if (A == 0.0) {
                return dValue;
            }
            double num = A * (1 - E2);
            dValue = num * (GetParamC(E2) - GetParamB(E2) - GetParamD(E2));
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    public static double GetK3(double A, double E2) {
        double dValue = 0.0;
        try {
            if (A == 0.0) {
                return dValue;
            }
            double num = A * (1 - E2);
            dValue = num * ((16.0 / 3.0) * GetParamD(E2) - 2.0 * GetParamC(E2));
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    public static double GetK4(double A, double E2) {
        double dValue = 0.0;
        try {
            if (A == 0) {
                return dValue;
            }
            double num = A * (1 - E2);
            dValue = num * (-(16.0 / 3.0) * GetParamD(E2));
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    public static double GetParamA(double E2) {
        double dValue = 1 + (3.0 / 4.0) * E2 + (45.0 / 64.0) *
                Math.pow(E2, 2.0) + (175.0 / 256.0) * Math.pow(E2, 3.0)
                + (11025.0 / 16384.0) * Math.pow(E2, 4.0) + (43659.0 / 65536.0) * Math.pow(E2, 5.0)
                + (693693.0 / 1048576.0) * Math.pow(E2, 6.0);
        return dValue;
    }

    public static double GetParamB(double E2) {
        double dValue = (3.0 / 4.0) * E2 + (15.0 / 16.0) * Math.pow(E2, 2.0) + (525.0 / 512.0) * Math.pow(E2, 3.0)
                + (2205.0 / 2048.0) * Math.pow(E2, 4.0) + (72765.0 / 65536.0) * Math.pow(E2, 5.0);
        //+ (297297.0 / 262144.0) * Math.Pow(E2, 6.0);
        return dValue;
    }

    public static double GetParamC(double E2) {
        double dValue = (15.0 / 64.0) * Math.pow(E2, 2.0) + (105.0 / 256.0) * Math.pow(E2, 3.0)
                + (2205.0 / 4096.0) * Math.pow(E2, 4.0) + (10359.0 / 16384.0) * Math.pow(E2, 5.0);
        return dValue;
    }

    public static double GetParamD(double E2) {
        double dValue = (35.0 / 512.0) * Math.pow(E2, 3.0) + (315.0 / 2048.0) * Math.pow(E2, 4.0) + (31185.0 / 13072.0) * Math.pow(E2, 5.0);
        ;
        return dValue;
    }

    /// <summary>
    /// 高斯正算y坐标值(坐标形式：度)
    /// </summary>
    /// <param name="Lat">经度</param>
    /// <param name="Lon">纬度</param>
    /// <param name="dCentralMeridian">中央经线</param>
    /// <param name="A">长半轴</param>
    /// <param name="B">短半轴</param>
    /// <returns>y坐标值</returns>
    public static double GaussPositiveX(double Lat,
                                        double Lon,
                                        double dCentralMeridian,
                                        double A,
                                        double B) {
        double dValue = 0.0;
        try {
            double E2 = GetE2(A, B);                                            //// 第一偏心率的平方
            double R_Lat = DegreeToArc(Lat);                                     //// 纬度弧度
            double R_Lon = DegreeToArc(Lon);                                     //// 经度弧度
            double R_CentralMeridian = DegreeToArc(dCentralMeridian);            //// 中央经线弧度

            //// 经线弧长
            double x0 = GetK1(A, E2) * R_Lat + Math.cos(R_Lat) * (GetK2(A, E2) * Math.sin(R_Lat)
                    + GetK3(A, E2) * Math.pow(Math.sin(R_Lat), 3) + GetK4(A, E2) * Math.pow(Math.sin(R_Lat), 5));

            double l = R_Lon - R_CentralMeridian;                            //// 经差
            double t = Math.sin(R_Lat) / Math.cos(R_Lat);                    //// tanB
            double m = l * Math.cos(R_Lat);                                  //// lcosB
            double g2 = E2 / (1 - E2) * Math.pow(Math.cos(R_Lat), 2);         //// e2/(1-e2)*cosB*cosB
            double N = A / Math.sqrt(1 - E2 * Math.pow(Math.sin(R_Lat), 2));    //// A/根号下（1-e2*sinB*sinB）

            dValue = x0 + N * t * (1.0 / 2.0 * Math.pow(m, 2.0) + 1 / 24.0 * (5.0 - t * t + 9 * g2 + 4 * g2 * g2) * Math.pow(m, 4.0)
                    + 1.0 / 720.0 * (61.0 - 58.0 * t * t + Math.pow(t, 4.0)) * Math.pow(m, 6.0)
                    + 1.0 / 24.0 * (9 - 11 * t * t) * g2 * Math.pow(m, 6.0));
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    /// <summary>
    /// 高斯正算X坐标值(坐标形式：度)
    /// </summary>
    /// <param name="Lat">经度</param>
    /// <param name="Lon">纬度</param>
    /// <param name="dCentralMeridian">中央经线</param>
    /// <param name="A">长半轴</param>
    /// <param name="B">短半轴</param>
    /// <param name="dFalseEasting">东偏移量</param>
    /// <returns>x坐标值</returns>
    public static double GaussPositiveY(double Lat,
                                        double Lon,
                                        double dCentralMeridian,
                                        double A, double B,
                                        double dFalseEasting) {
        double dValue = 0.0;
        try {
            double E2 = GetE2(A, B);                                             //// 第一偏心率的平方
            double R_Lat = DegreeToArc(Lat);                                     //// 纬度弧度
            double R_Lon = DegreeToArc(Lon);                                     //// 经度弧度
            double R_CentralMeridian = DegreeToArc(dCentralMeridian);            //// 中央经线弧度

            double l = R_Lon - R_CentralMeridian;                                           //// 经差
            double t = Math.sin(R_Lat) / Math.cos(R_Lat);                                   //// tanB
            double m = l * Math.cos(R_Lat);                                                 //// lcosB
            double g2 = E2 / (1 - E2) * Math.pow(Math.cos(R_Lat), 2);                       //// e2/(1-e2)*cosB*cosB
            double N = A / Math.sqrt(1 - E2 * Math.pow(Math.sin(R_Lat), 2));                //// A/根号下（1-e2*sinB*sinB）

            dValue = N * m * (1 + 1 / 6.0 * (1.0 - t * t + g2) * m * m
                    + 1 / 120.0 * (5.0 - 18.0 * t * t + Math.pow(t, 4.0) + 14.0 * g2 - 58.0 * g2 * t * t) * Math.pow(m, 4.0)
                    + 1 / 5040.0 * (61 - 479 * t * t + 179 * Math.pow(t, 4.0) - Math.pow(t, 6.0)) * Math.pow(m, 6.0));
            dValue = dValue + dFalseEasting;
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    /// <summary>
    /// 高斯反算纬度(坐标形式：度)
    /// </summary>
    /// <param name="x">x坐标</param>
    /// <param name="y">y坐标</param>
    /// <param name="dCentralMeridian">中央经线</param>
    /// <param name="A">长半轴</param>
    /// <param name="B">短半轴</param>
    /// <param name="dFalseEasting">东偏移量</param>
    /// <returns>纬度</returns>
    public static double GaussNegativeB(double x,
                                        double y,
                                        double dCentralMeridian,
                                        double A, double B,
                                        double dFalseEasting) {
        double dValue = 0.0;
        try {
            double E2 = GetE2(A, B);                                                        //// 第一偏心率的平方
            double R_CentralMeridian = DegreeToArc(dCentralMeridian);                       //// 中央经线弧度
            double y0 = y - dFalseEasting;                                                  //// 公式中对应的y
            double x0 = x;                                                                  //// 公式中对应的x

            double K1 = GetK1(A, E2);
            double K2 = GetK2(A, E2);
            double K3 = GetK3(A, E2);
            double K4 = GetK4(A, E2);

            double Bf0 = x0 / K1;                                                 //// Bf0
            double Bf = 0.0;

            //// 经线弧长   4.8E-11M,1E-09
            while (Math.abs(Bf - Bf0) > 1E-09) {
                Bf = Bf0;
                Bf0 = (x0 - (Math.cos(Bf) *
                        (K2 * Math.sin(Bf) + K3 * Math.pow(Math.sin(Bf), 3.0) + K4 * Math.pow(Math.sin(Bf), 5.0)
                        ))) / K1;
            }

            Bf = Bf0;
            double tf = Math.sin(Bf) / Math.cos(Bf);                                   //// tanB
            double gf2 = E2 / (1 - E2) * Math.pow(Math.cos(Bf), 2);                       //// e2/(1-e2)*cosB*cosB
            double Nf = A / Math.sqrt(1 - E2 * Math.pow(Math.sin(Bf), 2));                //// A/根号下（1-e2*sinB*sinB）
            double Vf2 = 1 + gf2;

            dValue = Bf - Vf2 * tf * Math.pow(y0 / Nf, 2.0) / 2.0 + Vf2 * tf * Math.pow(y0 / Nf, 4.0) * (5.0 + 3.0 * tf * tf + gf2 - 9.0 * gf2 * tf * tf) / 24.0
                    - Vf2 * tf * Math.pow(y0 / Nf, 6.0) * (61.0 + 90.0 * tf * tf + 45.0 * Math.pow(tf, 4.0)) / 720.0;
            //// 弧度转为度
            dValue = ArcToDegree(dValue);
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    /// <summary>
    /// 高斯反算经度(坐标形式：度)
    /// </summary>
    /// <param name="x"></param>
    /// <param name="y"></param>
    /// <param name="dCentralMeridian"></param>
    /// <param name="A"></param>
    /// <param name="B"></param>
    /// <param name="dFalseEasting"></param>
    /// <returns></returns>
    public static double GaussNegativeL(double x,
                                        double y,
                                        double dCentralMeridian,
                                        double A, double B,
                                        double dFalseEasting) {
        double dValue = 0.0;
        try {
            double E2 = GetE2(A, B);                                             //// 第一偏心率的平方
            double R_CentralMeridian = DegreeToArc(dCentralMeridian);            //// 中央经线弧度
            double y0 = y - dFalseEasting;                                      //// 公式中对应的y
            double x0 = x;                                                      //// 公式中对应的x

            double K1 = GetK1(A, E2);
            double K2 = GetK2(A, E2);
            double K3 = GetK3(A, E2);
            double K4 = GetK4(A, E2);

            double Bf0 = x0 / K1;                                                 //// Bf0
            double Bf = 0.0;

            //// 经线弧长   4.8E-11M,1E-09
            while (Math.abs(Bf - Bf0) > 1E-09) {
                Bf = Bf0;
                Bf0 = (x0 - (Math.cos(Bf) *
                        (K2 * Math.sin(Bf) + K3 * Math.pow(Math.sin(Bf), 3.0) + K4 * Math.pow(Math.sin(Bf), 5.0)
                        ))) / K1;
            }

            Bf = Bf0;
            double tf = Math.sin(Bf) / Math.cos(Bf);                                       //// tanB
            double gf2 = E2 / (1 - E2) * Math.pow(Math.cos(Bf), 2);                       //// e2/(1-e2)*cosB*cosB
            double Nf = A / Math.sqrt(1 - E2 * Math.pow(Math.sin(Bf), 2));                //// A/根号下（1-e2*sinB*sinB）

            dValue = (y0 / Nf - (1 + 2 * tf * tf + gf2) * Math.pow(y0 / Nf, 3.0) / 6.0
                    + (5.0 + 28.0 * tf * tf + 24.0 * Math.pow(tf, 4.0) + 6.0 * gf2 + 8.0 * tf * tf * gf2) * Math.pow(y0 / Nf, 5.0) / 120.0) / Math.cos(Bf);
            dValue += R_CentralMeridian;
            //// 弧度转为度
            dValue = ArcToDegree(dValue);
        } catch (Exception ex) {
            return 0.0;
        }
        return dValue;
    }

    /// <summary>
    /// 计算七参数（X,Y,Z）
    /// </summary>
    /// <param name="pairs"></param>
    /// <returns></returns>
    public static SevenParams Compute7Params(EarthParams earth_old,
                                             EarthParams earth_new,
                                             List<CoordinatePointPair> pairs,
                                             EnumTransFormModel model) {
        SevenParams pSevenParams = new SevenParams();
        try {
            if (model == EnumTransFormModel.Molodensky) {
                pSevenParams = GetMolodenskyParams(earth_old, earth_new, pairs);
            }

            int count = pairs.size();                           //// 坐标点对个数
            if (pairs == null || count == 0) {
                return null;
            }

            double[][] L = GetMatrixL(earth_old, earth_new, pairs, model);                              //// 矩阵L   3Count X 1 的矩阵
            double[][] B = GetMatrixB(earth_old, earth_new, pairs, model);            //// 矩阵B   3Count X 7 的矩阵

            double[][] dValue = LeastSquare(B, L);
            pSevenParams.set_DX(dValue[0][0]);
            pSevenParams.set_DY(dValue[1][0]);
            pSevenParams.set_DZ(dValue[2][0]);
            pSevenParams.set_ScaleK(dValue[3][0] * Math.pow(10, 6));                     //// 单位ppm
            //// 单位：秒
            pSevenParams.set_AngleX(dValue[4][0]);
            pSevenParams.set_AngleY(dValue[5][0]);
            pSevenParams.set_AngleZ(dValue[6][0]);
            if (model != EnumTransFormModel._2D7Params && model != EnumTransFormModel._3D7Params) {
                pSevenParams.set_AngleX(ArcToDegree(dValue[4][0]) * 3600);
                pSevenParams.set_AngleY(ArcToDegree(dValue[5][0]) * 3600);
                pSevenParams.set_AngleZ(ArcToDegree(dValue[6][0]) * 3600);
            }
        } catch (Exception ex) {
            return null;
        }

        return pSevenParams;
    }

    /// <summary>
    /// 根据公共坐标点对和转换模型获取转换矩阵B
    /// </summary>
    /// <param name="pairs"></param>
    /// <param name="model"></param>
    /// <returns></returns>
    public static double[][] GetMatrixB(EarthParams earth_old,
                                        EarthParams earth_new,
                                        List<CoordinatePointPair> pairs,
                                        EnumTransFormModel model) {
        if (pairs == null || pairs.size() == 0) {
            return null;
        }
        double[][] B = null;            //// 矩阵B   3Count X 7 的矩阵
        try {
            double p = 180.0 * 3600 / L_AREACAL_PI;            //// 弧度秒   180.0 * 3600 / L_AREACAL_PI   p = 1.0;
            double E2 = GetE2(earth_old.getA(), earth_old.getB());
            double W = 0.0, N = 0.0, M = 0.0;
            double Lat1 = 0.0, Lon1 = 0.0, H1 = 0.0;

            switch (model) {
                case _3D7Params:
                    B = new double[pairs.size() * 3][7];
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);
                        Lat1 = pair.getX_lat_1();
                        Lon1 = pair.getY_lon_1();
                        H1 = pair.getH_z_1();

                        W = Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat1), 2));
                        N = earth_old.getA() / W;                //// A/根号下（1-e2*sinB*sinB）   卯酉圈曲率半径N
                        M = earth_old.getA() * (1 - E2) / Math.pow(W, 3);                       //// 子午圈曲率半径M
                        //// L矩阵赋值
                        //// 第一行
                        B[3 * i][0] = -Math.sin(Lon1) * p / ((N + H1) * Math.cos(Lat1));
                        B[3 * i][1] = Math.cos(Lon1) * p / ((N + H1) * Math.cos(Lat1));
                        B[3 * i][2] = 0;
                        B[3 * i][3] = 0;
                        B[3 * i][4] = ((N * (1 - E2) + H1) / (N + H1)) * Math.tan(Lat1) * Math.cos(Lon1);
                        B[3 * i][5] = ((N * (1 - E2) + H1) / (N + H1)) * Math.tan(Lat1) * Math.sin(Lon1);
                        B[3 * i][6] = -1;
                        //// 第二行
                        B[3 * i + 1][0] = -Math.sin(Lat1) * Math.cos(Lon1) * p / (M + H1);
                        B[3 * i + 1][1] = -Math.sin(Lat1) * Math.sin(Lon1) * p / (M + H1);
                        B[3 * i + 1][2] = Math.cos(Lat1) * p / (M + H1);
                        B[3 * i + 1][3] = -N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * p / M;
                        B[3 * i + 1][4] = -Math.sin(Lon1) * (N + H1 - N * E2 * Math.pow(Math.sin(Lat1), 2)) / (M + H1);
                        B[3 * i + 1][5] = Math.cos(Lon1) * (N + H1 - N * E2 * Math.pow(Math.sin(Lat1), 2)) / (M + H1);
                        B[3 * i + 1][6] = 0;
                        //// 第三行
                        B[3 * i + 2][0] = Math.cos(Lat1) * Math.cos(Lon1);
                        B[3 * i + 2][1] = Math.sin(Lat1) * Math.sin(Lon1);
                        B[3 * i + 2][2] = Math.sin(Lat1);
                        B[3 * i + 2][3] = N + H1 - N * E2 * Math.pow(Math.sin(Lat1), 2);
                        B[3 * i + 2][4] = -N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * Math.sin(Lon1) / p;
                        B[3 * i + 2][5] = N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * Math.cos(Lon1) / p;
                        B[3 * i + 2][6] = 0;
                    }
                    break;

                case _2D7Params:
                    B = new double[pairs.size() * 2][7];
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);
                        Lat1 = pair.getX_lat_1();
                        Lon1 = pair.getY_lon_1();

                        W = Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat1), 2));
                        N = earth_old.getA() / W;                //// A/根号下（1-e2*sinB*sinB）   卯酉圈曲率半径N
                        M = earth_old.getA() * (1 - E2) / Math.pow(W, 3);                       //// 子午圈曲率半径M
                        //// L矩阵赋值
                        //// 第一行
                        B[2 * i][0] = -Math.sin(Lon1) * p / (N * Math.cos(Lat1));
                        B[2 * i][1] = Math.cos(Lon1) * p / (N * Math.cos(Lat1));
                        B[2 * i][2] = 0;
                        B[2 * i][3] = 0;
                        B[2 * i][4] = Math.tan(Lat1) * Math.cos(Lon1);
                        B[2 * i][5] = Math.tan(Lat1) * Math.sin(Lon1);
                        B[2 * i][6] = -1;
                        //// 第二行
                        B[2 * i + 1][0] = -Math.sin(Lat1) * Math.cos(Lon1) * p / M;
                        B[2 * i + 1][1] = -Math.sin(Lat1) * Math.sin(Lon1) * p / M;
                        B[2 * i + 1][2] = Math.cos(Lat1) * p / M;
                        B[2 * i + 1][3] = -N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * p / M;
                        B[2 * i + 1][4] = -Math.sin(Lon1);
                        B[2 * i + 1][5] = Math.cos(Lon1);
                        B[2 * i + 1][6] = 0;
                    }
                    break;
                case Molodensky:
                    B = new double[pairs.size() * 3][7];
                    //// 旋转中心坐标
                    double x0 = 0.0;
                    double y0 = 0.0;
                    double z0 = 0.0;
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);
                        x0 += pair.getX_lat_1();
                        y0 += pair.getY_lon_1();
                        z0 += pair.getH_z_1();
                    }
                    x0 = x0 / pairs.size();
                    y0 = y0 / pairs.size();
                    z0 = z0 / pairs.size();
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);
                        //// B矩阵赋值
                        //// 第一行
                        B[3 * i][0] = 1;
                        B[3 * i][1] = 0;
                        B[3 * i][2] = 0;
                        B[3 * i][3] = pair.getX_lat_1() - x0;
                        B[3 * i][4] = 0;
                        B[3 * i][5] = -(pair.getH_z_1() - z0);
                        B[3 * i][6] = pair.getY_lon_1() - y0;
                        //// 第二行
                        B[3 * i + 1][0] = 0;
                        B[3 * i + 1][1] = 1;
                        B[3 * i + 1][2] = 0;
                        B[3 * i + 1][3] = pair.getY_lon_1() - y0;
                        B[3 * i + 1][4] = pair.getH_z_1() - z0;
                        B[3 * i + 1][5] = 0;
                        B[3 * i + 1][6] = -(pair.getX_lat_1() - x0);
                        //// 第三行
                        B[3 * i + 2][0] = 0;
                        B[3 * i + 2][1] = 0;
                        B[3 * i + 2][2] = 1;
                        B[3 * i + 2][3] = pair.getH_z_1() - z0;
                        B[3 * i + 2][4] = -(pair.getY_lon_1() - y0);
                        B[3 * i + 2][5] = pair.getX_lat_1() - x0;
                        B[3 * i + 2][6] = 0;
                    }
                    break;
                default:
                    B = new double[pairs.size() * 3][7];
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);

                        //// B矩阵赋值
                        //// 第一行
                        B[3 * i][0] = 1;
                        B[3 * i][1] = 0;
                        B[3 * i][2] = 0;
                        B[3 * i][3] = pair.getX_lat_1();
                        B[3 * i][4] = 0;
                        B[3 * i][5] = -pair.getH_z_1();
                        B[3 * i][6] = pair.getY_lon_1();
                        //// 第二行
                        B[3 * i + 1][0] = 0;
                        B[3 * i + 1][1] = 1;
                        B[3 * i + 1][2] = 0;
                        B[3 * i + 1][3] = pair.getY_lon_1();
                        B[3 * i + 1][4] = pair.getH_z_1();
                        B[3 * i + 1][5] = 0;
                        B[3 * i + 1][6] = -pair.getX_lat_1();
                        //// 第三行
                        B[3 * i + 2][0] = 0;
                        B[3 * i + 2][1] = 0;
                        B[3 * i + 2][2] = 1;
                        B[3 * i + 2][3] = pair.getH_z_1();
                        B[3 * i + 2][4] = -pair.getY_lon_1();
                        B[3 * i + 2][5] = pair.getX_lat_1();
                        B[3 * i + 2][6] = 0;
                    }
                    break;
            }
            return B;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 获取莫洛金斯基参数
    /// </summary>
    /// <param name="earth_old"></param>
    /// <param name="earth_new"></param>
    /// <returns></returns>
    public static MolodenskyParams GetMolodenskyParams(EarthParams earth_old,
                                                        EarthParams earth_new,
                                                        List<CoordinatePointPair> pairs) {
        if (pairs == null || pairs.size() == 0) {
            return null;
        }

        MolodenskyParams molodenskyParams = null;
        try {
            molodenskyParams = new MolodenskyParams();
            //// 旋转中心坐标
            double x0 = 0.0;
            double y0 = 0.0;
            double z0 = 0.0;

            for (int i = 0; i < pairs.size(); i++) {
                CoordinatePointPair pair = pairs.get(i);
                x0 += pair.getX_lat_1();
                y0 += pair.getY_lon_1();
                z0 += pair.getH_z_1();
            }

            x0 = x0 / pairs.size();
            y0 = y0 / pairs.size();
            z0 = z0 / pairs.size();

            molodenskyParams.setX0(x0);
            molodenskyParams.setY0(y0);
            molodenskyParams.setZ0(z0);
        } catch (Exception ex) {
            return null;
        }

        return molodenskyParams;
    }

    /// <summary>
    /// 根据公共坐标点对和转换模型获取转换矩阵L(新旧差值矩阵)
    /// </summary>
    /// <param name="pairs"></param>
    /// <param name="model"></param>
    /// <returns></returns>
    public static double[][] GetMatrixL(EarthParams earth_old,
                                        EarthParams earth_new,
                                        List<CoordinatePointPair> pairs,
                                        EnumTransFormModel model) {
        if (pairs == null || pairs.size() == 0) {
            return null;
        }
        double[][] L = null;            //// 矩阵B   3Count X 7 的矩阵
        try {
            double p = 180.0 * 3600 / L_AREACAL_PI;       //// 弧度秒   180.0 * 3600 / L_AREACAL_PI   p = 1.0;
            double dA = earth_new.getA() - earth_old.getA();
            double dF = (1 / earth_new.getF()) - (1 / earth_old.getF());
            double E2 = GetE2(earth_old.getA(), earth_old.getB());
            double W = 0.0, N = 0.0, M = 0.0;
            double Lat1 = 0.0;
            double num1 = 0.0, num2 = 0.0;

            switch (model) {
                case _3D7Params:
                    L = new double[pairs.size() * 3][1];
                    //// 矩阵赋值
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);
                        Lat1 = pair.getX_lat_1();

                        W = Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat1), 2));
                        N = earth_old.getA() / W;                //// A/根号下（1-e2*sinB*sinB）   卯酉圈曲率半径N
                        M = earth_old.getA() * (1 - E2) / Math.pow(W, 3);                       //// 子午圈曲率半径M

                        num1 = N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * p * dA / (M * earth_old.getA())
                                + (2 - E2 * Math.pow(Math.sin(Lat1), 2)) * Math.sin(Lat1) * Math.cos(Lat1) * p * dF / (1 - 1 / earth_old.getF());

                        num2 = -W * dA + earth_old.getA() * (1 - E2) * Math.pow(Math.sin(Lat1), 2) * dF / ((1 - earth_old.getA()) * W);

                        //// L矩阵赋值
                        L[3 * i][0] = (pair.getY_lon_2() - pair.getY_lon_1()) * p;              //// 新旧经度值之差
                        L[3 * i + 1][0] = (pair.getX_lat_2() - Lat1) * p - num1;          //// 新旧纬度值之差-
                        L[3 * i + 2][0] = pair.getH_z_2() - pair.getH_z_1() - num2;          //// △H-
                    }
                    break;
                case _2D7Params:
                    L = new double[pairs.size() * 2][1];
                    //// 矩阵赋值
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);
                        Lat1 = pair.getX_lat_1();

                        W = Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat1), 2));
                        N = earth_old.getA() / W;                //// A/根号下（1-e2*sinB*sinB）   卯酉圈曲率半径N
                        M = earth_old.getA() * (1 - E2) / Math.pow(W, 3);                       //// 子午圈曲率半径M

                        num1 = N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * p * dA / (M * earth_old.getA())
                                + (2 - E2 * Math.pow(Math.sin(Lat1), 2)) * Math.sin(Lat1) * Math.cos(Lat1) * p * dF / (1 - 1 / earth_old.getF());

                        //// L矩阵赋值
                        L[2 * i][0] = (pair.getY_lon_2() - pair.getY_lon_1()) * p;              //// 新旧经度值之差
                        L[2 * i + 1][0] = (pair.getX_lat_2() - Lat1) * p - num1;          //// 新旧纬度值之差-
                    }
                    break;

                default:
                    L = new double[pairs.size() * 3][1];
                    //// 矩阵赋值
                    for (int i = 0; i < pairs.size(); i++) {
                        CoordinatePointPair pair = pairs.get(i);
                        //// L矩阵赋值
                        L[3 * i][0] = pair.getX_lat_2() - pair.getX_lat_1();          //// 新旧X值之差
                        L[3 * i + 1][0] = pair.getY_lon_2() - pair.getY_lon_1();      //// 新旧Y值之差
                        L[3 * i + 2][0] = pair.getH_z_2() - pair.getH_z_1();          //// 新旧Z值之差
                    }
                    break;
            }
            return L;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 根据原数据点获取矩阵B
    /// </summary>
    /// <param name="earth_old"></param>
    /// <param name="earth_new"></param>
    /// <param name="point"></param>
    /// <param name="pSevenParams"></param>
    /// <param name="model"></param>
    /// <returns></returns>
    public static double[][] GetMatrixB(EarthParams earth_old,
                                        EarthParams earth_new,
                                        CoordinatePoint point,
                                        EnumTransFormModel model) {
        if (point == null) {
            return null;
        }
        double[][] B = null;            //// 矩阵B   3Count X 7 的矩阵
        try {
            double p = 180.0 * 3600 / L_AREACAL_PI;             //// 弧度秒   180.0 * 3600 / L_AREACAL_PI  p = 1.0;
            double E2 = GetE2(earth_old.getA(), earth_old.getB());
            double W = 0.0, N = 0.0, M = 0.0;
            double Lat1 = 0.0, Lon1 = 0.0, H1 = 0.0;

            switch (model) {
                case _3D7Params:
                    B = new double[3][7];
                    Lat1 = point.GetXorLat();
                    Lon1 = point.GetYorLon();
                    H1 = point.GetHorZ();

                    W = Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat1), 2));
                    N = earth_old.getA() / W;                //// A/根号下（1-e2*sinB*sinB）   卯酉圈曲率半径N
                    M = earth_old.getA() * (1 - E2) / Math.pow(W, 3);                       //// 子午圈曲率半径M
                    //// L矩阵赋值
                    //// 第一行
                    B[0][0] = -Math.sin(Lon1) * p / ((N + H1) * Math.cos(Lat1));
                    B[0][1] = Math.cos(Lon1) * p / ((N + H1) * Math.cos(Lat1));
                    B[0][2] = 0;
                    B[0][3] = 0;
                    B[0][4] = ((N * (1 - E2) + H1) / (N + H1)) * Math.tan(Lat1) * Math.cos(Lon1);
                    B[0][5] = ((N * (1 - E2) + H1) / (N + H1)) * Math.tan(Lat1) * Math.sin(Lon1);
                    B[0][6] = -1;
                    //// 第二行
                    B[1][0] = -Math.sin(Lat1) * Math.cos(Lon1) * p / (M + H1);
                    B[1][1] = -Math.sin(Lat1) * Math.sin(Lon1) * p / (M + H1);
                    B[1][2] = Math.cos(Lat1) * p / (M + H1);
                    B[1][3] = -N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * p / M;
                    B[1][4] = -Math.sin(Lon1) * (N + H1 - N * E2 * Math.pow(Math.sin(Lat1), 2)) / (M + H1);
                    B[1][5] = Math.cos(Lon1) * (N + H1 - N * E2 * Math.pow(Math.sin(Lat1), 2)) / (M + H1);
                    B[1][6] = 0;
                    //// 第三行
                    B[2][0] = Math.cos(Lat1) * Math.cos(Lon1);
                    B[2][1] = Math.sin(Lat1) * Math.sin(Lon1);
                    B[2][2] = Math.sin(Lat1);
                    B[2][3] = N + H1 - N * E2 * Math.pow(Math.sin(Lat1), 2);
                    B[2][4] = -N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * Math.sin(Lon1) / p;
                    B[2][5] = N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * Math.cos(Lon1) / p;
                    B[2][6] = 0;
                    break;

                case _2D7Params:
                    B = new double[2][7];
                    Lat1 = point.GetXorLat();
                    Lon1 = point.GetYorLon();

                    W = Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat1), 2));
                    N = earth_old.getA() / W;                //// A/根号下（1-e2*sinB*sinB）   卯酉圈曲率半径N
                    M = earth_old.getA() * (1 - E2) / Math.pow(W, 3);                       //// 子午圈曲率半径M
                    //// L矩阵赋值
                    //// 第一行
                    B[0][0] = -Math.sin(Lon1) * p / (N * Math.cos(Lat1));
                    B[0][1] = Math.cos(Lon1) * p / (N * Math.cos(Lat1));
                    B[0][2] = 0;
                    B[0][3] = 0;
                    B[0][4] = Math.tan(Lat1) * Math.cos(Lon1);
                    B[0][5] = Math.tan(Lat1) * Math.sin(Lon1);
                    B[0][6] = -1;
                    //// 第二行
                    B[1][0] = -Math.sin(Lat1) * Math.cos(Lon1) * p / M;
                    B[1][1] = -Math.sin(Lat1) * Math.sin(Lon1) * p / M;
                    B[1][2] = Math.cos(Lat1) * p / M;
                    B[1][3] = -N * E2 * Math.sin(Lat1) * Math.cos(Lat1) * p / M;
                    B[1][4] = -Math.sin(Lon1);
                    B[1][5] = Math.cos(Lon1);
                    B[1][6] = 0;
                    break;
                default:
                    B = new double[3][7];
                    //// B矩阵赋值
                    //// 第一行
                    B[0][0] = 1;
                    B[0][1] = 0;
                    B[0][2] = 0;
                    B[0][3] = point.GetXorLat();
                    B[0][4] = 0;
                    B[0][5] = -point.GetHorZ();
                    B[0][6] = point.GetYorLon();
                    //// 第二行
                    B[1][0] = 0;
                    B[1][1] = 1;
                    B[1][2] = 0;
                    B[1][3] = point.GetYorLon();
                    B[1][4] = point.GetHorZ();
                    B[1][5] = 0;
                    B[1][6] = -point.GetXorLat();
                    //// 第三行
                    B[2][0] = 0;
                    B[2][1] = 0;
                    B[2][2] = 1;
                    B[2][3] = point.GetHorZ();
                    B[2][4] = -point.GetYorLon();
                    B[2][5] = point.GetXorLat();
                    B[2][6] = 0;
                    break;
            }

            return B;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 根据原数据点获取矩阵B
    /// </summary>
    /// <param name="point"></param>
    /// <param name="pMolodenskyParams"></param>
    /// <returns></returns>
    public static double[][] GetMatrixB(CoordinatePoint point,
                                        MolodenskyParams pMolodenskyParams) {
        if (point == null) {
            return null;
        }

        double[][] B = null;            //// 矩阵B   3Count X 7 的矩阵
        try {
            B = new double[][]
                    {
                            {1, 0, 0, point.GetXorLat() - pMolodenskyParams.getX0(), 0, -(point.GetHorZ() - pMolodenskyParams.getZ0()), point.GetYorLon() - pMolodenskyParams.getY0()}
                            ,
                            {0, 1, 0, point.GetYorLon() - pMolodenskyParams.getY0(), point.GetHorZ() - pMolodenskyParams.getZ0(), 0, -(point.GetXorLat() - pMolodenskyParams.getX0())}
                            ,
                            {0, 0, 1, point.GetHorZ() - pMolodenskyParams.getZ0(), -(point.GetYorLon() - pMolodenskyParams.getY0()), point.GetXorLat() - pMolodenskyParams.getX0(), 0}
                    };
        } catch (Exception ex) {
            return null;
        }

        return B;
    }

    /// <summary>
    /// 最小二乘法
    /// </summary>
    /// <param name="B"></param>
    /// <param name="L"></param>
    /// <returns></returns>
    public static double[][] LeastSquare(double[][] B, double[][] L) {
        try {
            double[][] BT = MatrixTranspose(B);                 //// 转置矩阵
            double[][] BTL = MatrixMultiply(BT, L);             //// BT*L
            double[][] BTB = MatrixMultiply(BT, B);             //// BT*B
            double[][] VBTB = Inverse(BTB);               //// BT*B的逆矩阵
            if (VBTB == null) {
                return null;
            }
            double[][] dValue = MatrixMultiply(VBTB, BTL);
            return dValue;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 矩阵加法
    /// </summary>
    /// <param name="X"></param>
    /// <param name="Y"></param>
    /// <returns></returns>
    public static double[][] MatrixPlus(double[][] X, double[][] Y) {
        try {
            int RowX = X.length;
            int ColX = X[0].length;
            int RowY = Y.length;
            int ColY = Y[0].length;
            if (RowX != RowY || ColX != ColY) {
                return null;
            }

            double[][] target = new double[RowX][ColX];
            for (int i = 0; i < RowX; i++) {
                for (int j = 0; j < ColX; j++) {
                    target[i][j] = X[i][j] + Y[i][j];
                }
            }
            return target;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 矩阵转置运算
    /// </summary>
    /// <param name="source"></param>
    /// <returns></returns>
    public static double[][] MatrixTranspose(double[][] source) {
        try {
            int Row = source.length;
            int Col = source[0].length;
            double[][] target = new double[Col][Row];
            for (int i = 0; i < Col; i++) {
                for (int j = 0; j < Row; j++) {
                    target[i][j] = source[j][i];
                }
            }
            return target;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 矩阵相乘
    /// </summary>
    /// <param name="X"></param>
    /// <param name="Y"></param>
    /// <returns></returns>
    public static double[][] MatrixMultiply(double[][] X, double[][] Y) {
        try {
            //// 不符合矩阵相乘的规则
            if (X[0].length != Y.length) {
                return null;
            }

            int row = X.length;
            int col = Y[0].length;
            int mid = X[0].length;
            double[][] target = new double[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    for (int k = 0; k < mid; k++) {
                        target[i][j] += X[i][k] * Y[k][j];
                    }
                }
            }
            return target;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 数乘
    /// </summary>
    /// <param name="source"></param>
    /// <param name="k"></param>
    /// <returns></returns>
    public static double[][] MatrixPly(double[][] source, double k) {
        try {
            int row = source.length;
            int col = source[0].length;
            double[][] target = new double[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    target[i][j] = source[i][j] * k;
                }
            }
            return target;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 求逆矩阵
    /// </summary>
    /// <param name="source"></param>
    /// <returns></returns>
    public static double[][] Inverse(double[][] source) {
        int row = source.length;
        int col = source[0].length;
        if (row != col) {
            return null;
        }
        //// 复制数组 
        double[][] copy = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                copy[i][j] = source[i][j];
            }
        }

        //// 单位矩阵
        double[][] E = new double[row][col];
        for (int i = 0; i < row; i++) {
            E[i][i] = 1;
        }

        //i表示第几行，j表示第几列  
        for (int j = 0; j < row; j++) {
            Boolean flag = false;
            for (int i = j; i < row; i++) {
                if (copy[i][j] != 0) {
                    flag = true;
                    double temp;
                    //交换i,j,两行  
                    if (i != j) {
                        for (int k = 0; k < row; k++) {
                            temp = copy[j][k];
                            copy[j][k] = copy[i][k];
                            copy[i][k] = temp;

                            temp = E[j][k];
                            E[j][k] = E[i][k];
                            E[i][k] = temp;
                        }
                    }
                    //第j行标准化  
                    double d = copy[j][j];
                    for (int k = 0; k < row; k++) {
                        copy[j][k] = copy[j][k] / d;
                        E[j][k] = E[j][k] / d;
                    }
                    //消去其他行的第j列  
                    d = copy[j][j];
                    for (int k = 0; k < row; k++) {
                        if (k != j) {
                            double t = copy[k][j];
                            for (int n = 0; n < row; n++) {
                                copy[k][n] -= (t / d) * copy[j][n];
                                E[k][n] -= (t / d) * E[j][n];
                            }
                        }
                    }
                }
            }
            if (!flag) return null;
        }
        return E;
    }

    /// <summary>
    /// 矩阵求逆(高斯法)
    /// </summary>
    /// <param name="source"></param>
    /// <returns></returns>
    public static double[][] MatrixInv(double[][] source) {
        int m = source.length;
        int n = source[0].length;
        if (m != n) {
            return null;
        }

        double[][] a = (double[][]) source.clone();
        double[][] b = new double[m][n];

        int i, j, row, k;
        double max, temp;

        //单位矩阵
        for (i = 0; i < n; i++) {
            b[i][i] = 1;
        }
        for (k = 0; k < n; k++) {
            max = 0;
            row = k;
            //找最大元，其所在行为row
            for (i = k; i < n; i++) {
                temp = Math.abs(a[i][k]);
                if (max < temp) {
                    max = temp;
                    row = i;
                }
            }
            if (max == 0) {
                return null;
            }

            //交换k与row行
            if (row != k) {
                for (j = 0; j < n; j++) {
                    temp = a[row][j];
                    a[row][j] = a[k][j];
                    a[k][j] = temp;

                    temp = b[row][j];
                    b[row][j] = b[k][j];
                    b[k][j] = temp;
                }
            }

            //首元化为1
            for (j = k + 1; j < n; j++) a[k][j] /= a[k][k];
            for (j = 0; j < n; j++) b[k][j] /= a[k][k];

            a[k][k] = 1;

            //k列化为0
            //对a
            for (j = k + 1; j < n; j++) {
                for (i = 0; i < k; i++) a[i][j] -= a[i][k] * a[k][j];
                for (i = k + 1; i < n; i++) a[i][j] -= a[i][k] * a[k][j];
            }
            //对b
            for (j = 0; j < n; j++) {
                for (i = 0; i < k; i++) b[i][j] -= a[i][k] * b[k][j];
                for (i = k + 1; i < n; i++) b[i][j] -= a[i][k] * b[k][j];
            }
            for (i = 0; i < n; i++) a[i][k] = 0;
            a[k][k] = 1;
        }

        return b;
    }

    /// <summary>
    /// 矩阵求逆(返回值如果为null，说明矩阵不可逆、矩阵数据有问题、或者矩阵不是方阵等)
    /// </summary>
    /// <param name="source"></param>
    /// <returns></returns>
    public static double[][] MatrixInverse(double[][] source) {
        try {
            //// 判断是否是方阵
            int RowNum = source.length;
            int ColNum = source[0].length;
            if (RowNum != ColNum) {
                return null;
            }

            //// N阶方阵可逆的充分必要条件是|A| ！= 0
            BigDecimal DetValue = MatrixValue(source);          //// 矩阵行列式值
            if (DetValue.equals(0)) {
                return null;
            }

            //// 获取伴随矩阵
            double[][] adjoint = new double[RowNum][RowNum];
            for (int i = 0; i < RowNum; i++) {
                for (int j = 0; j < RowNum; j++) {
                    adjoint[i][j] = Double.parseDouble((MatrixValue(MatrixCofactor(source, j, i)).divide(DetValue)).toString());
                }
            }

            return adjoint;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 求行列式的值(返回值如果是-1，表示矩阵有问题)
    /// </summary>
    /// <param name="source"></param>
    /// <returns></returns>
    public static BigDecimal MatrixValue(double[][] source) {
        try {
            BigDecimal dValue = new BigDecimal(0.0);
            int row = source.length;
            int col = source[0].length;
            //// 判断是否是方阵
            if (row != col) {
                return new BigDecimal(-1.0);
            }

            //// 1阶矩阵直接返回该值
            if (row == 1) {
                return new BigDecimal(source[0][0]);
            }

            //// 行列式展开
            for (int i = 0; i < col; i++) {
                BigDecimal anum = new BigDecimal(source[0][i]);
                BigDecimal bnum = MatrixValue(MatrixCofactor(source, 0, i));
                dValue = dValue.add(anum.multiply(bnum));
            }
            return dValue;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// 计算代数余子式
    /// </summary>
    /// <param name="source"></param>
    /// <param name="row"></param>
    /// <param name="col"></param>
    /// <returns></returns>
    public static double[][] MatrixCofactor(double[][] source, int row, int col) {
        try {
            int RowNum = source.length;
            int ColNum = source[0].length;
            double[][] target = new double[RowNum - 1][ColNum - 1];
            //// 矩阵赋值
            for (int i = 0; i < RowNum - 1; i++) {
                for (int j = 0; j < ColNum - 1; j++) {
                    if (i < row && j < col) {
                        target[i][j] = source[i][j];
                    } else if (i < row && j >= col) {
                        target[i][j] = source[i][j + 1];
                    } else if (i >= row && j < col) {
                        target[i][j] = source[i + 1][j];
                    } else {
                        target[i][j] = source[i + 1][j + 1];
                    }
                }
            }

            for (int i = 0; i < RowNum - 1; i++) {
                target[i][0] = Math.pow(-1, row + col) * target[i][0];
            }

            return target;
        } catch (Exception ex) {
            return null;
        }
    }

    /// <summary>
    /// BLH => XYZ
    /// </summary>
    /// <param name="format">度或者度分秒</param>
    /// <param name="earth"></param>
    /// <param name="point"></param>
    /// <returns></returns>
    public static CoordinatePoint TransFormXYZ(EnumCoordinateFormat format,
                                               EarthParams earth,
                                               CoordinatePoint point) {
        CoordinatePoint NewPoint = new CoordinatePoint();
        try {
            double B = DegreeToArc(point.GetXorLat());
            double L = DegreeToArc(point.GetYorLon());
            double H = point.GetHorZ();
            if (format == EnumCoordinateFormat.ddmmss) {
                B = DegreeToArc(DmsToDegree(point.GetXorLat()));
                L = DegreeToArc(DmsToDegree(point.GetYorLon()));
            }

            double E2 = GetE2(earth.getA(), earth.getB());
            double N = earth.getA() / Math.sqrt(1 - E2 * Math.pow(Math.sin(B), 2.0));

            NewPoint.SetXorLat((N + H) * Math.cos(B) * Math.cos(L));
            NewPoint.SetYorLon((N + H) * Math.cos(B) * Math.sin(L));
            NewPoint.SetHorZ(Math.sin(B) * (N * (1 - E2) + H));
        } catch (Exception ex) {
            return null;
        }
        return NewPoint;
    }

    /// <summary>
    /// XYZ => BLH
    /// </summary>
    /// <param name="earth"></param>
    /// <param name="point"></param>
    /// <returns></returns>
    public static CoordinatePoint TransFormBLH(EnumCoordinateFormat format,
                                               EarthParams earth,
                                               CoordinatePoint point) {
        CoordinatePoint NewPoint = new CoordinatePoint();
        try {
            double A = earth.getA();
            double B = earth.getB();
            double X = point.GetXorLat();
            double Y = point.GetYorLon();
            double Z = point.GetHorZ();
            double E2 = GetE2(earth.getA(), earth.getB());
            double E21 = (Math.pow(A, 2.0) - Math.pow(B, 2.0)) / Math.pow(B, 2.0);

            double L = Math.atan2(Y, X);

            double angel = Math.atan(Z * A / (B * Math.sqrt(X * X + Y * Y)));

            double Lat = Math.atan((Z + E21 * B * Math.pow(Math.sin(angel), 3.0)) / (Math.sqrt(X * X + Y * Y) - E2 * A * Math.pow(Math.cos(angel), 3.0)));

            double N = earth.getA() / Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat), 2.0));
            double H = Math.sqrt(X * X + Y * Y) / Math.cos(Lat) - N;

            NewPoint.SetXorLat(ArcToDegree(Lat));
            NewPoint.SetYorLon(ArcToDegree(L));
            NewPoint.SetHorZ(H);
            if (format == EnumCoordinateFormat.ddmmss) {
                NewPoint.SetXorLat(DegreeToDms(NewPoint.GetXorLat()));
                NewPoint.SetYorLon(DegreeToDms(NewPoint.GetYorLon()));
            }
        } catch (Exception ex) {
            return null;
        }
        return NewPoint;
    }

    /// <summary>
    /// XYZ => BLH(迭代法求解)
    /// </summary>
    /// <param name="earth"></param>
    /// <param name="point"></param>
    /// <returns></returns>
    public static CoordinatePoint TransFormBLH1(EnumCoordinateFormat format,
                                                EarthParams earth,
                                                CoordinatePoint point) {
        CoordinatePoint NewPoint = new CoordinatePoint();
        try {
            double X = point.GetXorLat();
            double Y = point.GetYorLon();
            double Z = point.GetHorZ();
            double E2 = GetE2(earth.getA(), earth.getB());

            double L = Math.atan2(Y, X);
            double N = 0.0;
            double Lat = 0.0;
            double Lat0 = Math.atan(Z / Math.sqrt(X * X + Y * Y));
            while ((Lat0 - Lat) > 1E-15) {
                Lat = Lat0;
                N = earth.getA() / Math.sqrt(1 - E2 * Math.pow(Math.sin(Lat), 2.0));
                Lat0 = Math.atan((Z + E2 * N * Math.sin(Lat)) / Math.sqrt(X * X + Y * Y));
            }

            Lat = Lat0;
            double H = Math.sqrt(X * X + Y * Y) / Math.cos(Lat) - N;

            NewPoint.SetXorLat(ArcToDegree(Lat));
            NewPoint.SetYorLon(ArcToDegree(L));
            NewPoint.SetHorZ(H);
            if (format == EnumCoordinateFormat.ddmmss) {
                NewPoint.SetXorLat(DegreeToDms(NewPoint.GetXorLat()));
                NewPoint.SetYorLon(DegreeToDms(NewPoint.GetYorLon()));
            }
        } catch (Exception ex) {
            return null;
        }
        return NewPoint;
    }
}