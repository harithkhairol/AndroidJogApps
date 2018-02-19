package net.harithproperties.jogapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pika on 4/2/2018.
 */

public class ListAdapterJogging extends ArrayAdapter<Jogging> {

    public ArrayList<Jogging> MainList;

    public ArrayList<Jogging> StudentListTemp;

    public ListAdapterJogging.SubjectDataFilter studentDataFilter;

    public ListAdapterJogging(Context context, int id, ArrayList<Jogging> studentArrayList) {

        super(context, id, studentArrayList);

        this.StudentListTemp = new ArrayList<Jogging>();

        this.StudentListTemp.addAll(studentArrayList);

        this.MainList = new ArrayList<Jogging>();

        this.MainList.addAll(studentArrayList);
    }

    @Override
    public Filter getFilter() {

        if (studentDataFilter == null) {

            studentDataFilter = new ListAdapterJogging.SubjectDataFilter();
        }
        return studentDataFilter;
    }


    public class ViewHolder {

        TextView txtdate;
        TextView txttime;
        TextView txtdistance;
        TextView txtspeed;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListAdapterJogging.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.searchlistcell, null);

            holder = new ListAdapterJogging.ViewHolder();

            holder.txtdate = (TextView) convertView.findViewById(R.id.txtdate);

            holder.txttime = (TextView) convertView.findViewById(R.id.txttime);

            holder.txtdistance = (TextView) convertView.findViewById(R.id.txtdistance);


            holder.txtspeed = (TextView) convertView.findViewById(R.id.txtspeed);


            convertView.setTag(holder);

        } else {

            holder = (ListAdapterJogging.ViewHolder) convertView.getTag();
        }

        Jogging exercise = StudentListTemp.get(position);

        holder.txtdate.setText(exercise.getDate());

        holder.txttime.setText(exercise.getTime());

        holder.txtdistance.setText(exercise.getDistance());


        holder.txtspeed.setText(exercise.getDuration());



        return convertView;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {

                ArrayList<Jogging> arrayList1 = new ArrayList<Jogging>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    Jogging subject = MainList.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            StudentListTemp = (ArrayList<Jogging>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = StudentListTemp.size(); i < l; i++)
                add(StudentListTemp.get(i));

            notifyDataSetInvalidated();
        }


    }



}
