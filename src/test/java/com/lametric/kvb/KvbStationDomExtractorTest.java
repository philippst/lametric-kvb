package com.lametric.kvb;

import de.philippst.abfahrtsmonitor.exception.KvbAppException;
import de.philippst.abfahrtsmonitor.model.KvbStationDeparture;
import de.philippst.abfahrtsmonitor.utils.KvbStationDomExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KvbStationDomExtractorTest {
    private File getResourceFile(String name) {
        String fileName = getClass().getResource("/kvb-dom/"+name).getFile().replace("%20"," ");
        return new File(fileName);
    }

    @Test
    public void getStationTitle() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures-disruption.html"),"utf-8");
        String stationTitel = KvbStationDomExtractor.getStationTitle(dom);
        assertEquals("Leyendeckerstr.",stationTitel);
    }

    @Test
    public void getStationTitleEmpty() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("invalid-station.html"),"utf-8");
        assertThrows(KvbAppException.class, () -> KvbStationDomExtractor.getStationTitle(dom));
    }

    @Test
    public void getDisruptionMessageEmpty() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures.html"),"utf-8");
        List<String> stationDisruptionMessage = KvbStationDomExtractor.getDisruptionMessage(dom);
        assertEquals(0,stationDisruptionMessage.size());
    }

    @Test
    public void getDisruptionMessageSingle() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures-disruption.html"),"utf-8");
        List<String> stationDisruptionMessage = KvbStationDomExtractor.getDisruptionMessage(dom);
        List<String> actual = Arrays.asList("Linie 18 * Folgende Fahrt entfällt * (H) Klettenbergpark 14:09h *");
        assertEquals(actual,stationDisruptionMessage);
    }

    @Test
    public void getDisruptionMessageSeveral() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures-disruptions.html"),"utf-8");
        List<String> stationDisruptionMessage = KvbStationDomExtractor.getDisruptionMessage(dom);
        List<String> actual = Arrays.asList(
                "Linie 18 * Folgende Fahrt entfällt * (H) Klettenbergpark 14:09h *",
                "Linie 18 * Folgende Fahrt entfällt * (H) Klettenbergpark 14:09h *");
        assertEquals(actual,stationDisruptionMessage);
    }

    @Test
    public void getDepartures() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("station-departures.html"),"utf-8");
        List<KvbStationDeparture> departures = KvbStationDomExtractor.getDepartures(dom);
        assertEquals(50,departures.size());
    }

    @Test
    public void getDeparturesNone() throws Exception {
        Document dom = Jsoup.parse(getResourceFile("invalid-station.html"),"utf-8");
        List<KvbStationDeparture> departures = KvbStationDomExtractor.getDepartures(dom);
        assertEquals(0,departures.size());
    }

}