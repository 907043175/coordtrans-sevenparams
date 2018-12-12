package com.ztesoft.gis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterPoints {
    public static List<List<String>> getCharacterPoints(String tableName){
        List<List<String>> result = new ArrayList<List<String>>();
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String sql = " select gridid,bj54_x,bj54_y,wgs84_x,wgs84_y from "+tableName +" order by gridid ";
        try {
            pstmt = conn.prepareStatement(sql);

            resultSet = pstmt.executeQuery();
            int count = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                List<String> tmpList = new ArrayList<String>();

                for(int i = 1; i <= count; i++) {//遍历列
                    tmpList.add(resultSet.getString(i));
                }

                result.add(tmpList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return result;
    }

    public static void insert7Params(String tableName, SevenParams sevenParams, int gridId){
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql = " insert into " + tableName + "(TYPEID, GRIDID, DX,DY,DZ,ANGLEX,ANGLEY,ANGLEZ,SCALEK) "
                + "values (1," + gridId + "," + sevenParams.get_DX() + "," + sevenParams.get_DY() + ","
                + sevenParams.get_DZ() + "," + sevenParams.get_AngleX() + "," + sevenParams.get_AngleY()
                + "," + sevenParams.get_AngleZ() + "," +sevenParams.get_ScaleK() + ")";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            DBUtil.commit(conn);
            System.out.println("grid:"+gridId+"已录入");
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }
    public static void insertCheckCoord(String tableName,int gridId,Double bj54x,Double bj54y,
                                        Double new_wgs84x,Double new_wgs84y,Double error_x,Double error_y){
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
//        String sql1 = " insert into " + tableName + "(PARENTGRIDID, A_X, A_Y,B_X,B_Y,B_X_NEW,B_Y_NEW,ERROR_X,ERROR_Y) "
//                + "values (" + gridId + "," + bj54x + "," + bj54y + "," + old_wgs84x + "," + old_wgs84y
//                + "," + new_wgs84x+ "," + new_wgs84y + "," + error_x + "," + error_y + ")";
        String sql = " update " + tableName + " set WGS84_NEW_X = '" + new_wgs84x + "',WGS84_NEW_Y = '"
                   + new_wgs84y + "', WGS84_ERR_X = '" + error_x + "',WGS84_ERR_Y = '" + error_y
                   + "' where gridid = " + gridId + " and BJ54_X = '" + bj54x + "' and BJ54_Y = '" + bj54y + "'";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            DBUtil.commit(conn);
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    public static void insertCheckCoord84To54(String tableName,int gridId,Double bj54x,Double bj54y,
                                        Double new_bj54x,Double new_bj54y,Double error_x,Double error_y){
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
//        String sql1 = " insert into " + tableName + "(PARENTGRIDID, A_X, A_Y,B_X,B_Y,B_X_NEW,B_Y_NEW,ERROR_X,ERROR_Y) "
//                + "values (" + gridId + "," + bj54x + "," + bj54y + "," + old_wgs84x + "," + old_wgs84y
//                + "," + new_wgs84x+ "," + new_wgs84y + "," + error_x + "," + error_y + ")";
        String sql = " update " + tableName + " set BJ54_NEW_X = '" + new_bj54x + "',BJ54_NEW_Y = '"
                + new_bj54y + "', BJ54_ERR_X = '" + error_x + "',BJ54_ERR_Y = '" + error_y
                + "' where gridid = " + gridId + " and BJ54_X = '" + bj54x + "' and BJ54_Y = '" + bj54y + "'";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            DBUtil.commit(conn);
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    public static List<List<String>> GetSevenParams(String tableName){
        List<List<String>> result = new ArrayList<List<String>>();
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String sql = " select * from "+tableName +" order by gridid ";
        try {
            pstmt = conn.prepareStatement(sql);

            resultSet = pstmt.executeQuery();
            int count = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                List<String> tmpList = new ArrayList<String>();

                for(int i = 1; i <= count; i++) {//遍历列
                    tmpList.add(resultSet.getString(i));
                }

                result.add(tmpList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return result;
    }
}
