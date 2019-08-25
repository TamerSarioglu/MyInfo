package com.tamersarioglu.myinfo;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    MyAdapter adapter;
    ArrayList<Model> filterList;

    public CustomFilter(ArrayList<Model> filterList, MyAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    //filtering occurs
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();

        //check constraint validity
        if (constraint != null && constraint.length() > 0) {

            //change it to uppercase
            constraint = constraint.toString().toUpperCase();

            //store our filtered MODELS
            ArrayList<Model> filteredModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {

                //check
                if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
                    //add model to filtered models
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.models = (ArrayList<Model>)results.values;
        //refresh
        adapter.notifyDataSetChanged();
    }
}
