package com.cp.app.checkpoint.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.models.OldOrderChildModel;
import com.cp.app.checkpoint.data.models.OldOrderGroupModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 24/12/2017.
 */

public class OldOrdersExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<OldOrderGroupModel> _listDataHeader;

    public OldOrdersExpandableListAdapter(Context _context, ArrayList<OldOrderGroupModel> _listDataHeader) {
        this._context = _context;
        this._listDataHeader = _listDataHeader;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<OldOrderChildModel> chList = _listDataHeader.get(groupPosition).getItemsOfGroup();
        return chList.size()+2;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        ArrayList<OldOrderChildModel> chList = _listDataHeader.get(groupPosition).getItemsOfGroup();
        return chList.get(childPosititon);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        // TODO change this
        OldOrderGroupModel groupModel = (OldOrderGroupModel) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_old_orders_row, null);
            viewHolder = new GroupViewHolder();
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_groupe_title);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        //TODO change this
        viewHolder.tvTitle.setText(groupModel.getTitle());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        OldOrderGroupModel groupItem = (OldOrderGroupModel) getGroup(groupPosition);
        ChildViewHolder viewHolder;
        ChildFooterViewHolder footerViewHolder;


        if(childPosition ==0)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_old_order_header_child, null);
        }

        if (childPosition>0 && childPosition<getChildrenCount(groupPosition)-1)
        {
            final OldOrderChildModel childModel = (OldOrderChildModel) getChild(groupPosition,childPosition-1);

                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = infalInflater.inflate(R.layout.list_child_old_orders_row, null);
                TextView tvItemName;
                TextView tvQuantity;
                tvItemName =  convertView.findViewById(R.id.child_title);
                tvQuantity = convertView.findViewById(R.id.tv_quantity);



            tvItemName.setText(childModel.getName());
            tvQuantity.setText(childModel.getQuantity());
        }

        if (childPosition == getChildrenCount(groupPosition)-1)
        {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = infalInflater.inflate(R.layout.list_group_total_price_old_orders_row, null);
                TextView tvTotalPrice;
                tvTotalPrice =  convertView.findViewById(R.id.tv_total_price);


            String unit = _context.getResources().getString(R.string.unit_egypt);
            String totalPrice = groupItem.getTotalPriceForOrder()+" " + unit;
            tvTotalPrice.setText(totalPrice);

        }




        return convertView;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public class GroupViewHolder {
        TextView tvTitle;
    }

    public class ChildViewHolder {
        TextView tvItemName;
        TextView tvQuantity;
    }
    public class ChildFooterViewHolder {
        TextView tvTotalPrice;
    }
}
