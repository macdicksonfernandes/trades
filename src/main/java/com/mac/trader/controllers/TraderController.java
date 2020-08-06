package com.mac.trader.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac.trader.entity.TradeEntity;
import com.mac.trader.exception.TradeException;
import com.mac.trader.models.Trade;
import com.mac.trader.repository.TradeRepository;

import lombok.extern.slf4j.Slf4j;

/***
 * 
 * @author Mac Controller for Trader Service
 */

@RestController
@Slf4j
@RequestMapping("/trader")
public class TraderController {

	@Autowired
	private TradeRepository tradeRepo;

	/***
	 * Method to create trade
	 * @param trade
	 * @throws Exception
	 */
	@PostMapping
	public void createTrades(@Valid @RequestBody Trade trade) throws Exception {
		log.info("Create Trade: " + trade);

			Optional<TradeEntity> tradeEntity = tradeRepo.findFirstByTradeIdOrderByVersionDesc(trade.getTradeId());

			TradeEntity objTradeEntity = null;
			if (!tradeEntity.isPresent()
					|| (tradeEntity.isPresent() && tradeEntity.get().getVersion() < trade.getVersion())) {
				//New upper version of trade or new Trade
				objTradeEntity = new TradeEntity();
				BeanUtils.copyProperties(trade, objTradeEntity);
			} else if (tradeEntity.isPresent() && tradeEntity.get().getVersion() == trade.getVersion()) {
				//Same version overwrite the record
				objTradeEntity = tradeEntity.get();
				BeanUtils.copyProperties(trade, objTradeEntity);
			} else {
				//version less than existing
				throw new TradeException("INVALID", "Record Rejected");
			}

		// For simplicity directly calling Repository
		if(objTradeEntity!=null)
			tradeRepo.save(objTradeEntity);
	}

	/***
	 * Method to get trades
	 * @return
	 */
	@GetMapping
	public List<Trade> getTrades() {
		List<TradeEntity> tradeEntities = tradeRepo.findAll();

		List<Trade> lstTrades = tradeEntities.stream().map((tradeEntity) -> {
			Trade trade = new Trade();
			BeanUtils.copyProperties(tradeEntity, trade);
			return trade;
		}).collect(Collectors.toList());

		return lstTrades;
	}
}
