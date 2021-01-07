package com.glasgowuniversity.assay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static boolean authenticated;
    private static String status;
    TextView userId;
    TextView userPassword;
    Button loginButton;
    TextView userName;
    String clientId = "79e8118eaa5fc3c85d6e";
    String clientSecret = "60c736bbb106f712b8e7db4ec6eee9fedb51907f";
    String redirectUri = "http://34.107.60.133:3000";
    protected static String port;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = findViewById(R.id.login_textView_user_id);
        userPassword =findViewById(R.id.login_textView_password);
        userName = findViewById(R.id.login_textView_username);

        loginButton = findViewById(R.id.login_button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(userId.getText().toString(), userName.getText().toString() ,userPassword.getText().toString());
            }
        });

//        Intent authorize = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize"
//                + "?client_id=" + clientId + "&redirect_uri=" + redirectUri));
//
//        startActivity(authorize);
    }

    public static boolean isAuthenticated(){
        return  authenticated;
    }

    public static void setAuthenticated(boolean authenticated){
        LoginActivity.authenticated = authenticated;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        LoginActivity.status = status;
    }

    private void login(String user, String username, String password){
        String[] users = {"O3001","O3002","O3003","M2001","M2002","M2003","A1001","A1002","A1003","Admin"};
        String[] passwords = {"qwertyO3","asdfgM2","zxcvbnA1","admin"};

        for(String s:users){
            for(String p:passwords){
                if(user.equals(s) && password.equals(p)){
                    if(user.startsWith("O") && password.equals(passwords[0])){
                        port = "3000";
                        setAuthenticated(true);
                        setStatus("Operator");
                        Operator.setOperatorID(user);
                        Operator.setOperatorUsername(username);
                    }
                    else if(user.startsWith("M") && password.equals(passwords[1])){
                        port = "3000";
                        setAuthenticated(true);
                        setStatus("Manufacturer");
                        Manufacturer.setManufacturerID(user);
                        Manufacturer.setManufacturerUsername(username);
                    }
                    else if(user.startsWith("A") && password.equals(passwords[2])){
                        port = "3000";
                        setAuthenticated(true);
                        setStatus("Analyst");
                        Analyst.setAnalystID(user);

                    }
                    else if(user.equals("Admin") && password.equals(passwords[3])){
                        port = "3000";
                        setAuthenticated(true);
                        setStatus("Admin");
                        Admin.setAdminId("Admin");
                        Admin.setAdminUsername(username);
                    }
                    if(isAuthenticated()) startActivity(new Intent(this, MainActivity.class));
                }
            }
        }
        if(!isAuthenticated()) Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {}
}
