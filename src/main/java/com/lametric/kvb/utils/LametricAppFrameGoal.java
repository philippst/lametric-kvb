package com.lametric.kvb.utils;

public class LametricAppFrameGoal extends LametricAppFrame{

    public LametricAppFrameGoal(){
        goalData = new GoalData();
    }

    public GoalData goalData;

    public class GoalData {

        public int start;
        public int current;
        public int end;
        public String unit;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
