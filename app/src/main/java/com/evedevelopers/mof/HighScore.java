package com.evedevelopers.mof;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class HighScore extends AppCompatActivity {
    TextView hs3,hs4,hs5,best;
    String mCurrentPhotoPath;
    ImageView leaderboard;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_LEADERBOARD_UI = 9004;
    int high,l;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount signedInAccount;
    static String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        hs3 = findViewById(R.id.hs3);
        hs4 = findViewById(R.id.hs4);
        hs5 = findViewById(R.id.hs5);
        best = findViewById(R.id.best);
        leaderboard = findViewById(R.id.leaderboard);

        //View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
       /* share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View screenView = findViewById(R.id.root);
                screenView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
                screenView.setDrawingCacheEnabled(false);
                File dir = new File(dirPath);
                if(!dir.exists())
                    dir.mkdirs();
                File file = new File(dirPath, "screenshot.png");
                try {
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Uri uri = FileProvider.getUriForFile(HighScore.this, HighScore.this.getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    List<ResolveInfo> resolveInfoList = getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resolveInfoList){
                        String packageName = resolveInfo.activityInfo.packageName;
                        getApplicationContext().grantUriPermission(packageName,uri,Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "Check my high score in M overflow Try it https://play.google.com/store/apps/details?id=com.evedevelopers.mof ");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    try {
                        startActivity(Intent.createChooser(intent, "Share Screenshot"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(HighScore.this, "No App Available", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });*/
        SharedPreferences hscore = getSharedPreferences("score", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences lv = getSharedPreferences("lev", AppCompatActivity.MODE_PRIVATE);
        int hsc = hscore.getInt("score",0);
        best.setText(String.valueOf(hsc));
        hs3.setText(String.valueOf(lv.getInt("l3",0)));
        hs4.setText(String.valueOf(lv.getInt("l4",0)));
        hs5.setText(String.valueOf(lv.getInt("l5",0)));
        high = hsc;
        if(lv.getInt("l3",0) == 9){
            hs3.setTextColor(getResources().getColor(R.color.green));
        }
        if(lv.getInt("l4",0) == 16){
            hs3.setTextColor(getResources().getColor(R.color.green));
        }
        if(lv.getInt("l5",0) == 25){
            hs3.setTextColor(getResources().getColor(R.color.green));
        }
        //signedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        l=0;
       leaderboard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               l=1;
               if(!isSignedIn()) {
                   signInSilently();
               }else{
                   showLeaderboard();
               }

           }
       });
    }


    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        //Intent signInIntent = signInClient.getSignInIntent();
        //startActivityForResult(signInIntent, RC_SIGN_IN);

        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                             signedInAccount = task.getResult();
                             //Toast.makeText(getApplicationContext(),"SignedIn",Toast.LENGTH_SHORT).show();
                            Games.getLeaderboardsClient(HighScore.this,signedInAccount);
                            Games.getLeaderboardsClient(getApplicationContext(),signedInAccount)
                                    .submitScore(getString(R.string.leaderboard_high_score), high);
                            if(l==1){
                                l=0;
                                showLeaderboard();

                            }
                        } else {
                            startSignInIntent();
                        }
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                signedInAccount = result.getSignInAccount();
                Games.getLeaderboardsClient(HighScore.this,signedInAccount);
                Games.getLeaderboardsClient(getApplicationContext(),signedInAccount)
                        .submitScore(getString(R.string.leaderboard_high_score), high);
                if(l==1){
                    l=0;
                    showLeaderboard();

                }

            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }
    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    private void showLeaderboard() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_high_score))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }
    private boolean isSignedIn() {
        signedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        return signedInAccount != null;
    }
}
