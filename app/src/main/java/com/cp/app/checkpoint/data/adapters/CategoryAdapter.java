package com.cp.app.checkpoint.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 03/01/2018.
 */

public class CategoryAdapter extends ArrayAdapter<CategoryModel> {

    Context context;
    ViewHolder viewHolder;

    private customButtonListener customListener;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> items)
    {
        super(context,0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final CategoryModel itemModel = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_category,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvNameOfCategory = convertView.findViewById(R.id.tv_header_title);
            viewHolder.btntransition = convertView.findViewById(R.id.btn_hot_drinks);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvNameOfCategory.setText(itemModel.getCategoryName());
        viewHolder.btntransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = itemModel.getCategoryId();
                customListener.onItemClickListner(itemModel.getCategoryId(), itemModel.getCategoryName(),view,position);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvNameOfCategory;
        LinearLayout btntransition;
    }

    public interface customButtonListener {
        public void onItemClickListner(String id, String catName, View buttonView, int position);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }
}
