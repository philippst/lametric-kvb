package com.lametric.kvb;

import com.lametric.kvb.exception.KvbAppException;
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
        Element kvbTitel = dom.select("div.qr_top_head_rot_small").last();
        if(kvbTitel.textNodes().size()==0) throw new KvbAppException("KVB station id invalid");
        String stationTitle = kvbTitel.textNodes().get(0).text().trim();
        return stationTitle;
    }

    /**
     * Suche Text zu Betriebsstörungen in DOM
     */
    public static List<String> getDisruptionMessage(Document dom){
        Elements disruptionRows = dom.select("table.qr_table").first().select("tbody tr");

        List<String> disruptionStrings = new ArrayList<>();
        for(Element disurptionRow : disruptionRows){
            String disruptionString = disurptionRow.select("td").first().text().replace("\u00a0", "");
            if(disruptionString.equals("Derzeit liegen an dieser Haltestelle keine Störungen vor.")) continue;
            disruptionStrings.add(disruptionString);
        }
        return disruptionStrings;
    }

    /**
     * Suche Abfahrtsdaten zu Haltestelle in DOM
     */
    public static List<KvbStationDeparture> getDepartures(Document dom){
        Elements elements = dom.select("table.qr_table").last().select("tbody tr");
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
