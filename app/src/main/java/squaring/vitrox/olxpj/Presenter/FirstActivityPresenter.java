package squaring.vitrox.olxpj.Presenter;

import squaring.vitrox.olxpj.Model.Category;

/**
 * Created by miguelgomez on 10/27/16.
 */

public interface FirstActivityPresenter {

    void checkIfFirstTime();
    void getMostClickedCat();

     interface view
     {
         void isfirstTime(Boolean b);
         void mostViewedCategory(Category c);
     }

}
