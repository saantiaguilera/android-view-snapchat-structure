package com.santiago.stuff;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by santiago on 27/03/16.
 */
public class ViewAdapter extends PagerAdapter {

    private List<? extends View> data;

    public ViewAdapter(List<? extends View> data) {
        this.data = data;
    }
    
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        container.addView(data.get(position));
        container.setTag(data.get(position));
        return data.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    public View getItem(int index){
        return data.get(index);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
    
}