package com.mac.trader.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mac.trader.repository.TradeRepository;

import lombok.extern.slf4j.Slf4j;

/***
 * Scheduler for updating expiry
 * @author Mac
 *
 */
@Component
@Slf4j
public class TradeExpiryScheduler {

	@Autowired
	private TradeRepository tradeRepo;

	//Testing set it to fixed time of 5s. We can use cron expression to set it to run everyday
	@Scheduled(fixedRate = 50000)
	public void cronJobTradeExpiry() {
		int updatedRecords = tradeRepo.expireTrades();
		log.info("Updated Records to expired: " + updatedRecords);
	}

}
