package com.mac.trader.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/***
 * Trade Entity Class
 * @author Mac
 *
 */
@Data
@Entity
public class TradeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String tradeId;
	private int version;
	private String counterPartyId;
	private String bookId;
	
	private LocalDate maturityDate;	
	private LocalDate createdDate;
	private String expired;
}
