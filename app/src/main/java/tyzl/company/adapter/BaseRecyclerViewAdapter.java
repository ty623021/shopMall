package tyzl.company.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by Administrator on 2017/3/15.
 */

public abstract class BaseRecyclerViewAdapter<T,E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> {
    public List<T> list;
    public LayoutInflater mInflater;

    public BaseRecyclerViewAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public E onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater=LayoutInflater.from(parent.getContext());
        return mCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(E holder, int position) {
        mBindViewHolder(holder,position);
    }

    public abstract E mCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void mBindViewHolder(E holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void add(T t) {
        this.list.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

}
