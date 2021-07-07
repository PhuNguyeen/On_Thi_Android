package com.example.nguyenducphu_181201867;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class Phu_Adapter extends BaseAdapter implements Filterable {
    private Context context; //context
    private ArrayList<Contact_181201867> arrayListObject; //data source of the list adapter

    private CustomFilter filter;
    private ArrayList<Contact_181201867> filterList;


    public Phu_Adapter(Context context, ArrayList<Contact_181201867> arrayListObject) {
        this.context = context;
        this.arrayListObject = arrayListObject;
        this.filterList = arrayListObject;
    }

    @Override
    public int getCount() {
        return arrayListObject.size();
    }

    @Override
    public Contact_181201867 getItem(int position) {
        return arrayListObject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(ArrayList<Contact_181201867> lUser) {
        this.arrayListObject = lUser;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_list_view, null);
        }
        Contact_181201867 object = (Contact_181201867) getItem(position);
        if (object != null) {
            // Ánh xạ
            TextView textView1 = (TextView) view.findViewById(R.id.textView1);
            TextView textView2 = (TextView) view.findViewById(R.id.textView2);
            TextView textView3 = (TextView) view.findViewById(R.id.textView3);

            textView1.setText(String.valueOf(object.getId()));
            textView2.setText(object.getHoTen());
            textView3.setText(object.getSDT());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Contact_181201867> arrayList = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    // search theo filterList.get(i).getTenSP() -------------------------------
                    if (filterList.get(i).getSDT().toUpperCase().contains(constraint) || filterList.get(i).getHoTen().toUpperCase().contains(constraint)) {
                        Contact_181201867 object = new Contact_181201867(filterList.get(i).getId(), filterList.get(i).getSDT(), filterList.get(i).getHoTen());
                        arrayList.add(object);
                    }
                }
                results.count = arrayList.size();
                results.values = arrayList;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayListObject = (ArrayList<Contact_181201867>) results.values;
            notifyDataSetChanged();
        }
    }

    public void updateResults(ArrayList<Contact_181201867> results) {
        arrayListObject = results;
        notifyDataSetChanged();
    }

}
