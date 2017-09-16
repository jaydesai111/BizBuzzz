package com.challenge.bizbuzzz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.challenge.bizbuzzz.CallBack.InsertItemCallBack;
import com.challenge.bizbuzzz.CallBack.RemoveItemCallBack;
import com.challenge.bizbuzzz.Pojo.Upload;
import com.challenge.bizbuzzz.R;
import com.challenge.bizbuzzz.Utility.BizBuzzzUtility;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Upload upload = uploads.get(position);

        holder.tv_name.setText(upload.getName());

        Glide.with(context).load(upload.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                       holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.iv_photo);
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
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
            tv_name =  itemView.findViewById(R.id.tv_name);
            iv_photo =  itemView.findViewById(R.id.iv_photo);
            bt_insert = itemView.findViewById(R.id.bt_insert);
            bt_remove = itemView.findViewById(R.id.bt_remove);
            bt_insert.setOnClickListener((View.OnClickListener) this);
            bt_remove.setOnClickListener((View.OnClickListener) this);

        }

        @Override
        public void onClick(View view) {
            if(!BizBuzzzUtility.isConnected()) {
                BizBuzzzUtility.displayMessageAlert("No internet",context);
                return;
            }

            switch (view.getId())
            {
                case R.id.bt_insert:
                    if(BizBuzzzUtility.isConnected()) {
                        insertItemCallBack.onItemInserted(getAdapterPosition());
                    }
                    else
                    {
                        BizBuzzzUtility.displayMessageAlert("No internet",context);
                    }

                    break;
                case R.id.bt_remove:
                    removeItemCallBack.onItemRemove(getAdapterPosition());

                    break;
            }

        }
    }

}
