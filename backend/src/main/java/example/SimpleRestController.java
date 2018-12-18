package example;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.concurrent.atomic.AtomicLong;
import java.util.List;


@RestController
public class SimpleRestController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name",defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
				String.format(template,name));
	}
	
}
