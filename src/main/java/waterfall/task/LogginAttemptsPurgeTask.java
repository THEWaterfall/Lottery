package waterfall.task;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import waterfall.service.LoginAttemptsService;

@Component
public class LogginAttemptsPurgeTask {

	private static final Logger logger = LoggerFactory.getLogger(LogginAttemptsPurgeTask.class);
	
	@Autowired
	private LoginAttemptsService loginAttemptsService;
	
	@Scheduled(cron="${purge.loginAttempts.cron}")
	public void purgeExpiredLoginAttempts() {
		logger.debug("Processing login attempts purging");
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		
		loginAttemptsService.removeExpiredSince(now);
	}
}
