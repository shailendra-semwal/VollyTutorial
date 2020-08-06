package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<List_Data> list_data;
    private Context context;

    public MyAdapter(List<List_Data> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_data,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final List_Data listData=list_data.get(position);
        Picasso.with(context)
                .load(listData.getImageurl())
                .into(holder.img);
        holder.img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(context)
                        .load(listData.getImageurl())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                                try {
                                    File mydie = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/RI_IMG");
                                    if (!mydie.exists()) {
                                        mydie.mkdirs();
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(new File(mydie, new Date().toString() + ".jpg"));
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    Toast.makeText(context, "Save", Toast.LENGTH_LONG).show();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }




                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
            }
        });
        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(context)
                        .load(listData.getImageurl())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                Intent intent = new Intent("android.intent.action.SEND");
                                intent.setType("image/*");
                                intent.putExtra("android.intent.extra.STREAM", getlocalBitmapUri(bitmap));
                                context.startActivity(Intent.createChooser(intent, "share"));

                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });

            }
        });
    }

    private Uri getlocalBitmapUri(Bitmap bitmap) {



        Uri bmuri = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            bmuri = Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bmuri;

    }



    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img,img_download,img_share;
        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.image_view);
            img_download=(ImageView)itemView.findViewById(R.id.btn_download);
            img_share=(ImageView)itemView.findViewById(R.id.btn_share);
        }
    }
}