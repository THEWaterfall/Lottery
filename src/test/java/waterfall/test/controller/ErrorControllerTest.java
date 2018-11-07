package waterfall.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import waterfall.controller.ErrorController;
import waterfall.test.config.HibernateTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@WebAppConfiguration
public class ErrorControllerTest {

	@Autowired
	private ErrorController errorController;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(errorController)
				.build();
	}
	
	@Test
	public void show400Error() throws Throwable {
		mockMvc.perform(get("/400"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/ErrorView"));
	}
	
	@Test
	public void show401Error() throws Throwable {
		mockMvc.perform(get("/401"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/ErrorView"));
	}
	
	@Test
	public void show404Error() throws Throwable {
		mockMvc.perform(get("/404"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/ErrorView"));
	}
	
	@Test
	public void show500Error() throws Throwable {
		mockMvc.perform(get("/500"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/ErrorView"));
	}
}
