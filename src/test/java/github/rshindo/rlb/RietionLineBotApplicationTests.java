package github.rshindo.rlb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RietionLineBotApplicationTests {

	@Autowired
	Twitter twitter;
	
	@Test
	public void contextLoads() {
	}

}
