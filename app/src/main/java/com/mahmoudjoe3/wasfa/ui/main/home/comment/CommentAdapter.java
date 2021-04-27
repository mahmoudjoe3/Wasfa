package com.mahmoudjoe3.wasfa.ui.main.home.comment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Recipe;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.VH> {
    List<Comment> commentList;
    public CommentAdapter() {
        this.commentList = new ArrayList<>();
    }
    public void setCommentList(List<Comment> commentList) {
        if(commentList!=null)
            this.commentList = commentList;
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
        vh.comment_loves_count.setText(""+comment.getLoveCount());
        vh.comment_love_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vh.comment_love_layout.getTag().equals("of")){
                    vh.comment_love_btn.setImageResource(R.drawable.ic_love);
                    vh.comment_loves_count.setTextColor(vh.itemView.getContext().getColor(R.color.colorTap));
                    vh.comment_love_layout.setTag("on");
                    vh.comment_loves_count.setText((Integer.parseInt(vh.comment_loves_count.getText().toString())+1)+"");
                    mOnLoveCommentListener.onLove(comment);
                }else {
                    vh.comment_love_btn.setImageResource(R.drawable.ic_love_invers);
                    vh.comment_loves_count.setTextColor(vh.itemView.getContext().getColor(R.color.transparentDark));
                    vh.comment_love_layout.setTag("of");
                    vh.comment_loves_count.setText((Integer.parseInt(vh.comment_loves_count.getText().toString())-1)+"");
                    mOnLoveCommentListener.onDisLove(comment);
                }
            }
        });
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

    static class VH extends RecyclerView.ViewHolder{
        TextView comment_username, comment_from_time, comment_creator_tag, comment_text, comment_loves_count;
        ImageView comment_profile_img, comment_love_btn;
        LinearLayout comment_love_layout;
        public VH(@NonNull View itemView) {
            super(itemView);

            comment_profile_img=itemView.findViewById(R.id.comment_profile_img);
            comment_username=itemView.findViewById(R.id.comment_username);
            comment_from_time=itemView.findViewById(R.id.comment_from_time);
            comment_creator_tag=itemView.findViewById(R.id.comment_creator_tag);
            comment_text=itemView.findViewById(R.id.comment_text);
            comment_loves_count=itemView.findViewById(R.id.comment_loves_count);
            comment_love_btn=itemView.findViewById(R.id.comment_love_btn);
            comment_love_layout=itemView.findViewById(R.id.comment_love_layout);
        }
    }
    OnLoveCommentListener mOnLoveCommentListener;
    public void setmOnLoveListenner(OnLoveCommentListener mOnLoveCommentListener) {
        this.mOnLoveCommentListener = mOnLoveCommentListener;
    }
    public interface OnLoveCommentListener{
        void onLove(Comment comment);
        void onDisLove(Comment comment);
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
