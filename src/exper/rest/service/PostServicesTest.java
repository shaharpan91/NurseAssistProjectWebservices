package exper.rest.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PostServicesTest {
	private static PostServices pse;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pse = new PostServices();
	}


	@Test
	public final void test() {
		try {

			Assert.assertEquals("System accepted wrong username and password", "[]",
					pse.getString("@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@"));
			String str = pse.getString("Admin", "1234");
			Assert.assertTrue(str.toCharArray().length > 0);
			Assert.assertEquals("System accepted wrong password", "[]", pse.getString("Admin", "@@@@@@@@@@@@@@@@@"));
			Assert.assertEquals("System accepted wrong username", "[]", pse.getString("@@@@@@@@@@@@@@@@@", "1234"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
