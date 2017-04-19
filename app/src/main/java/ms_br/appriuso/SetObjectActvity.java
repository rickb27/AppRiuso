package ms_br.appriuso;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface.OnClickListener;
import android.widget.PopupMenu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import ms_br.appriuso.app.AppConfig;
import ms_br.appriuso.app.AppController;
import ms_br.appriuso.app.SingletonLatLng;
import ms_br.appriuso.helper.SQLiteHandler;
import ms_br.appriuso.helper.SQLiteHandlerObject;
import ms_br.appriuso.helper.SessionManager;

public class SetObjectActvity extends Activity implements View.OnClickListener {

    /*
    ** Define
     */
    private static final String TAG = SetObjectActvity.class.getSimpleName();
    public static final int MAX_STEP = 1;
    public static final int STEP_0 = 0;
    public static final int STEP_1 = 1;
    public static final int STEP_2 = 2;

    /*
    ** Variable
     */
    Button buttonCategObject;
    Button buttonNextStep;
    Button buttonNextStep1;
    Button buttonBackStep1;
    Button buttonConfirm;
    EditText inputObjectTitle;
    EditText inputObjectDescription;
    int curPage = 0;
    private SQLiteHandlerObject dbObj;
    private SQLiteHandler dbUsr;
    String credentialName;
    String credentialPassword;
    private ProgressDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setobject);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Setup buttons
        buttonCategObject = (Button) findViewById(R.id.btnObjectCategory);
        buttonCategObject.setOnClickListener(this);
        buttonNextStep = (Button) findViewById(R.id.btnNextStep);
        buttonNextStep.setOnClickListener(this);

        //Setup input field
        inputObjectTitle = (EditText) findViewById(R.id.objectTitle);
        inputObjectDescription = (EditText) findViewById(R.id.objectDescription);

        // SQLite database handler
        dbUsr = new SQLiteHandler(getApplicationContext());
        dbObj = new SQLiteHandlerObject(getApplicationContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = dbUsr.getUserDetails();

        credentialName = user.get("name");
        credentialPassword = user.get("email");
    }


    @Override
    public void onContentChanged () {

        if (curPage == STEP_0) {
            buttonCategObject = (Button) findViewById(R.id.btnObjectCategory);
            buttonCategObject.setOnClickListener(this);
            buttonNextStep = (Button) findViewById(R.id.btnNextStep);
            buttonNextStep.setOnClickListener(this);
        }
        if (curPage == STEP_1) {
            buttonBackStep1 = (Button) findViewById(R.id.btnBackStep1);
            buttonBackStep1.setOnClickListener(this);
            buttonNextStep1 = (Button) findViewById(R.id.btnNextStep1);
            buttonNextStep1.setOnClickListener(this);
        }
    }

    /*
    ** Buttons management
     */
    @Override
    public void onClick(View view) {

        // Open category choose
        if (view == buttonCategObject) {doPopupCategory();}

        // Next step
        if (view == buttonNextStep || view == buttonNextStep1) {/*doNextStep(0);*/doConfirm();}

        // Prew step
        if (view == buttonBackStep1) {doNextStep(1);}

        // Confirm object details
        if (view == buttonConfirm) {doConfirm();}

    }

    /*
    ** Logic category choose
     */
    public void doPopupCategory() {

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(SetObjectActvity.this, buttonCategObject);

        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_setobject, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(SetObjectActvity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                buttonCategObject.setText(item.getTitle());

                return true;
            }
        });
        //showing popup menu
        popup.show();
    }

    /*
    ** Next step management
     */
    public void doNextStep(int modo) {

        //Index
        if (modo == 0) {
            if (curPage++ > MAX_STEP) curPage = MAX_STEP;
        }
        if (modo == 1) {
            if (curPage-- < 0) curPage = 0;
        }
        //Layout change logic
        switch (curPage) {

            case STEP_0:
                setContentView(R.layout.activity_setobject); break;
            case STEP_1:
                setContentView(R.layout.activity_uploadimage); break;
        }
    }

    public void doConfirm() {

        String category = buttonCategObject.getText().toString();
        String title = inputObjectTitle.getText().toString().trim();
        String description = inputObjectDescription.getText().toString().trim();
        String name = credentialName;
        String email = credentialPassword;
        double latitude = SingletonLatLng.getInstance().getLatLng().latitude;
        double longitude = SingletonLatLng.getInstance().getLatLng().longitude;

        if (!category.isEmpty() && !title.isEmpty() && !description.isEmpty()) {

            registerObject(category, title, description, name, email, latitude, longitude);
        } else {

            Toast.makeText(getApplicationContext(), "Please enter all details!", Toast.LENGTH_LONG).show();
        }
    }


    public void registerObject(final String category, final String title,
                                final String description, final String name, final String email,
                                    final double latitude, final double longitude) {

        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                        AppConfig.URL_REGISTER_OBJECT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Obj Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject obj= jObj.getJSONObject("object");
                        String category = obj.getString("category");
                        String title = obj.getString("title");
                        String description = obj.getString("description");
                        String latitude = obj.getString("latitude");
                        String longitude = obj.getString("longitude");
                        String image1 = obj.getString("image1");
                        String image2 = obj.getString("image2");
                        String image3 = obj.getString("image3");
                        String image4 = obj.getString("image4");
                        String image5 = obj.getString("image5");
                        String image6 = obj.getString("image6");
                        String image7 = obj.getString("image7");
                        String image8 = obj.getString("image8");
                        String name = obj.getString("name");
                        String email = obj.getString("email");
                        String created_at = obj.getString("created_at");

                        // Inserting row in users table
                        dbObj.addObject(category, title, description,
                                //Double.parseDouble(latitude), Double.parseDouble(longitude),
                                latitude, longitude,
                                    name, email, image1, image2, image3, image4, image5, image6,
                                        image7, image8, uid, created_at);

                        Toast.makeText(getApplicationContext(), "Object registered! Enjoy",
                                Toast.LENGTH_LONG).show();

                        // Launch login activity
                        /*Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);*/
                        //finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("category", category);
                params.put("title", title);
                params.put("description", description);
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                params.put("name", name);
                params.put("email", email);
                params.put("image1", "link_image");
                params.put("image2", "link_image");
                params.put("image3", "link_image");
                params.put("image4", "link_image");
                params.put("image5", "link_image");
                params.put("image6", "link_image");
                params.put("image7", "link_image");
                params.put("image8", "link_image");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "req_register");


    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
