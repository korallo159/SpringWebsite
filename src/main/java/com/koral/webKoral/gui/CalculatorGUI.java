package com.koral.webKoral.gui;
import com.koral.webKoral.reqapi.CurrencyApi;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;

import java.util.HashSet;
import java.util.Set;


@Route("kantor")
@CssImport(value="./style.css", themeFor = "vaadin-text-field")
public class CalculatorGUI extends VerticalLayout {
    public CalculatorGUI(){
        VerticalLayout Vertical = new VerticalLayout();
        Set items = new HashSet(CurrencyApi.getBases());
        items.add("EUR");
        ComboBox<String> comboBoxBase = new ComboBox<>();
        comboBoxBase.setItems(items);
        comboBoxBase.setLabel("Waluta którą chcesz");
        add(comboBoxBase);

        ComboBox<String> comboBoxExchange = new ComboBox<>();
        comboBoxExchange.setItems(items);
        comboBoxExchange.setLabel("Wymieniana waluta");
        add(comboBoxExchange);

        NumberField numberField = new NumberField("Ilość");
        numberField.setId("numberfield");
        add(numberField);

        Label label = new Label("Wynik:");
        add(label);
        Button button = new Button("Przelicz");
        add(button);
        button.addClickListener(buttonClickEvent -> {
          Double exchange = CurrencyApi.currentsApi(comboBoxBase.getValue(), comboBoxExchange.getValue());
            Double result = exchange * numberField.getValue();
            label.setText(result.toString() + " " +  comboBoxBase.getValue());
        });
        Vertical.setHorizontalComponentAlignment(Alignment.CENTER, comboBoxBase);
        Vertical.setHorizontalComponentAlignment(Alignment.CENTER, comboBoxExchange);
        Vertical.setHorizontalComponentAlignment(Alignment.CENTER, button);
        Vertical.setHorizontalComponentAlignment(Alignment.CENTER, label);
        Vertical.setHorizontalComponentAlignment(Alignment.CENTER, numberField);
    }

}
