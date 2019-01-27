package cz.srnet.pisqorker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
final class WebController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

}
