package download;

import java.util.Calendar;


public class CreateURL {
	public String createURL(Calendar date) {
		String gfs = "gfs-avn_201";
		int year = date.get(Calendar.YEAR);
	    int month = date.get(Calendar.MONTH);
	    month = month+7;// 0 to 11
	    int day = date.get(Calendar.DAY_OF_MONTH);
	    day = day+2;
	    year = year - 14;
		String url_base = "https://nomads.ncdc.noaa.gov/data/gfs/";
		
		String utl = url_base + year + "0" + month + "/"
		+ year + "0" + month + day + "/" + "tmp/" + gfs + "_" +
		year + "0" + month + day+ "_" + "0000" + "_" + "060" + ".grb";
		System.out.println(utl);
		return utl;
	}
}
