package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.utils.Constants;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class OutputViewController extends Controller {

    private ScrollPane _topParentContainer;

    public TextFlow outputTextFlow;

    @Override
    public void init() {
        //outputTextFlow.setPrefWidth(Double.MAX_VALUE);
        outputTextFlow.getChildren().addListener(
                (ListChangeListener<? super Node>) change -> {
                    //outputTextFlow.layout();
                    //_topParentContainer.layout();
                    //_topParentContainer.setVvalue(1.0);
                });
    }

    public void setTopParentContainer(ScrollPane parentContainer) {
        if (parentContainer != null)
            _topParentContainer = parentContainer;
    }

    public void onInfoMessage(String infoMessage) {
        if (infoMessage != null) {
            Text text = new Text(infoMessage + Constants.NEWLINE);
            text.setFill(Color.BLACK);
            text.setWrappingWidth(Double.MAX_VALUE);

            outputTextFlow.getChildren().add(text);
        }
    }

    public void onErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            Text text = new Text(errorMessage + Constants.NEWLINE);
            text.setFill(Color.RED);
            text.setWrappingWidth(Double.MAX_VALUE);

            outputTextFlow.getChildren().add(text);
        }
    }
}
