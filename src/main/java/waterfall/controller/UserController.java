package waterfall.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import waterfall.model.Role;
import waterfall.model.User;
import waterfall.service.RoleService;
import waterfall.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = {"/users"}, method = RequestMethod.GET)
	public String showUserList(ModelMap model) {
		List<User> userList = userService.findAll();
		model.addAttribute("users", userList);
		
		return "UserListView";
	}
	
	@RequestMapping(value = {"/users/add"}, method = RequestMethod.GET)
	public String showAddUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		
		return "AddUserView";
	}
	
	@RequestMapping(value = {"/users/add"}, method = RequestMethod.POST)
	public String addUser(ModelMap model, @Valid @ModelAttribute("user") User user, BindingResult result) {
		if(userService.findByUsername(user.getUsername()) != null) {
			FieldError fieldError = new FieldError("user", "username", "username is already taken");
			result.addError(fieldError);
		}
		
		if(result.hasErrors()) {
			return "AddUserView";
		}
		
		userService.save(user);
		
		return "redirect:/users";
	}
	
	@RequestMapping(value = {"/users/edit/{id}"}, method = RequestMethod.GET)
	public String showEditUser(ModelMap model, @PathVariable int id) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "EditUserView";
	}
	
	@RequestMapping(value = {"/users/edit/{id}"}, method = RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
		if(!userService.isUsernameUnique(user)) {
			FieldError fieldError = new FieldError("user", "username", "username is already taken");
			result.addError(fieldError);
		}
		
		if(result.hasErrors()) {
			return "AddUserView";
		}
		
		userService.update(user);
		
		return "redirect:/users";
	}
	
	@RequestMapping(value = {"/users/remove/{id}"}, method = RequestMethod.GET)
	public String removeUser(ModelMap model, @PathVariable int id) {
		User user = userService.findById(id);
		userService.remove(user);
		
		return "redirect:/users";
	}
	
	@ModelAttribute("roles")
	public List<Role> getRoles() {
		List<Role> roles = roleService.findAll();
		
		return roles;
	}
}
