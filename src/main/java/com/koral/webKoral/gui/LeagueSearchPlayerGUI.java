package com.koral.webKoral.gui;
import com.koral.webKoral.reqapi.RiotSummonerV4;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("searchLeaguePlayer")
public class LeagueSearchPlayerGUI extends VerticalLayout {
    private RiotSummonerV4 riotApi;

    public LeagueSearchPlayerGUI(RiotSummonerV4 riotApi){
        this.riotApi = riotApi;
        TextField textFieldRegion = new TextField("Region");
        TextField textFieldNick = new TextField("Nick");
        Button button = new Button("Wyszukaj");
        Label labelDetails = new Label();
        add(textFieldRegion, textFieldNick, button, labelDetails);

        button.addClickListener(buttonClickEvent -> {
            riotApi.getSummonerDetails(textFieldRegion.getValue(), textFieldNick.getValue());
            labelDetails.setText(String.valueOf(riotApi.getSummonerLevel()));
        });

    }
}