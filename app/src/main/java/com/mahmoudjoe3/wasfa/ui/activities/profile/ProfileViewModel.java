package com.mahmoudjoe3.wasfa.ui.activities.profile;

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

public class ProfileViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recipe>> recipeMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<List<Recipe>> getRecipeMutableLiveData() {
        List<Recipe> recipes=new ArrayList<>();

        Recipe recipe = new Recipe(1,1,"MahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                ,"Apple Pie",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","french",
                System.currentTimeMillis()-10000,"40 Min",List.of("dessert","vegiterian","well-cocked"),
                List.of("apples","butter","flour","eggs","Soda","vinegar","ice cream","cinnamon"),
                List.of("In the bottom of large mixing bowl, add the flour . Add the  butter, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the apples","add ice cteam"),
                203,3,
                List.of("https://adminassets.devops.arabiaweather.com/sites/default/files/field/image/arabiaweatehr2324.jpg"

                )
        );
        recipe.setComments(
                List.of(
                        new Comment(3,2,"Mahmoud mamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"this recipe is very good")
                        ,new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"thanks bro")
                        ,new Comment(2,3,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"bad smell")
                        ,new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"Ok")
                )
        );
        recipes.add(recipe);

        recipe=new Recipe(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1","this is title about the recipe that i make ",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","syria",
                System.currentTimeMillis()-10000,"40 Min",List.of("meets","vegiterian","fish","well-cocked","small meals"),
                List.of("meets","fat","salad","parsley","Spices Kofta","Onions","garlic","latency","vinegar"),
                List.of("In the bottom of large mixing bowl, add the shawarma spices. Add the olive oil, " +
                                "vinegar, and zest and juice of one lemon. Using a spoon, mix to combine.",
                        "Add the olive oil, vinegar. Using a spoon, mix to combine.","add the shawarma spices.","add salad"),
                203,12,
                List.of("https://www.englishclub.com/images/vocabulary/food/italian/italian-food-1024.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-9.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-2.jpg",
                        "https://weneedfun.com/wp-content/uploads/2015/10/Beautiful-Food-Photos-25.jpg")
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing")
                        ,new Comment(2,2,"Mahmoud mamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"i'm glad you like it")

                        ,new Comment(3,3,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"bad smell")
                        ,new Comment(4,4,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"good job")
                )
        );
        recipes.add(recipe);
        recipe=new Recipe(3,3,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg","Pizza Recipe ",
                "this recipe does not take time ","italian",
                System.currentTimeMillis()-50000,"30 Min",List.of("meets","vegiterian","well-cocked","small meals"),
                List.of("meets","fat","machroom","cheese","Spices ","Onions","garlic","sauce","vinegar","flour","oliv oil"),
                List.of("In the bottom of large mixing bowl, add the spices and the floor. Add the olive oil, " +
                        "garlic, and zest and juice of one lemon. Using a spoon, mix to combine.","add sauce"),203,12,
                List.of("https://www.glutenfreepalate.com/wp-content/uploads/2018/08/Gluten-Free-Pizza-3.2-480x360.jpg",
                        "https://www.glutenfreepalate.com/wp-content/uploads/2018/08/Gluten-Free-Pizza-1.2.jpg",
                        "https://i.pinimg.com/originals/dc/e2/97/dce297f1f308d8efc128de4925e4597e.jpg"
                )
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing")
                        ,new Comment(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"great pizza")

                        ,new Comment(3,1,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"thanks guys")
                        ,new Comment(4,5,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"good job")
                )
        );

        recipes.add(recipe);


        recipes.add(recipe);
        recipe=new Recipe(4,4,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU","Pizza Recipe ",
                "grap leaves ","turkish",
                System.currentTimeMillis()-300000,"50 Min",List.of("meats","vegiterian","well-cocked","small meals","rice"),
                List.of("meats","fat","parsley","cheese","Spices ","Onions","garlic","grap leaves","lemon juice","olive oil"),
                List.of("mix the meat , rice, parsley and onions, add the spices and the mix. fill the grap leaves with the mix and roll them " +
                                "garlic, and zest and juice of one lemon. Using a spoon, mix to combine."
                        ,"add tomatosauce on the top"),
                203,12,
                List.of("https://feelgoodfoodie.net/wp-content/uploads/2017/06/Lebanese-Stuffed-Grape-Leaves-30.jpg",
                        "https://i.pinimg.com/originals/6e/20/c3/6e20c3ec14a75c15f5d1e511f112496c.jpg",
                        "https://i.pinimg.com/originals/dc/49/9e/dc499e481d2def2dc2fc72103b8d4c63.jpg"
                )
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing great job")
                        ,new Comment(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"i loved it")

                        ,new Comment(3,2,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"i was happy with the results")
                        ,new Comment(4,2,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"i'm glad you like it guys")
                )
        );

        recipes.add(recipe);

        recipe=new Recipe(5,5,"MariamJemy","https://harrypotterfanfiction.com/var/covers/340413.jpg?1595391755","ceaser salad",
                "grap leaves ","turkish",
                System.currentTimeMillis()-22230,"50 Min",List.of("chicken","vegiterian","well-cocked","small meals","rice"),
                List.of("mayo","chicken","parmisain","Spices ","garlic","olive oil"),
                List.of("mix the chicken , mayo, spices " ),
                203,12,
                List.of("https://natashaskitchen.com/wp-content/uploads/2019/01/Caesar-Salad-Recipe-3.jpg",
                        "https://feelgoodfoodie.net/wp-content/uploads/2020/04/Caesar-Salad-6.jpg",
                        "https://www.jessicagavin.com/wp-content/uploads/2019/07/caesar-salad-9-600x900.jpg"
                )
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing great job")
                        ,new Comment(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"i loved it")

                        ,new Comment(3,2,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"i was happy with the results")
                        ,new Comment(4,2,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"i'm glad you like it guys")
                )
        );

        recipes.add(recipe);

        recipe = new Recipe(6,1,"MahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                ,"Roasted chicken",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","England",
                System.currentTimeMillis()-1340,"40 Min",List.of("chicken","well-cocked"),
                List.of("chicken","butter","garlic","basil","Spices","lemon","oil"),
                List.of(" mixing all the ingredients with the chicken then put it in oven "
                ),
                203,3,
                List.of("https://static.toiimg.com/thumb/53007558.cms?width=1200&height=900",
                        "https://keviniscooking.com/wp-content/uploads/2019/04/Tropical-Roasted-Chicken-square.jpg"

                )
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing great job")
                        ,new Comment(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"i loved it")

                        ,new Comment(3,2,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"i was happy with the results")
                        ,new Comment(4,2,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"i'm glad you like it guys")
                )
        );

        recipes.add(recipe);
        recipe = new Recipe(7,1,"MahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                ,"Lazagnia",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","french",
                System.currentTimeMillis()-134,"40 Min",List.of("chicken","well-cocked"),
                List.of("chicken","carrots","mashroom","milk","Onions","flour","cheese","butter","spices"),
                List.of("large mixing bowl, add the flour . Add the  butter, " +
                        "onions, and zest and juice of one lemon. Using a spoon, mix to combine."
                ),
                203,3,
                List.of("https://demos.ithemelandco.com/woocommerce-dynamic-prices-by-user-role/wp-content/uploads/sites/70/2020/02/Lazania-600x425.jpg",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRC7rxGuTUsfYIvoQ7b-1yM5Jbfbb84EXrppA&usqp=CAU",
                        "https://d3fch0cwivr6nf.cloudfront.net/system/uploads/medium/data/12688/recipe_main_lazania-site.jpg"

                )
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing great job")
                        ,new Comment(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"i loved it")

                        ,new Comment(3,2,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"i was happy with the results")
                        ,new Comment(4,2,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"i'm glad you like it guys")
                )
        );

        recipes.add(recipe);
        recipe = new Recipe(8,1,"MahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                ,"ice cream",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","italian",
                System.currentTimeMillis()-100,"40 Min",List.of("dessert","vegiterian","fruits","frozen"),
                List.of("milk","fruits","chocolate"),
                List.of(" whip the milk with the fruits and put chocolate on top"
                ),
                203,3,
                List.of("https://cleobuttera.com/wp-content/uploads/2019/06/kk-ice-cream-full-mood-720x405.jpg",
                        "https://media.blogto.com/articles/20191217-swensons.jpg?w=1200&cmd=resize_then_crop&height=630&quality=70",
                        "https://c8.alamy.com/comp/JGH5P8/icecream-swensons-icecream-parlor-vientiane-laos-JGH5P8.jpg"

                )
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing great job")
                        ,new Comment(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"i loved it")

                        ,new Comment(3,2,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"i was happy with the results")
                        ,new Comment(4,2,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"i'm glad you like it guys")
                )
        );

        recipes.add(recipe);
        recipe = new Recipe(9,1,"MahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                ,"Pasta",
                "this recipe is is easy and we can make it at home as we can make it with our family and friends  ","italian",
                System.currentTimeMillis()-1000,"40 Min",List.of("pastries","vegiterian","well-cocked"),
                List.of("chicken","garlic","cheese","tomatosauce","olive oil"),
                List.of(" put the tomatosauce on the oven","add olive oil and garlic ","wait untill it boils then add the pasta","add cheese on the top"),
                203,3,
                List.of("https://www.femalefoodie.com/wp-content/uploads/2020/07/mama-ds-pink-sauce-pasta-2.jpg",
                        "https://everylittlecrumb.com/wp-content/uploads/pinksaucepasta-scaled.jpg"


                )
        );
        recipe.setComments(
                List.of(
                        new Comment(1,1,"mahmoudJoe3","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4"
                                ,"the taste is amazing great job")
                        ,new Comment(2,2,"Mahmoudmamdouh","https://api.time.com/wp-content/uploads/2014/07/301386_full1.jpg?quality=85&w=766&h=512&crop=1"
                                ,"i loved it")

                        ,new Comment(3,2,"MahmoudEmad","https://upload.wikimedia.org/wikipedia/en/d/d7/Harry_Potter_character_poster.jpg"
                                ,"i was happy with the results")
                        ,new Comment(4,2,"NadaAhmed","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4muXlltI4S4Rh9bvUOyrJIdKMw33AjDuvPg&usqp=CAU"
                                ,"i'm glad you like it guys")
                )
        );

        recipes.add(recipe);





        recipes.add(new Recipe(10,1,"Mahmoud Youseef","https://avatars.githubusercontent.com/u/49236858?s=400&u=4062ee63badec0dd55b775b5be2370c3ad582a44&v=4","this is title about the recipe that i make ",
                "chocolate cake ","English",
                System.currentTimeMillis()-1022,"40 Min",List.of("dessert","vegiterian","well-backed","pastries"),
                List.of("flour","butter","eggs","vanila","chocolate","milk"),
                List.of("mix all the ingredients and put it in the oven","put fruits on the top"),
                203,12,
                List.of("https://i0.wp.com/www.society19.com/wp-content/uploads/2018/06/chocolate-cake-houston.jpg?fit=1000%2C750&ssl=1"
                        ,"https://cdn.sallysbakingaddiction.com/wp-content/uploads/2013/04/triple-chocolate-cake-4.jpg",
                        "https://cdn.sallysbakingaddiction.com/wp-content/uploads/2013/04/triple-chocolate-cake-4.jpg")
        ));

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


    public ProfileViewModel(Application application) {
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
