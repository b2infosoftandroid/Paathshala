package com.b2infosoft.paathshala.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.credential.Active;

import org.json.JSONObject;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Automatic session tracking
        Branch.getAutoInstance(getApplication());
        setContentView(R.layout.activity_splash);

        findViewById(R.id.imageView).startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    Active active= Active.getInstance(getApplicationContext());
                    if(active.isLogin()) {
                        startActivity(new Intent(Splash.this, MainActivity.class));
                    }else{
                        startActivity(new Intent(Splash.this, LoginActivity_1.class));
                    }
                    finish();
                } catch (InterruptedException e) {

                }
            }
        }).start();

    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance();
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked before showing up
                    Log.i("BranchConfigTest", "deep link data: " + referringParams.toString());
                } else {
                    Log.i("Branch Error", error.toString());
                }
            }
        }, this.getIntent().getData(), this);
/*      Log.d("DEEP LINK",branch.getDeeplinkDebugParams().toString());
        Log.d("LAST",branch.getLatestReferringParams().toString());
        Log.d("FIRST LINK",branch.getFirstReferringParams().toString());
*/

        BranchUniversalObject branchUniversalObject = new BranchUniversalObject()
                .setCanonicalIdentifier("article/12345")
                .setTitle("Check out this article!")
                .setContentDescription("It’s really entertaining...")
                .setContentImageUrl("https://mysite.com/article_logo.png")
                .addContentMetadata("read_progress", "17%");

        LinkProperties linkProperties = new LinkProperties()
                .setChannel("facebook")
                .setFeature("sharing")
                .addControlParameter("$fallback_url", "http://mysite.com/article/12345");

        branchUniversalObject.generateShortUrl((Activity) this, linkProperties, new Branch.BranchLinkCreateListener() {
            @Override
            public void onLinkCreate(String url, BranchError error) {
                Log.i("MyApp", "Got my Branch link to share: " + url);
            }
        });

    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
