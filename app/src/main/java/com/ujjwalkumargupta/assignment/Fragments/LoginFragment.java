package com.ujjwalkumargupta.assignment.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ujjwalkumargupta.assignment.Application.MyApplication;
import com.ujjwalkumargupta.assignment.Common.CommonFragment;
import com.ujjwalkumargupta.assignment.LocalDatabase.SharedPrefKeys;
import com.ujjwalkumargupta.assignment.LocalDatabase.SharedPreferenceTask;
import com.ujjwalkumargupta.assignment.R;
import com.ujjwalkumargupta.assignment.ServerTask.AppConfig;
import com.ujjwalkumargupta.assignment.Utils.TaskUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends CommonFragment {
    private String TAG = LoginFragment.class.getName();
    private View rootView;
    private EditText etUserName, etUserPassword;
    private Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initialize();
        action();
        return rootView;
    }

    private void initialize() {
        etUserName = (EditText) rootView.findViewById(R.id.et_user_name);
        etUserPassword = (EditText) rootView.findViewById(R.id.et_user_pass);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
    }

    private void action() {
        mFragmentContext.getSupportActionBar().setTitle("Login");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                if (etUserName.getText().toString().equals("")){
                    etUserName.setError("Enter Username");
                } else if (etUserPassword.getText().toString().equals("")){
                    etUserPassword.setError("Enter Password");
                } else {
                    requestForLogin(etUserName.getText().toString(), etUserPassword.getText().toString());
                }
            }
        });
    }

    private void requestForLogin(final String email, final String password) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(mFragmentContext);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        String tag_string_req = "login_req";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                progressDialog.dismiss();
                processResult(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void processResult(String result) {
        if (TaskUtils.checkingSuccess(result)){
            new SharedPreferenceTask(mFragmentContext).putStringValueIntoSPref(SharedPrefKeys.SP_USER_LOGIN_KEY, "logged_in");
            replaceFramgentWithoutBackStack(new DashboardFragment());
        } else {
            Toast.makeText(mFragmentContext, "Login Failed!\nPlease Try Again", Toast.LENGTH_LONG).show();
        }
    }

}
