package com.xwh.lib.corelib.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author venfox
 * @description
 * @since 1.0.0
 */
public abstract class CommonAdapter<T> extends BaseAdapter implements Filterable {

    private final Object mLock = new Object();

    private ObjectFilter mFilter;
    /**
     * 原始数据
     */
    private ArrayList<T> mOriginalValues;
    /**
     * 数据源
     */
    protected List<T> mObjects;

    protected Context mContext;

    /**
     * 视图ID
     * <p>
     * 多 item view 支持
     *
     * @since 1.3.0
     */
    @LayoutRes
    private int[] layout;

    IcommonAdapterNotifiCallBack icommonAdapterNotifiCallBack;

    /**
     * 增加多 item View 支持
     *
     * @param context
     * @param objects
     * @param layout
     */
    public CommonAdapter(Context context, List<T> objects, @NonNull int... layout) {
        this.mContext = context;
        this.mObjects = objects;
        this.layout = layout;
    }

    public void setNotifiCallBack(IcommonAdapterNotifiCallBack callBack) {
        icommonAdapterNotifiCallBack = callBack;
    }

    public int[] getLayouts() {
        return layout;
    }

    @LayoutRes
    public int getItemLayoutId(int position) {
        return layout[getItemViewType(position)];
    }

    @Override
    public int getCount() {
        return mObjects == null ? 0 : mObjects.size();
    }

    @Override
    public T getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return layout == null ? 0 : layout.length;
    }


    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {

        View view = getConvertView(mContext, position, convertView, parent);

        Object tag = view.getTag();

        ViewHolder viewHolder = tag instanceof ViewHolder ? (ViewHolder) tag : new ViewHolder(mContext, view);

        onBindData(viewHolder, getItem(position), position, parent);

        return viewHolder.getItemView();
    }

    protected abstract void onBindData(@NonNull ViewHolder viewHolder, @NonNull final T item, final int position, ViewGroup parent);

    /**
     * 可以不使用从外界传布局进来
     *
     * @param context
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    protected View getConvertView(Context context, int position, View convertView, ViewGroup parent) {
        return convertView == null ? LayoutInflater.from(context).inflate(getItemLayoutId(position), parent, false) : convertView;
    }


    /**
     * 添加单个数据对象
     *
     * @param object
     */
    public final void add(T object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(object);
            } else {
                mObjects.add(object);
            }
        }
        notifyDataSetChanged();
    }


    public final void addAll(List<T> collection) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.addAll(collection);
            } else {
                mObjects.addAll(collection);
            }
        }
        notifyDataSetChanged();
    }

    public final void insert(T object, int index) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(index, object);
            } else {
                mObjects.add(index, object);
            }
        }
        notifyDataSetChanged();
    }

    public final void set(T object, int index) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.set(index, object);
            } else {
                mObjects.set(index, object);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(icommonAdapterNotifiCallBack!=null)icommonAdapterNotifiCallBack.callNotifi();

    }

    public final boolean remove(T object) {
        boolean b;
        synchronized (mLock) {
            if (mOriginalValues != null) {
                b = mOriginalValues.remove(object);
            } else {
                b = mObjects.remove(object);
            }
        }
        notifyDataSetChanged();
        return b;
    }

    public final T remove(int index) {
        T t;
        synchronized (mLock) {
            if (mOriginalValues != null) {
                t = mOriginalValues.remove(index);
            } else {
                t = mObjects.remove(index);
            }
        }
        notifyDataSetChanged();
        return t;
    }

    /**
     * 清除数据
     *
     * @param notify true 刷新
     */
    public final void clear(boolean notify) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.clear();
            } else {
                mObjects.clear();
            }
        }

        if (notify) {
            notifyDataSetChanged();
        }
    }

    /**
     * 取出所有数据
     *
     * @return
     */
    public final List<T> getObjects() {
        return mObjects;
    }

    /**
     * 对数据排序
     *
     * @param comparator
     */
    public final void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Collections.sort(mOriginalValues, comparator);
            } else {
                Collections.sort(mObjects, comparator);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ObjectFilter();
        }
        return mFilter;
    }

    private class ObjectFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<T> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                ArrayList<T> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final ArrayList<T> newValues = new ArrayList<>();
                final int count = values.size();
                String prefixString = prefix.toString().toLowerCase();
                for (int i = 0; i < count; i++) {
                    final T value = values.get(i);
                    final String valueText = value.toString().toLowerCase();
                    if (valueText.contains(prefixString)) {
                        newValues.add(value);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mObjects = (List<T>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    /**
     * View Holder
     *
     * @description
     */
    public static class ViewHolder {
        private Context mContext;
        private SparseArray<View> mViews;
        private View mItemView;

        ViewHolder(Context context, View itemView) {
            mContext = context;
            mViews = new SparseArray<>();
            mItemView = itemView;
            mItemView.setTag(this);
        }


        /**
         * @param id
         * @param <T>
         * @return
         * @since 1.2.0
         */
        public <T extends View> T find(@IdRes int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = mItemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public ViewHolder setText(@IdRes int viewId, @StringRes int textId) {
            return setText(viewId, mContext.getString(textId));
        }

        /**
         * 设置 TextView 的显示的字符串
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(@IdRes int viewId, CharSequence text) {
            TextView view = find(viewId);
            view.setText(text);
            return this;
        }

        public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
            TextView view = find(viewId);
            view.setTextColor(textColor);
            return this;
        }

        public ViewHolder setTextColorRes(@IdRes int viewId, @ColorRes int textColorRes) {
            TextView view = find(viewId);
            view.setTextColor(mContext.getResources().getColor(textColorRes));
            return this;
        }

        public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
            ImageView view = find(viewId);
            view.setImageResource(resId);
            return this;
        }

        public ViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
            ImageView view = find(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }

        public View getItemView() {
            return mItemView;
        }
    }
}
