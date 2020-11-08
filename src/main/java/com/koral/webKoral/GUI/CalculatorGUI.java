package com.koral.webKoral.GUI;
import com.koral.webKoral.CurrencyApi;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;


@Route("kantor")
@CssImport("./style.css")
public class CalculatorGUI extends VerticalLayout {

    public CalculatorGUI(){
        TextField textField = new TextField("Wpisz walute.");
        TextField textField1 = new TextField("Wpisz przeliczana walute");
        Label label = new Label("Wynik:");
        Button button = new Button("Przelicz");
        button.addClickListener(buttonClickEvent -> {
          Double result = CurrencyApi.currentsApi(textField.getValue(), textField1.getValue());
            label.setText(result.toString());
        });
        add(textField);
        add(textField1);
        add(button);
        add(label);
    }

}
