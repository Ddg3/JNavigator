package com.zachl.jnavigator.objects.managers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SqlManager {
    public static final String PAIRER = "::", SEPARATOR = "==";
    public static String getFields(Context context, View[] fieldViews){
        String fields = "";
        for(View view : fieldViews){
            if(view instanceof TextView){
                String name = context.getResources().getResourceEntryName(view.getId()).replace("Field", "");
                String content = ((TextView) view).getText().toString();
                if(!content.equalsIgnoreCase(""))
                    fields += name.toUpperCase() + PAIRER + content + SEPARATOR;
            }
            else if(view instanceof RecyclerView){
                int count = ((RecyclerView) view).getChildCount();
                for(int i = 0; i < count; i++){
                    View child = ((ConstraintLayout)((RecyclerView) view).getChildAt(i)).getChildAt(0);
                    String content = ((TextView)child).getText().toString();
                    fields += "KEYWORDS" + PAIRER + content + SEPARATOR;
                }
            }
        }
        return fields;
    }

    public static boolean fieldsEmpty(View[] fieldViews){
        for(View field : fieldViews){
            if(field instanceof TextView){
                if (((TextView) field).getText().toString().equalsIgnoreCase("")){
                    return true;
                }
            }
            else{
                if(((RecyclerView)field).getChildCount() == 0){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean allFieldsEmpty(View[] fieldViews){
        int count = 0;
        for(View field : fieldViews){
            if(field instanceof TextView){
                if (((TextView) field).getText().toString().equalsIgnoreCase("")){
                    count++;
                }
            }
            else{
                if(((RecyclerView)field).getChildCount() == 0){
                    count++;
                }
            }
        }
        if(count == fieldViews.length){
            return true;
        }
        return false;
    }

    public static String getKeywords(RecyclerView recycler){
        String result = "";
        int childCount = recycler.getChildCount();
        for(int i = 0; i < childCount; i++){
            View child = ((ConstraintLayout)recycler.getChildAt(i)).getChildAt(0);
            String content = ((TextView)child).getText().toString();
            result += content + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }
}
