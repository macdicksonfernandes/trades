package com.mac.trader.models;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/***
 * Trade Data Class
 * @author Mac
 *
 */
@Data
public class Trade {

	private String tradeId;
	private int version;
	private String counterPartyId;
	private String bookId;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@FutureOrPresent(message = "Maturity date should not be in past")
	private LocalDate maturityDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDate;
	private String expired;

}
