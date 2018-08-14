package com.mickaelbrenoit.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.api.model.PhotoApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListPhotosAdapter extends RecyclerView.Adapter<ListPhotosAdapter.ViewHolderPhoto>{

    private List<PhotoApi> photoApiList;
    private Context context;

    public ListPhotosAdapter(List<PhotoApi> photoApiList, Context context) {
        this.photoApiList = photoApiList;
        this.context = context;
    }

    public class ViewHolderPhoto extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_photo)
        ImageView imageView_photo;
        @BindView(R.id.textView_title_photo)
        TextView textView_title_photo;

        public ViewHolderPhoto(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolderPhoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photo, parent, false);
        return new ViewHolderPhoto(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPhoto holder, int position) {
        final PhotoApi photoApi = photoApiList.get(position);

        holder.textView_title_photo.setText(photoApi.getTitle());
        Glide.with(context).load(photoApi.getUrl()).into(holder.imageView_photo);

    }

    @Override
    public int getItemCount() {
        return photoApiList.size();
    }
}
