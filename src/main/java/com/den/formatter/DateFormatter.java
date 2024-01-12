package com.den.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date> {
  private static final String DATE_FORMAT = "dd-MM-yyyy";

  @Override
  public String print(Date object, Locale locale) {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, locale);
    return sdf.format(object);
  }

  @Override
  public Date parse(String text, Locale locale) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, locale);
    return sdf.parse(text);
  }

}
