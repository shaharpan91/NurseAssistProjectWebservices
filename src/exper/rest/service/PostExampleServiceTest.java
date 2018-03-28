package exper.rest.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PostExampleServiceTest {
	private static PostExampleService ps;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ps = new PostExampleService();

	}

	@Test
	public final void test() {
		try {

			Assert.assertEquals("System accepted wrong username and password", "Not Found",
					ps.login("@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@"));
			String s = ps.login("Admin", "1234");
			Assert.assertTrue(s.toCharArray().length > 0);
			Assert.assertEquals("System accepted wrong password", "Not Found", ps.login("Admin", "@@@@@@@@@@@@@@@@@"));
			Assert.assertEquals("System accepted wrong username", "Not Found", ps.login("@@@@@@@@@@@@@@@@@", "1234"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
