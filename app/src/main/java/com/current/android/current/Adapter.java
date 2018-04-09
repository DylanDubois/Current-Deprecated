package com.current.android.current;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder{
    public ProgressBar mProgressBar;

    public LoadingViewHolder(View itemView){
        super(itemView);
        mProgressBar = itemView.findViewById(R.id.progbar);
    }
}


class ItemViewHolder extends RecyclerView.ViewHolder{
    public TextView eventName;
    public TextView eventDescription;

    public ItemViewHolder(View itemView){
        super(itemView);
        eventName = (TextView)itemView.findViewById(R.id.eventname);
        eventDescription = (TextView)itemView.findViewById(R.id.eventdescription);
    }
}

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM=0, VIEW_TYPE_LOADING=1;
    LoadMoreI mLoadMoreI;
    Boolean isLoading;
    Activity mActivity;
    List<EventPost> mEventsList;
    int visibleThresholder=5;
    int lastVisibleEvent, totalEventCount;

    public Adapter(RecyclerView recyclerView, Activity activity, List<EventPost> eventsList) {
        mActivity = activity;
        mEventsList = eventsList;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalEventCount = linearLayoutManager.getItemCount();
                lastVisibleEvent = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalEventCount<= (lastVisibleEvent+visibleThresholder)){
                    if(mLoadMoreI != null)
                        mLoadMoreI.onLoadMore();
                    isLoading = true;
                }

            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return mEventsList.get(position)==null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    public void setLoadMoreI(LoadMoreI loadMoreI) {
        mLoadMoreI = loadMoreI;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(mActivity).inflate(R.layout.event_layout,parent,false);
            return new ItemViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(mActivity).inflate(R.layout.event_loading,parent,false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            EventPost event = mEventsList.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder)holder;
            viewHolder.eventName.setText(mEventsList.get(position).getEventName());
            viewHolder.eventDescription.setText(mEventsList.get(position).getEventDescription());
        }
        else if (holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.mProgressBar.setIndeterminate(true);

        }
    }



    public void setLoaded() {
        isLoading = false;
    }
}
