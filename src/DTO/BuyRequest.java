package DTO;

import java.util.Objects;

public class BuyRequest {
    private String playerName;
    private String clubName;
    private int price;

    public BuyRequest(String playerName, String clubName, int price) {
        this.playerName = playerName;
        this.clubName = clubName;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyRequest that = (BuyRequest) o;
        return Objects.equals(playerName, that.playerName) &&
                Objects.equals(clubName, that.clubName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, clubName);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}