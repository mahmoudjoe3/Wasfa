package com.mahmoudjoe3.wasfa.ui.main.home;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.repo.InteractionRepo;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<Recipe>> recipeMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<List<Recipe>> getRecipeMutableLiveData() {
        List<Recipe> recipes=new ArrayList<>();

        Recipe recipe = new Recipe(1,1,"Jakwartin","https://i.pinimg.com/1200x/11/c7/35/11c7359cc1bf8d43011a58c0b9fe1ef2.jpg"
                ,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,3,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg",
                        "https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-25.jpg",
                        "http://wallpoper.com/images/00/36/49/53/food-cookies_00364953.jpg")
        );
        recipe.setComments(
                List.of(
                        new Comment(1,"Jakwatre","https://i.pinimg.com/1200x/11/c7/35/11c7359cc1bf8d43011a58c0b9fe1ef2.jpg"
                                ,false,"this recipe is very good")
                        ,new Comment(1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,true,"thanks bro")
                        ,new Comment(1,"mamdoh2","http://fuckingyoung.es/wp-content/uploads/2015/02/Billy-Reid-FW15-Backstage_fy1.jpg"
                                ,false,"bad smill")
                        ,new Comment(1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,true,"Ok")
                )
        );
        recipes.add(recipe);

        recipes.add(new Recipe(2,1,"mahmoudjoe","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4","this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                        "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,12,
                List.of("https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-25.jpg")
                ));
        recipes.add(new Recipe(3,1,"ebrahim","http://fuckingyoung.es/wp-content/uploads/2015/02/Billy-Reid-FW15-Backstage_fy1.jpg","this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,12,
                List.of("https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg",
                        "https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg")
        ));
        recipes.add(new Recipe(4,1,"Mahmoud Youseef","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4","this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,12,
                List.of("http://wallpoper.com/images/00/36/49/53/food-cookies_00364953.jpg"
                        ,"https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg")
        ));
        recipe=new Recipe(5,1,"Mahmoud Youseef","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4","this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                3,0,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg")
        );
        recipes.add(recipe);
        recipeMutableLiveData.setValue(recipes);
        return recipeMutableLiveData;
    }

    private MutableLiveData<User> userLiveData=new MutableLiveData<>();
    public LiveData<User> getUserLiveData() {
        User user=new User(1,"MahmoudJoe3"
                ,"https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                , "your are the best of yourself", "eg");
        userLiveData.setValue(user);
        return userLiveData;
    }


    public HomeViewModel(Application application) {
        super(application);
        interactionRepo = InteractionRepo.getInstance(application);
        interactionsLiveData = interactionRepo.getInteractionsLiveData();
    }

    private InteractionRepo interactionRepo;
    private LiveData<List<Interaction>> interactionsLiveData;

    public void insertInteraction(Interaction interaction) {
        interactionRepo.insertInteraction(interaction);
    }

    public void deleteInteraction(Interaction interaction) {
        interactionRepo.deleteInteraction(interaction);
    }

}
