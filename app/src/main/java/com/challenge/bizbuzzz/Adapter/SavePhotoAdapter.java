package com.challenge.bizbuzzz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.challenge.bizbuzzz.CallBack.InsertItemCallBack;
import com.challenge.bizbuzzz.CallBack.RemoveItemCallBack;
import com.challenge.bizbuzzz.Pojo.Upload;
import com.challenge.bizbuzzz.R;

import java.util.List;



public class SavePhotoAdapter  extends RecyclerView.Adapter<SavePhotoAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;
    private String TAG = "SAVEPHOTOADAPTER";
    InsertItemCallBack insertItemCallBack;
    RemoveItemCallBack removeItemCallBack;

    public SavePhotoAdapter(Context context, List<Upload> uploads,InsertItemCallBack insertItemCallBack,RemoveItemCallBack removeItemCallBack) {
        this.uploads = uploads;
        this.context = context;
        this.insertItemCallBack = insertItemCallBack;
        this.removeItemCallBack = removeItemCallBack;
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



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_name;
        public ImageView iv_photo;
        public Button bt_insert;
        public Button bt_remove;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name =  itemView.findViewById(R.id.tv_name);
            iv_photo =  itemView.findViewById(R.id.iv_photo);
            bt_insert = itemView.findViewById(R.id.bt_insert);
            bt_remove = itemView.findViewById(R.id.bt_remove);
            bt_insert.setOnClickListener((View.OnClickListener) this);
            bt_remove.setOnClickListener((View.OnClickListener) this);

        }

        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
                case R.id.bt_insert:
                    insertItemCallBack.onItemInserted(getAdapterPosition());
                    break;
                case R.id.bt_remove:
                    removeItemCallBack.onItemRemove(getAdapterPosition());
                    break;
            }

        }
    }

}
