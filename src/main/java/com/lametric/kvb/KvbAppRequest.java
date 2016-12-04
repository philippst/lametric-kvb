package com.lametric.kvb;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class KvbAppRequest {

    // QR Id laut KVB Webseite
    private Integer stationId;

    // Minimale Abfahrtszeit in Minuten
    private Integer minTime;

    // Maximale Abfahrtszeit in Minuten
    private Integer maxTime;

    // Anzahl maximale Abfahrszeiten
    private Integer limit;

    // Ignoriere Bahnen mit diesem Fahrtziel
    private String[] filteredDestinations;

    private String[] frames;

    public KvbAppRequest(){
        this.stationId = 255;
        this.minTime = 4;
        this.maxTime = 90;
        this.limit = 5;
        this.frames = new String[]{"disruption", "allDepartures", "singleDeparture"};
        this.filteredDestinations = new String[]{"Bocklemünd", "Mengenich", "Bickendorf"};
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getMinTime() {
        return minTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String[] getFilteredDestinations() {
        return filteredDestinations;
    }

    public void setFilteredDestinationsArray(String[] filteredDestinations) {
        this.filteredDestinations = filteredDestinations;
    }

    public void setFilteredDestinations(String filteredDestinations) {
        Iterable<String> destinations = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split(filteredDestinations);
        this.filteredDestinations = Iterables.toArray(destinations,String.class);
    }

    public String[] getFrames() {
        return frames;
    }

    public void setFrames(String frames) {
        Iterable<String> frame = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split(frames);
        this.frames = Iterables.toArray(frame,String.class);
    }
}
