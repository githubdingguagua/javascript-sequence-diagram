package org.binqua.testing.csd.multiplereportswebapp;


public interface SupportNotifier {

    void info(String message);

    void info(String message,Exception e);

}
