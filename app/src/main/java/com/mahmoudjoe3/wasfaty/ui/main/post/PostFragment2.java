package com.mahmoudjoe3.wasfaty.ui.main.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.viewModel.PostSharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostFragment2 extends Fragment {

    private static PostFragment2 postFragment2;
    private PostSharedViewModel postSharedViewModel;
    private TextView nextTextView;
    private List<String> appCategoryList;
    private List<String> userCategoryList;
    private ChipGroup categoryChipGroup;
    private RecyclerView ingredientRecyclerView;
    private IngredientStepsAdapter ingredientStepsAdapter;
    private List<String> ingredientList;
    private ImageButton addIngredientImageButton, backImageButton, dropDownImageButton;
    private EditText ingredientEditText;
    private LinearLayout linearLayout_cat;
    private ScrollView chipScrollView;
    private Recipe recipe;
    private View view;

    public PostFragment2() {
    }

    public static PostFragment2 getInstance() {
        if(postFragment2 == null) {
            postFragment2 = new PostFragment2();
        }
        return postFragment2;
    }

    public static void removeFragment(){
        postFragment2=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_fragement2, container, false);

        init();
        initRecyclerView();


        nextTextView = view.findViewById(R.id.next_textView);
        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSet = setPostDate();
                if(isSet) {
                    getParentFragmentManager().beginTransaction().replace(
                            R.id.fragment_container, PostFragment3.getInstance()
                    ).commit();
                } else {
                    Toast.makeText(getActivity(), "Complete All the recipe data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addIngredientImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = ingredientEditText.getText().toString().trim();
                if(!ingredient.isEmpty()) {
                    ingredientList.add(ingredient);
                    ingredientStepsAdapter.notifyDataSetChanged();
                    ingredientEditText.setText("");

                    if(dropDownImageButton.getTag().equals("Vis")){
                        chipScrollView.setVisibility(View.GONE);
                        dropDownImageButton.setTag("Gone");
                    }

                }
            }
        });

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, PostFragment1.getInstance()
                ).commit();
            }
        });
        
        dropDownImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dropDownImageButton.getTag().equals("Vis")){
                    chipScrollView.setVisibility(View.GONE);
                    dropDownImageButton.setTag("Gone");
                }else {
                    chipScrollView.setVisibility(View.VISIBLE);
                    dropDownImageButton.setTag("Vis");
                }
            }
        });



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                int pos_dragged = dragged.getAdapterPosition();
                int pos_target = target.getAdapterPosition();
                Collections.swap(ingredientList, pos_dragged, pos_target);
                ingredientStepsAdapter.notifyItemMoved(pos_dragged, pos_target);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(ingredientRecyclerView);


        postSharedViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe r) {
                recipe = r;
                if(r.getIngredients() != null) {
                    ingredientStepsAdapter.setList(r.getIngredients());
                    ingredientList = r.getIngredients();
                }
                if(r.getCategories() != null) {
                    userCategoryList = r.getCategories();
                    for(int i = 0; i < categoryChipGroup.getChildCount(); i ++) {
                        Chip chip = (Chip) categoryChipGroup.getChildAt(i);
                        if (userCategoryList.contains(chip.getText().toString())) {
                            chip.setChecked(true);
                        }
                    }
                }
            }
        });

        return view;
    }

    private boolean setPostDate() {
        if(ingredientList.size() > 0 && userCategoryList.size() > 0) {
            recipe.setIngredients(ingredientList);
            recipe.setCategories(userCategoryList);
            postSharedViewModel.setRecipe(recipe);
            return true;
        }
        return false;
    }

    private void init() {
        postSharedViewModel = new ViewModelProvider(getActivity()).get(PostSharedViewModel.class);
        linearLayout_cat =view.findViewById(R.id.post_frag2_cat_layout);
        chipScrollView=view.findViewById(R.id.chip_scroll);
        addIngredientImageButton = view.findViewById(R.id.addIngredient_imageButton);
        backImageButton = view.findViewById(R.id.back_imageButton);
        ingredientEditText = view.findViewById(R.id.ingredient_EditText);
        categoryChipGroup = view.findViewById(R.id.category_chipGroup);
        dropDownImageButton = view.findViewById(R.id.dropdown_imageButton);
        recipe = new Recipe();
        getAppCategoryList();
        userCategoryList = new ArrayList<>();



        for(int i = 0; i < appCategoryList.size(); i ++) {
            final Chip chip = (Chip) LayoutInflater.from(getActivity()).inflate(R.layout.category_item, null, false);
            chip.setText(appCategoryList.get(i));
            categoryChipGroup.addView(chip);

            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chipText = chip.getText().toString();
                    if(userCategoryList.contains(chipText)) {
                        userCategoryList.remove(chipText);
                    } else {
                        userCategoryList.add(chipText);
                    }
                }
            });
        }
    }

    @NotNull
    private void getAppCategoryList() {
        appCategoryList =new ArrayList<>();
        appCategoryList.add("Pizza French");
        appCategoryList.add("peef");
        appCategoryList.add("fruits");
        appCategoryList.add("vegie");
        appCategoryList.add("sweet");
        appCategoryList.add("American");
        appCategoryList.add("Appetizer & Snacks");
        appCategoryList.add("Asian");
        appCategoryList.add("Barbecue & Grilling");
        appCategoryList.add("Beef");
        appCategoryList.add("Breakfast & Brunch");
        appCategoryList.add("Budget Cooking");
        appCategoryList.add("Cake");
        appCategoryList.add("Casseroles");
        appCategoryList.add("Chicken");
        appCategoryList.add("Chinese");
        appCategoryList.add("Cookie");
        appCategoryList.add("Dairy-Free");
        appCategoryList.add("Dessert");
        appCategoryList.add("Dinner");
        appCategoryList.add("English");
        appCategoryList.add("Entertaining");
        appCategoryList.add("Five Minutes Craft");
        appCategoryList.add("French");
        appCategoryList.add("German");
        appCategoryList.add("Gluten-Free");
        appCategoryList.add("Greek");
        appCategoryList.add("Hawaiian");
        appCategoryList.add("Healthy");
        appCategoryList.add("Indian");
        appCategoryList.add("Irish");
        appCategoryList.add("Italian");
        appCategoryList.add("Japanese");
        appCategoryList.add("Kid-Friendly");
        appCategoryList.add("Lamb");
        appCategoryList.add("Low-Calorie");
        appCategoryList.add("Low-Fat");
        appCategoryList.add("Main Dish");
        appCategoryList.add("Mexican");
        appCategoryList.add("Miscellaneous");
        appCategoryList.add("Paste & Noodle");
        appCategoryList.add("Pastries");
        appCategoryList.add("Salad");
        appCategoryList.add("Salmon");
        appCategoryList.add("Seafood");
        appCategoryList.add("Side Dish");
        appCategoryList.add("Slow Cooker");
        appCategoryList.add("Soups,Stews & Chili");
        appCategoryList.add("Southern");
        appCategoryList.add("Spanish");
        appCategoryList.add("Vegan");
        appCategoryList.add("Vegetarian");
        appCategoryList.add("Weight-Loss");
        appCategoryList.add("World Cuisine");
    }

    private void initRecyclerView() {
        ingredientRecyclerView = view.findViewById(R.id.ingredients_recyclerView);
        ingredientStepsAdapter = new IngredientStepsAdapter();
        ingredientList = new ArrayList<>();
        ingredientStepsAdapter.setList(ingredientList);
        ingredientRecyclerView.setAdapter(ingredientStepsAdapter);
        ingredientStepsAdapter.setOnItemClickListener(new IngredientStepsAdapter.onItemClickListener() {
            @Override
            public void onRemoveClick(int position) {
                ingredientStepsAdapter.removeAt(position);
            }
        });
    }
}
