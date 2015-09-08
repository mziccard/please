package me.mziccard.please.entities;

import java.text.ParseException;

public interface CommandLineParser<T> {

  public T parse() throws ParseException;

};