package org.aliensource.symptommanagement.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.aliensource.symptommanagement.cloud.service.SecurityService;

import java.util.concurrent.Callable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 
 * This application uses ButterKnife. AndroidStudio has better support for
 * ButterKnife than Eclipse, but Eclipse was used for consistency with the other
 * courses in the series.
 * 
 * http://jakewharton.github.io/butterknife/ide-eclipse.html
 * 
 */
public class LoginScreenActivity extends Activity {

	@InjectView(R.id.userName)
	protected EditText userName_;

	@InjectView(R.id.password)
	protected EditText password_;

	@InjectView(R.id.server)
	protected EditText server_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		ButterKnife.inject(this);
	}

	@OnClick(R.id.loginButton)
	protected void login() {
		final String user = userName_.getText().toString();
		String pass = password_.getText().toString();
		String server = server_.getText().toString();

		final SecurityService svc = SecuritySvc.init(server, user, pass);

		CallableTask.invoke(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				return svc.hasRole("doesnotmatter");
			}
		}, new TaskCallback<Boolean>() {

			@Override
			public void success(Boolean result) {
				// OAuth 2.0 grant was successful and we
				// can talk to the server, open up the video listing
                Intent intent = new Intent(LoginScreenActivity.this, MainActivity.class);
                Bundle args = new Bundle();
                args.putString(MainActivity.ARGUMENT_USERNAME, user);
                intent.putExtras(args);

				startActivity(intent);
			}

			@Override
			public void error(Exception e) {
				Log.e(LoginScreenActivity.class.getName(), "Error logging in via OAuth.", e);
				
				Toast.makeText(
						LoginScreenActivity.this,
						"Login failed, check your Internet connection and credentials.",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
