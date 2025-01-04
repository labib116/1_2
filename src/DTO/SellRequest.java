package DTO;

import java.io.Serializable;
import java.util.Objects;

public class SellRequest implements Serializable {
    private String playerName;
    private String clubName;
    private int price;

    public SellRequest(String name, String loginClub, int weeklySalary) {
        this.playerName=name;
        this.clubName=loginClub;
        this.price=weeklySalary;
    }

    public SellRequest() {

    }

    public String getPlayerName() {
        return playerName;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellRequest that = (SellRequest) o;
        return price == that.price &&
                Objects.equals(playerName, that.playerName) &&
                Objects.equals(clubName, that.clubName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, clubName, price);
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