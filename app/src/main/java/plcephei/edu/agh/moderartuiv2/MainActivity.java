package plcephei.edu.agh.moderartuiv2;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 *
 *  Contains MainActivity class
 *
 * @author Piotr Ptak
 * @version 1.0
 */

/**
 *  MainActivity of application
 */
public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getName();

    private RelativeLayout palette;

    /**
     * Called when the activity is starting.
     * <p/>
     * Sets up the main activity content view and registers a change handler for the activities
     * seek bar.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most
     *                           recently supplied.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        palette = ( RelativeLayout ) findViewById( R.id.palette ); // initialize palette of views
        SeekBar seek = ( SeekBar ) findViewById( R.id.seekBar );   // initialize seekbar

        int size = palette.getChildCount() + 1;     // number of views in palette (needed for a for loop)

        int[] initialColor = new int[size];         // array of initial color of the view in palette
        final int[] initialRed = new int[size];     // array of the red channel of the view in the palette (needed for inversion)
        final int[] initialGreen = new int[size];   // array of the green channel of the view in the palette (needed for inversion)
        final int[] initialBlue = new int[size];    // array of the blue channel of the view in the palette (needed for inversion)
        final int[] inverseRed = new int[size];     // array of the red channel of inversed color of the view in the palette (needed for inversion)
        final int[] inverseGreen = new int[size];   // array of the green channel of inversed color of the view in the palette (needed for inversion
        final int[] inverseBlue = new int[size];    // array of the blue channel of inversed color of the view in the palette (needed for inversion)
        for( int i = 0; i < palette.getChildCount(); i++)   // iterating over all views in the palette to fill all arrays mentioned above
        {
            View child = palette.getChildAt(i);
            initialColor[i] = Color.parseColor((String) child.getTag());
            initialRed[i] = Color.red(initialColor[i]);
            initialGreen[i] = Color.green(initialColor[i]);
            initialBlue[i] = Color.blue(initialColor[i]);

            inverseRed[i] = 255 - initialRed[i];
            inverseGreen[i] = 255 - initialGreen[i];
            inverseBlue[i] = 255 - initialBlue[i];
        }



        seek.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {

                                             /**
                                              * Notification that the seek bar progress level has changed.
                                              *
                                              * When the seek bar state changes, loop through the child's of the palette view and
                                              * change each individuals view color based on the percentage that the seek bar is
                                              * currently at. Ignores views that are either white or some shade of gray.
                                              *
                                              * @param seekBar  The SeekBar whose progress has changed
                                              * @param progress The current progress level. This will be in the range 0..100
                                              * @param fromUser True if the progress change was initiated by the user.
                                              */
                                             @Override
                                             public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {
                                                 for ( int i = 0; i < palette.getChildCount(); i++ )
                                                 {
                                                     View child = palette.getChildAt( i );

                                                     int white = getResources().getColor(R.color.White); // defining white color for the comparison
                                                     int grey = getResources().getColor(R.color.Gray);   // defining grey color for the comparison
                                                     int initialColor = Color.parseColor((String) child.getTag());   // definining color of the view in palette to calculate gradient change

                                                     int red = (int) (initialRed[i] + (inverseRed[i] - initialRed[i])*(progress/100f));          //defining red channel of n-th step of progress of a gradient change for red channel
                                                     int green = (int) (initialGreen[i] + (inverseGreen[i] - initialGreen[i])*(progress/100f));  //defining green channel of n-th step of progress of a gradient change for green channel
                                                     int blue = (int) (initialBlue[i] + (inverseBlue[i] - initialBlue[i])*(progress/100f));      //defining blue channel of n-th step of progress of a gradient change for blue channel
                                                     int color = Color.rgb(red, green, blue);    // color of the n-th step of progress of a gradient change

                                                     if(initialColor != white && initialColor != grey)   // change color if the initial color is diffrent than white or grey assignment condition
                                                     {
                                                         child.setBackgroundColor(color);
                                                         child.invalidate();     // force the view to draw color
                                                     }
                                                 }
                                             }

                                             /**
                                              * unused method
                                              *
                                              * @param seekBar
                                              */
                                             @Override
                                             public void onStartTrackingTouch(SeekBar seekBar) {

                                             }

                                             /**
                                              *  unused method
                                              *
                                              * @param seekBar
                                              */
                                             @Override
                                             public void onStopTrackingTouch(SeekBar seekBar) {

                                             }
                                         }
        );
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
        if (id == R.id.more_information) {
            showMoreInfoDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMoreInfoDialog() {
        new MoreInformationDialog().show(getFragmentManager(), TAG);
    }
}
