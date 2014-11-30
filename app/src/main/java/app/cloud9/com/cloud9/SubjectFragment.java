package app.cloud9.com.cloud9;

/**
 * Created by chirag on 20/11/14.
 */

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;
import android.graphics.Paint.Style;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chirag on 06-11-2014.
 */
public class SubjectFragment extends Fragment {

    JSONObject full_json;
    JSONObject json_current_subject;

    String all_marks_string;
    String subject_name_string;

    String current_subject_string;

    //Strings
    String internal_marks[] = new String[3];

    String quiz_marks[] = new String[2];

    String lab_marks[] = new String[2];
    String lab_externals;
    String lab_attended;
    String lab_held;

    String theory_attended;
    String theory_held;

    String final_cie_total;

    TextView hoarding;
    ImageView subject_icon;

    ProgressBar progressBar[] = new ProgressBar[3];

    TextView tv_internal_marks[] = new TextView[3];
    TextView tv_quiz_marks[] = new TextView[2];
    TextView tv_lab_marks[] = new TextView[2];
    TextView tv_attendance;
    TextView tv_cie_total;

    int calculatedAttendance;

    int mIndex;

    RelativeLayout attendanceCircle;


    public static LayerDrawable createDrawable(String accentColor, int back) {

        ShapeDrawable shape = new ShapeDrawable();
        shape.getPaint().setStyle(Style.FILL);
        shape.getPaint().setColor(Color.parseColor(accentColor));

        ShapeDrawable shapeD = new ShapeDrawable();
        shapeD.getPaint().setStyle(Style.FILL);
        shapeD.getPaint().setColor(Color.BLUE);
        ClipDrawable clipDrawable = new ClipDrawable(shapeD, Gravity.LEFT,
                ClipDrawable.HORIZONTAL);

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] {
                 clipDrawable });
        return layerDrawable;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View d1 = inflater.inflate(R.layout.display_marks_and_attendance, container, false);

        //Get the index of fragment
        mIndex = getArguments().getInt("index");

        final String[] color_list = getResources().getStringArray(R.array.subjectMainColors);
        final String[] accent_list =  getResources().getStringArray(R.array.subjectAccentColors);


        attendanceCircle = (RelativeLayout) d1.findViewById(R.id.circle);
        Temp activity = (Temp) getActivity();
        String all_marks_string = activity.getAllMarks();

        GradientDrawable bgShape = (GradientDrawable) attendanceCircle.getBackground();
        bgShape.setColor(Color.parseColor(accent_list[mIndex]));

        hoarding = (TextView) d1.findViewById(R.id.current_total_hoarding);

        tv_quiz_marks[0] = (TextView) d1.findViewById(R.id.tv_q1_marks);
        tv_quiz_marks[1] = (TextView) d1.findViewById(R.id.tv_q2_marks);

        tv_lab_marks[0] = (TextView) d1.findViewById(R.id.tv_l1_marks);
        tv_lab_marks[1] = (TextView) d1.findViewById(R.id.tv_l2_marks);

        tv_internal_marks[0] =  (TextView) d1.findViewById(R.id.tv_i1_marks);
        tv_internal_marks[1] =  (TextView) d1.findViewById(R.id.tv_i2_marks);
        tv_internal_marks[2] =  (TextView) d1.findViewById(R.id.tv_i3_marks);

        tv_attendance = (TextView) d1.findViewById(R.id.tv_attendance);

        progressBar[0] = (ProgressBar) d1.findViewById(R.id.pb1);
        progressBar[1] = (ProgressBar) d1.findViewById(R.id.pb2);
        progressBar[2] = (ProgressBar) d1.findViewById(R.id.pb3);

        tv_cie_total = (TextView) d1.findViewById(R.id.tv_current_total);

        LinearLayout ll = (LinearLayout) d1.findViewById(R.id.ll);
        ll.setBackground(new ColorDrawable(Color.parseColor(color_list[mIndex])));


        subject_icon = (ImageView) d1.findViewById(R.id.subject_icon);
        String uri = "@drawable/";
        String subject_name_string = activity.getPageTitle(mIndex);
        String st = subject_name_string.replaceAll("\\s", "");

        uri = uri + st;

        int imageResource = getResources().getIdentifier(uri, null, "app.cloud9.com.cloud9");


        Drawable res = getResources().getDrawable(imageResource);
        subject_icon.setImageDrawable(res);

        try {
            full_json = new JSONObject(all_marks_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            current_subject_string = full_json.getString(subject_name_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json_current_subject = new JSONObject(current_subject_string);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Get 3 internals marks

            for(int i = 0; i<3; i++){

                try {

                    internal_marks[i] = json_current_subject.getString("internal_" + (i + 1));
                    progressBar[i].setProgress(Integer.parseInt(internal_marks[i]));
                    progressBar[i].setProgressDrawable(createDrawable(accent_list[mIndex],getResources().getColor(R.color.progressBack)));
                    tv_internal_marks[i].setText(internal_marks[i]);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        //Get 2 quiz and lab marks

        for(int i = 0; i<2; i++){

            try {

                quiz_marks[i] = json_current_subject.getString("quiz_" + (i + 1));
                tv_quiz_marks[i].setText(quiz_marks[i]);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                lab_marks[i] = json_current_subject.getString("lab_internal_" + (i + 1));
                tv_lab_marks[i].setText(lab_marks[i]);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //Get attendance

        try {
            theory_held = json_current_subject.getString("theory_classes_held");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            theory_attended = json_current_subject.getString("theory_classes_attended");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            lab_held = json_current_subject.getString("lab_held");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            lab_attended = json_current_subject.getString("lab_attended");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Set attendance after calculation

        System.out.println(Integer.parseInt(theory_attended) / Integer.parseInt(theory_held) * 100);
        calculatedAttendance = (int)((Integer.parseInt(theory_attended) * 100.0f) / Integer.parseInt(theory_held));
        tv_attendance.setText(String.valueOf(calculatedAttendance));

        try {
            lab_externals = json_current_subject.getString("lab_external");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Final Cie Marks

        try {
            final_cie_total = json_current_subject.getString("final_cie_marks");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tv_cie_total.setText("37");
        return d1;


        //Progress Bars






    }

}