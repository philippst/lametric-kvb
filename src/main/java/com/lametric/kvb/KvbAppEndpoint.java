package com.lametric.kvb;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.lametric.kvb.exception.KvbAppConfigurationException;
import com.lametric.kvb.exception.KvbAppException;
import com.lametric.kvb.utils.LametricApp;
import com.lametric.kvb.utils.LametricAppFrame;
import com.lametric.kvb.utils.LametricAppFrameName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KvbAppEndpoint {
    private static Logger logger = LoggerFactory.getLogger(KvbAppEndpoint.class);

    private KvbStation kvbStation;

    public KvbAppEndpoint(){}

    public LametricApp getResponse(KvbAppRequest kvbAppRequest) throws IOException, KvbAppConfigurationException {
        validateRequest(kvbAppRequest);

        LametricApp lametricApp = new LametricApp();

        try {
            kvbStation = KvbLoadStationData.loadData(kvbAppRequest.getStationId());
        } catch (KvbAppException e) {
            lametricApp.addFrame(errorFrame(e.getMessage()));
            return lametricApp;
        }

        List<String> frames = Arrays.asList(kvbAppRequest.getFrames());

        if(frames.contains("disruption")) {
            for (String disruptionMessage : kvbStation.getDisruptionMessage()) {
                lametricApp.addFrame(getKvbDisruptionFrame(disruptionMessage));
            }
        }

        if(frames.contains("allDepartures")){
            lametricApp.addFrame(getKvbDepartureFrame(kvbAppRequest));
        }

        if(frames.contains("singleDeparture")){
            lametricApp.addFrame(getKvbNextDepartureFrame(kvbAppRequest));
        }
        return lametricApp;
    }

    public LametricAppFrame getKvbDisruptionFrame(String disruptionMessage){
        return new LametricAppFrameName(disruptionMessage,"a4369");
    }

    public LametricAppFrame getKvbNextDepartureFrame(KvbAppRequest kvbAppRequest){
        KvbStation filteredStationData = departureFilter(kvbStation,kvbAppRequest.getMinTime(),kvbAppRequest.getMaxTime(),
                kvbAppRequest.getLimit(),kvbAppRequest.getFilteredDestinations());

        if(filteredStationData.getDepartures().size() > 0){
            KvbStationDeparture nextDeparture = filteredStationData.getDepartures().get(0);

            LametricAppFrameName lametricAppFrame = new LametricAppFrameName();
            lametricAppFrame.setIcon("i2451");
            lametricAppFrame.setText(Strings.padStart(nextDeparture.getMinutes().toString(), 2, ' ')+" min");
            return lametricAppFrame;
        } else {
            LametricAppFrameName lametricAppFrame = new LametricAppFrameName();
            lametricAppFrame.setIcon("i2451");
            lametricAppFrame.setText("");
            return lametricAppFrame;
        }
    }

    public LametricAppFrame getKvbDepartureFrame(KvbAppRequest kvbAppRequest){
        KvbStation filteredStationData = departureFilter(kvbStation,kvbAppRequest.getMinTime(),kvbAppRequest.getMaxTime(),
                kvbAppRequest.getLimit(),kvbAppRequest.getFilteredDestinations());

        LametricAppFrameName kvbDepartureFrame = new LametricAppFrameName();
        kvbDepartureFrame.setIcon("a1395");

        String responseText = "";

        if(filteredStationData.getDepartures().size() > 0) {
            responseText = responseText.concat(Joiner.on(", ").join(filteredStationData.getDepartures()));
        } else {
            responseText = "Kein Betrieb";
        }

        kvbDepartureFrame.setText(responseText);

        return kvbDepartureFrame;
    }

    /*
     * Entferne unerwuenschte Abfahrtsdaten
     */
    private KvbStation departureFilter(KvbStation kvbStation, Integer minTime, Integer maxTime, Integer limit, String[] filterStation){

        KvbStation filteredStation = kvbStation;

        if(filteredStation.getDepartures() == null || filteredStation.getDepartures().size() == 0) return kvbStation;

        List<KvbStationDeparture> filteredDepeartures = new ArrayList<>();
        for(KvbStationDeparture departure : filteredStation.getDepartures()){
            if(filterStation != null && Arrays.asList(filterStation).contains(departure.getDestination())) continue;
            if(minTime != null && departure.getMinutes() < minTime) continue;
            if(maxTime != null && departure.getMinutes() > maxTime) continue;
            filteredDepeartures.add(departure);
        }

        if(filteredDepeartures.size() == 0) filteredDepeartures = filteredStation.getDepartures();

        if(limit != null && filteredDepeartures.size() > limit)
            filteredDepeartures = filteredDepeartures.subList(0,limit);

        filteredStation.setDepartures(filteredDepeartures);
        return filteredStation;
    }

    private LametricAppFrame errorFrame(String errorText){
        LametricAppFrameName frame = new LametricAppFrameName();
        frame.setText(errorText);
        frame.setIcon("i93");
        return frame;
    }

    private void validateRequest(KvbAppRequest kvbAppRequest) throws KvbAppConfigurationException {
        if(kvbAppRequest.getStationId() == null) throw new KvbAppConfigurationException("stationId parameter is missing");
        if(kvbAppRequest.getFrames() == null || kvbAppRequest.getFrames().length == 0)
            throw new KvbAppConfigurationException("frames parameter is missing");
    }

}