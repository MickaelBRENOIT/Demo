package com.mickaelbrenoit.demo.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mickaelbrenoit.demo.AddOrModifyPostActivity;
import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.database.model.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_OBJECT_POST;
import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_TITLE_POST;
import static com.mickaelbrenoit.demo.helper.RequestCode.RESULT_CODE_MODIFY_POST;

public class ListPostsAdapter extends RecyclerView.Adapter<ListPostsAdapter.ViewHolderPost> {

    private static final String TAG = "ListPostsAdapter";

    private List<Post> postList;
    private FragmentActivity fragmentActivity;

    public ListPostsAdapter(List<Post> postList, FragmentActivity fragmentActivity) {
        this.postList = postList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolderPost extends RecyclerView.ViewHolder {

        @BindView(R.id.view_background_post)
        public RelativeLayout view_background_post;
        @BindView(R.id.view_foreground_post)
        public RelativeLayout view_foreground_post;

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
        holder.textView_title_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragmentActivity, AddOrModifyPostActivity.class);
                intent.putExtra(PUT_EXTRA_TITLE_POST, fragmentActivity.getString(R.string.button_modify_post));
                intent.putExtra(PUT_EXTRA_OBJECT_POST, post);
                fragmentActivity.startActivityForResult(intent, RESULT_CODE_MODIFY_POST);
            }
        });

        holder.textView_content_post.setText(post.getBody());

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.textView_content_post.getVisibility() == View.VISIBLE) {
                    holder.imageView_arrow_up_post.animate().rotation(0).start();
                    holder.textView_content_post.setVisibility(View.GONE);
                }
                else {
                    holder.imageView_arrow_up_post.animate().rotation(180).start();
                    holder.textView_content_post.setVisibility(View.VISIBLE);
                }
            }
        };

        holder.textView_display_content_post.setOnClickListener(onClickListener);
        holder.imageView_arrow_up_post.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void notifyItemAdded(List<Post> posts){
        postList = posts;
        notifyDataSetChanged();
    }

    public void notifyItemDeleted(List<Post> posts, int position) {
//        postList.remove(position);
        postList = posts;
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void notifyItemUpdated(List<Post> posts) {
        postList = posts;
        notifyDataSetChanged();
    }


}
