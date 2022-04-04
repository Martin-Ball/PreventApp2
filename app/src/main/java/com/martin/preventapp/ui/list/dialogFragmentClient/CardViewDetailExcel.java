package com.martin.preventapp.ui.list.dialogFragmentClient;

public class CardViewDetailExcel {

    private String client;
    private String CUIT;
    private String FantasyName;
    private String StreetAddress;

    public CardViewDetailExcel(String client2, String CUIT2, String FantasyName2, String StreetAddress2) {
        client = client2;
        CUIT = CUIT2;
        FantasyName = FantasyName2;
        StreetAddress = StreetAddress2;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCUIT() {
        return CUIT;
    }

    public void setCUIT(String CUIT) {
        this.CUIT = CUIT;
    }

    public String getFantasyName() {
        return FantasyName;
    }

    public void setFantasyName(String fantasyName) {
        FantasyName = fantasyName;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }
}
