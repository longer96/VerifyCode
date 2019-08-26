package com.longer.zfbtable;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ItemDragAdapter extends BaseItemDraggableAdapter<MoudleBean, BaseViewHolder> {
    public ItemDragAdapter(List<MoudleBean> data) {
        super(R.layout.item_moudle, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MoudleBean item) {
        helper.setText(R.id.tv_name,item.name + "s");
    }
}
