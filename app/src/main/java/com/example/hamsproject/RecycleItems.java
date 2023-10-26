package com.example.hamsproject;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleItems extends RecyclerView.ItemDecoration {
    private int spacing;
    private Context context;

    public RecycleItems(int spacing, Context context) {
        this.spacing = spacing;
        this.context = context;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;
        outRect.bottom = spacing;
    }
}
