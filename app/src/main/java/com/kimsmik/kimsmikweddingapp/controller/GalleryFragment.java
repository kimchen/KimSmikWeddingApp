package com.kimsmik.kimsmikweddingapp.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.kimsmik.kimsmikweddingapp.IMenuFragment;
import com.kimsmik.kimsmikweddingapp.R;


public class GalleryFragment extends Fragment implements IMenuFragment {

    private int[] photoList = new int[]{
            R.drawable.photo034,R.drawable.photo047,R.drawable.photo048,R.drawable.photo088,R.drawable.photo091,
            R.drawable.photo099,R.drawable.photo108,R.drawable.photo131,R.drawable.photo132,R.drawable.photo151,
            R.drawable.photo167,R.drawable.photo191,R.drawable.photo197,R.drawable.photo198,R.drawable.photo205};
    private ImageView photoView = null;
    private int photoNum = 0;
    private int photoIndex = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        photoView = (ImageView)root.findViewById(R.id.photoView);
        photoNum = photoList.length;
        showPhoto(photoIndex);

        LinearLayout ll = new LinearLayout(getActivity());
        HorizontalScrollView hsv = (HorizontalScrollView)root.findViewById(R.id.horizontalScrollView);
        for(int i=0; i<photoList.length; i++){

            final int index = i;
            Bitmap bmp = createMiniMap(photoList[i],50,50);
            ImageView iv = new ImageView(getActivity());
            iv.setPadding(3, 2, 3, 2);
            iv.setImageBitmap(bmp);
            iv.setAdjustViewBounds(true);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoView.setImageResource(photoList[index]);
                }
            });
            ll.addView(iv, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        }
        hsv.addView(ll,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        return root;
    }
    private Bitmap createMiniMap(int resId, double desW, double desH){
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        option.inPurgeable = true;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),resId,option);
        int yRatio = (int) Math.ceil(option.outHeight / desH);
        int xRatio = (int) Math.ceil(option.outWidth / desW);
        if (yRatio > 1 || xRatio > 1) {
            if (yRatio > xRatio) {
                option.inSampleSize = yRatio;
            } else {
                option.inSampleSize = xRatio;
            }
        }
        option.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(getResources(),resId,option);
        return bmp;
    }
    @Override
    public String GetTitle() {
        return "婚照欣賞";
    }

    private void showPhoto(int index){
        if(index < 0 || index >= photoList.length)
            return;
        photoView.setImageResource(photoList[index]);
    }
}
