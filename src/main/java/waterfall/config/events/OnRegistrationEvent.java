package waterfall.config.events;

import org.springframework.context.ApplicationEvent;

import waterfall.model.User;

public class OnRegistrationEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private String url;
	private User user;

	public OnRegistrationEvent(User user, String url) {
		super(user);
		
		this.user = user;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
