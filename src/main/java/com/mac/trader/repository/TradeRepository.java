package com.mac.trader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import com.mac.trader.entity.TradeEntity;

/***
 * Repository for Trade
 * @author Mac
 *
 */
@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
	
	/***
	 * Update expiry date when maturity date is less than curent date
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE TradeEntity t SET t.expired='Y' where t.expired='N' and t.maturityDate < CURRENT_DATE()")
	int expireTrades();
	
	/***
	 * find method to get Max of version for given trade Id
	 * @param tradeId
	 * @return
	 */
	Optional<TradeEntity> findFirstByTradeIdOrderByVersionDesc(String tradeId);

}
