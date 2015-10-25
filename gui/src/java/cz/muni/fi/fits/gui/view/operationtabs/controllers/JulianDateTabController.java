package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.models.inputdata.JulianDateInputData;
import cz.muni.fi.fits.gui.utils.Parsers;
import cz.muni.fi.fits.gui.utils.ValidationException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class JulianDateTabController extends JulianDateBasedOperationTabController {

    private Validator _validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.jd");
        _validator = new Validator();
    }

    @Override
    public InputData getInputData() {
        try {
            // validate fields
            _validator.validateDatetimeRecordTypeField();
            _validator.validateDatetimeValueField();
            _validator.validateExposureRecordTypeField();
            _validator.validateExposureValueField();

            // get datetime
            String datetime = null;
            switch (datetimeRecordTypeField.getValue().getType()) {
                case KEYWORD:
                    datetime = datetimeKeywordField.getText();
                    break;

                case VALUE:
                    LocalDate date = datetimeValueDateField.getValue();
                    LocalTime time = Parsers.Time.parse(datetimeValueTimeField.getText());

                    if (date == null)
                        date = LocalDate.of(0, 1, 1);
                    if (time == null)
                        time = LocalTime.of(0, 0, 0);

                    datetime = LocalDateTime.of(date, time).toString();
                    break;
            }

            //get exposure
            String exposure = null;
            switch (exposureRecordTypeField.getValue().getType()) {
                case KEYWORD:
                    exposure = exposureKeywordField.getText();
                    break;

                case VALUE:
                    double exposureValue = Parsers.Double.parse(exposureValueField.getText());
                    exposure = Double.toString(exposureValue);
                    break;
            }

            // get comment
            String comment = commentField.getText();

            return new JulianDateInputData(datetime, exposure, comment);
        } catch (ValidationException vEx) {
            // validation errors
            return null;
        }
    }


    /**
     *
     */
    class Validator extends JulianDateBasedOperationTabController.Validator { }
}
