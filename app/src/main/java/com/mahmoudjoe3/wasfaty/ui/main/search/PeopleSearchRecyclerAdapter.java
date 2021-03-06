package com.mahmoudjoe3.wasfaty.ui.main.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.MyLogic;
import com.mahmoudjoe3.wasfaty.pojo.User;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleSearchRecyclerAdapter extends RecyclerView.Adapter<PeopleSearchRecyclerAdapter.PeopleSearchViewHolder> {
    private List<UserPost> userList = new ArrayList<>();
    private UserPost user;
    @NonNull
    @Override
    public PeopleSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_search_item, parent, false);
        return new PeopleSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleSearchViewHolder holder, int position) {
        if(user.getId()==userList.get(position).getId()){ return; }

        if(MyLogic.Followed(userList.get(position).getId(),user.getFollowings())){
            holder.followUser.setVisibility(View.GONE);
            holder.lotti_people_follow_btn.setProgress(1);
            holder.lotti_people_follow_btn.setVisibility(View.VISIBLE);
        }

        holder.nameTextView.setText(userList.get(position).getName());
        holder.bioTextView.setText(userList.get(position).getBio());
        Picasso.get().load(userList.get(position).getImageUrl()).fit().centerCrop().into(holder.profileCircleImageView);
        holder.followUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.followUser.setVisibility(View.GONE);
                holder.lotti_people_follow_btn.setVisibility(View.VISIBLE);
                holder.lotti_people_follow_btn.playAnimation();
                //todo follow user;
                onItemClickListener.onFollow(userList.get(position));
            }
        });

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(userList.get(position));
            }
        });
        holder.profileCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(userList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<UserPost> userList) {
        if(!userList.isEmpty()) {
            user = userList.get(userList.size() - 1);
            userList.remove(userList.size() - 1);
            this.userList = userList;
        }else this.userList=new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class PeopleSearchViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileCircleImageView;
        TextView nameTextView, bioTextView;
        LottieAnimationView lotti_people_follow_btn;
        ImageButton followUser;
        public PeopleSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            profileCircleImageView = itemView.findViewById(R.id.profile_imageView);
            nameTextView = itemView.findViewById(R.id.name_textView);
            bioTextView = itemView.findViewById(R.id.bio_textView);
            lotti_people_follow_btn=itemView.findViewById(R.id.lotti_people_follow_btn);
            followUser=itemView.findViewById(R.id.follow_imageButton);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener{
        void onClick(UserPost user);
        void onFollow(UserPost user);
    }
}
