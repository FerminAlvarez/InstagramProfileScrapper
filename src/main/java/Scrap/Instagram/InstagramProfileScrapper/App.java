package Scrap.Instagram.InstagramProfileScrapper;

import javax.swing.JOptionPane;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
    	ChromeDriverController controllerDriver = null;
        try {        	
	        InstagramCredentials instagramCredentials = new InstagramCredentials();
	        instagramCredentials.setUsername(JOptionPane.showInputDialog("Ingrese su usuario por favor."));
	        instagramCredentials.setPassword(JOptionPane.showInputDialog("Ingrese su contraseña por favor."));
	        
	        controllerDriver = new ChromeDriverController(instagramCredentials);
			controllerDriver.login();
			controllerDriver.closePopUps();
			controllerDriver.goToProfile();
			controllerDriver.downloadImages(FilesController.createFolder(instagramCredentials.getUsername()));
			
		} catch (InstagramCredentialsException e) {
			e.printStackTrace();
		}finally{
			controllerDriver.closeDriver();
		}
    }
}
