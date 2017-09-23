package de.philippst.abfahrtsmonitor.utils;

import com.google.common.base.Stopwatch;
import de.philippst.abfahrtsmonitor.exception.KvbAppException;
import de.philippst.abfahrtsmonitor.model.KvbStation;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StationDataLoader {

    private static Logger logger = LoggerFactory.getLogger(StationDataLoader.class);

    public static KvbStation loadData(int stationId) throws KvbAppException, IOException {
        if(stationId == 0) throw new KvbAppException("Invalid kvb station id");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.kvb-koeln.de/qr/"+stationId+"/");
        Stopwatch stopwatch = Stopwatch.createStarted();
        CloseableHttpResponse httpClientResponse = httpclient.execute(httpGet);
        String responseString;
        try {
            HttpEntity httpClientEntity = httpClientResponse.getEntity();
            responseString = EntityUtils.toString(httpClientEntity);
        } finally {
            httpClientResponse.close();
        }
        stopwatch.stop();
        long milis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        logger.info("KVB website request time: "+milis);

        KvbStation kvbStation = new KvbStation();

        Document dom = null;

   //     try {
            dom = Jsoup.parse(responseString);
       //     dom = Jsoup.connect("http://www.kvb-koeln.de/qr/"+stationId+"/").get();
   //     } catch (IOException e) {
   //         throw new KvbAppException("Störung der Fahrgastinformation");
   //     }

        kvbStation.setTitle(StationDomExtractor.getStationTitle(dom));
        kvbStation.setDisruptionMessage(StationDomExtractor.getDisruptionMessage(dom));
        kvbStation.setDepartures(StationDomExtractor.getDepartures(dom));

        return kvbStation;
    }
}