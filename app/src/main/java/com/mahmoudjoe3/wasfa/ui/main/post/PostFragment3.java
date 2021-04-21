package com.mahmoudjoe3.wasfa.ui.main.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.snaptimepicker.SnapTimePickerDialog;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostFragment3 extends Fragment {

    private static PostFragment3 postFragment3;
    private PostSharedViewModel postSharedViewModel;
    private RecyclerView stepsRecyclerView;
    private IngredientStepsAdapter stepsAdapter;
    private List<String> stepsList;
    private View view;
    private ImageButton addStepImageButton, backImageButton;
    private EditText stepEditText;
    private TextView postTextView;
    private Recipe recipe;

    public PostFragment3() {
    }

    public static PostFragment3 getInstance() {
        if (postFragment3 == null) {
            postFragment3 = new PostFragment3();
        }
        return postFragment3;
    }
    public static void removeFragment(){
        postFragment3=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_fragement3, container, false);

        init();
        initRecyclerView();

        postTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnapTimePickerDialog dialog = new SnapTimePickerDialog.Builder()
                        .setTitle(R.string.Recipe_prepare_time)
                        .setThemeColor(R.color.colorPrimaryDark)
                        .setTitleColor(R.color.colorWhite)
                        .setPositiveButtonText(R.string.post)
                        .setNegativeButtonText(R.string.Back)
                        .setNegativeButtonColor(R.color.dark_grey)
                        .build();
                dialog.show(getActivity().getSupportFragmentManager(),SnapTimePickerDialog.TAG);
                dialog.setListener(new SnapTimePickerDialog.Listener() {
                    @Override
                    public void onTimePicked(int hour, int minute) {
                        if(!(hour==0&&minute==0)) {
                            recipe.setPrepareTime(hour + ":" + minute);
                            recipe.setPostTime(System.currentTimeMillis());
                            boolean isSet = setPostData();
                            if (isSet) {
                                Toast.makeText(getActivity(), "Post Uploud..", Toast.LENGTH_SHORT).show();

                                getParentFragmentManager().beginTransaction().replace(
                                        R.id.fragment_container, PostFragment1.getInstance()
                                ).commit();
                                //Todo send to api
                                Toast.makeText(getActivity(), "Post Uplouded successfully", Toast.LENGTH_SHORT).show();
                                removeData();
                                getActivity().onBackPressed();
                                
                            } else {
                                Toast.makeText(getActivity(), "Complete All the recipe data", Toast.LENGTH_SHORT).show();
                            }
                        }else  Toast.makeText(getActivity(), "Recipe prepare time Invalid", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        addStepImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String step = stepEditText.getText().toString().trim();
                if (!step.isEmpty()) {
                    stepsList.add(step);
                    stepsAdapter.notifyDataSetChanged();
                    stepEditText.setText("");
                }
            }
        });

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSet = setPostData();
                getParentFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, PostFragment2.getInstance()
                ).commit();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                int pos_dragged = dragged.getAdapterPosition();
                int pos_target = target.getAdapterPosition();
                Collections.swap(stepsList, pos_dragged, pos_target);
                stepsAdapter.notifyItemMoved(pos_dragged, pos_target);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(stepsRecyclerView);


        postSharedViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe r) {
                recipe = r;
                if(recipe.getSteps() != null){
                    stepsAdapter.setList(recipe.getSteps());
                    stepsList = recipe.getSteps();
                }
            }
        });
        return view;
    }

    private void removeData() {
        recipe.setDescription("");
        recipe.setImgUrls(new ArrayList<>());
        recipe.setCategories(new ArrayList<>());
        recipe.setIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        postSharedViewModel.setRecipe(recipe);
    }

    private boolean setPostData() {
        if(stepsList.size() > 0) {
            recipe.setSteps(stepsList);
            postSharedViewModel.setRecipe(recipe);
            return true;
        }
        return false;
    }

    private void init() {
        postSharedViewModel = new ViewModelProvider(getActivity()).get(PostSharedViewModel.class);
        addStepImageButton = view.findViewById(R.id.addStep_imageButton);
        backImageButton = view.findViewById(R.id.back_imageButton);
        stepEditText = view.findViewById(R.id.step_EditText);
        postTextView = view.findViewById(R.id.post_textView);
        recipe = new Recipe();
    }

    private void initRecyclerView() {
        stepsRecyclerView = view.findViewById(R.id.steps_recyclerView);
        stepsAdapter = new IngredientStepsAdapter();
        stepsList = new ArrayList<>();
        stepsAdapter.setList(stepsList);
        stepsRecyclerView.setAdapter(stepsAdapter);

        stepsAdapter.setOnItemClickListener(new IngredientStepsAdapter.onItemClickListener() {
            @Override
            public void onRemoveClick(int position) {
                stepsAdapter.removeAt(position);
            }
        });
    }
}
