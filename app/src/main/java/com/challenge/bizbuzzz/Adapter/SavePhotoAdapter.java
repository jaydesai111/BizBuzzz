package com.challenge.bizbuzzz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.challenge.bizbuzzz.Pojo.Upload;
import com.challenge.bizbuzzz.R;

import java.util.List;

/**
 * Created by Guidezie on 14-09-2017.
 */

public class SavePhotoAdapter  extends RecyclerView.Adapter<SavePhotoAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;
    private String TAG = "SAVEPHOTOADAPTER";

    public SavePhotoAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_save_photo, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Upload upload = uploads.get(position);

        holder.tv_name.setText(upload.getName());

        Glide.with(context).load(upload.getUrl()).into(holder.iv_photo);
        Log.i(TAG,"this is url "+upload.getUrl());
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public ImageView iv_photo;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name =  itemView.findViewById(R.id.tv_name);
            iv_photo =  itemView.findViewById(R.id.iv_photo);
        }
    }
}
