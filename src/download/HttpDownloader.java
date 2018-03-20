package download;

import java.io.IOException;
import java.util.Calendar;

public class HttpDownloader {
		 
	    public static void main(String[] args) {
	    	CreateURL c = new CreateURL();
	        String fileURL = c.createURL(Calendar.getInstance()); ;
	        String saveDir = "C:\\Users\\Utilisateur\\Videos\\Captures";
	        try {
	            DownloadFile.downloadFile(fileURL, saveDir);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

