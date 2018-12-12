package com.ztesoft.gis;

import java.util.List;

public class Program {

    /**TraformBy2D_7Params_NT
     * 主程序入口
     * @param args
     */
    public static void main(String[] args) {
        //获取坐标串并计算七参数入库
//        getCharacterCoordsPair(1);
        //验证坐标：
        checkCoorBySevenParams(1);
        /*SevenParams pSevenParams = BJ54ToBDMCBy7params.GetSevenParamsByNT();
        String pNewPnt = BJ54ToBDMCBy7params.TraformBy2D_7Params_NT(
                3545751.105
                ,870511.977
                ,pSevenParams);
        System.out.println(pNewPnt);*/
//        String params = "120.919343149362,31.974158435745,934080.316406250000000000,-317186.079589843983000000,1182056.083007809940000000,33343.751434326201000000,32747.106491088899900000,-17468.596374511700600000,83433.254185365498400000";
//        String pNewPnt = BJ54ToBDMCBy7params.TraformByStrParamsReverse(params);
//        System.out.println(pNewPnt);
//
//        String params2 = "864644.196,3549185.642,-933882.462890625000000000,316070.562500000000000000,-1180385.625427250050000000,-33284.795570373498800000,-32718.983520507801600000,17479.708648681600600000,-83491.368044633403800000";
//        String pNewPnt2 = BJ54ToBDMCBy7params.TraformByStrParams(params2);
//        System.out.println(pNewPnt2);
    }

    /**
     * 获取每个网格内的坐标串对
     * @param direction 方向 1正向 2反向
     */
    public static void getCharacterCoordsPair(int direction){
        String tableName = "nt_characterpoint_54to84";
        if (direction == 2) {
            tableName = "nt_characterpoint_84to54";
        }
        List<List<String>> characterpoint = CharacterPoints.getCharacterPoints(tableName);
        String pointStr ="";
        int tmpGridid= 0;
        for(int i=0;i<characterpoint.size();i++){
            List<String>  tmp = characterpoint.get(i);
            int gridId = Integer.valueOf(tmp.get(0));
            if(tmpGridid == 0){
                tmpGridid = gridId;
                //计算七参数坐标串格式：3540847.1190999998,871612.8258000000,31.9296080929,120.9290532183
                //先纬度 后经度
                pointStr += tmp.get(2).toString()+"," + tmp.get(1).toString() + ","
                         + tmp.get(4).toString()+"," + tmp.get(3).toString() + ";";
            }else if (tmpGridid == gridId){
                pointStr += tmp.get(2).toString()+"," + tmp.get(1).toString() + ","
                         + tmp.get(4).toString()+"," + tmp.get(3).toString() + ";";
                if(i==characterpoint.size()-1){
                    pointStr += tmp.get(2).toString()+"," + tmp.get(1).toString() + ","
                             + tmp.get(4).toString()+"," + tmp.get(3).toString() + ";";
                    insertSevenParamsToDb(pointStr,tmpGridid,direction);
                }
            }else{
                insertSevenParamsToDb(pointStr,tmpGridid,direction);
                //不同的网格，tmpgrid和坐标串要重新刷值
                tmpGridid = gridId;
                pointStr = tmp.get(2).toString()+"," + tmp.get(1).toString() + ","
                         + tmp.get(4).toString()+"," + tmp.get(3).toString() + ";";
            }

        }
    }

    public static void insertSevenParamsToDb(String sPnts,int gridId, int direction){
        SevenParams pSevenParams;
        String tableName = "";
        if (direction == 1) {
            tableName = "map_coord_sevenparams_54to84";
            pSevenParams = SevenParamsCompute.Compute7ParamsBy54ToBD_NT(sPnts);
        }
        else {
            tableName = "map_coord_sevenparams_84to54";
            pSevenParams = SevenParamsCompute.Compute7ParamsByBDTo54_NT(sPnts);
        }
        CharacterPoints.insert7Params(tableName,pSevenParams,gridId);
    }

