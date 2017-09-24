package de.philippst.abfahrtsmonitor.utils;

import com.google.common.base.Stopwatch;
import de.philippst.abfahrtsmonitor.exception.KvbAppException;
import de.philippst.abfahrtsmonitor.model.KvbStation;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class StationDataLoader {

    private static Logger logger = LoggerFactory.getLogger(StationDataLoader.class);

    public static KvbStation loadData(int stationId) throws KvbAppException {
        if(stationId == 0) throw new KvbAppException("Invalid kvb station id");

        try{
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("www.kvb-koeln.de")
                    .setPath("qr/"+ stationId)
                    .build();

            Stopwatch stopwatchRequest = Stopwatch.createStarted();
            String responseString = Request.Get(uri).execute().returnContent().asString();
            stopwatchRequest.stop();

            logger.info("KVB website request time: {}",stopwatchRequest.elapsed(TimeUnit.MILLISECONDS));

            Document dom = Jsoup.parse(responseString);

            return new KvbStation(
                    StationDomExtractor.getStationTitle(dom),
                    StationDomExtractor.getDisruptionMessage(dom),
                    StationDomExtractor.getDepartures(dom)
            );

        } catch (IOException e) {
            throw new KvbAppException("loading station data from KVB failed",e);
        } catch (URISyntaxException e) {
            throw new KvbAppException("building kvb request uri failed.",e);
        }
    }
}