package abl.libreria;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class SampleDates {

	public static void main(String[] args) {
		
		LocalDate fecha = LocalDate.of(2017, Month.DECEMBER, 15);
		System.out.println(fecha.toString());
		
		
		LocalDate fecha1 = LocalDate.now();
		System.out.println(fecha1.toString());
		
		LocalDate fecha2 = fecha1.plus(5, ChronoUnit.DAYS);
		          fecha2 = fecha1.plusDays(5);
		
		ChronoUnit crono = ChronoUnit.valueOf("DAYS");
		           crono = ChronoUnit.DAYS;
		           
		           
		LocalDate fecha3 = fecha1.plus(-5, crono);
		System.out.println(fecha3.toString());
		
		LocalDate fecha4 = fecha1.plusDays(6);
		System.out.println(fecha4.toString());
		
		Period p = Period.ofDays(10);
		
		LocalDate fecha5 = fecha1.plus(p);
		System.out.println(fecha5.toString());
		
		Duration d = Duration.ofDays(10);
		LocalDate fecha6 = fecha1.plus(p);
		System.out.println(fecha6.toString());
		
		
		
		Period periodo1 = Period.between(fecha1, fecha6);
		Period periodo2 = Period.of(1, 2, 3);
		Period periodo3 = Period.ofDays(3);
		Period periodo4 = Period.ofMonths(4);
		
		Duration duracion1 = Duration.of(3, ChronoUnit.DAYS);
		
		LocalDateTime fecha7 = LocalDateTime.now();
		
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy MM dd, d H:m:s");
		String text = fecha7.format(formatter1);
		System.out.println(text);
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ISO_DATE_TIME;
		text = fecha7.format(DateTimeFormatter.ISO_DATE_TIME);
		System.out.println(text);
		
		ZonedDateTime fecha8 = ZonedDateTime.now();
		
		DateTimeFormatter formatter3 = DateTimeFormatter.ISO_ZONED_DATE_TIME;
		text = fecha8.format(formatter3);
		System.out.println(text);
		
		LocalDateTime fecha9 = fecha7.plusDays(7);
		text = fecha9.format(formatter2);
		System.out.println(text);
		
		LocalDate ld = LocalDate.of(2014, Month.JUNE, 21);
	    LocalTime lt = LocalTime.of(17, 30, 20);
	    LocalDateTime fecha10 = LocalDateTime.of(ld, lt);
	    
		DateTimeFormatter formatter4 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		text = formatter4.format(fecha10);
		System.out.println(text);
		
		LocalDateTime fecha11 = fecha9.minus(duracion1);
		text = fecha11.format(formatter2);
		System.out.println(text);
		
		if (fecha9.isBefore(fecha7)) {
			System.out.println("Fecha anterior");
		}
		if (fecha9.isAfter(fecha7)) {
			System.out.println("Fecha posterior");
		}	
	}

}
