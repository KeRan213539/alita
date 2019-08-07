package top.klw8.alita.entitys.demo;

import org.springframework.lang.Nullable;
import top.klw8.alita.utils.ValidateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @ClassName: GeoPoint
 * @Description: 位置数据pojo,用于mongodb实体中存放位置数据
 * @author klw
 * @date 2019-01-24 11:21:31
 */
public class GeoPoint implements java.io.Serializable {

    private static final long serialVersionUID = 5627974833973649527L;

    private String type = "Point";

    private final List<Double> coordinates = new ArrayList<Double>();

    public GeoPoint() {
    }

    /**
     * @Title: GeoPoint
     * @author klw
     * @Description: 构造方法
     * @param latitude
     *            纬度(x)
     * @param longitude
     *            经度(y)
     */
    public GeoPoint(double latitude, double longitude) {
	if(!ValidateUtil.checkLatLngRange(latitude, longitude)) {
	    throw new IllegalArgumentException("经纬度超过范围(经度: -90~90, 纬度: -180~180)");
	}
	coordinates.add(latitude);
	coordinates.add(longitude);
    }

    public GeoPoint(final List<Double> coordinates) {
	if(!ValidateUtil.checkLatLngRange(coordinates.get(0), coordinates.get(1))) {
	    throw new IllegalArgumentException("经纬度超过范围(经度: -90~90, 纬度: -180~180)");
	}
	this.coordinates.addAll(coordinates);
    }

    public List<Double> getCoordinates() {
	return coordinates;
    }

    public double getLatitude() {
	return coordinates.get(0);
    }

    public double getLongitude() {
	return coordinates.get(1);
    }
    
    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    @Override
    public int hashCode() {

	int result = 1;

	long temp = Double.doubleToLongBits(getLatitude());
	result = 31 * result + (int) (temp ^ temp >>> 32);

	temp = Double.doubleToLongBits(getLongitude());
	result = 31 * result + (int) (temp ^ temp >>> 32);

	return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

	if (this == obj) {
	    return true;
	}

	if (!(obj instanceof GeoPoint)) {
	    return false;
	}

	GeoPoint other = (GeoPoint) obj;

	if (Double.doubleToLongBits(getLatitude()) != Double
		.doubleToLongBits(other.getLatitude())) {
	    return false;
	}

	if (Double.doubleToLongBits(getLongitude()) != Double
		.doubleToLongBits(other.getLongitude())) {
	    return false;
	}

	return true;
    }

    @Override
    public String toString() {
	return String.format(Locale.ENGLISH, "Point [x=%f, y=%f]", getLatitude(), getLongitude());
    }
    
}