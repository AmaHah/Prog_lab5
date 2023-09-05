import Model.IModel;
import Model.CSVModel;
import Presenter.CommandsPresenter;
import Presenter.IPresenter;
import View.IView;
import View.View;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        IModel model = new CSVModel();
        IView view = new View();
        IPresenter presenter = new CommandsPresenter(view, model);
        presenter.start();
    }
}
