package com.lametric.kvb;

import de.philippst.abfahrtsmonitor.exception.KvbAppException;
import de.philippst.abfahrtsmonitor.model.KvbStationDeparture;
import de.philippst.abfahrtsmonitor.utils.StationDomExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StationDomExtractorTest {

    private File getResourceFile(String name) {
        String fileName = getClass().getResource("/kvb-dom/"+name).getFile().replace("%20"," ");
        return new File(fileName);
    }

    @Test
    public void getStationTitle() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures.html"),"utf-8");
        String stationTitel = StationDomExtractor.getStationTitle(dom);
        assertEquals("Leyendeckerstr.",stationTitel);
    }

    @Test(expected=KvbAppException.class)
    public void getStationTitleEmpty() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("invalid-station.html"),"utf-8");
        String stationTitel = StationDomExtractor.getStationTitle(dom);
    }

    @Test
    public void getDisruptionMessageEmpty() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures.html"),"utf-8");
        List<String> stationDisruptionMessage = StationDomExtractor.getDisruptionMessage(dom);
        assertEquals(0,stationDisruptionMessage.size());
    }

    @Test
    public void getDisruptionMessageSingle() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures-disruption.html"),"utf-8");
        List<String> stationDisruptionMessage = StationDomExtractor.getDisruptionMessage(dom);
        List<String> actual = Arrays.asList("Linie 18 * Folgende Fahrt entfällt * (H) Klettenbergpark 14:09h *");
        assertEquals(actual,stationDisruptionMessage);
    }

    @Test
    public void getDisruptionMessageSeveral() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures-disruptions.html"),"utf-8");
        List<String> stationDisruptionMessage = StationDomExtractor.getDisruptionMessage(dom);
        List<String> actual = Arrays.asList(
                "Linie 3 * Folgende Fahrt entfällt * (H) Thielenbruch 19:06h *",
                "Linie 18 * Folgende Fahrt entfällt * (H) Thielenbruch 15:08h *");
        assertEquals(actual,stationDisruptionMessage);
    }

    @Test
    public void getDepartures() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures.html"),"utf-8");
        List<KvbStationDeparture> departures = StationDomExtractor.getDepartures(dom);
        assertEquals(4,departures.size());
    }

    @Test
    public void getDeparturesNone() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("invalid-station.html"),"utf-8");
        List<KvbStationDeparture> departures = StationDomExtractor.getDepartures(dom);
        assertEquals(0,departures.size());
    }
}