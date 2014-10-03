package net.kenyang.libcalendarview;


public class Marker {
    public COLOR_CODE color;
    
    public boolean bIsShow=false;
    
    public enum COLOR_CODE {
        RED ("#D12946"),
        GREEN ("#00fff6"),
        YELLOW ("#D12946");

        
        private final String name; 
        private COLOR_CODE(String s) {
            name = s;
        }
        public boolean equalsName(String otherName){
            return (otherName == null)? false:name.equals(otherName);
        }
        public String toString(){
           return name;
        }
    }
    
    public Marker() {
    }
    
    /**
     * 
     * @param c
     */
    public Marker(COLOR_CODE c) {
        color = c;
    }
    
    /**
     * 
     * @param c
     * @param bShow
     */
    public Marker(COLOR_CODE c,boolean bShow) {
        color = c;
        bIsShow = bShow;
    }
}
