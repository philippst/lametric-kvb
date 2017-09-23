package com.lametric.kvb;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;

import java.util.Map;

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

    // Darzustellende Ansichten
    private String[] frames;

    public KvbAppRequest(){}

    public KvbAppRequest(Map<String, String> queryStringParams){
        if(queryStringParams.get("stationId") != null){
            setStationId(Ints.tryParse(queryStringParams.get("stationId")));
        }

        if(queryStringParams.get("minTime") != null){
            setMinTime(Ints.tryParse(queryStringParams.get("minTime")));
        }

        if(queryStringParams.get("maxTime") != null){
            setMaxTime(Ints.tryParse(queryStringParams.get("maxTime")));
        }

        if(queryStringParams.get("limit") != null){
            setLimit(Ints.tryParse(queryStringParams.get("limit")));
        }

        if(queryStringParams.get("frames") != null){
            setFrames(queryStringParams.get("frames"));
        }
        if(queryStringParams.get("filteredDestinations") != null){
            setFilteredDestinations(queryStringParams.get("filteredDestinations"));
        }
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
        if(filteredDestinations == null) return;
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

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("stationId", stationId)
                .add("frames", frames)
                .add("minTime", minTime)
                .add("maxTime", maxTime)
                .add("filteredDestinations",filteredDestinations)
                .toString();
    }
}
