package osg.loki.simple_auth.model;

public class AlertDataModel {
	
	 private String name;
	    private String type;
	    private double longitude;
	    private double altitude;
	    private String about;

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public String getAbout() {
	        return about;
	    }

	    public void setAbout(String about) {
	        this.about = about;
	    }

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getAltitude() {
			return altitude;
		}

		public void setAltitude(double altitude) {
			this.altitude = altitude;
		}

	   


}
