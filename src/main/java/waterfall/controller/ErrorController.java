package waterfall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
	
	@RequestMapping(value = {"/400"}, method = RequestMethod.GET)
	public String error400(ModelMap model) {
		String errorMsg = "Http Error Code: 400. Bad Request";
		model.addAttribute("errorMsg", errorMsg);
		
		return "error/ErrorView";
	}
	
	@RequestMapping(value = {"/401"}, method = RequestMethod.GET)
	public String error401(ModelMap model) {
		String errorMsg = "Http Error Code: 401. Unauthorized";
		model.addAttribute("errorMsg", errorMsg);
		
		return "error/ErrorView";
	}
	
	@RequestMapping(value = {"/404"}, method = RequestMethod.GET)
	public String error404(ModelMap model) {
		String errorMsg = "Http Error Code: 404. Resource not found";
		model.addAttribute("errorMsg", errorMsg);
		
		return "error/ErrorView";
	}
	
	@RequestMapping(value = {"/500"}, method = RequestMethod.GET)
	public String error500(ModelMap model) {
		String errorMsg = "Http Error Code: 500. Internal Server Error";
		model.addAttribute("errorMsg", errorMsg);
		
		return "error/ErrorView";
	}
	
	@RequestMapping(value = {"/403"}, method = RequestMethod.GET)
	public String error403(ModelMap model) {
		String errorMsg = "Http Error Code: 404. Resource not found";
		model.addAttribute("errorMsg", errorMsg);
		
		return "error/ErrorView";
	}
	
}
