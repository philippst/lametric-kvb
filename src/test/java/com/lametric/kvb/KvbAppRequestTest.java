package com.lametric.kvb;

import de.philippst.abfahrtsmonitor.KvbAppRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class KvbAppRequestTest {

    @Test
    public void setFilteredDestinationsArray() throws Exception {
    }

    @Test
    public void setFilteredDestinations() throws Exception {
        KvbAppRequest kvbAppRequest = new KvbAppRequest();
        kvbAppRequest.setFilteredDestinations("Hauptbahnhof, ,Deutz,");
        String[] destinationsArray = {"Hauptbahnhof","Deutz"};
        assertArrayEquals(destinationsArray, kvbAppRequest.getFilteredDestinations());
    }

    @Test
    public void setFilteredDestinationsEmpty() throws Exception {
        KvbAppRequest kvbAppRequest = new KvbAppRequest();
        kvbAppRequest.setFilteredDestinations("");
        assertArrayEquals(new String[]{}, kvbAppRequest.getFilteredDestinations());
    }

    @Test
    public void setFrames() throws Exception {
    }

}