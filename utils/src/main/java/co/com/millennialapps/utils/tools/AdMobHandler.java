package co.com.millennialapps.utils.tools;

/**
 * Created by Erick Velasco on 12/3/2018.
 */

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/**
 * Created by Erick Velasco on 31/12/2017.
 */

public class AdMobHandler implements RewardedVideoAdListener {

    private OnReward onReward;
    private RewardedVideoAd mRewardedVideoAd;
    private static AdMobHandler handler;
    private Activity activity;

    public static AdMobHandler getInstance() {
        return handler == null ? handler = new AdMobHandler() : handler;
    }

    public void initializeAdMob(Activity activity, int idAdMob) {
        MobileAds.initialize(activity, activity.getString(idAdMob));
    }

    public void initializeRewardedVideo(Activity activity) {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        activity = activity;
    }

    @Override
    public void onRewarded(RewardItem reward) {
        onReward.reward(reward);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        //TODO
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //TODO
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        //TODO
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //TODO
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //TODO
    }

    @Override
    public void onRewardedVideoStarted() {
        //TODO
    }

    public void loadTestAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }

    public void loadAd(String idCampaign) {
        mRewardedVideoAd.loadAd(idCampaign, new AdRequest.Builder().build());
    }

    public void resumeAd() {
        mRewardedVideoAd.resume(activity);
    }

    public void pauseAd() {
        mRewardedVideoAd.pause(activity);
    }

    public void destroyAd() {
        mRewardedVideoAd.destroy(activity);
    }

    public boolean isAdLoaded() {
        return mRewardedVideoAd.isLoaded();
    }

    public void showAd() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {
            Toast.makeText(activity, "El vídeo aun no se ha cargado. Danos un momento e intenta otra vez.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnReward(OnReward onReward) {
        this.onReward = onReward;
    }


    public interface OnReward {
        void reward(RewardItem reward);
    }
}
