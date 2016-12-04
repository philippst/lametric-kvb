package com.lametric.kvb;

public class KvbStationDeparture {
    public String line;
    public String destination;
    public Integer minutes;

    public KvbStationDeparture(String line, String destination, Integer minutes){
        this.line = line;
        this.destination = destination;
        this.minutes = minutes;
    }

    public KvbStationDeparture(String line, String destination, String minutes){

        this.line = line.replace("\u00a0", "");
        this.destination = destination.replace("\u00a0", "");
        String myMinutes = minutes.replace("\u00a0", "").replace("Min","");
        if(myMinutes.equals("Sofort")){
            this.minutes = 0;
        } else {
            this.minutes = Integer.valueOf(myMinutes);
        }
    }

    public String longString(){
        return "Linie "+this.line+" nach "+this.destination+" in "+this.minutes+" min";
    }


    public String toString(){
        return this.minutes+"min";
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
