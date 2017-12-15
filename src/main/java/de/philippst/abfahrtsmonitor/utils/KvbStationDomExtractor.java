package de.philippst.abfahrtsmonitor.utils;

import de.philippst.abfahrtsmonitor.exception.KvbAppException;
import de.philippst.abfahrtsmonitor.model.KvbStationDeparture;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class KvbStationDomExtractor {

    /**
     * Suche Namen der Haltestelle in DOM
     */
    public static String getStationTitle(Document dom) throws KvbAppException {
        String text = dom.select("body > div:nth-child(6) > div > h1 > span.red-text").text();
        if(text.trim().isEmpty()) throw new KvbAppException("KVB station id invalid");
        return text;
    }

    /**
     * Suche Text zu Betriebsstörungen in DOM
     */
    public static List<String> getDisruptionMessage(Document dom){
        Elements disruptionRows = dom.select("body > div:nth-child(6) > div > table").first().select("table > tbody" +
                " > tr");

        List<String> disruptionStrings = new ArrayList<>();
        for(Element disurptionRow : disruptionRows){
            String disruptionString = disurptionRow.text().replace("\u00a0", "").trim();
            if(disruptionString.equals("Derzeit liegen an dieser Haltestelle keine Störungen vor.")) continue;
            disruptionStrings.add(disruptionString);
        }
        return disruptionStrings;
    }

    /**
     * Suche Abfahrtsdaten zu Haltestelle in DOM
     */
    public static List<KvbStationDeparture> getDepartures(Document dom){
        Elements elements = dom.select("#qr_ergebnis > tbody > tr");
        List<KvbStationDeparture> departures = new ArrayList<>();
        for(Element row : elements){
            String lineNumber = row.select("td").get(0).text().trim();
            String lineDestination = row.select("td").get(1).text();
            String lineDeparture = row.select("td").get(2).text().trim();
            departures.add(new KvbStationDeparture(lineNumber,lineDestination,lineDeparture));
        }
        return departures;
    }

}
