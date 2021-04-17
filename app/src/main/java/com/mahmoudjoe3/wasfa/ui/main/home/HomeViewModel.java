package com.mahmoudjoe3.wasfa.ui.main.home;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.repo.FirebaseAuthRepo;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private FirebaseAuthRepo authRepo;
    private MutableLiveData<List<Recipe>> recipeMutableLiveData=new MutableLiveData<>();

    public LiveData<List<Recipe>> getRecipeMutableLiveData() {
        List<Recipe> recipes=new ArrayList<>();
//        Recipe(int userId, String userName, String userProfileThumbnail, String title,
//                String description, long postTime, String prepareTime, List<String> categories,
//                List<String> ingredients, List<String> steps, int numberOfLike, int numberOfComments,
//        int numberOfShare, List<String> imgUrls)

        recipes.add(new Recipe(1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,23,12,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg",
                        "https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-25.jpg",
                        "http://wallpoper.com/images/00/36/49/53/food-cookies_00364953.jpg")
        ));
        recipes.add(new Recipe(1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                        "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,23,12,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg",
                        "https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-25.jpg")
                ));
        recipes.add(new Recipe(1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,23,12,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg",
                        "https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg")
        ));
        recipes.add(new Recipe(1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,23,12,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg")
        ));
        recipes.add(new Recipe(1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,23,12,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg")
        ));
        recipeMutableLiveData.setValue(recipes);
        return recipeMutableLiveData;
    }

    public HomeViewModel() {
        this.authRepo = FirebaseAuthRepo.getInstance();
    }

    public Intent getAuthIntent() {
        return authRepo.getAuthIntent();
    }

    public void authStateListener(FirebaseAuthRepo.OnAuthStateListener onAuthStateListener) {
        authRepo.authStateListener(onAuthStateListener);
    }

    public void SignOut(Context context) {
        authRepo.SignOut(context);
    }
}
