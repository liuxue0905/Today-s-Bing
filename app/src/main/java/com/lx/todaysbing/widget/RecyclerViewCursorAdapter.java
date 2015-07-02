package com.lx.todaysbing.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

/**
 * Created by liuxue on 2015/7/2.
 */
public abstract class RecyclerViewCursorAdapter extends RecyclerView.Adapter<RecyclerViewCursorAdapter.ViewHolder> {

    private Context mContext;
    private CursorAdapter mCursorAdapter;

    public RecyclerViewCursorAdapter(Context context, Cursor c) {
        mContext = context;
        mCursorAdapter = new MyCursorAdapter(context, c);
    }

    public RecyclerViewCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        mContext = context;
        mCursorAdapter = new MyCursorAdapter(context, c, autoRequery);
    }

    public RecyclerViewCursorAdapter(Context context, Cursor c, int flags) {
        mContext = context;
        mCursorAdapter = new MyCursorAdapter(context, c, flags);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerViewCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;

        // Passing the inflater job to the cursor-adapter
//        View v = mCursorAdapter.newView(mContext, null, parent);
        View v = newView(parent, viewType);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewCursorAdapter.ViewHolder holder, int position) {
        Cursor cursor = mCursorAdapter.getCursor();
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }

        // Passing the binding operation to cursor loader
        mCursorAdapter.bindView(holder.itemView, mContext, cursor);
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    private class MyCursorAdapter extends CursorAdapter {

        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        public MyCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return null;
//            return RecyclerViewCursorAdapter.this.newView(context, cursor, parent);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            RecyclerViewCursorAdapter.this.bindView(view, context, cursor);
        }

//        @Override
//        protected void onContentChanged() {
//            super.onContentChanged();
//            RecyclerViewCursorAdapter.this.notifyDataSetChanged();
//        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            RecyclerViewCursorAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void notifyDataSetInvalidated() {
            super.notifyDataSetInvalidated();
            RecyclerViewCursorAdapter.this.notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return mCursorAdapter.getCursor();
    }

    public Object getItem(int position) {
        return mCursorAdapter.getItem(position);
    }

    public Cursor swapCursor(Cursor newCursor) {
        return mCursorAdapter.swapCursor(newCursor);
    }

//    public abstract View newView(Context context, Cursor cursor, ViewGroup parent);
    public abstract void bindView(View view, Context context, Cursor cursor);

    public abstract View newView(ViewGroup parent, int viewType);
}
