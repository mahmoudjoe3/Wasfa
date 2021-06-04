package com.mahmoudjoe3.wasfaty.ui.main.home.comment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.MyLogic;
import com.mahmoudjoe3.wasfaty.pojo.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.VH> {
    ArrayList<Comment> commentList;
    public CommentAdapter() {
        this.commentList = new ArrayList<>();
    }
    public void setCommentList(List<Comment> commentList) {
        if(commentList!=null)
            this.commentList = new ArrayList<>(commentList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_comment_item_list,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int position) {
        Comment comment=commentList.get(position);

        Glide.with(vh.comment_profile_img.getContext())
                .load(Uri.parse(comment.getUserImageUrl()))
                .into(vh.comment_profile_img);
        vh.comment_username.setText(comment.getUsername());

        int creator_tag_visabilty=(comment.isCreator())? View.VISIBLE:View.GONE;
        vh.comment_creator_tag.setVisibility(creator_tag_visabilty);

        vh.comment_text.setText(comment.getCommentText());
        vh.comment_from_time.setText(MyLogic.getTimeFrom(comment.getCreatedTime()));

        vh.comment_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnOpenProfileListenner.onClick(comment.getUserId());
            }
        });
        vh.comment_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnOpenProfileListenner.onClick(comment.getUserId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void addComment(Comment comment) {
        commentList.add(0,comment);
        notifyItemInserted(0);
    }

    static class VH extends RecyclerView.ViewHolder{
        TextView comment_username, comment_from_time, comment_creator_tag, comment_text;
        ImageView comment_profile_img;
        public VH(@NonNull View itemView) {
            super(itemView);

            comment_profile_img=itemView.findViewById(R.id.comment_profile_img);
            comment_username=itemView.findViewById(R.id.comment_username);
            comment_from_time=itemView.findViewById(R.id.comment_from_time);
            comment_creator_tag=itemView.findViewById(R.id.comment_creator_tag);
            comment_text=itemView.findViewById(R.id.comment_text);

        }
    }

    OnOpenProfileListenner mOnOpenProfileListenner;
    public void setOnOpenProfileListenner(OnOpenProfileListenner mOnOpenProfileListenner) {
        this.mOnOpenProfileListenner = mOnOpenProfileListenner;
    }
    public interface OnOpenProfileListenner{
        void onClick(int userid);
    }

    OnAddCommentListenner mOnAddCommentListenner;
    public void setOnAddCommentListenner(OnAddCommentListenner mOnAddCommentListenner) {
        this.mOnAddCommentListenner = mOnAddCommentListenner;
    }
    public interface OnAddCommentListenner{
        void onAdded(Comment comment);
    }
}
