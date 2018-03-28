package exper.rest.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author student
 * @version 1.0
 * @since  2018-04-27
 */

@Path("/SendMail")
public class MailServices {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final Boolean SESSION_DEBUG = true;
	private static final int SMTP_HOST_PORT = 465;
	private static final String SMTP_AUTH_PWD = "alexpoint123";
	private static final String SMTP_AUTH_EMAIL = "alexpoint123alexpoint@gmail.com";

	@POST
	@Path("/mail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)

	/**
	 * private static final String DOMAIN = "gmail.com"; private static final
	 * int PORT = 485; private static final String FROM_EMAIL =
	 * "alexpoint123alexpoint@gmail.com"; private static final String FROM_NAME
	 * = "Test account";
	 * 
	 * @param emailId
	 * @return
	 */
	public String sendMailService(@FormParam("email") String emailId) {
		StringBuilder builder = new StringBuilder();
		builder.append("<h2>Dear, Arpan Shah</h2>");
		builder.append("<p>Your Nurse Assistance Application Login can access with:</p>");
		builder.append("<p>Username: shaharpan8</p>");
		builder.append("<p>Password: 1234</p>");

		Properties properties = System.getProperties();
		properties.put("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.auth", "true");
		Session session = Session.getDefaultInstance(properties);
		session.setDebug(SESSION_DEBUG);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SMTP_AUTH_EMAIL));
			message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(emailId) });
			message.setSubject("Nurse Assistant Login Credentials");
			message.setContent(builder.toString(), "text/html");
			Transport transport = session.getTransport();
			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_EMAIL, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return "Email will be sent successfully";
	}
}
