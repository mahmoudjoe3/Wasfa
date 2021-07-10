package com.mahmoudjoe3.wasfaty.ui.main.post;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.ImageCompressor;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.RecipePost;
import com.mahmoudjoe3.wasfaty.prevalent.prevalent;
import com.mahmoudjoe3.wasfaty.viewModel.PostSharedViewModel;
import com.mahmoudjoe3.wasfaty.viewModel.PostViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
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
    private LinearProgressIndicator post_progress;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    PostViewModel postViewModel;
    StorageReference RecipeImageReference;

    public PostFragment3() {
    }

    public static PostFragment3 getInstance() {
        if (postFragment3 == null) {
            postFragment3 = new PostFragment3();
        }
        return postFragment3;
    }

    public static void removeFragment() {
        postFragment3 = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecipeImageReference = FirebaseStorage.getInstance("gs://wasfa-9891c.appspot.com/").getReference().child(prevalent.refStorage_RecipeImage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_fragement3, container, false);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
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
                dialog.show(getActivity().getSupportFragmentManager(), SnapTimePickerDialog.TAG);
                dialog.setListener(new SnapTimePickerDialog.Listener() {
                    @Override
                    public void onTimePicked(int hour, int minute) {
                        if (!(hour == 0 && minute == 0)) {
                            recipe.setPrepareTime(hour + ":" + minute);
                            recipe.setCreatedDate(System.currentTimeMillis());
                            boolean isSet = setPostData();
                            if (isSet) {
                                Toast.makeText(getActivity(), "Post Uploud..", Toast.LENGTH_SHORT).show();
                                recipe.setComments(new ArrayList<>());
                                recipe.setShareCount(0);
                                recipe.setLoveCount(0);
                                //Todo send to api
                                postRecipe(recipe);


                            } else {
                                Toast.makeText(getActivity(), "Complete All the recipe data", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(getActivity(), "Recipe prepare time Invalid", Toast.LENGTH_SHORT).show();

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
                if (recipe.getSteps() != null) {
                    stepsAdapter.setList(recipe.getSteps());
                    stepsList = recipe.getSteps();
                }
            }
        });
        return view;
    }

    private void removeData() {
        recipe.setDescription("");
        recipe.setImgUris(new ArrayList<>());
        recipe.setImgUrls(new ArrayList<>());
        recipe.setCategories(new ArrayList<>());
        recipe.setIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        postSharedViewModel.setRecipe(recipe);
    }

    private boolean setPostData() {
        if (stepsList.size() > 0) {
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
        post_progress = view.findViewById(R.id.post_progress);
        recipe = new Recipe();
        sharedPreferences = getActivity().getSharedPreferences("new_user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
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


    public void postRecipe(Recipe recipe) {
        post_progress.setVisibility(View.VISIBLE);
        List<Uri> imgs = recipe.getImageUris();
        recipe.setImgUrls(new ArrayList<>());
        RecipePost recipePost = RecipePost.makeRecipe(recipe);
        upload( (ArrayList<Uri>) imgs, 0, recipePost);
        //up(imgs, recipePost);
    }

    public Uri bitMapToUri(Bitmap bmp) {
        // for Image send ignore URI error
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Uri bmpUri=null;
        try {
            File file = new File(getActivity().getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private Bitmap uriToBitmap(Uri uri) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT >= 29) {
            ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), uri);
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void upload(ArrayList<Uri> mImageUri, int i, RecipePost recipe) {
        if (i >= mImageUri.size()) {
            postViewModel.PostRecipe(recipe).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (!(response.code() >= 200) && !(response.code() < 300))
                        Toast.makeText(getActivity(), "Faild to post recipe", Toast.LENGTH_SHORT).show();
                    else {
                        post_progress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "post recipe Successfully", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().beginTransaction().replace(
                                R.id.fragment_container, PostFragment1.getInstance()
                        ).commit();
                        removeData();
                        getActivity().onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getActivity(), "Faild to post recipe", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if (mImageUri.get(i) != null) {
            Uri ur=mImageUri.get(i);
            // compress image
            //[1] convert uri to bitmap
            Bitmap bitmap = uriToBitmap(ur);
            //[2] encode image
            String code = ImageCompressor.encode_Image_To_String(bitmap, 45);
            //[3] decode image
            Bitmap CodedBitmap = ImageCompressor.decode_String_To_Image(code);
            //[4] convert bitmap to uri
            ur = bitMapToUri(CodedBitmap);
            RecipeImageReference.child(ur.getLastPathSegment())
                    .putFile(ur)
                    .addOnSuccessListener(taskSnapshot -> {
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();
                                    Log.d("TAG####", "upload: "+imageUrl+"  "+i);
                                    recipe.getImagesUrls().add(imageUrl);
                                    upload(mImageUri, i + 1, recipe);
                                });
                            }
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    post_progress.setProgressCompat((int)progress,true);
                }
            })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Uploading image error :: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG####", "upload: "+e.getMessage());

                    });
        }
    }
}
