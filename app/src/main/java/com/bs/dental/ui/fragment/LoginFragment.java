package com.bs.dental.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.dental.MainActivity;
import com.bs.dental.R;
import com.bs.dental.model.LoginData;
import com.bs.dental.model.LoginDataTurn;
import com.bs.dental.model.LoginResponse;
import com.bs.dental.model.LoginResponseTurn;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.RetroClientTurn;
import com.bs.dental.networking.postrequest.FacebookLogin;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.UiUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Ashraful on 11/30/2015.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.login_button)
    Button loginButton;
    @InjectView(R.id.register_button)
    Button registerButton;
    @InjectView(R.id.et_login_email)
    EditText emailEditText;
    @InjectView(R.id.et_login_password)
    EditText passwordEditText;
    private CallbackManager callbackManager;

    @Inject
    PreferenceService preferenceService;
    private View rootView;

    @InjectView(R.id.forgetPassword)
    TextView forgetPassword;

    private String email, pass;
    private FloatingActionMenu fam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getActivity().setTitle(getString(R.string.login));
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton facebook = (LoginButton) rootView.findViewById(R.id.fb);
        facebook.setReadPermissions("email");
        facebook.setFragment(this);
        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookLogin", "From LoginButton");
                onFacebookLogin(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    public void callLoginWebservice(LoginData loginData) {
        RetroClient.getApi().performLogin(loginData).enqueue(new CustomCB<LoginResponse>(this.getView()));
    }

    public void onEvent(LoginResponse response) {
        if (response.getToken() != null) {
            preferenceService.SetPreferenceValue(PreferenceService.TOKEN_KEY, response.getToken());
            preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY, true);
            /*NetworkUtilities.token=response.getToken();*/
            NetworkUtil.setToken(response.getToken());
            Log.e("token", ">>" + NetworkUtil.getToken());
            showToast(getString(R.string.login_succssful));
            //*** mmkr: getFragmentManager().popBackStack();

            // TODO: 5/7/2019 --- mmkr : densyx features for check user for doctor role
            callLoginTurnWebservice(new LoginDataTurn(response.getUsername(), response.getPhone(), "false", "/"));
            //***
        } else {
            showSnack(getString(R.string.username_password_not_match));
        }
    }

    // TODO: 5/8/2019 --- mmkr : turn api for densyx features
    public void callLoginTurnWebservice(LoginDataTurn loginDataTurn) {
        RetroClientTurn.getApi().performLoginTurn(loginDataTurn).enqueue(new Callback<LoginResponseTurn>() {
            @Override
            public void onResponse(Call<LoginResponseTurn> call, Response<LoginResponseTurn> response) {
                if (response.isSuccessful()) {
                    LoginResponseTurn lrt = response.body();
                    String cookie = response.headers().get("Set-Cookie");
                    //Toast.makeText(getActivity(), "Login_Doctor : SUCCESS \n" + lrt.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("LOGIN", "LOGIN_TURN : RESPONSE : " + lrt.toString());
                    Log.d("LOGIN", "LOGIN_TURN : COOKIE : " + cookie);
                    preferenceService.SetPreferenceValue(PreferenceService.COOKIE_TURN, cookie);

                    // show turn menu for doctor
                    if (lrt.getResultCode() == 1004) {
                        showTurnMenu();
                        preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY_TURN, true);
                        showSnack("از کلید شناور برای اطلاعات تکمیلی استفاده نمایید");
                    }

                    getFragmentManager().popBackStack();


                } else {
                    Log.d("LOGIN", "LOGIN_TURN : ERROR : " + response.errorBody().toString());
                    Toast.makeText(getActivity(), "LOGIN_TURN : ERROR \n" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseTurn> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
        if (resourceId == R.id.login_button) {
            performLogin();
            UiUtils.hideSoftKeyboard(getActivity());
        } else if (resourceId == R.id.register_button) {

            getFragmentManager().beginTransaction().replace(R.id.container,
                    new RegisterFragment()).addToBackStack(null).commit();

        } else if (resourceId == forgetPassword.getId()) {
            getFragmentManager().beginTransaction().replace(R.id.container,
                    new ForgotPasswordFragment()).addToBackStack(null).commit();
        }
    }

    private void performLogin() {
        email = emailEditText.getText().toString();
        pass = passwordEditText.getText().toString();
        if (email.length() > 0 && pass.length() > 0) {
            callLoginWebservice(new LoginData(email, pass));
        } else {
            showSnack(getString(R.string.username_password_require));
        }
    }

    private void showTurnMenu() {
        //MainActivity a = (MainActivity) rootView.getParent();
        fam = (FloatingActionMenu) getActivity().findViewById(R.id.fam);
        fam.setVisibility(View.VISIBLE);
    }

    private void onFacebookLogin(LoginResult loginResult) {
        final AccessToken accessToken = loginResult.getAccessToken();
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String name = object.getString("name");
                            String email = object.getString("email");

                            FacebookLogin facebookLogin = new FacebookLogin();
                            facebookLogin.setUserId(accessToken.getUserId());
                            facebookLogin.setAccessToken(accessToken.getToken());
                            facebookLogin.setEmail(email);
                            facebookLogin.setName(name);

                            RetroClient.getApi().loginUsingFaceBook(facebookLogin).enqueue(new CustomCB<LoginResponse>(getView()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoginManager.getInstance().logOut();
                            Toast.makeText(getApplicationContext(), getString(R.string.email_permission_require), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}