package com.poly.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
	
	@Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();           
        } catch (Exception e) {         
            e.printStackTrace();
        }
    }
    
//    public static void sendMessageWithTemplate(String toNumber, String fullname, String body, Map<String, String> variables) {
//        try {
//			Twilio.init(accountSid, authToken);
//			variables.put("fullname", fullname);
//			variables.put("content", body);
//			
//			String templateName = "Xin chào {fullname}, {content}";
//			MessageCreator messageCreator = new MessageCreator(
//                new PhoneNumber(toNumber),
//                new PhoneNumber(twilioPhoneNumber),
//                templateName);
//
//			messageCreator.setContentVariables(variables);
//			messageCreator.create();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    }
}
