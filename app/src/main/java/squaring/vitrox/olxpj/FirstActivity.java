package squaring.vitrox.olxpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Presenter.FirstActivityPresenter;
import squaring.vitrox.olxpj.Presenter.FirstActivityPresenterImp;

public class FirstActivity extends AppCompatActivity implements FirstActivityPresenter.view, View.OnClickListener {

    FirstActivityPresenter presenter;
    ImageView mainCategoryImage;
    Button gotoCatButton;
    RelativeLayout mlayout;
    TextView categorynameTextVew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_t);
        mlayout = (RelativeLayout) findViewById(R.id.first_content_layout);
        categorynameTextVew = (TextView) findViewById(R.id.firstcatTitle);
        mlayout.setVisibility(View.GONE);
        gotoCatButton = (Button) findViewById(R.id.continue_button);
        gotoCatButton.setOnClickListener(this);
        mainCategoryImage = (ImageView) findViewById(R.id.mostviewedImage);
        mainCategoryImage.setOnClickListener(this);
        presenter = new FirstActivityPresenterImp(this, FirstActivity.this);
        presenter.checkIfFirstTime();
    }

    @Override
    public void isfirstTime(Boolean b) {
        if (b) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            presenter.getMostClickedCat();
        }
    }

    @Override
    public void mostViewedCategory(Category c) {
        categorynameTextVew.setText(c.getCategoryname());
        Glide.with(this).load(c.getLink())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(requestListener)
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .centerCrop().into(mainCategoryImage);
        mlayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {

        if (v instanceof ImageView) {
            Intent i = new Intent(this, ProductsActivity.class);
            startActivity(i);

        } else if (v instanceof Button) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //CAPTURE ERROR ON URL FOR SOME IMAGES THAT ARE NOT AVALIABLE
            Log.d("ImageErrorUrl: ", model);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };
}
