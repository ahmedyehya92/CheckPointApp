package com.cp.app.checkpoint.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 20/12/2017.
 */

public class ListToOrderAdabter extends ArrayAdapter<ListOfOneOrderModel> {
    Context context;
    ViewHolder viewHolder;

    customButtonListener customListener;

    public ListToOrderAdabter(Context context, ArrayList<ListOfOneOrderModel> items)
    {
        super(context,0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ListOfOneOrderModel itemModel = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_of_list_to_order,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvNameOfItem = convertView.findViewById(R.id.tv_header_title);
            viewHolder.tvDesiredQuantity = convertView.findViewById(R.id.tv_desired_quantity);
            viewHolder.btnRemoveItem = convertView.findViewById(R.id.btn_remove_item);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvNameOfItem.setText(itemModel.getItemName());
        viewHolder.tvDesiredQuantity.setText(itemModel.getDesiredqQuantity());
        viewHolder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.onRemoveButtonClickListner(itemModel.getDbId(),view,position);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvNameOfItem;
        TextView tvDesiredQuantity;
        Button btnRemoveItem;
    }

    public interface customButtonListener {
        public void onRemoveButtonClickListner(String dbId, View buttonView, int position);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }
}
