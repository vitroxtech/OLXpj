package squaring.vitrox.olxpj;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import squaring.vitrox.olxpj.Helper.Config;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        photo= (ImageView)findViewById(R.id.detailedImage);
        Intent i= getIntent();
        String Urlimage= i.getStringExtra(Config.SELECTED_IMAGE);
        Glide.with(this).load(Urlimage)
                .skipMemoryCache(true)
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .centerCrop().into(photo);
    }
}
