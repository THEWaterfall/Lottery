package waterfall.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import waterfall.model.User;
import waterfall.service.UserService;

@Controller
public class JsonController {
	
	@Autowired	
	private UserService userService;
	
	@RequestMapping(value = {"/users/save/{id}"}, method = RequestMethod.GET)
	public String saveToJson(@PathVariable int id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
			    WRITE_DATES_AS_TIMESTAMPS , false);
		
		File file = new File("user"+id+".json");
		User user = userService.findById(id);
		
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/users";
	}
}
