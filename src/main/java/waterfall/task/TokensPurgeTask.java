package waterfall.task;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import waterfall.service.VerificationTokenService;

@Component
public class TokensPurgeTask {
	
	private static final Logger logger = LoggerFactory.getLogger(TokensPurgeTask.class);
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Scheduled(cron = "${purge.token.cron}")
	public void purgeExpiredTokens() {
		logger.debug("Processing tokens purging");
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		
		tokenService.removeExpiredSince(now);
	}
}
