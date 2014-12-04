package app.cloud9.com.cloud9;

/**
 * Created by chirag on 20/11/14.
 */

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private LayoutInflater mInflater;
    private ViewGroup mContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View d1 = inflater.inflate(R.layout.display_marks_and_attendance, container, false);
        mInflater = inflater;
        mContainer = container;

            SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) d1.findViewById(R.id.swipe_container);
            //swipeLayout.setOnRefreshListener();


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
               System.out.println("Refresh code below");
            }
        });


            //Get the index of fragment
        mIndex = getArguments().getInt("index");

        final String[] color_list = getResources().getStringArray(R.array.subjectMainColors);
        final String[] accent_list =  getResources().getStringArray(R.array.subjectAccentColors);

        swipeLayout.setColorSchemeColors(Color.parseColor(accent_list[mIndex]),
                Color.parseColor(accent_list[mIndex]),
                Color.parseColor(accent_list[mIndex]),
                Color.parseColor(accent_list[mIndex]));



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

        int pgId = getResources().getIdentifier("pd"+(mIndex + 1), "drawable","app.cloud9.com.cloud9");


        for(int i = 0; i<3; i++){

                try {

                    internal_marks[i] = json_current_subject.getString("internal_" + (i + 1));
                    try {
                        progressBar[i].setProgressDrawable(getResources().getDrawable(pgId));
                    }
                    catch (Exception e){
                        System.out.println("Drawable error for mindex = " + mIndex);
                    }

                    if(android.os.Build.VERSION.SDK_INT >= 11){
                        // will update the "progress" propriety of seekbar until it reaches progress
                        progressBar[i].setProgress(0);
                        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar[i], "progress", Integer.parseInt(internal_marks[i]));
                        animation.setDuration(600); // 0.5 second
                        animation.setInterpolator(new DecelerateInterpolator());
                        animation.start();
                    }
                    else
                      progressBar[i].setProgress(Integer.parseInt(internal_marks[i]));
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

        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, calculatedAttendance);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_attendance.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round((endValue - startValue) * fraction);
            }
        });
        animator.setDuration(1000);
        animator.start();

        //tv_attendance.setText(String.valueOf(calculatedAttendance));

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

        final_cie_total = "37";
        ValueAnimator cie_animator = new ValueAnimator();
        cie_animator.setObjectValues(0, Integer.parseInt(final_cie_total));
        cie_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_cie_total.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        cie_animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round((endValue - startValue) * fraction);
            }
        });
        cie_animator.setDuration(800);
        cie_animator.start();

        //tv_cie_total.setText("37");
        return d1;

    }

    public void onRefresh() {

    }

}