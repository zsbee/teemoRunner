package com.Zsombor.Fuszenecker.gameservices;

/**
 * Created by zsomborfuszenecker on 11/05/14.
 */
public interface ActionResolver {
    public boolean getSignedInGPGS();
    public void loginGPGS();
    public void submitScoreGPGS(int score);
    public void unlockAchievementGPGS(String achievementId);
    public void getLeaderboardGPGS();
    public void getAchievementsGPGS();
}
