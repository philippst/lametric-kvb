package com.lametric.kvb;

import com.google.common.base.Stopwatch;
import com.lametric.kvb.exception.KvbAppException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class KvbLoadStationData {

    private static final Logger logger = Logger.getLogger(KvbLoadStationData.class);

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

        kvbStation.setTitle(KvbStationDomExtractor.getStationTitle(dom));
        kvbStation.setDisruptionMessage(KvbStationDomExtractor.getDisruptionMessage(dom));
        kvbStation.setDepartures(KvbStationDomExtractor.getDepartures(dom));

        return kvbStation;
    }
}