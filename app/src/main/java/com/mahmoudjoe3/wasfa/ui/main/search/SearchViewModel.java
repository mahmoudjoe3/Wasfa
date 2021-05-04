package com.mahmoudjoe3.wasfa.ui.main.search;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.repo.InteractionRepo;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private InteractionRepo interactionRepo;
    private LiveData<List<Interaction>> interactionsLiveData;

    public SearchViewModel(Application application) {
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
