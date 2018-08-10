package com.mickaelbrenoit.demo.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.database.model.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAlbumsAdapter extends RecyclerView.Adapter<ListAlbumsAdapter.ViewHolderAlbum>{

    private static final String TAG = "ListAlbumsAdapter";

    private List<Album> albumList;
    private FragmentActivity fragmentActivity;

    public ListAlbumsAdapter(List<Album> albumList, FragmentActivity fragmentActivity) {
        this.albumList = albumList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolderAlbum extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_title_album)
        TextView textView_title_album;
        @BindView(R.id.textView_created_by_album)
        TextView textView_created_by_album;
        @BindView(R.id.textView_author_album)
        TextView textView_author_album;

        public ViewHolderAlbum(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolderAlbum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_album, parent, false);
        return new ViewHolderAlbum(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAlbum holder, int position) {
        final Album album = albumList.get(position);

        holder.textView_title_album.setText(album.getTitle());
        holder.textView_author_album.setText(String.valueOf(album.getUserId()));

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
