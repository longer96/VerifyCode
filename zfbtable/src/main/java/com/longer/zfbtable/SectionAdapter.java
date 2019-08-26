package com.longer.zfbtable;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SectionAdapter extends BaseSectionQuickAdapter<MySectionBean, BaseViewHolder> {

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<MySectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySectionBean item) {
        helper.setText(R.id.tv_head, item.header);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MySectionBean item) {
        helper.setText(R.id.tv_name,item.t.name);
    }
}
