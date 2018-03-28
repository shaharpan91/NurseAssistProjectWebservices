package exper.rest.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

/**
 * 
 * @author student
 * @version 1.0
 * @since 2018-04-24
 */

@Path("/PostExampleService")
public class PostExampleService {
	final static String url = "jdbc:mysql://javabootcamptest2.cwxzbm81rju6.eu-central-1.rds.amazonaws.com:3307/Medication_assistant?autoReconnect=true&useSSL=false";
	final static String user = "root";
	final static String pass = "javabootcamp2018";

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)

	/**
	 * The Login program implements an application that displays patient list
	 * from database to the standard output.
	 * <p>
	 * Giving proper comments in your program makes it more user friendly and it
	 * is assumed as a high quality code.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(@FormParam("username") String username, @FormParam("password") String password) {
		Gson gson = new Gson();
		String result = "Not Found";
		Map<String, String> map = new HashMap<String, String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			String stat = "select * from Medication_assistant.login_master where login_username='" + username
					+ "' and login_password='" + password + "'";
			Statement ps = con.createStatement();

			ResultSet rs = ps.executeQuery(stat);

			if (rs.next()) {
				String level = "";
				level = rs.getString("login_type");
				if (level.equalsIgnoreCase("Nurse")) {
					int id = rs.getInt("login_nurse_id");
					String stat1 = "select nurse_first_name, nurse_last_name from Medication_assistant.Nurses_master where nurse_id="
							+ id;
					Statement ps1 = con.createStatement();
					ResultSet rs1 = ps1.executeQuery(stat1);
					if (rs1.next()) {
						map.put("Role", rs.getString("login_type"));
						map.put("firstname", rs1.getString("nurse_first_name"));
						map.put("lastname", rs1.getString("nurse_last_name"));

						result = gson.toJson(map);
						// result = rs1.getString("nurse_first_name") +" "+
						// rs1.getString("nurse_last_name");
						return result;
					}
				} else if (level.equalsIgnoreCase("Doctor")) {
					int id = rs.getInt("login_doctor_id");
					String stat1 = "select doc_doctor_first_name, doc_doctor_last_name from Medication_assistant.doctor_master where doc_id="
							+ id;
					Statement ps1 = con.createStatement();
					ResultSet rs1 = ps1.executeQuery(stat1);
					if (rs1.next()) {
						map.put("Role", rs.getString("login_type"));
						map.put("firstname", rs1.getString("doc_doctor_first_name"));
						map.put("lastname", rs1.getString("doc_doctor_last_name"));

						result = gson.toJson(map);
						// result = rs1.getString("doc_doctor_first_name") +" "+
						// rs1.getString("doc_doctor_last_name");
						return result;
					}
				} else if (level.equalsIgnoreCase("Admin")) {
					// map.put("name", "Admin");
					// map.put("status", "OK");

					// result ="Admin";
					map.put("Role", rs.getString("login_type"));
					map.put("Name", "Admin");
					result = gson.toJson(map);
					return result;
				}
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}