package com.gestor.driveToJava;

import java.io.IOException;
import java.net.URL;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;

public class DriveToJava {
 
	private static Analytics servicioAnalytics;
    private static GoogleCredential googleCredencial;
	    public static void main(String[] args) throws MalformedURLException, GeneralSecurityException, IOException, ServiceException {
	        URL SPREADSHEET_FEED_URL;
	        SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

	        File p12 = new File(OAuth2ClientCredenciales.getPATH_P12KEY_SERVICE_ACCOUNT());

	        HttpTransport httpTransport = new NetHttpTransport();
	        JacksonFactory jsonFactory = new JacksonFactory();
	        String[] SCOPESArray = {"https://spreadsheets.google.com/feeds", "https://spreadsheets.google.com/feeds/spreadsheets/private/full", "https://docs.google.com/feeds"};
	        final List<String> SCOPES = Arrays.asList(SCOPESArray);
//	        GoogleCredential credential = new GoogleCredential.Builder()
//	                .setTransport(httpTransport)
//	                .setJsonFactory(jsonFactory)
//	                .setServiceAccountId(OAuth2ClientCredenciales.getSERVICE_ACCOUNT_EMAIL())
//	                .setServiceAccountScopes(SCOPES)
//	                .setServiceAccountPrivateKeyFromP12File(p12)
//	                .build();
//
//	        SpreadsheetService service = new SpreadsheetService("8888");
//
//	        service.setOAuth2Credentials(credential);
	        
	        googleCredencial = new GoogleCredential.Builder()
                    .setTransport(OAuth2ClientCredenciales.getHTTP_TRANSPORT())
                    .setJsonFactory(OAuth2ClientCredenciales.getJSON_FACTORY())
                    .setServiceAccountId(OAuth2ClientCredenciales.getSERVICE_ACCOUNT_EMAIL())
                    .setServiceAccountScopes(SCOPES)
                    .setServiceAccountPrivateKeyFromP12File(new File(OAuth2ClientCredenciales.getPATH_P12KEY_SERVICE_ACCOUNT()))
                    .build();

	        SpreadsheetService service = new SpreadsheetService("graftin220terés");

	        service.setOAuth2Credentials(googleCredencial);
	        System.out.println(service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class).getEntries());
	        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	        System.out.println(feed.getEntries());
	        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

	        if (spreadsheets.size() == 0) {
	            System.out.println("No spreadsheets found.");
	        }

	        SpreadsheetEntry spreadsheet = null;
	        for (int i = 0; i < spreadsheets.size(); i++) {
	            if (spreadsheets.get(i).getTitle().getPlainText().startsWith("ListOfSandboxes")) {
	                spreadsheet = spreadsheets.get(i);
	                System.out.println("Name of editing spreadsheet: " + spreadsheets.get(i).getTitle().getPlainText());
	                System.out.println("ID of SpreadSheet: " + i);
	            }
	        }

	    }
	    	
	private static void obtenerServicioAutenticacion() throws IOException, GeneralSecurityException {
       
    }

    //Este mwtodo se encarga de cargar el objeto analytics tomando en cuenta la credencial que se contruyo anteriormente
    public static Analytics getServicioAnalytics() throws IOException, GeneralSecurityException {
        obtenerServicioAutenticacion();
        servicioAnalytics = new Analytics.Builder(
                OAuth2ClientCredenciales.getHTTP_TRANSPORT(), OAuth2ClientCredenciales.getJSON_FACTORY(), googleCredencial)
                .setApplicationName(OAuth2ClientCredenciales.getAPPLICATION_NAME())
                .build();
        return servicioAnalytics;
    }

	}
