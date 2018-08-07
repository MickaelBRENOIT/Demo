package com.mickaelbrenoit.demo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.database.model.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListPostsAdapter extends RecyclerView.Adapter<ListPostsAdapter.ViewHolderPost> {

    private static final String TAG = "ListPostsAdapter";

    private List<Post> postList;

    public ListPostsAdapter(List<Post> postList) {
        this.postList = postList;
    }

    public class ViewHolderPost extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_title_post)
        TextView textView_title_post;
        @BindView(R.id.textView_content_post)
        TextView textView_content_post;
        @BindView(R.id.textView_display_content_post)
        TextView textView_display_content_post;

        @BindView(R.id.imageView_arrow_up_post)
        ImageView imageView_arrow_up_post;


        public ViewHolderPost(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolderPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false);
        return new ViewHolderPost(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderPost holder, int position) {
        final Post post = postList.get(position);

        holder.textView_title_post.setText(post.getTitle());
        holder.textView_content_post.setText(post.getBody());

        holder.imageView_arrow_up_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.textView_content_post.getVisibility() == View.VISIBLE) {
                    holder.imageView_arrow_up_post.animate().rotation(0).start();
                    holder.textView_content_post.setVisibility(View.GONE);
                }
                else {
                    holder.imageView_arrow_up_post.animate().rotation(180).start();
                    holder.textView_content_post.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
