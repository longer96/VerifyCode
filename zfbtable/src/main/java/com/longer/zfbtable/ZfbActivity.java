package com.longer.zfbtable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;

import java.util.ArrayList;
import java.util.List;

public class ZfbActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView_head;
    GridLayoutManager gridLayoutManager;
    GridLayoutManager gridLayoutManager2;
    List<MySectionBean> list;
    List<MoudleBean> list_moudle = new ArrayList<MoudleBean>();
    SectionAdapter adapter;
    ItemDragAdapter dragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycle);
        recyclerView_head = findViewById(R.id.recycle_head);
        initContentRecycle();
        initHeadRecycle();
    }

    private void initContentRecycle() {
        gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        list = getData();

        adapter = new SectionAdapter(R.layout.item_moudle, R.layout.item_head, list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySectionBean bean = (MySectionBean) adapter.getItem(position);
                if (bean.t == null)
                    return;

                Log.i("longer", "vaiue:" + bean.t.getId());
                dragAdapter.addData(bean.t);
            }
        });
    }

    private void initHeadRecycle() {
        gridLayoutManager2 = new GridLayoutManager(this, 4);
        recyclerView_head.setLayoutManager(gridLayoutManager2);

        dragAdapter = new ItemDragAdapter(list_moudle);
        recyclerView_head.setAdapter(dragAdapter);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(dragAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView_head);

        // 开启拖拽
        dragAdapter.enableDragItem(itemTouchHelper, R.id.ll_item, true);
        dragAdapter.setOnItemDragListener(onItemDragListener);
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            List<MoudleBean> beanList = dragAdapter.getData();
            StringBuffer sb = new StringBuffer();
            for (MoudleBean bean:beanList){
                sb.append(bean.id);
                sb.append(" - ");
            }
            Log.i("longer", "onItemDragEnd:" + sb.toString());
        }
    };

    private List<MySectionBean> getData() {
        List<MySectionBean> list = new ArrayList<>();
        list.add(new MySectionBean(true, "智慧家", false));
        for (int i = 0; i < 5; i++) {
            list.add(new MySectionBean(new MoudleBean(i, "功能" + i, "img")));
        }
        list.add(new MySectionBean(true, "零距离", false));
        for (int i = 5; i < 12; i++) {
            list.add(new MySectionBean(new MoudleBean(i, "木块" + i, "img")));
        }
        list.add(new MySectionBean(true, "云社区", false));
        for (int i = 12; i < 19; i++) {
            list.add(new MySectionBean(new MoudleBean(i, "公司" + i, "img")));
        }

        return list;
    }

}
