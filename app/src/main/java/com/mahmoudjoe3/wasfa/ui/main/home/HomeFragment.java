package com.mahmoudjoe3.wasfa.ui.main.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.ChipGroup;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.ui.main.SharedViewModel;
import com.mahmoudjoe3.wasfa.ui.main.home.comment.CommentAdapter;
import com.mahmoudjoe3.wasfa.ui.main.viewImage.ViewImageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mahmoudjoe3.wasfa.logic.MyLogic.getTimeFrom;


public class HomeFragment extends Fragment {
    private static final String TAG = "homey";
    HomeViewModel viewModel;
    User user;
    SharedViewModel sharedViewModel;
    @BindView(R.id.shimmer_recycler_view)
    RecyclerView recyclerView;
    private int REC_AUTH_CODE = 1;

    RecipePostAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        adapter = new RecipePostAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipes(recipes);
            }
        });
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user1) {
                user = user1;
            }
        });

        adapter.setmOnImageClickListener(new RecipePostAdapter.OnImageClickListener() {
            @Override
            public void onNumberClick(Recipe recipe) {
                init_post_details_sheet_dialog(recipe);
            }

            @Override
            public void onImageClick(List<String> imgUrls, int pos) {
                showImage(imgUrls, pos);
            }
        });

        //comment click listener
        adapter.setmOnCommentClickListener(new RecipePostAdapter.OnCommentClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                openCommentSheet(recipe);
            }
        });


        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //recived data
            }
        });

    }

    private void init_post_details_sheet_dialog(Recipe recipe) {

        BottomSheetDialog sheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.post_details_layout,
                (LinearLayout) getActivity().findViewById(R.id.post_details_card));

        initUserCaption(sheetView, recipe);
        initPostCaption(sheetView, recipe);

        LinearLayout layout = sheetView.findViewById(R.id.post_details_images_frame);
        for (String img : recipe.getImgUrls()) {
            ImageView imageView = new ImageView(getActivity());
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

    private void initUserCaption(View sheetView, Recipe recipe) {
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
            }
        });
        post_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open prifile
            }
        });
        post_user_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //follow user
                lotti_post_user_follow_btn.playAnimation();
                post_user_follow_btn.setVisibility(View.GONE);

            }
        });
    }

    private void initPostCaption(View sheetView, Recipe recipe) {

        LottieAnimationView post_more_menu;
        post_more_menu = sheetView.findViewById(R.id.post_more_menu);

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
            View cardItem = LayoutInflater.from(getActivity()).inflate(R.layout.post_list_item, null, false);
            TextView textView = cardItem.findViewById(R.id.contentText);
            textView.setText(cat);
            post_categories_linerLayout.addView(cardItem);
        }
        post_categories_hScrollView.addView(post_categories_linerLayout);

        //init IngredientList
        post_ing_ChipGroup.removeAllViews();
        for (String ing : recipe.getIngredients()) {
            View cardItem = LayoutInflater.from(getActivity()).inflate(R.layout.post_list_item, null, false);
            CardView cardView = cardItem.findViewById(R.id.post_list_item_card);
            cardView.setCardBackgroundColor(getActivity().getColor(R.color.colorAccent));
            TextView textView = cardItem.findViewById(R.id.contentText);
            textView.setText(ing);
            post_ing_ChipGroup.addView(cardItem);
        }

        //init StepsList
        post_steps_ChipGroup.removeAllViews();
        for (String st : recipe.getSteps()) {
            View cardItem = LayoutInflater.from(getActivity()).inflate(R.layout.post_list_item, null, false);
            CardView cardView = cardItem.findViewById(R.id.post_list_item_card);
            cardView.setCardBackgroundColor(getActivity().getColor(R.color.color_blue_green));
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
        post_more_menu.setProgress(0f);

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

        post_more_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo open post Menu
                post_more_menu.playAnimation();
            }
        });
        post_love_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo make love
                if(post_love_btn.getTag().toString().equals("of")&&!post_love_btn_lotti.isAnimating()){
                    post_love_txt.setTextColor(getActivity().getColor(R.color.colorTap));
                    post_love_btn.setTag("on");
                    post_love_btn_lotti.playAnimation();
                    post_love_number.setText((Integer.parseInt(post_love_number.getText().toString())+1)+"");
                }else {
                    post_love_txt.setTextColor(getActivity().getColor(R.color.transparentDark));
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
                openCommentSheet(recipe);
            }
        });
        post_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo make share
                post_share_btn_lotti.playAnimation();
            }
        });
        post_comment_share_love_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo open comment sheet
                post_comment_btn_lotti.playAnimation();
                openCommentSheet(recipe);
            }
        });
    }

    public void showKeyboard(final EditText ettext) {
        ettext.requestFocus();
        ettext.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext, 0);
                               }
                           }
                , 200);
    }

    private void openCommentSheet(Recipe recipe) {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.post_comment_sheet_layout,
                (LinearLayout) getActivity().findViewById(R.id.post_comment_sheet_card));

        TextView commentNum = sheetView.findViewById(R.id.comment_sheet_num_comments);
        ImageButton close = sheetView.findViewById(R.id.comment_sheet_close);
        RecyclerView commentRecyclerView = sheetView.findViewById(R.id.comment_sheet_recycle_view);
        TextView showAddComment = sheetView.findViewById(R.id.comment_sheet_show_addComment_sheet);

        commentNum.setText(recipe.getComments().size()+" comments");

        CommentAdapter commentAdapter = new CommentAdapter();
        commentRecyclerView.setAdapter(commentAdapter);

        commentAdapter.setCommentList(recipe.getComments());

        commentAdapter.setmOnLoveListenner(new CommentAdapter.OnLoveCommentListener() {
            @Override
            public void onLove(Comment comment) {
                //todo increase comment love count

            }

            @Override
            public void onDisLove(Comment comment) {
                //todo decraese comment love count

            }
        });

        commentAdapter.setOnOpenProfileListenner(new CommentAdapter.OnOpenProfileListenner() {
            @Override
            public void onClick(int userid) {
                //todo open user profile whose comment

            }
        });

        showAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show comment sheet
                BottomSheetDialog sh = new BottomSheetDialog(getActivity());
                View shView = LayoutInflater.from(getActivity()).inflate(R.layout.post_add_comment_txt_layout
                        , (LinearLayout) getActivity().findViewById(R.id.post_add_comment_text_layout_inner));

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
                        addComment(new Comment(recipe.getId(), user.getName(), user.getImageUrl(), isCreator, commentTxt.getText().toString()));
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

    private void addComment(Comment comment) {
        //todo insert to database
        List<Recipe> recipeList = viewModel.getRecipeMutableLiveData().getValue();
        for (Recipe recipe1 : recipeList) {
            if (recipe1.getId() == comment.getRecipeId()) {
                List<Comment> comments = new ArrayList<>(recipe1.getComments());
                comments.add(comment);
                recipe1.setComments(comments);
                break;
            }
        }
        viewModel.getRecipeMutableLiveData().setValue(recipeList);
    }

    private void showImage(List<String> imgUrls, int pos) {
        Intent intent = new Intent(getActivity(), ViewImageActivity.class);
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS, new ArrayList<>(imgUrls));
        intent.putExtra(ViewImageActivity.IMG_POS, pos);
        startActivity(intent);
    }


}