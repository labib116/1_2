package DTO;

import java.io.Serializable;

public class BuyConfirmation implements Serializable {
    private String playerName;
    private String PreviousClubName;
    private String newClubName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getPreviousClubName() {
        return PreviousClubName;
    }
    public String getNewClubName() {
        return newClubName;
    }
    public void setPreviousClubName(String previousClubName) {
        PreviousClubName = previousClubName;
    }
    public void setNewClubName(String newClubName) {
        this.newClubName = newClubName;
    }

}