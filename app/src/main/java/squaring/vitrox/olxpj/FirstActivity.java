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

import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Presenter.FirstActivityPresenter;
import squaring.vitrox.olxpj.Presenter.FirstActivityPresenterImp;

public class FirstActivity extends AppCompatActivity implements FirstActivityPresenter.view, View.OnClickListener {

    private FirstActivityPresenter presenter;
    private ImageView mainCategoryImage;
    private Button gotoCatButton;
    private RelativeLayout mlayout;
    private TextView categorynameTextVew;
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

    /*
    get if is the firstTime to open the app then i decide if go to MainActivity or remain here
    */
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

    /*
    get the mostviewed category from db then show
    */
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

    /*
    one handler for two views, in case of image go to
    */

    @Override
    public void onClick(View v) {

        if (v instanceof ImageView) {
          presenter.onImageClicked();

        } else if (v instanceof Button) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
    /*
       I add to db also the click on this category on this activity
       */
    @Override
    public void goToProducts(String CategoryName) {
        Intent i = new Intent(this, ProductsActivity.class);
        i.putExtra(Config.SELECTED_CATEGORY,CategoryName);
        startActivity(i);
    }
    
    /*
   error and handler for glide
   */
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
