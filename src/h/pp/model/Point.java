package h.pp.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Point {

	private final IntegerProperty Num;
	private final FloatProperty AX;
	private final FloatProperty AY;
	private final FloatProperty AZ;
	
	private final FloatProperty MX;
	private final FloatProperty MY;
	private final FloatProperty MZ;
	
	private final FloatProperty GX;
	private final FloatProperty GY;
	private final FloatProperty GZ;
	

	private final FloatProperty Heading;

	private final FloatProperty Tiltheading;
	
	private final FloatProperty Temperature;
	
	private final FloatProperty Pressure;
	
	private final FloatProperty ATM;
	
	private final FloatProperty Altitude;
	
	private final FloatProperty Time;
	
	/**
     *  онструктор по умолчанию.
     */
	public Point() {
		this(0,0,0);
	}
	/**
     *  онструктор с некоторыми начальными данными.
     * 
     * @param AX
     * @param AY
     */
	public Point(int num,float AX,float AY) {
		this.Num = new SimpleIntegerProperty(num);
		this.AX = new SimpleFloatProperty(AX);
		this.AY = new SimpleFloatProperty(AY);
		this.AZ = new SimpleFloatProperty();
		
		this.MX = new SimpleFloatProperty();
		this.MY = new SimpleFloatProperty();
		this.MZ = new SimpleFloatProperty();
		
		this.GX = new SimpleFloatProperty();
		this.GY = new SimpleFloatProperty();
		this.GZ = new SimpleFloatProperty();
		
		this.Heading = new SimpleFloatProperty();
		this.Tiltheading = new SimpleFloatProperty();
		this.Temperature = new SimpleFloatProperty();
		this.Pressure = new SimpleFloatProperty();
		this.ATM = new SimpleFloatProperty();
		this.Altitude = new SimpleFloatProperty();
		this.Time = new SimpleFloatProperty();	
	}
	public Integer getNum() {
		return Num.get();
	}
	public void setNum(Integer num) {
		this.Num.set(num);
	}
	public IntegerProperty NumProperty() {
		return Num;
	}
	
	public Float getAX() {
		return AX.get();
	}
	public void setAX(Float AX) {
		this.AX.set(AX);
	}
	public FloatProperty AXProperty() {
		return AX;
	}
	
	public Float getAY() {
		return AY.get();
	}
	public void setAY(Float AY) {
		this.AY.set(AY);
	}
	public FloatProperty AYProperty() {
		return AY;
	}
	
	public Float getAZ() {
		return AZ.get();
	}
	public void setAZ(Float AZ) {
		this.AZ.set(AZ);
	}
	public FloatProperty AZProperty() {
		return AZ;
	}
	
	public Float getGX() {
		return GX.get();
	}
	public void setGX(Float GX) {
		this.GX.set(GX);
	}
	public FloatProperty GXProperty() {
		return GX;
	}
	
	public Float getGY() {
		return GY.get();
	}
	public void setGY(Float GY) {
		this.GY.set(GY);
	}
	public FloatProperty GYProperty() {
		return GY;
	}
	
	public Float getGZ() {
		return GZ.get();
	}
	public void setGZ(Float GZ) {
		this.GZ.set(GZ);
	}
	public FloatProperty GZProperty() {
		return GZ;
	}
	
	public Float getMX() {
		return MX.get();
	}
	public void setMX(Float MX) {
		this.MX.set(MX);
	}
	public FloatProperty MXProperty() {
		return MX;
	}
	
	public Float getMY() {
		return MY.get();
	}
	public void setMY(Float MY) {
		this.MY.set(MY);
	}
	public FloatProperty MYProperty() {
		return MY;
	}
	
	public Float getMZ() {
		return MZ.get();
	}
	public void setMZ(Float MZ) {
		this.MZ.set(MZ);
	}
	public FloatProperty MZProperty() {
		return MZ;
	}
	
	public Float getHeading() {
		return Heading.get();
	}
	public void setHeading(Float Heading) {
		this.Heading.set(Heading);
	}
	public FloatProperty HeadingProperty() {
		return Heading;
	}
	
	public Float getTiltheading() {
		return Tiltheading.get();
	}
	public void setTiltheading(Float Tiltheading) {
		this.Tiltheading.set(Tiltheading);
	}
	public FloatProperty TiltheadingProperty() {
		return Tiltheading;
	}
	
	public Float getTemperature() {
		return Temperature.get();
	}
	public void setTemperature(Float Temperature) {
		this.Temperature.set(Temperature);
	}
	public FloatProperty TemperatureProperty() {
		return Temperature;
	}
	
	public Float getPressure() {
		return Pressure.get();
	}
	public void setPressure(Float Pressure) {
		this.Pressure.set(Pressure);
	}
	public FloatProperty PressureProperty() {
		return Pressure;
	}
	
	public Float getATM() {
		return ATM.get();
	}
	public void setATM(Float ATM) {
		this.ATM.set(ATM);
	}
	public FloatProperty ATMProperty() {
		return ATM;
	}
	
	public Float getAltitude() {
		return Altitude.get();
	}
	public void setAltitude(Float Altitude) {
		this.Altitude.set(Altitude);
	}
	public FloatProperty AltitudeProperty() {
		return Altitude;
	}

	public Float getTime() {
		return Time.get();
	}
	public void setTime(Float Time) {
		this.Time.set(Time);
	}
	public FloatProperty TimeProperty() {
		return Time;
	}
}
