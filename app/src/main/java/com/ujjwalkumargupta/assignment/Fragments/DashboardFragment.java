package com.ujjwalkumargupta.assignment.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends CommonFragment {
    private String TAG = "DashboardFragment";
    private View rootView;
    private Button btnLogout;
    private TextView tvName, tvEmail, tvToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initialize();
        action();
        return rootView;
    }

    private void initialize() {
        tvName = (TextView) rootView.findViewById(R.id.tv_dashboard_name);
        tvEmail = (TextView) rootView.findViewById(R.id.tv_dashboard_email);
        tvToken = (TextView) rootView.findViewById(R.id.tv_dashboard_token);
        btnLogout = (Button) rootView.findViewById(R.id.btn_logout);
    }

    private void action() {
        mFragmentContext.getSupportActionBar().setTitle("Dashboard");
        requestForDashboardContetnts();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreferenceTask(mFragmentContext).clearSPref();
                replaceFramgentWithoutBackStack(new LoginFragment());
            }
        });
    }

    private void requestForDashboardContetnts() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(mFragmentContext);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        String tag_string_req = "dashboard_req";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response: " + response.toString());
                progressDialog.dismiss();
                processResult(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "getData");
                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void processResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            tvName.setText("Name: " + jsonObject.getString("name"));
            tvEmail.setText("Email: " + jsonObject.getString("email"));
            tvToken.setText("Token: " + jsonObject.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
