package com.longer.zfbtable;

import com.chad.library.adapter.base.entity.SectionEntity;

public class MySectionBean extends SectionEntity<MoudleBean> {
    private boolean isMore;

    public MySectionBean(boolean isHeader, String header, boolean isMore) {
        super(isHeader, header);
        this.isMore = isMore;
    }

    public MySectionBean(MoudleBean moudleBean) {
        super(moudleBean);
    }
}
