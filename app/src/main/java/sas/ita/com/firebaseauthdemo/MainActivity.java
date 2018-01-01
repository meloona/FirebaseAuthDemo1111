package sas.ita.com.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText UserId, Password;
    FirebaseAuth auth;
    String TAG="result";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserId=(EditText)findViewById(R.id.id);
        Password=(EditText)findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();

    }

    public void createNewUserFirebase(View view) {
        String user = UserId.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "provide userId", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "provide password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "password should be more than six character", Toast.LENGTH_SHORT).show();
            return;
        }
      auth.createUserWithEmailAndPassword(user,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                    String str= auth.getCurrentUser().getEmail();
                    Log.d("Current Uset Is:", str);

                    Intent i= new Intent(MainActivity.this, Home.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
          }
      });

    }

    public void signInWithUserIdAndPassword(View view) {
        String user = UserId.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "provide userId", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "provide password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "password should be more than six character", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String str= auth.getCurrentUser().getEmail();
                    Log.d("Current Uset Is:", str);

                    Intent i= new Intent(MainActivity.this, Home.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void deleteuser(View view) {
        String user = UserId.getText().toString().trim();
        String password = Password.getText().toString().trim();
        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user, password);
        user2.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });


        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        user1.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Your profile is deleted:", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }


    public void forgetpassword(View view) {
        String user = UserId.getText().toString().trim();
        auth.sendPasswordResetEmail(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"check your email to reset your password",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"faild to send reset password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