    public static void checkCoorBySevenParams(int direction) {
        if (direction == 1) {
            checkCoorBySevenParams();
        } else {
            checkCoorBySevenParams84To54();
        }
    }
    //利用七参数验证坐标
    public static void checkCoorBySevenParams(){
        //获取所有网格的七参数
        List<List<String>> sevenParamsList = CharacterPoints.GetSevenParams("map_coord_sevenparams_54to84");
        //从数据库读坐标
        List<List<String>> characterpoint = CharacterPoints.getCharacterPoints("nt_characterpoint_54to84");
        String pointStr ="";
        SevenParams pSevenParams = new SevenParams();
        for(int i=0;i<characterpoint.size();i++){
            List<String>  tmp = characterpoint.get(i);
            int gridId = Integer.valueOf(tmp.get(0));
            for(int j=0;j<sevenParamsList.size();j++){
                List<String>  tmp1 = sevenParamsList.get(j);
                if(Integer.valueOf(tmp1.get(0)) == gridId){
                    setSevenParams(tmp1,pSevenParams);
                }
            }
            //根据北京54坐标和七参数计算wgs84坐标
            Double bj54x = Double.valueOf(tmp.get(2).toString());
            Double bj54y = Double.valueOf(tmp.get(1).toString());
            String pNewPnt = BJ54ToBDMCBy7params.TraformBy2D_7Params_NT(bj54x,bj54y,pSevenParams);
            //计算误差
            Double old_wgs84x = Double.valueOf(tmp.get(3).toString());
            Double old_wgs84y = Double.valueOf(tmp.get(4).toString());
            Double new_wgs84x = Double.valueOf(pNewPnt.split(",")[1]);
            Double new_wgs84y = Double.valueOf(pNewPnt.split(",")[0]);
            Double error_x = (old_wgs84x - new_wgs84x) * 111000;
            Double error_y = (old_wgs84y - new_wgs84y) * 111000;
            CharacterPoints.insertCheckCoord("nt_characterpoint_54to84",gridId,bj54y,bj54x,
                    new_wgs84x,new_wgs84y,error_x,error_y);//这边传的时候bj54x和也要反传
        }
        System.out.println("误差数据已录入");
    }

    //利用七参数验证坐标
    public static void checkCoorBySevenParams84To54(){
        //获取所有网格的七参数
        List<List<String>> sevenParamsList = CharacterPoints.GetSevenParams("map_coord_sevenparams_84to54");
        //从数据库读坐标
        List<List<String>> characterpoint = CharacterPoints.getCharacterPoints("nt_characterpoint_84to54");
        String pointStr ="";
        SevenParams pSevenParams = new SevenParams();
        for(int i=0;i<characterpoint.size();i++){
            List<String>  tmp = characterpoint.get(i);
            int gridId = Integer.valueOf(tmp.get(0));
            for(int j=0;j<sevenParamsList.size();j++){
                List<String>  tmp1 = sevenParamsList.get(j);
                if(Integer.valueOf(tmp1.get(0)) == gridId){
                    setSevenParams(tmp1,pSevenParams);
                }
            }
            //根据wgs84坐标和七参数计算北京54坐标
            Double wgs84x = Double.valueOf(tmp.get(4).toString());
            Double wgs84y = Double.valueOf(tmp.get(3).toString());
            String pNewPnt = BJ54ToBDMCBy7params.TraformBy2D_7Params_84to54_NT(wgs84x,wgs84y,pSevenParams);
            //计算误差
            Double old_bj54x = Double.valueOf(tmp.get(1).toString());
            Double old_bj54y = Double.valueOf(tmp.get(2).toString());
            Double new_bj54x = Double.valueOf(pNewPnt.split(",")[1]);
            Double new_bj54y = Double.valueOf(pNewPnt.split(",")[0]);
            Double error_x = old_bj54x - new_bj54x;
            Double error_y = old_bj54y - new_bj54y;
            CharacterPoints.insertCheckCoord84To54("nt_characterpoint_84to54",gridId,old_bj54x,old_bj54y,
                    new_bj54x,new_bj54y,error_x,error_y);//这边传的时候bj54x和也要反传
        }
        System.out.println("误差数据已录入");
    }

    protected static SevenParams setSevenParams(List<String> sevenParams,SevenParams pSevenParams) {
        pSevenParams.set_DX(Double.valueOf(sevenParams.get(2)));
        pSevenParams.set_DY(Double.valueOf(sevenParams.get(3)));
        pSevenParams.set_DZ(Double.valueOf(sevenParams.get(4)));
        pSevenParams.set_AngleX(Double.valueOf(sevenParams.get(5)));
        pSevenParams.set_AngleY(Double.valueOf(sevenParams.get(6)));
        pSevenParams.set_AngleZ(Double.valueOf(sevenParams.get(7)));
        pSevenParams.set_ScaleK(Double.valueOf(sevenParams.get(8)));
        return pSevenParams;
    }
}