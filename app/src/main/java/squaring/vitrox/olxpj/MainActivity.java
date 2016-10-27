package squaring.vitrox.olxpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import squaring.vitrox.olxpj.Adapters.CategoryAdapter;
import squaring.vitrox.olxpj.Helper.Config;
import squaring.vitrox.olxpj.Model.Category;
import squaring.vitrox.olxpj.Presenter.MainPresenter;
import squaring.vitrox.olxpj.Presenter.MainPresenterImp;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    //private RecyclerView.LayoutManager mLayoutManager;
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
        //mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CategoryAdapter(getApplicationContext(), ItemClicked);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) return 3;
                if (position==2)return 2;
                return 1;
            }
        });

    }

    private CategoryAdapter.OnItemClickListener ItemClicked = new CategoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Category item) {
            mPresenter.onItemClick(item);
            System.out.println("CATEGORY " + item.getCategoryname());
        }
    };


    @Override
    public void clickSaved(String categoryName) {
        Intent i= new Intent(this,ProductsActivity.class);
        i.putExtra(Config.SELECTED_CATEGORY,categoryName);
        startActivity(i);
    }

    @Override
    public void refresh(Category Photo) {
        mAdapter.addData(Photo);
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
