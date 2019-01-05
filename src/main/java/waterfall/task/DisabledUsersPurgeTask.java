package waterfall.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import waterfall.service.UserService;

@Component
public class DisabledUsersPurgeTask {
	
	public static final Logger logger = LoggerFactory.getLogger(DisabledUsersPurgeTask.class);
	
	@Autowired
	private UserService userService;
	
	@Scheduled(cron = "${purge.user.cron}")
	public void purgeDisabledUsers() {
		logger.debug("Processing users purging");
		
		userService.removeAllDisabled();
	}
}
