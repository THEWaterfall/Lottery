package waterfall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import waterfall.model.Role;
import waterfall.service.RoleService;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

	@Autowired
	private RoleService roleService;
	
	@Override
	public Role convert(String id) {
		Role role = roleService.findById(Integer.valueOf(id));
		
		return role;
	}

}
