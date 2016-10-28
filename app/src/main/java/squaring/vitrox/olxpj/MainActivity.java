package squaring.vitrox.olxpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import squaring.vitrox.olxpj.Adapters.CategoryAdapter;
import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Presenter.MainPresenter;
import squaring.vitrox.olxpj.Presenter.MainPresenterImp;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private GridLayoutManager mLayoutManager;
    private CategoryAdapter mAdapter;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenterImp(this, MainActivity.this);
        mPresenter.onload();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CategoryAdapter(getApplicationContext(), ItemClicked);
        mRecyclerView.setAdapter(mAdapter);

    }

    private CategoryAdapter.OnItemClickListener ItemClicked = new CategoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Category item) {
            mPresenter.onItemClick(item);
            //System.out.println("CATEGORY " + item.getCategoryname());
        }
    };

    /*
    Im saved the click before go forward
    */
    @Override
    public void clickSaved(String categoryName) {
        Intent i = new Intent(this, ProductsActivity.class);
        i.putExtra(Config.SELECTED_CATEGORY, categoryName);
        startActivity(i);
    }

    /*
    the categories list will be loaded in async way, for this Im generating the span layout also dinamically based on current state of dataset
    of adapter i know is mvp and i should not handle data here but for this case is useful to have the dynamic setting here in view
    */
    @Override
    public void refresh(final Category photos, final List<Category> catsMostViewed) {
        mAdapter.addData(photos);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                             @Override
                                             public int getSpanSize(int position) {
                                                 if (catsMostViewed.size() == 1) {
                                                     if (mAdapter.getDataset().get(position).getCategoryname().contains(catsMostViewed.get(0).getCategoryname())) {
                                                         return 3;
                                                     }
                                                     return 1;
                                                 } else if (catsMostViewed.size() == 2) {
                                                     if (mAdapter.getDataset().get(position).getCategoryname().contains(catsMostViewed.get(0).getCategoryname())) {
                                                         return 3;
                                                     } else if (mAdapter.getDataset().get(position).getCategoryname().contains(catsMostViewed.get(1).getCategoryname())) {
                                                         return 2;
                                                     }
                                                     return 1;
                                                 }
                                                 return 1;
                                             }
                                         }

        );

    }

    /*
       Being this the real main activity it will not have back, so it  close the app
       */
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
