package de.philippst.abfahrtsmonitor;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import de.philippst.abfahrtsmonitor.exception.KvbAppConfigurationException;
import de.philippst.abfahrtsmonitor.exception.KvbAppException;
import com.lametric.IndicatorApp;
import com.lametric.frame.FrameAbstract;
import com.lametric.frame.FrameText;
import de.philippst.abfahrtsmonitor.model.KvbStation;
import de.philippst.abfahrtsmonitor.model.KvbStationDeparture;
import de.philippst.abfahrtsmonitor.utils.StationDataLoader;
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

    public IndicatorApp getResponse(KvbAppRequest kvbAppRequest) throws IOException, KvbAppConfigurationException {
        validateRequest(kvbAppRequest);

        IndicatorApp lametricApp = new IndicatorApp();

        try {
            kvbStation = StationDataLoader.loadData(kvbAppRequest.getStationId());
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

    public FrameAbstract getKvbDisruptionFrame(String disruptionMessage){
        return new FrameText(disruptionMessage,"a4369");
    }

    public FrameAbstract getKvbNextDepartureFrame(KvbAppRequest kvbAppRequest){
        KvbStation filteredStationData = departureFilter(kvbStation,kvbAppRequest.getMinTime(),kvbAppRequest.getMaxTime(),
                kvbAppRequest.getLimit(),kvbAppRequest.getFilteredDestinations());

        if(filteredStationData.getDepartures().size() > 0){
            KvbStationDeparture nextDeparture = filteredStationData.getDepartures().get(0);

            FrameText lametricAppFrame = new FrameText();
            lametricAppFrame.setIcon("i2451");
            lametricAppFrame.setText(Strings.padStart(nextDeparture.getMinutes().toString(), 2, ' ')+" min");
            return lametricAppFrame;
        } else {
            FrameText lametricAppFrame = new FrameText();
            lametricAppFrame.setIcon("i2451");
            lametricAppFrame.setText("");
            return lametricAppFrame;
        }
    }

    public FrameAbstract getKvbDepartureFrame(KvbAppRequest kvbAppRequest){
        KvbStation filteredStationData = departureFilter(kvbStation,kvbAppRequest.getMinTime(),kvbAppRequest.getMaxTime(),
                kvbAppRequest.getLimit(),kvbAppRequest.getFilteredDestinations());

        FrameText kvbDepartureFrame = new FrameText();
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

    private FrameAbstract errorFrame(String errorText){
        FrameText frame = new FrameText();
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