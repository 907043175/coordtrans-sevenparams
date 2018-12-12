package com.ztesoft.gis;

public class EarthParams {

    private String _Name;
    private String _GeoName;
    private double _A;
    private double _F;
    private double _B;

    /**
     * 参考椭球名称
     *
     * @return
     */
    public String get_Name() {
        return _Name;
    }

    /**
     * 地理坐标系名称
     *
     * @return
     */
    public String getGeoName() {
        return _GeoName;
    }

    /**
     * 长半轴
     *
     * @return
     */
    public double getA() {
        return _A;
    }

    /**
     * 短半轴
     *
     * @return
     */
    public double getB() {
        if (_B == 0 || _Name == "自定义") {
            _B = TransFormMethod.GetB(_A, _F);
        }
        return _B;
    }

    /**
     * 扁率
     *
     * @return
     */
    public double getF() {
        return _F;
    }

    public void set_A(double _A) {
        this._A = _A;
    }

    public void set_B(double _B) {
        this._B = _B;
    }

    public void set_F(double _F) {
        this._F = _F;
    }

    public void set_GeoName(String _GeoName) {
        this._GeoName = _GeoName;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }
}
