package github.rshindo.rlb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Ryo Shindo
 *
 */
@RestController
public class HealthController {

	@GetMapping("ping")
	public String ping() {
		return "pong";
	}
	
}
