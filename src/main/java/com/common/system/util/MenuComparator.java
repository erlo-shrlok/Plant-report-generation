package com.common.system.util;

import com.common.system.entity.RcMenu;

import java.util.Comparator;

/**
 *
 */
public class MenuComparator implements Comparator<RcMenu> {
    @Override
    public int compare(RcMenu o1, RcMenu o2) {
        // Check if both objects are null
        if (o1 == null && o2 == null){
            return 0;// Return 0 to indicate(表示) that they are equal
        }
        // Check if o2 is null
        if (o1 != null && o2 == null){
            return -1;// Return -1 to indicate that o1 is greater than o2
        }
        // Check if o1 is null
        if (o1 == null && o2 != null){
            return 1;// Return 1 to indicate that o2 is greater than o1
        }
        // Compare the sort values of the two RcMenu object using compareTo method
        if (o1.getSort().compareTo(o2.getSort())>0){
            return 1;// Return 1 to indicate that o1 is greater than o2
        }
        // If the sort values are equal or o2`s sort value is greater, return -1 to indicate that o2 is greater than o1
        return -1;
    }
}
