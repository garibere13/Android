package com.example.gari.modern_art_ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends Activity implements OnSeekBarChangeListener {


    public static DialogFragment mDialog;
    public static String url="https://www.moma.org/";
    public SeekBar sb;
    private LinearLayout[] panels=new LinearLayout[5];
    private int[] original_Color=new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        panels[0]=findViewById(R.id.color_1);
        panels[1]=findViewById(R.id.color_2);
        panels[2]=findViewById(R.id.color_3);
        panels[3]=findViewById(R.id.color_4);
        panels[4]=findViewById(R.id.color_5);
        for (int i = 0; i < panels.length; i++)
        {
            original_Color[i]=((ColorDrawable)panels[i].getBackground()).getColor();
        }
        sb=findViewById(R.id.seekbar);
        sb.setOnSeekBarChangeListener(MainActivity.this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu) {

            mDialog = AlertDialogFragment.newInstance();
            mDialog.show(getFragmentManager(), "Alert");

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        for (int i=0;i<panels.length;i++) {
            int color=original_Color[i];
            if (color!=0xffffffff&&color!=0xff888888) {
                int invert=(0xFFFFFF-color)|0xFF000000;
                int r=(color>>16)&0x000000FF;
                int g=(color>>8)&0x000000FF;
                int b=(color)&0x000000FF;
                int invr=(invert>>16)&0x000000FF;
                int invg=(invert>>8)&0x000000FF;
                int invb=(invert)&0x000000FF;
                int new_Color = Color.rgb(
                        (int) (r+((invr-r)*(progress/100f))),
                        (int) (g+((invg-g)*(progress/100f))),
                        (int) (b+((invb-b)*(progress/100f))));
                panels[i].setBackgroundColor(new_Color);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    // Class that creates the AlertDialog
    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        // Build AlertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Inspired by the works of artists such as Piet Mondrian and Ben Nicholson. \nClick below to learn more!")

                    // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                    // Set up No Button
                    .setNegativeButton("Visit MOMA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            })

                    // Set up Yes Button
                    .setPositiveButton("Not Now",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    mDialog.dismiss();
                                }
                            }).create();
        }
    }
}
