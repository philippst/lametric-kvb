package com.lametric.kvb;

import com.lametric.kvb.exception.KvbAppException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class KvbLoadStationData {

    private static final Logger logger = Logger.getLogger(KvbLoadStationData.class);

    public static KvbStation loadData(int stationId) throws KvbAppException {
        if(stationId == 0) throw new KvbAppException("Invalid kvb station id");

        KvbStation kvbStation = new KvbStation();

        Document dom = null;

        try {
            dom = Jsoup.connect("http://www.kvb-koeln.de/qr/"+stationId+"/").get();
        } catch (IOException e) {
            throw new KvbAppException("Störung der Fahrgastinformation");
        }

        kvbStation.setTitle(KvbStationDomExtractor.getStationTitle(dom));
        kvbStation.setDisruptionMessage(KvbStationDomExtractor.getDisruptionMessage(dom));
        kvbStation.setDepartures(KvbStationDomExtractor.getDepartures(dom));

        return kvbStation;
    }
}