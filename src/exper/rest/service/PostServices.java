package exper.rest.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
 * @since 2018-04-26
 */

@Path("/PostMethod")
public class PostServices {
	final static String url = "jdbc:mysql://javabootcamptest2.cwxzbm81rju6.eu-central-1.rds.amazonaws.com:3307/Medication_assistant?autoReconnect=true&useSSL=false";
	final static String user = "root";
	final static String pass = "javabootcamp2018";

	@POST
	@Path("/patList")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)

	/**
	 * The Login program implements an application that return logged first name
	 * and last name with their role to the standard output.
	 * <p>
	 * Giving proper comments in your program makes it more user friendly and it
	 * is assumed as a high quality code.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public String getString(@FormParam("uname") String username, @FormParam("pwd") String password) {
		// StringBuilder str = new StringBuilder();
		Gson gson = new Gson();
		String json = "";
		List<Map<String, String>> map = new LinkedList<Map<String, String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);

			String stat1 = "select login_username, login_password from Medication_assistant.login_master where login_username='"
					+ username + "' and login_password='" + password + "'";
			Statement ps1 = con.createStatement();

			ResultSet rs1 = ps1.executeQuery(stat1);
			if (rs1.next()) {
				String stat = "SELECT pa.pat_personal_code, pa.pat_patient_first_name, pa.pat_patient_last_name, pa.pat_room, pat_floor_no, ra.recipe_medicine_name,ra.recipe_dosage_type,ra.recipe_time FROM Medication_assistant.Patient_master  as pa INNER JOIN Medication_assistant.Recipes as ra ON pa.pat_id = ra.pat_id AND pa.pat_active=1";
				Statement ps = con.createStatement();
				ResultSet rs = ps.executeQuery(stat);
				while (rs.next()) {
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("personal code", rs.getString("pa.pat_personal_code"));
					map2.put("firstname", rs.getString("pa.pat_patient_first_name"));
					map2.put("lastname", rs.getString("pa.pat_patient_last_name"));
					map2.put("room no", rs.getString("pa.pat_room"));
					map2.put("floor no", rs.getString("pa.pat_floor_no"));
					map2.put("medicine name", rs.getString("ra.recipe_medicine_name"));
					map2.put("dosage type", rs.getString("ra.recipe_dosage_type"));
					map2.put("recipe time", rs.getString("ra.recipe_time"));

					map.add(map2);
				}
			}
			json = gson.toJson(map);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

}
