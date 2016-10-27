package squaring.vitrox.olxpj.Presenter;

/**
 * Created by miguelgomez on 10/27/16.
 */

public interface ProductsActivityPresenter {

    void onLoad(String selectedCategory);

    void onItemClick(String url);

    interface view {

        void onProductReturn(String urlToLoad);

    }
}
