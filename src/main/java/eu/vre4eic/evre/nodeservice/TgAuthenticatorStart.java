package eu.vre4eic.evre.nodeservice;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import eu.vre4eic.evre.nodeservice.usermanager.impl.TgAuthenticator;

public class TgAuthenticatorStart {

	public TgAuthenticatorStart() {
		// TODO Auto-generated constructor stub
	}
	    public static void main(String[] args) {

	        ApiContextInitializer.init();

	        TelegramBotsApi botsApi = new TelegramBotsApi();

	        try {
	            botsApi.registerBot(new TgAuthenticator());
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	    }
}
