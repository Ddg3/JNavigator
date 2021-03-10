package com.zachl.jnavigator.objects;

import java.util.ArrayList;
import java.util.List;

public class JournalManager {
    public static ArrayList<Journal> getResultJournals() {
        return resultJournals;
    }

    public static void setResultJournals(List<Object> resultJournals) {
        JournalManager.resultJournals.clear();
        for(Object o : resultJournals){
            JournalManager.resultJournals.add((Journal)o);
        }
    }

    private static ArrayList<Journal> resultJournals = new ArrayList<>();

    public static Journal getByUrl(String url){
        for(Journal j : resultJournals){
            if(j.url.equalsIgnoreCase(url)){
                return j;
            }
        }
        return null;
    }
}
