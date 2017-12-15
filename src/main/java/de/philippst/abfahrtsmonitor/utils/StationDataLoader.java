package de.philippst.abfahrtsmonitor.utils;

import de.philippst.abfahrtsmonitor.exception.KvbAppException;
import de.philippst.abfahrtsmonitor.model.KvbStation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class StationDataLoader {

    private static Logger logger = LoggerFactory.getLogger(StationDataLoader.class);

    public static KvbStation loadData(int stationId) throws KvbAppException {
        if(stationId == 0) throw new KvbAppException("Invalid kvb station id");

        try{

            URL requestUrl = new URL("https://kvb.koeln/qr/"+stationId);
            logger.info("Request KVB Station: {}",requestUrl);

            Document dom = Jsoup.parse(requestUrl,1000*5);

            return new KvbStation(
                    KvbStationDomExtractor.getStationTitle(dom),
                    KvbStationDomExtractor.getDisruptionMessage(dom),
                    KvbStationDomExtractor.getDepartures(dom)
            );
        } catch (IOException e) {
            throw new KvbAppException("loading station data from KVB failed",e);
        }
    }
}