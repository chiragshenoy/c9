package app.cloud9.com.cloud9;

/**
 * Created by chirag on 20/11/14.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chirag on 06-11-2014.
 */
public class Subject2 extends Fragment {

    JSONObject full_json;
    JSONObject json_current_subject;

    String all_marks_string;
    String subject_name_string;

    String current_subject_string;

    //Strings
    String s_i1;
    String s_i2;
    String s_i3;

    String s_l1;
    String s_l2;

    String s_el;

    String s_q1;
    String s_q2;

    String s_lh;
    String s_la;

    String s_th;
    String s_ta;

    String s_final_cie_marks;
    ImageView subject_icon;

    TextView hoarding;

    int first_internal_theory;
    int second_internal_theory;
    int third_internal_theory;

    int first_quiz;
    int second_quiz;

    int first_lab_internals;
    int second_lab_internals;

    int classes_held;
    int classes_attended;

    int current_total;

    float attendance_status;

    ProgressBar pb1;
    TextView tv_i1_marks;
    TextView internal_number1;

    ProgressBar pb2;
    TextView tv_i2_marks;
    TextView internal_number2;

    ProgressBar pb3;
    TextView tv_i3_marks;
    TextView internal_number3;

    TextView tv_q1_marks;
    TextView tv_q2_marks;


    TextView tv_l1_marks;
    TextView tv_l2_marks;

    TextView tv_current_total;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View d1 = inflater.inflate(R.layout.display_marks_and_attendance, container, false);
        final String[] color_list = {"#009688", "#FF5722", "#673AB7", "#00BCD4", "#CDDC39", "#FFC107", "#9E9E9E"};
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/myfont.ttf");

        hoarding = (TextView) d1.findViewById(R.id.current_total_hoarding);
        hoarding.setTypeface(face);

        tv_q1_marks = (TextView) d1.findViewById(R.id.tv_q1_marks);
        tv_q2_marks = (TextView) d1.findViewById(R.id.tv_q2_marks);
        tv_l1_marks = (TextView) d1.findViewById(R.id.tv_l1_marks);
        tv_l2_marks = (TextView) d1.findViewById(R.id.tv_l2_marks);


        pb1 = (ProgressBar) d1.findViewById(R.id.pb1);
        tv_i1_marks = (TextView) d1.findViewById(R.id.tv_i1_marks);
        internal_number1 = (TextView) d1.findViewById(R.id.internal_number1);
        internal_number1.setTypeface(face);
        internal_number1.setText("#1");

        pb2 = (ProgressBar) d1.findViewById(R.id.pb2);
        tv_i2_marks = (TextView) d1.findViewById(R.id.tv_i2_marks);
        internal_number2 = (TextView) d1.findViewById(R.id.internal_number2);
        internal_number2.setTypeface(face);
        internal_number2.setText("#2");

        pb3 = (ProgressBar) d1.findViewById(R.id.pb3);
        tv_i3_marks = (TextView) d1.findViewById(R.id.tv_i3_marks);
        internal_number3 = (TextView) d1.findViewById(R.id.internal_number3);
        internal_number3.setTypeface(face);
        internal_number3.setText("#3");

        tv_current_total = (TextView) d1.findViewById(R.id.tv_current_total);

//        i1 = (TextView) d1.findViewById(R.id.i1);
//        i2 = (TextView) d1.findViewById(R.id.i2);
//        i3 = (TextView) d1.findViewById(R.id.i3);
//
//        i1.setTypeface(face);
//
//        q1 = (TextView) d1.findViewById(R.id.q1);
//        q2 = (TextView) d1.findViewById(R.id.q2);
//
//        la = (TextView) d1.findViewById(R.id.la);
//        lh = (TextView) d1.findViewById(R.id.lh);
//
//        ta = (TextView) d1.findViewById(R.id.ta);
//        th = (TextView) d1.findViewById(R.id.th);
//
//        final_cie_marks = (TextView) d1.findViewById(R.id.final_cie_marks);
//        el = (TextView) d1.findViewById(R.id.el);
//
//        l1 = (TextView) d1.findViewById(R.id.l1);
//        l2 = (TextView) d1.findViewById(R.id.l2);

