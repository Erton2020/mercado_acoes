package br.pucminas.stockmarket.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderUtil
{
	@Value("${mail.credentials.addressFrom}")
	private String addressFrom;
	
	@Value("${mail.credentials.personal}")
	private String personal;
	
	@Value("${mail.credentials.password}")
	private String password;
	
	public void sendEmail(String recipientsTO, String subject, String htmlMessage)
	{
		try
		{
			Authenticator auth = configAuth();
			
			Session session = Session.getInstance(getProperties(),auth);
			
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(addressFrom,personal));
			mimeMessage.setReplyTo(InternetAddress.parse(recipientsTO, false));
			mimeMessage.setSubject(subject, "UTF-8");
			mimeMessage.setSentDate(Calendar.getInstance().getTime());
			mimeMessage.setContent(htmlMessage, "text/html; charset=utf-8");
			
			Transport.send(mimeMessage);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private Authenticator configAuth() 
	{
		Authenticator auth = new Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(addressFrom, password);
			}
		};
		
		return auth;
	}
	
	private Properties getProperties() throws IOException 
	{

		InputStream inputStream = null;

		try
		{
			
			Properties properties = new Properties();
			String propertyFile = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);

			if (inputStream != null) 
			{
				properties.load(inputStream);
			} 
			else 
			{
				throw new FileNotFoundException("File '" + propertyFile + "' not found on classpath.");
			}

			return properties;
		} 
		catch (Exception e) 
		{
			System.out.println("Exception: " + e);
		} 
		finally
		{
			inputStream.close();
		}

		return null;
	}

}
