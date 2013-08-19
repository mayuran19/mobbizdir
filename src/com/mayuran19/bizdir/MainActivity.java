package com.mayuran19.bizdir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayuran19.bizdir.adaptor.ImageAdaptor;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.mayuran19.bizdir.MESSAGE";
	private ListView listView;
	static final String[] CATEGORIES = new String[] { "Android", "iOS",
			"Windows", "Blackberry" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) this.findViewById(R.id.bizCategoryListView);
//		gridView.setAdapter(new ImageAdaptor(this, CATEGORIES));
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View v,
//					int position, long id) {
//				Toast.makeText(
//						getApplicationContext(),
//						((TextView) v.findViewById(R.id.grid_item_label))
//								.getText(), Toast.LENGTH_SHORT).show();
//			}
//		});
		BusinessCategoryWebService businessCategoryWebService = new BusinessCategoryWebService();
		businessCategoryWebService.execute(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// public void sendMessage(View view) {
	// Intent intent = new Intent(this, DisplayMessageActivity.class);
	// EditText editText = (EditText) findViewById(R.id.edit_message);
	// String message = editText.getText().toString();
	// intent.putExtra(EXTRA_MESSAGE, message);
	// startActivity(intent);
	// }

	public void sendMessage(View view) {
		Intent intent = new Intent(this, BizCategoryGridView.class);
		startActivity(intent);
	}

	private class BusinessCategoryWebService extends
			AsyncTask<Object, String, String> {
		private Context context = null;

		@Override
		protected String doInBackground(Object... params) {
			Activity activity = (Activity) params[0];
			this.context = activity.getApplicationContext();
			StringBuilder stringBuilder = new StringBuilder();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(
					"http://mayuran19-bizdir.herokuapp.com/business_categories/root_business_categories.json?authentication_key=G2sD9MUo5zHxCYe8diK2");
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				StatusLine statusLine = httpResponse.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = httpResponse.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line);
					}
				} else {
					Log.e(BusinessCategoryWebService.class.toString(),
							"Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
			return stringBuilder.toString();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				JSONArray jsonArray = new JSONArray(result);
				String[] array = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					array[i] = jsonArray.getJSONObject(i).getString("name");
				}
				listView.setAdapter(new ImageAdaptor(context, array));
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						Toast.makeText(
								getApplicationContext(),
								((TextView) v
										.findViewById(R.id.grid_item_label))
										.getText(), Toast.LENGTH_SHORT).show();
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

}
