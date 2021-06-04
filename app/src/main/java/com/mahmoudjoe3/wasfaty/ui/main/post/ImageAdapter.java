package com.mahmoudjoe3.wasfaty.ui.main.post;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mahmoudjoe3.wasfaty.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private static final String TAG = "Imagey";
    private List<Uri> imageList = new ArrayList<>();
    private onItemClickListener mListener;

    public ImageAdapter() {
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+imageList);
        holder.imageView.setImageURI(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setImageList(List<Uri> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        imageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, imageList.size());
    }

    public interface onItemClickListener {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton removeImageButton;
        public ImageViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            removeImageButton = itemView.findViewById(R.id.remove_imageButton);

            removeImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onRemoveClick(position);
                        }
                    }
                }
            });
        }
    }
}
