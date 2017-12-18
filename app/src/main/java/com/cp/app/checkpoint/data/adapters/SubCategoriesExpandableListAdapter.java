package com.cp.app.checkpoint.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.models.SubCategoryChildModel;
import com.cp.app.checkpoint.data.models.SubCategoryGroupModel;
import com.cp.app.checkpoint.data.viewholders.SubCategoriesChildViewHolder;
import com.cp.app.checkpoint.data.viewholders.SubCategoriesGroupViewHolder;

import java.util.ArrayList;


/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public class SubCategoriesExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<SubCategoryGroupModel> _listDataHeader;


    customButtonListener customListner;

    public SubCategoriesExpandableListAdapter(Context _context, ArrayList<SubCategoryGroupModel> _listDataHeader) {
        this._context = _context;
        this._listDataHeader = _listDataHeader;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        ArrayList<SubCategoryChildModel> chList = _listDataHeader.get(groupPosition).getItemsOfGroup();
        return chList.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        SubCategoriesChildViewHolder viewHolder;
        final SubCategoryChildModel childModel = (SubCategoryChildModel) getChild(groupPosition,childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child_sub_category, null);
            viewHolder = new SubCategoriesChildViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.child_title);
            viewHolder.btnPlusToOrder = (Button) convertView.findViewById(R.id.btn_plus_to_order);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (SubCategoriesChildViewHolder) convertView.getTag();
        }
        //TODO set text here
        viewHolder.title.setText(childModel.getName());
        viewHolder.btnPlusToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customListner != null)
                {
                    customListner.onButtonClickListner(groupPosition,childPosition,childModel.getName());
                }
            }
        });
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<SubCategoryChildModel> chList = _listDataHeader.get(groupPosition).getItemsOfGroup();
        return chList.size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        SubCategoriesGroupViewHolder viewHolder;
        // TODO change this
        SubCategoryGroupModel groupModel = (SubCategoryGroupModel)getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_groub_sub_category, null);
            viewHolder = new SubCategoriesGroupViewHolder();
            viewHolder.headerTitle =(TextView) convertView.findViewById(R.id.tv_header_title);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (SubCategoriesGroupViewHolder) convertView.getTag();
        }
        //TODO change this
        viewHolder.headerTitle.setText(groupModel.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public interface customButtonListener {
        public void onButtonClickListner(int groupPosition, int childPosition,String value);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
}




