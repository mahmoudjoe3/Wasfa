package com.mahmoudjoe3.wasfaty.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.MyLogic;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.ui.activities.profile.profileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.mahmoudjoe3.wasfaty.logic.MyLogic.getTimeFrom;

public class RecipePostAdapter extends RecyclerView.Adapter<RecipePostAdapter.VH> {
    List<Recipe> recipes =new ArrayList<>();
    List<Following> followings=new ArrayList<>();
    UserPost user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        if(recipes==null){
            this.recipes=new ArrayList<>();
        }else
            this.recipes = recipes;
        notifyDataSetChanged();
    }
    public void addAll(List<Recipe> recipes) {
        if(recipes==null){
            this.recipes=new ArrayList<>();
        }else {
            for(Recipe r:recipes){
                this.recipes.add(r);
                notifyItemInserted(this.recipes.size());
            }
        }
    }
    public void setFollowing(List<Following> followings) {
        this.followings=followings;
    }

    public RecipePostAdapter() {
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int position) {
        Recipe recipe=recipes.get(position);
        Shimmer shimmer=new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#e7e7e7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();

        ShimmerDrawable drawable=new ShimmerDrawable();
        drawable.setShimmer(shimmer);

        initUserCaption(vh,recipe);
        initPostCaption(vh,recipe);
    }

    private void initUserCaption(VH vh, Recipe recipe) {
        //user object
        vh.post_from_time.setText(getTimeFrom(recipe.getCreatedDate()));
        vh.post_username.setText(recipe.getUserName());
        Picasso.get().load(recipe.getUserProfileThumbnail())
                .into(vh.post_profile_img);

        vh.post_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile(v, recipe);
            }
        });
        vh.post_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open prifile
                openProfile(v, recipe);
            }
        });
        for(Following f:followings){
            if(recipe.getUserId()==f.getId()){
                vh.post_user_follow_btn.setVisibility(View.GONE);
                break;
            }
        }
        if(user.getId()==recipe.getUserId()|| MyLogic.Followed(recipe.getUserId(),followings))
        {
            vh.post_user_follow_btn.setVisibility(View.GONE);
        }

        vh.post_user_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //follow user
                vh.lotti_post_user_follow_btn.playAnimation();
                vh.post_user_follow_btn.setVisibility(View.GONE);
                if(mOninteractionClickListener!=null)
                    mOninteractionClickListener.onfollow(recipe);
            }
        });
    }

    private void openProfile(View v, Recipe recipe) {
        //open prifile
        if(user.getId()==recipe.getUserId()){
            openProfileListener.intentToAccount();
        }else {
        Intent intent = new Intent(v.getContext(), profileActivity.class);
        intent.putExtra(profileActivity.USER_INTENT, recipe.getUserId());
        v.getContext().startActivity(intent);
        }
    }


    private void initPostCaption(VH vh, Recipe recipe) {
        vh.post_rate.setRating((float) (recipe.getReviewsRateTotal()/2));
        String desc=recipe.getDescription();
        if(desc.length() > 250) {
            desc = desc.substring(0,247);
            desc += "...";
        }
        vh.post_description.setText(desc);
        vh.prepare_time.setText(recipe.getPrepareTime());
        vh.nationality.setText(recipe.getNationality());
        initCategoryList(vh,recipe.getCategories());
        initIngredientList(vh,recipe.getIngredients());
        initStepsList(vh,recipe.getSteps());
        initImages(vh,recipe);
        if(recipe.getLoveCount()>0) {
            vh.post_love_number.setText(recipe.getLoveCount()+"");
            vh.post_love_number.setVisibility(View.VISIBLE);
            vh.post_love_number_ic.setVisibility(View.VISIBLE);
        }
        if(recipe.getComments().size()>0) {
            vh.post_comments_number.setText(recipe.getComments().size() + " Comment");
            vh.post_comments_number.setVisibility(View.VISIBLE);
        }
        if(recipe.getShareCount()>0) {
            vh.post_shares_number.setText(recipe.getShareCount() + " Share");
            vh.post_shares_number.setVisibility(View.VISIBLE);
            if(recipe.getComments().size()>0)
                vh.itemView.findViewById(R.id.share_card_dot).setVisibility(View.VISIBLE);
        }


        vh.post_comment_btn_lotti.setProgress(1f);
        vh.post_share_btn_lotti.setProgress(1f);

        vh.post_show_details_layout_btn_p_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vh.post_details_layout.getVisibility()==View.GONE) {
                    vh.post_details_layout.setVisibility(View.VISIBLE);
                    vh.post_show_details_layout_btn_ch_2_img.setImageResource(R.drawable.upbutton);
                    vh.post_show_details_layout_btn_ch_1_txt.setText("Show less");

                }else {
                    vh.post_details_layout.setVisibility(View.GONE);
                    vh.post_show_details_layout_btn_ch_2_img.setImageResource(R.drawable.downbutton);
                    vh.post_show_details_layout_btn_ch_1_txt.setText("Show more");

                }
            }
        });

        sharedPreferences = vh.post_love_btn.getContext().getSharedPreferences("userShared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        List<String> loveList = new ArrayList<>();
        String loveString = sharedPreferences.getString("love", null);
        if(loveString == null)
            loveList=new ArrayList<>();
        else{

            loveList = new Gson().fromJson(loveString, new TypeToken<List<String>>(){}.getType());
        }

        if(loveList.contains(recipe.getId()+","+recipe.getUserId())) {
            vh.post_love_txt.setTextColor(vh.itemView.getContext().getColor(R.color.colorTap));
            vh.post_love_btn.setTag("on");
            vh.post_love_btn_lotti.playAnimation();
        }


        List<String> finalLoveList = loveList;
        vh.post_love_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make love
                if(vh.post_love_btn.getTag().toString().equals("of")&&!vh.post_love_btn_lotti.isAnimating()){
                    vh.post_love_txt.setTextColor(vh.itemView.getContext().getColor(R.color.colorTap));
                    vh.post_love_btn.setTag("on");
                    vh.post_love_btn_lotti.playAnimation();
                    if(vh.post_love_number.getText().toString().isEmpty())
                        vh.post_love_number.setText("0");
                    vh.post_love_number.setText((Integer.parseInt(vh.post_love_number.getText().toString())+1)+"");
                    if(mOninteractionClickListener!=null)
                        mOninteractionClickListener.onlove(recipe,vh.post_love_number,vh.post_love_number_ic);

                    finalLoveList.add(recipe.getId()+","+recipe.getUserId());

                }else {
                    vh.post_love_txt.setTextColor(vh.itemView.getContext().getColor(R.color.transparentDark));
                    vh.post_love_btn.setTag("of");
                    vh.post_love_btn_lotti.pauseAnimation();
                    vh.post_love_btn_lotti.setProgress(0f);
                    vh.post_love_number.setText((Integer.parseInt(vh.post_love_number.getText().toString())-1)+"");
                    if(mOninteractionClickListener!=null)
                        mOninteractionClickListener.onDislove(recipe,vh.post_love_number,vh.post_love_number_ic);
                    for(int i = 0; i < finalLoveList.size(); i ++){
                        if(finalLoveList.get(i).equals(recipe.getId()+","+recipe.getUserId())) {
                            finalLoveList.remove(i);
                            break;
                        }
                    }

                }
                editor.putString("love", new Gson().toJson(finalLoveList));
                editor.commit();

            }
        });
        vh.post_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open comment sheet

                vh.post_comment_btn_lotti.playAnimation();
                mOnCommentClickListener.onClick(recipe);
            }
        });
        vh.post_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make share
                vh.post_share_btn_lotti.playAnimation();
                if(vh.post_shares_number.getText().toString().trim().equals(""))
                    vh.post_shares_number.setText("0 Share");
                vh.post_shares_number.setText((Integer.parseInt(vh.post_shares_number.getText().toString().split(" ")[0])+1)+" Share");
                vh.post_shares_number.setVisibility(View.VISIBLE);
                if(recipe.getComments().size()>0)
                    vh.itemView.findViewById(R.id.share_card_dot).setVisibility(View.VISIBLE);
                if(mOninteractionClickListener!=null)
                    mOninteractionClickListener.onshare(recipe,vh.post_image_1);
            }
        });
        vh.post_comment_share_love_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open comment sheet
                mOnCommentClickListener.onClick(recipe);
            }
        });
    }


    private void initImages(VH vh,Recipe recipe ) {
        List<String> imgUrls=recipe.getImgUrls();
        hideImages(vh);
        int size=imgUrls.size();
        if(size>=1){
            vh.post_images_frame.setVisibility(View.VISIBLE);
            vh.post_image_1.setVisibility(View.VISIBLE);
            Glide.with(vh.post_image_1.getContext()).load(recipe.getImgUrls().get(0))
                    .into(vh.post_image_1);
        }if(size>=2){
            vh.post_image_2.setVisibility(View.VISIBLE);
            Glide.with(vh.post_image_2.getContext()).load(recipe.getImgUrls().get(1))
                    .into(vh.post_image_2);
        }if(size>=3){
            vh.post_img_3_4_layout.setVisibility(View.VISIBLE);
            vh.post_image_3.setVisibility(View.VISIBLE);
            Glide.with(vh.post_image_3.getContext()).load(recipe.getImgUrls().get(2))
                    .into(vh.post_image_3);
        }if(size>=4){
            vh.post_img_4_layout.setVisibility(View.VISIBLE);
            vh.post_image_4.setVisibility(View.VISIBLE);
            Glide.with(vh.post_image_4.getContext()).load(recipe.getImgUrls().get(3))
                    .into(vh.post_image_4);
        }if(size>4){
            vh.post_more_images_btn.setVisibility(View.VISIBLE);
            vh.post_more_images_btn.setText("+"+(size-4));
        }

        vh.post_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onImageClick(recipe.getImgUrls(),0);
            }
        });
        vh.post_image_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onImageClick(recipe.getImgUrls(),1);
            }
        });
        vh.post_image_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onImageClick(recipe.getImgUrls(),2);
            }
        });
        vh.post_image_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onImageClick(recipe.getImgUrls(),3);
            }
        });
        vh.post_more_images_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onNumberClick(recipe);
            }
        });


    }

    OnImageClickListener mOnImageClickListener;

    public void setmOnImageClickListener(OnImageClickListener mOnImageClickListener) {
        this.mOnImageClickListener = mOnImageClickListener;
    }

    public void setuser(UserPost mUser) {
        user=mUser;
    }


    public interface OnImageClickListener{
        void onNumberClick(Recipe recipe);

        void onImageClick(List<String> imgUrls, int pos);
    }

    private void hideImages(VH vh) {
        vh.post_images_frame.setVisibility(View.GONE);
        vh.post_image_1.setVisibility(View.GONE);
        vh.post_image_2.setVisibility(View.GONE);
        vh.post_img_3_4_layout.setVisibility(View.GONE);
        vh.post_image_3.setVisibility(View.GONE);
        vh.post_img_4_layout.setVisibility(View.GONE);
        vh.post_image_4.setVisibility(View.GONE);
        vh.post_more_images_btn.setVisibility(View.GONE);
    }

    private void initCategoryList(VH vh, List<String> categories) {
        vh.post_categories_hScrollView.removeAllViews();
        vh.post_categories_linerLayout.removeAllViews();
        for(String cat:categories) {
            View cardItem = LayoutInflater.from(vh.itemView.getContext()).inflate(R.layout.post_list_item, null, false);
            TextView textView=cardItem.findViewById(R.id.contentText);
            textView.setText(cat);
            vh.post_categories_linerLayout.addView(cardItem);
        }
        vh.post_categories_hScrollView.addView(vh.post_categories_linerLayout);
    }

    private void initIngredientList(VH vh, List<String> ingredients) {
        vh.post_ing_ChipGroup.removeAllViews();
        for(String ing:ingredients) {
            View cardItem = LayoutInflater.from(vh.itemView.getContext()).inflate(R.layout.post_list_item, null, false);
            CardView cardView=cardItem.findViewById(R.id.post_list_item_card);
            cardView.setCardBackgroundColor(vh.itemView.getContext().getColor(R.color.colorAccent));
            TextView textView=cardItem.findViewById(R.id.contentText);
            textView.setText(ing);
            vh.post_ing_ChipGroup.addView(cardItem);
        }
    }

    private void initStepsList(VH vh, List<String> steps) {
        vh.post_steps_ChipGroup.removeAllViews();
        for(String st:steps) {
            View cardItem = LayoutInflater.from(vh.itemView.getContext()).inflate(R.layout.post_list_item, null, false);
            CardView cardView=cardItem.findViewById(R.id.post_list_item_card);
            cardView.setCardBackgroundColor(vh.itemView.getContext().getColor(R.color.color_blue_green));
            TextView textView=cardItem.findViewById(R.id.contentText);
            textView.setText(st);
            vh.post_steps_ChipGroup.addView(cardItem);
        }
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        public LottieAnimationView lotti_post_user_follow_btn;

        //user captoin
        public ImageView post_profile_img,post_user_nationality;
        public LottieAnimationView post_more_menu;
        public TextView post_username,post_from_time,post_user_follow_btn;
        public RatingBar post_rate;

        //post caption
        public TextView post_title,post_description,prepare_time;
        public TextView nationality;

        public CardView post_show_details_layout_btn_p_card;
        public ImageView post_show_details_layout_btn_ch_2_img;
        public TextView post_show_details_layout_btn_ch_1_txt;
        public LinearLayout post_details_layout;

        //category view group
        public HorizontalScrollView post_categories_hScrollView;
        public LinearLayout post_categories_linerLayout;
        //ingredients view group
        public ChipGroup post_ing_ChipGroup;
        //steps view group
        public ChipGroup post_steps_ChipGroup;

        //images caption
        public View post_images_frame;
        public ImageView post_image_1,post_image_2,post_image_3,post_image_4;
        public LinearLayout post_img_3_4_layout;
        public RelativeLayout post_img_4_layout;
        public Button post_more_images_btn;

        //interaction caption
        public TextView post_love_number,post_comments_number,post_shares_number;
        public CardView post_love_btn,post_comment_btn,post_share_btn;
        public TextView post_love_txt;
        public LottieAnimationView post_love_btn_lotti,post_comment_btn_lotti,post_share_btn_lotti;
        public LinearLayout post_comment_share_love_RelativeLayout;
        public ImageView post_love_number_ic;

        public VH(@NonNull View itemView) {
            super(itemView);

            post_rate=itemView.findViewById(R.id.post_rate);
            lotti_post_user_follow_btn=itemView.findViewById(R.id.lotti_post_user_follow_btn);

            post_profile_img=itemView.findViewById(R.id.post_profile_img);
            post_user_nationality=itemView.findViewById(R.id.post_user_nationality);
            post_user_follow_btn=itemView.findViewById(R.id.post_user_follow_btn);
            post_username=itemView.findViewById(R.id.post_username);
            post_from_time=itemView.findViewById(R.id.post_from_time);

            nationality=itemView.findViewById(R.id.post_nationality);
            post_description=itemView.findViewById(R.id.post_description);

            post_show_details_layout_btn_p_card=itemView.findViewById(R.id.post_show_details_layout_btn_p_card);
            post_show_details_layout_btn_ch_1_txt=itemView.findViewById(R.id.post_show_details_layout_btn_ch_1_txt);
            post_show_details_layout_btn_ch_2_img=itemView.findViewById(R.id.post_show_details_layout_btn_ch_2_img);

            post_details_layout=itemView.findViewById(R.id.post_details_layout);
            prepare_time=itemView.findViewById(R.id.prepare_time);

            post_categories_hScrollView=itemView.findViewById(R.id.post_categories_hScrollView);
            post_categories_linerLayout=itemView.findViewById(R.id.post_categories_linerLayout);
            post_ing_ChipGroup=itemView.findViewById(R.id.post_ing_ChipGroup);
            post_steps_ChipGroup=itemView.findViewById(R.id.post_steps_ChipGroup);

            post_images_frame=itemView.findViewById(R.id.post_images_frame);
            post_image_1=itemView.findViewById(R.id.post_image_1);
            post_image_2=itemView.findViewById(R.id.post_image_2);
            post_image_3=itemView.findViewById(R.id.post_image_3);
            post_image_4=itemView.findViewById(R.id.post_image_4);
            post_img_3_4_layout=itemView.findViewById(R.id.post_img_3_4_layout);
            post_img_4_layout=itemView.findViewById(R.id.post_img_4_layout);
            post_more_images_btn=itemView.findViewById(R.id.post_more_images_btn);

            post_love_number=itemView.findViewById(R.id.post_love_number);
            post_love_number_ic=itemView.findViewById(R.id.post_love_number_ic);
            post_comments_number=itemView.findViewById(R.id.post_comments_number);
            post_shares_number=itemView.findViewById(R.id.post_shares_number);
            post_love_btn=itemView.findViewById(R.id.post_love_btn);
            post_love_txt=itemView.findViewById(R.id.post_love_txt);
            post_love_btn_lotti=itemView.findViewById(R.id.post_love_btn_lotti);
            post_comment_btn=itemView.findViewById(R.id.post_comment_btn);
            post_comment_btn_lotti=itemView.findViewById(R.id.post_comment_btn_lotti);
            post_share_btn_lotti=itemView.findViewById(R.id.post_share_btn_lotti);
            post_share_btn=itemView.findViewById(R.id.post_share_btn);
            post_comment_share_love_RelativeLayout=itemView.findViewById(R.id.post_comment_share_love_RelativeLayout);

        }
    }

    OnCommentClickListener mOnCommentClickListener;

    public void setmOnCommentClickListener(OnCommentClickListener mOnCommentClickListener) {
        this.mOnCommentClickListener = mOnCommentClickListener;
    }

    interface OnCommentClickListener{
        void onClick(Recipe recipe);
    }

    OninteractionClickListener mOninteractionClickListener;

    public void setOninteractionClickListener(OninteractionClickListener mOninteractionClickListener) {
        this.mOninteractionClickListener = mOninteractionClickListener;
    }

    public interface OninteractionClickListener{
        void onshare(Recipe recipe,ImageView img);
        void onlove(Recipe recipe, TextView post_love_number, ImageView post_love_number_ic);
        void onDislove(Recipe recipe, TextView post_love_number, ImageView post_love_number_ic);
        void onfollow(Recipe recipe);
    }


    OnProfileClickListener mOnProfileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener mOnProfileClickListener) {
        this.mOnProfileClickListener = mOnProfileClickListener;
    }

    public interface OnProfileClickListener{
        void onClick(int userid);
    }

    OpenProfileListener openProfileListener;

    public void setOnOpenProfileListener(OpenProfileListener mOnProfileClickListener) {
        this.openProfileListener = mOnProfileClickListener;
    }

    public interface OpenProfileListener{
        void intentToAccount();
    }



}
