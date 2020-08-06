package com.mac.trader.exception;

import lombok.Data;

/***
 * Exception class for Trades
 * @author Mac
 *
 */
@Data
public class TradeException extends Exception {

	private String code;
	private String message;

	public TradeException(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
