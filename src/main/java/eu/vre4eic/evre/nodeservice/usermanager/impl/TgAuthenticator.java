package eu.vre4eic.evre.nodeservice.usermanager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TgAuthenticator extends TelegramLongPollingBot {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    public void onUpdateReceived(Update update) {
    	 if (update.hasMessage() && update.getMessage().hasText()) {
    	        SendMessage message = new SendMessage() 
    	                .setChatId(update.getMessage().getChatId())
    	                .setText(update.getMessage().getText());
    	        try {
    	        	log.info("************************** ");
    	        	log.info(" "+message.getChatId());
    	        	log.info("************************** ");
    	            sendMessage(message); // Call method to send the message
    	        } catch (TelegramApiException e) {
    	            e.printStackTrace();
    	            log.error(e.toString());
    	        }
    	    }
    }

    @Override
    public String getBotUsername() {
        
        return "evre_tg_auth_bot";
    }

    @Override
    public String getBotToken() {
        
        return "304076530:AAFryi3D0xd7e6Vu_yyfiOpIMAPiHDAwIq0";
    }

	
}
