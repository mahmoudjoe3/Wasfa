package com.mahmoudjoe3.wasfa.ui.main.account;

import android.app.Application;

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

public class AccountViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recipe>> recipeMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<List<Recipe>> getRecipeMutableLiveData() {
        List<Recipe> recipes=new ArrayList<>();

        Recipe recipe = new Recipe(1,1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","eg",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,3,
                List.of("http://wallpoper.com/images/00/36/49/53/food-cookies_00364953.jpg")
        );
        recipe.setComments(
                List.of(
                        new Comment(1,"Jakwatre","https://i.pinimg.com/1200x/11/c7/35/11c7359cc1bf8d43011a58c0b9fe1ef2.jpg"
                                ,"this recipe is very good")
                        ,new Comment(1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"thanks bro")
                        ,new Comment(1,"mamdoh2","http://fuckingyoung.es/wp-content/uploads/2015/02/Billy-Reid-FW15-Backstage_fy1.jpg"
                                ,"bad smill")
                        ,new Comment(1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"Ok")
                )
        );
        recipes.add(recipe);

        recipes.add(new Recipe(2,1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","eg",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,12,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-25.jpg")
        ));
        recipes.add(new Recipe(3,1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","eg",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,12,
                List.of("https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg")
        ));
        recipes.add(new Recipe(4,1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","eg",
                System.currentTimeMillis()-100000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,12,
                List.of("https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg")
        ));
        recipe=new Recipe(5,1,"Mahmoud Youseef",null,"this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","eg",
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
        User user=new User(1,"MahmoudJoe3","123"
                ,"https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                , "your are the best of yourself", "eg");
        userLiveData.setValue(user);
        return userLiveData;
    }

    private InteractionRepo interactionRepo;
    private LiveData<List<Interaction>> interactionsLiveData;

    public AccountViewModel(Application application) {
        super(application);
        interactionRepo = new InteractionRepo(application);
        interactionsLiveData = interactionRepo.getInteractionsLiveData();
    }

    public void insertInteraction(Interaction interaction) {
        interactionRepo.insertInteraction(interaction);
    }
    private MutableLiveData<List<User>> userListLiveData=new MutableLiveData<>();
    public LiveData<List<User>> getUserListLiveData() {
        List<User> users=new ArrayList<>();
        User user=new User(1,"MahmoudJoe3","123"
                ,"https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                , "your are the best of yourself", "eg");
        users.add(user);
        user=new User(2,"Mahmoudmamdouh","123"
                ,"https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                , "expelliarmus", "eg");
        users.add(user);
        user=new User(3,"MahmoudEmad","123"
                ,"https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                , "god help us", "eg");
        users.add(user);
        user=new User(4,"NadaAhmed","123"
                ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                , "allways love yourself", "eg");
        users.add(user);
        user=new User(5,"MariamJemy","123"
                ,"https://harrypotterfanfiction.com/var/covers/340413.jpg?1595391755"
                , "hapiness could be found in the darkest times", "eg");
        users.add(user);
        user=new User(6,"hermoineGranger","123"
                ,"https://www.denofgeek.com/wp-content/uploads/2020/08/Harry-Potter-Streaming-Peacock.jpg?fit=2048%2C1152"
                , "it's leviosa", "eg");
        users.add(user);
        user=new User(7,"NemoMostafa","123"
                ,"https://static.wikia.nocookie.net/hpmor/images/5/58/Harry_James_Potter-Evans-Verres.jpg/revision/latest/top-crop/width/360/height/450?cb=20120504152651"
                , "don't give up", "eg");
        users.add(user);

        user=new User(8,"EmanMahmoud","123"
                ,"https://media2.s-nbcnews.com/j/newscms/2021_12/1694108/gg-refresh-harry-potter-bd-2x1-210326_ddae56ff064dd8e19652dd11f2658b1b.fit-760w.jpg"
                , "your are the best of yourself", "eg");
        users.add(user);

        user=new User(9,"MaiEmad","123"
                ,"https://cdn.vox-cdn.com/thumbor/Hhuc156xe_2o03_0yBr3KZR7-DY=/0x0:260x170/1200x800/filters:focal(110x65:150x105)/cdn.vox-cdn.com/uploads/chorus_image/image/69116416/595152458.0.jpg"
                ,"your are the best of yourself", "eg");
        users.add(user);
        user=new User(10,"Sawaf","123"
                ,"https://pyxis.nymag.com/v1/imgs/171/429/c95b07becc2bef532d9669b4824ea4386f-08-harry-potter.rsquare.w700.jpg"
                , "allways love yourself", "eg");
        users.add(user);

        userListLiveData.setValue(users);

        return userListLiveData;
    }


}
