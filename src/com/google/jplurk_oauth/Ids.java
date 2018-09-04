package com.google.jplurk_oauth;

import java.util.ArrayList;
import java.util.List;

public class Ids {

    private List<Integer> idList = new ArrayList<Integer>();

    public Ids() {
    }
    
    public Ids(List<Integer> idList) {
        if(idList != null && !idList.isEmpty())
        {
            this.idList.addAll(idList);
        }
    }

    public String formatted() {
        if (!idList.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            buffer.append('[');
            for (Integer id : idList) {
                buffer.append(id);
                buffer.append(",");
            }
            buffer.deleteCharAt(buffer.length()-1);
            buffer.append(']');
            if (buffer.length() > 0) {
                buffer.setLength(buffer.length());
                return buffer.toString();
            }
        }
        return "[]";
    }

}
