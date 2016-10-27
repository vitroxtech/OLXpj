package squaring.vitrox.olxpj;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import squaring.vitrox.olxpj.Adapters.CategoryAdapter;
import squaring.vitrox.olxpj.Adapters.ProductGridAdapter;
import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Presenter.ProductsActivityPresenter;
import squaring.vitrox.olxpj.Presenter.ProductsActivityPresenterImp;

public class ProductsActivity extends AppCompatActivity implements ProductsActivityPresenter.view {

    ProductsActivityPresenter mPresenter;
    private GridLayoutManager mLayout;
    private ProductGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Intent bundle = getIntent();
        String selectedCategory = bundle.getStringExtra(Config.SELECTED_CATEGORY);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycle_products);
        mLayout = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayout);
        mAdapter = new ProductGridAdapter(this, ItemClicked);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ProductsActivityPresenterImp(this, ProductsActivity.this);
        mPresenter.onLoad(selectedCategory);

    }

    private ProductGridAdapter.OnItemClickListener ItemClicked = new ProductGridAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String url, ImageView imageView) {
            Intent i= new Intent(getApplicationContext(),ProductDetailActivity.class);
            i.putExtra(Config.SELECTED_IMAGE,url);

            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(ProductsActivity.this,
                            imageView, "poster");
            startActivity(i,options.toBundle());
        }
    };

    @Override
    public void onProductReturn(String url) {

        mAdapter.addData(url);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }
}
