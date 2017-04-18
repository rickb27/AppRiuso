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

import org.w3c.dom.Text;

public class SetObjectActvity extends Activity implements View.OnClickListener {

    /*
    ** Define
     */
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
    int curPage = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setobject);

        //Setup buttons
        buttonCategObject = (Button) findViewById(R.id.categOggetto);
        buttonCategObject.setOnClickListener(this);
        buttonNextStep = (Button) findViewById(R.id.btnNextStep);
        buttonNextStep.setOnClickListener(this);
    }


    @Override
    public void onContentChanged () {

        if (curPage == STEP_0) {
            buttonCategObject = (Button) findViewById(R.id.categOggetto);
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

        //Next step
        if (view == buttonNextStep || view == buttonNextStep1) {doNextStep(0);}

        //Prew step
        if (view == buttonBackStep1) {doNextStep(1);}
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
}