        LinearLayout ll = (LinearLayout) d1.findViewById(R.id.ll);
        ll.setBackground(new ColorDrawable(Color.parseColor(color_list[1])));

        Temp activity = (Temp) getActivity();

        String all_marks_string = activity.getAllMarks();

        subject_icon = (ImageView) d1.findViewById(R.id.subject_icon);
        String uri = "@drawable/";
        String subject_name_string = activity.getPageTitle(1);
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

        //First Internal
        try {
            s_i1 = json_current_subject.getString("internal_1");
            first_internal_theory = Integer.parseInt(s_i1);
            pb1.setProgress(first_internal_theory);
            SpannableString ss1 = new SpannableString(s_i1 + "/40");
            ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 2, 0);// set color
            tv_i1_marks.setTypeface(face);
            tv_i1_marks.setText(ss1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Second Internal
        try {
            s_i2 = json_current_subject.getString("internal_2");
            second_internal_theory = Integer.parseInt(s_i2);
            pb2.setProgress(second_internal_theory);
            SpannableString ss1 = new SpannableString(s_i2 + "/40");
            ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 2, 0);// set color
            tv_i2_marks.setTypeface(face);
            tv_i2_marks.setText(ss1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_i3 = json_current_subject.getString("internal_3");
            third_internal_theory = Integer.parseInt(s_i3);
            pb3.setProgress(third_internal_theory);
            SpannableString ss1 = new SpannableString(s_i3 + "/40");
            ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 2, 0);// set color
            tv_i3_marks.setTypeface(face);
            tv_i3_marks.setText(ss1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Quizzes


        try {
            s_q1 = json_current_subject.getString("quiz_1");
            first_quiz = Integer.parseInt(s_q1);
            SpannableString ss1 = new SpannableString(s_q1 + "/5");
            ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 2, 0);// set color
            tv_q1_marks.setTypeface(face);
            tv_q1_marks.setText(ss1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_q2 = json_current_subject.getString("quiz_2");
            second_quiz = Integer.parseInt(s_q2);
            SpannableString ss1 = new SpannableString(s_q2 + "/5");
            ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 2, 0);// set color
            tv_q2_marks.setTypeface(face);
            tv_q2_marks.setText(ss1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Lab Internals
        try {
            s_l1 = json_current_subject.getString("lab_internal_1");
            first_lab_internals = Integer.parseInt(s_l1);
            SpannableString ss1 = new SpannableString(s_l1 + "/40");
            ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 2, 0);// set color
            tv_l1_marks.setTypeface(face);
            tv_l1_marks.setText(ss1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_l2 = json_current_subject.getString("lab_internal_2");
            second_lab_internals = Integer.parseInt(s_l2);
            SpannableString ss1 = new SpannableString(s_l2 + "/40");
            ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 2, 0);// set color
            tv_l2_marks.setTypeface(face);
            tv_l2_marks.setText(ss1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Theory Classes
        try {
            s_th = json_current_subject.getString("theory_classes_held");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            s_ta = json_current_subject.getString("theory_classes_attended");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Enternal Lab

        try {
            s_el = json_current_subject.getString("lab_external");
            //el.setText("Lab External : " + s_el);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Final Cie Marks

        try {
            s_final_cie_marks = json_current_subject.getString("final_cie_marks");
            //final_cie_marks.setText("Final cie marks : " + s_final_cie_marks);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Lab Held and Attended

        try {
            s_lh = json_current_subject.getString("lab_held");
            //lh.setText("Lab Held : " + s_lh);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            s_la = json_current_subject.getString("lab_attended");
            //la.setText("Lab Attended : " + s_la);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpannableString ss1 = new SpannableString("37/50");
        ss1.setSpan(new RelativeSizeSpan(2f), 0, 2, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 2, 0);// set color
        tv_q2_marks.setTypeface(face);
        tv_current_total.setText(ss1);
        return d1;


    }

}