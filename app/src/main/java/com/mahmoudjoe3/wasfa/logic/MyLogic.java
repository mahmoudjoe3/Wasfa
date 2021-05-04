package com.mahmoudjoe3.wasfa.logic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.ChipGroup;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.ui.main.home.RecipePostAdapter;
import com.mahmoudjoe3.wasfa.ui.main.home.comment.CommentAdapter;
import com.mahmoudjoe3.wasfa.ui.main.interaction.InteractionsRecyclerAdapter;
import com.mahmoudjoe3.wasfa.ui.main.interaction.interactionsViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyLogic {

    public static boolean haveNetworkConnection(Context application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null &&
                    (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        // for Image send ignore URI error
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Uri bmpUri=null;
        try {
            File file = new File(inContext.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            inImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static String getTimeFrom(long PostTimeInMileSec){
        long postFromInMile=System.currentTimeMillis()-PostTimeInMileSec;
        System.out.println("current "+System.currentTimeMillis());
        System.out.println("given "+PostTimeInMileSec);
        int postFromInSec= (int) (postFromInMile/1000);
        String time="";
        if(postFromInSec<60){
            time="Now";
        }else if(postFromInSec<(60*60)){
            time= (postFromInSec/60) +" Min";
        }else if(postFromInSec<(60*60*24)){
            time= (postFromInSec/(60*60)) +" Hour";
        } else if(postFromInSec<(60*60*24*7)){
            time= (postFromInSec/(60*60*24)) +" Day";
        }else if(postFromInSec<(60*60*24*30)){
            time= (postFromInSec/(60*60*24*7)) +" Week";
        }else if(postFromInSec<(60*60*24*30*12)){
            time= (postFromInSec/(60*60*24*30)) +" Month";
        }else {
            time= (postFromInSec/(60*60*24*30*12)) +" Year";
        }
        return time;
    }


    public static void init_post_details_sheet_dialog(Activity context, Recipe recipe,User user) {

        BottomSheetDialog sheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        View sheetView = LayoutInflater.from(context).inflate(R.layout.post_details_layout,
                (LinearLayout) context.findViewById(R.id.post_details_card));

        initUserCaption(sheetView, recipe);
        initPostCaption(sheetView, recipe,context,user);

        LinearLayout layout = sheetView.findViewById(R.id.post_details_images_frame);
        for (String img : recipe.getImgUrls()) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 18, 0, 0);
            imageView.setLayoutParams(layoutParams);
            imageView.setAdjustViewBounds(true);
            Picasso.get().load(img).into(imageView);
            layout.addView(imageView);
        }

        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
    }

    private static void initUserCaption(View sheetView, Recipe recipe) {
        //user captoin
        LottieAnimationView lotti_post_user_follow_btn;
        ImageView post_profile_img, post_user_nationality;
        TextView post_username, post_from_time, post_user_follow_btn;


        lotti_post_user_follow_btn = sheetView.findViewById(R.id.lotti_post_user_follow_btn);
        post_profile_img = sheetView.findViewById(R.id.post_profile_img);
        post_user_nationality = sheetView.findViewById(R.id.post_user_nationality);
        post_user_follow_btn = sheetView.findViewById(R.id.post_user_follow_btn);
        post_username = sheetView.findViewById(R.id.post_username);
        post_from_time = sheetView.findViewById(R.id.post_from_time);

        //user object
        post_from_time.setText(getTimeFrom(recipe.getPostTime()));
        post_username.setText(recipe.getUserName());
//        Picasso.get().load(recipe.getUserProfileThumbnail())
//                .into(vh.post_profile_img);

        post_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open prifile
                if(mOnProfileClickListener1!=null) {
                    mOnProfileClickListener1.onClick(recipe.getUserId());
                }
            }
        });
        post_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open prifile
                if(mOnProfileClickListener1!=null) {
                    mOnProfileClickListener1.onClick(recipe.getUserId());
                }
            }
        });
        post_user_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //follow user
                lotti_post_user_follow_btn.playAnimation();
                post_user_follow_btn.setVisibility(View.GONE);
                if(mOninteractionClickListener1!=null){
                    mOninteractionClickListener1.onfollow(recipe);
                }

            }
        });
    }

    private static void initPostCaption(View sheetView, Recipe recipe,Activity context,User user) {

        LottieAnimationView post_more_menu;

        //post caption
        TextView post_title, post_description, prepare_time;
        ImageView nationality;
        CardView post_show_details_layout_btn_p_card;
        ImageView post_show_details_layout_btn_ch_2_img;
        TextView post_show_details_layout_btn_ch_1_txt;
        LinearLayout post_details_layout;

        //category view group
        HorizontalScrollView post_categories_hScrollView;
        LinearLayout post_categories_linerLayout;
        //ingredients view group
        ChipGroup post_ing_ChipGroup;
        //steps view group
        ChipGroup post_steps_ChipGroup;

        //interaction caption
        TextView post_love_number, post_comments_number, post_shares_number;
        CardView post_love_btn, post_comment_btn, post_share_btn;
        TextView post_love_txt;
        LottieAnimationView post_love_btn_lotti, post_comment_btn_lotti, post_share_btn_lotti;
        LinearLayout post_comment_share_love_RelativeLayout;

        post_title = sheetView.findViewById(R.id.post_title);
        //nationality=sheetView.findViewById(R.id.post_nationality);
        post_description = sheetView.findViewById(R.id.post_description);

        post_show_details_layout_btn_p_card = sheetView.findViewById(R.id.post_show_details_layout_btn_p_card);
        post_show_details_layout_btn_ch_1_txt = sheetView.findViewById(R.id.post_show_details_layout_btn_ch_1_txt);
        post_show_details_layout_btn_ch_2_img = sheetView.findViewById(R.id.post_show_details_layout_btn_ch_2_img);

        post_details_layout = sheetView.findViewById(R.id.post_details_layout);
        prepare_time = sheetView.findViewById(R.id.prepare_time);

        post_categories_hScrollView = sheetView.findViewById(R.id.post_categories_hScrollView);
        post_categories_linerLayout = sheetView.findViewById(R.id.post_categories_linerLayout);
        post_ing_ChipGroup = sheetView.findViewById(R.id.post_ing_ChipGroup);
        post_steps_ChipGroup = sheetView.findViewById(R.id.post_steps_ChipGroup);

        post_love_number = sheetView.findViewById(R.id.post_love_number);
        post_comments_number = sheetView.findViewById(R.id.post_comments_number);
        post_shares_number = sheetView.findViewById(R.id.post_shares_number);
        post_love_btn = sheetView.findViewById(R.id.post_love_btn);
        post_love_txt = sheetView.findViewById(R.id.post_love_txt);
        post_love_btn_lotti = sheetView.findViewById(R.id.post_love_btn_lotti);
        post_comment_btn = sheetView.findViewById(R.id.post_comment_btn);
        post_comment_btn_lotti = sheetView.findViewById(R.id.post_comment_btn_lotti);
        post_share_btn_lotti = sheetView.findViewById(R.id.post_share_btn_lotti);
        post_share_btn = sheetView.findViewById(R.id.post_share_btn);
        post_comment_share_love_RelativeLayout = sheetView.findViewById(R.id.post_comment_share_love_RelativeLayout);


        post_title.setText(recipe.getTitle());
        post_description.setText(recipe.getDescription());
        prepare_time.setText(recipe.getPrepareTime());

        //inti cat list
        post_categories_hScrollView.removeAllViews();
        post_categories_linerLayout.removeAllViews();
        for (String cat : recipe.getCategories()) {
            View cardItem = LayoutInflater.from(context).inflate(R.layout.post_list_item, null, false);
            TextView textView = cardItem.findViewById(R.id.contentText);
            textView.setText(cat);
            post_categories_linerLayout.addView(cardItem);
        }
        post_categories_hScrollView.addView(post_categories_linerLayout);

        //init IngredientList
        post_ing_ChipGroup.removeAllViews();
        for (String ing : recipe.getIngredients()) {
            View cardItem = LayoutInflater.from(context).inflate(R.layout.post_list_item, null, false);
            CardView cardView = cardItem.findViewById(R.id.post_list_item_card);
            cardView.setCardBackgroundColor(context.getColor(R.color.colorAccent));
            TextView textView = cardItem.findViewById(R.id.contentText);
            textView.setText(ing);
            post_ing_ChipGroup.addView(cardItem);
        }

        //init StepsList
        post_steps_ChipGroup.removeAllViews();
        for (String st : recipe.getSteps()) {
            View cardItem = LayoutInflater.from(context).inflate(R.layout.post_list_item, null, false);
            CardView cardView = cardItem.findViewById(R.id.post_list_item_card);
            cardView.setCardBackgroundColor(context.getColor(R.color.color_blue_green));
            TextView textView = cardItem.findViewById(R.id.contentText);
            textView.setText(st);
            post_steps_ChipGroup.addView(cardItem);
        }

        post_love_number.setText(recipe.getNumberOfLike() +"");
        if(recipe.getComments().size()>0) {
            post_comments_number.setText(recipe.getComments().size() + " Comment");
            post_comments_number.setVisibility(View.VISIBLE);
        }
        if(recipe.getNumberOfShare()>0) {
            post_shares_number.setText(recipe.getNumberOfShare() + " Share");
            post_shares_number.setVisibility(View.VISIBLE);
            if(recipe.getComments().size()>0)
                sheetView.findViewById(R.id.share_card_dot).setVisibility(View.VISIBLE);        }

        post_comment_btn_lotti.setProgress(1f);
        post_share_btn_lotti.setProgress(2f);

        post_show_details_layout_btn_p_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post_details_layout.getVisibility() == View.GONE) {
                    post_details_layout.setVisibility(View.VISIBLE);
                    post_show_details_layout_btn_ch_2_img.setImageResource(R.drawable.upbutton);
                    post_show_details_layout_btn_ch_1_txt.setText("Show less");
                } else {
                    post_details_layout.setVisibility(View.GONE);
                    post_show_details_layout_btn_ch_2_img.setImageResource(R.drawable.downbutton);
                    post_show_details_layout_btn_ch_1_txt.setText("Show more");

                }
            }
        });

        post_love_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo make love
                if(post_love_btn.getTag().toString().equals("of")&&!post_love_btn_lotti.isAnimating()){
                    if(mOninteractionClickListener1!=null){
                        mOninteractionClickListener1.onlove(recipe);
                    }
                    post_love_txt.setTextColor(context.getColor(R.color.colorTap));
                    post_love_btn.setTag("on");
                    post_love_btn_lotti.playAnimation();
                    post_love_number.setText((Integer.parseInt(post_love_number.getText().toString())+1)+"");
                }else {
                    if(mOninteractionClickListener1!=null){
                        mOninteractionClickListener1.onDislove(recipe);
                    }
                    post_love_txt.setTextColor(context.getColor(R.color.transparentDark));
                    post_love_btn.setTag("of");
                    post_love_btn_lotti.pauseAnimation();
                    post_love_btn_lotti.setProgress(0f);
                    post_love_number.setText((Integer.parseInt(post_love_number.getText().toString())-1)+"");

                }
            }
        });
        post_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo open comment sheet
                post_comment_btn_lotti.playAnimation();
                openCommentSheet(recipe,context,user);
            }
        });
        post_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo make share
                post_share_btn_lotti.playAnimation();
                if(mOninteractionClickListener1!=null){
                    mOninteractionClickListener1.onshare(recipe);
                }
            }
        });
        post_comment_share_love_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo open comment sheet
                post_comment_btn_lotti.playAnimation();
                openCommentSheet(recipe,context,user);
            }
        });
    }


    private static void showKeyboard(final EditText ettext) {
        ettext.requestFocus();
        ettext.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   InputMethodManager keyboard = (InputMethodManager) ettext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext, 0);
                               }
                           }
                , 200);
    }

    public static void openCommentSheet(Recipe recipe, Activity activity, User user) {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(activity, R.style.BottomSheetDialogTheme);
        View sheetView = LayoutInflater.from(activity).inflate(R.layout.post_comment_sheet_layout,
                (LinearLayout) activity.findViewById(R.id.post_comment_sheet_card));

        TextView commentNum = sheetView.findViewById(R.id.comment_sheet_num_comments);
        ImageButton close = sheetView.findViewById(R.id.comment_sheet_close);
        RecyclerView commentRecyclerView = sheetView.findViewById(R.id.comment_sheet_recycle_view);
        TextView showAddComment = sheetView.findViewById(R.id.comment_sheet_show_addComment_sheet);

        commentNum.setText(recipe.getComments().size()+" comments");

        CommentAdapter commentAdapter = new CommentAdapter();
        commentRecyclerView.setAdapter(commentAdapter);

        commentAdapter.setCommentList(recipe.getComments());



        commentAdapter.setOnOpenProfileListenner(new CommentAdapter.OnOpenProfileListenner() {
            @Override
            public void onClick(int userid) {
                //todo open user profile whose comment
                mOnProfileClickListener1.onClick(userid);

            }
        });

        showAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show comment sheet
                BottomSheetDialog sh = new BottomSheetDialog(activity);
                View shView = LayoutInflater.from(activity).inflate(R.layout.post_add_comment_txt_layout
                        , (LinearLayout) activity.findViewById(R.id.post_add_comment_text_layout_inner));

                EditText commentTxt = shView.findViewById(R.id.comment_sheet_add_comment_txt);
                LottieAnimationView sendComment = shView.findViewById(R.id.comment_sheet_add_comment_btn);
                showKeyboard(commentTxt);
                //listener
                commentTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            sendComment.setProgress(0.33f);
                            sendComment.setEnabled(true);
                        } else {
                            sendComment.setProgress(1f);
                            sendComment.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                sendComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo send comment to database
                        boolean isCreator = (user.getId() == recipe.getUserId());
                        Comment comment=new Comment(recipe.getId(), user.getName(), user.getImageUrl(), commentTxt.getText().toString());
                        comment.setCreator(isCreator);
                        if(mOnProfileClickListener1!=null){
                            mOnProfileClickListener1.onAddComment(comment);
                        }
                        sendComment.setMinAndMaxProgress(0.33f, 1f);
                        sendComment.playAnimation();
                        //sendComment.setProgress(0.6f);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String commentStr = commentTxt.getText().toString();
                                commentTxt.setText("");
                                sh.dismiss();
                            }
                        }, 900);
                    }
                });

                sh.setContentView(shView);
                sh.show();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });

        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
    }

    static OnProfileClickListener mOnProfileClickListener1;

    public static void setOnProfileClickListener(OnProfileClickListener mOnProfileClickListener) {
        mOnProfileClickListener1 = mOnProfileClickListener;
    }

    public interface OnProfileClickListener{
        void onClick(int userid);
        void onAddComment(Comment comment);
    }

    static OninteractionClickListener mOninteractionClickListener1;

    public static void setOninteractionClickListener(OninteractionClickListener mOninteractionClickListener) {
        mOninteractionClickListener1 = mOninteractionClickListener;
    }

    public interface OninteractionClickListener{
        void onshare(Recipe recipe);
        void onlove(Recipe recipe);
        void onDislove(Recipe recipe);
        void onfollow(Recipe recipe);
    }

}
