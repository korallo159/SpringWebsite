package com.koral.webKoral.reqapi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class RiotSummonerV4 {
    private String[] regions = {"EUN1", "BR1", "EUW1", "JP1", "KR", "LA1", "LA2", "NA1", "OC1", "RU", "TR1"};
    private String accountId;
    private String id;
    private String puuid;
    private int profileIconId;
    private int revisionDate;
    private int summonerLevel;
    private static String apikey = "RGAPI-cda13bf1-0bbe-4eee-8069-7ca992fcfdfd";
    public void getSummonerDetails(String region, String name)  {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> exchange
                    = restTemplate.exchange("https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apikey,
                    HttpMethod.GET, HttpEntity.EMPTY, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode object = objectMapper.readTree(exchange.getBody());
            accountId = object.get("accountId").toString().replace("\"", "");
            id = object.get("id").toString().replace("\"", "");
            puuid = object.get("puuid").toString().replace("\"", "");
            profileIconId = object.get("profileIconId").intValue();
            revisionDate = object.get("revisionDate").intValue();
            summonerLevel = object.get("summonerLevel").intValue();
            System.out.println(exchange.getBody());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public int getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(int revisionDate) {
        this.revisionDate = revisionDate;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }
}
