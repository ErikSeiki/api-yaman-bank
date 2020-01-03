package br.com.yaman.bank.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateParser {
	/**
	     * @see classe DateParser e método tryParseToDate
	     * @param String
	     *            date
	     * @return Date
	     * @throws Exception
	     */
	    @Deprecated
	    public static Date tryParseToDate(String date) throws Exception {
	        try {
	            return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
	                    .atStartOfDay(ZoneId.systemDefault()).toInstant());


	        } catch (Exception e) {
	            try {
	                return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
	                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
	            } catch (Exception e1) {
	                try {
	                    return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
	                            .atStartOfDay(ZoneId.systemDefault()).toInstant());
	                } catch (Exception e2) {
	                    try {
	                        return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
	                                .atStartOfDay(ZoneId.systemDefault()).toInstant());
	                    } catch (Exception e3) {
	                        try {
	                            return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
	                                    .atStartOfDay(ZoneId.systemDefault()).toInstant());
	                        } catch (Exception e4) {
	                            try {
	                                return Date.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
	                                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
	                            } catch (Exception e5) {
	                                throw new Exception("Não foi possivel formatar a data", e5);
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	 



}
