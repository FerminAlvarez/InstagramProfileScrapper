package Scrap.Instagram.InstagramProfileScrapper;

import java.io.File;

public class FilesController {
	public static String createFolder(String name) {

		File theDir = new File(System.getProperty("user.dir")+"/"+name);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		return theDir.getAbsolutePath();
	}
}
