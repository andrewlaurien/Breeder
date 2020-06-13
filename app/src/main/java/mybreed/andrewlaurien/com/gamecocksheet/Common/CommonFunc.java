package mybreed.andrewlaurien.com.gamecocksheet.Common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mybreed.andrewlaurien.com.gamecocksheet.Fragment.SectionFragment;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Breed;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Fight;
import mybreed.andrewlaurien.com.gamecocksheet.Model.GameCock;
import mybreed.andrewlaurien.com.gamecocksheet.R;

/**
 * Created by andrewlaurienrsocia on 08/01/2018.
 */

public class CommonFunc {


    static Dialog breedDialog = null;
    static Dialog fightDialog = null;
    static Dialog stagDialog = null;
    static DatePickerDialog datePickerDialog = null;


    public static void showDatePicker(Context mcontext, final TextInputEditText txtview) {


        int mYear, mMonth, mDay, mHour, mMinute;


        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int mymonth = monthOfYear + 1;
                        txtview.setText("" + year + "-" + mymonth + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }


    public static void showAddBreedingDialog(final Context mcontext, final int position) {

        breedDialog = new Dialog(mcontext);
        breedDialog.setCancelable(false);
        //breedDialog.setTitle("New Hatch");
        breedDialog.setContentView(R.layout.pop_breeding);
        breedDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        final TextInputEditText txtdateHatch = (TextInputEditText) breedDialog.findViewById(R.id.txtdateHatch);
        final TextInputEditText txtBroodCock = (TextInputEditText) breedDialog.findViewById(R.id.txtbroodCock);
        final TextInputEditText txtBroodHen = (TextInputEditText) breedDialog.findViewById(R.id.txtBroodHen);
        final TextInputEditText txtNumberHeads = (TextInputEditText) breedDialog.findViewById(R.id.txtNumberHeads);
        final TextInputEditText txtMarkings = (TextInputEditText) breedDialog.findViewById(R.id.txtMarkings);
        final TextInputEditText txtBroodCockWB = breedDialog.findViewById(R.id.txBroodCockWB);
        final TextInputEditText txtBroodHenWB = breedDialog.findViewById(R.id.txtBroodHenWB);

        if (MainActivity.selectedBreed != null) {
            txtdateHatch.setText(MainActivity.selectedBreed.getDateHatched());
            txtBroodCock.setText(MainActivity.selectedBreed.getBroodCock());
            txtBroodHen.setText(MainActivity.selectedBreed.getBroodHen());
            txtNumberHeads.setText(MainActivity.selectedBreed.getNumberHeads());
            txtMarkings.setText(MainActivity.selectedBreed.getMarkings());
            txtBroodCockWB.setText(MainActivity.selectedBreed.getBroodCockWB());
            txtBroodHenWB.setText(MainActivity.selectedBreed.getBroodHenWB());
        }

        txtdateHatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonFunc.showDatePicker(mcontext, txtdateHatch);
            }
        });

        Button btnAdd = (Button) breedDialog.findViewById(R.id.btnSave);
        Button btnCancel = (Button) breedDialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                breedDialog.dismiss();
                MainActivity.selectedBreed = null;
                clearEditText(new EditText[]{txtdateHatch, txtBroodCock, txtBroodHen, txtNumberHeads, txtMarkings, txtBroodCockWB, txtBroodHenWB});

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //JSONObject newBreed = new JSONObject();

                if (MainActivity.selectedBreed == null) {
                    Breed newBreed = new Breed();
                    newBreed.setUniqueID(getTimeLong());
                    newBreed.setDateHatched(txtdateHatch.getText().toString());
                    newBreed.setBroodCock(txtBroodCock.getText().toString());
                    newBreed.setBroodHen(txtBroodHen.getText().toString());
                    newBreed.setNumberHeads(txtNumberHeads.getText().toString());
                    newBreed.setMarkings(txtMarkings.getText().toString());
                    newBreed.setBroodCockWB(txtBroodCockWB.getText().toString());
                    newBreed.setBreedYear(CommonFunc.getYear(txtdateHatch.getText().toString()));
                    newBreed.setBroodHenWB(txtBroodHenWB.getText().toString());

                    MainActivity.myBreed.add(newBreed);
                    MainActivity.mDataBase.getReference("Breed").child(MainActivity.user.getMobile()).child(newBreed.getUniqueID()).setValue(newBreed);
//.child(newBreed.getBreedYear())
                } else {
                    MainActivity.selectedBreed.setDateHatched(txtdateHatch.getText().toString());
                    MainActivity.selectedBreed.setBroodCock(txtBroodCock.getText().toString());
                    MainActivity.selectedBreed.setBroodHen(txtBroodHen.getText().toString());
                    MainActivity.selectedBreed.setNumberHeads(txtNumberHeads.getText().toString());
                    MainActivity.selectedBreed.setMarkings(txtMarkings.getText().toString());
                    MainActivity.selectedBreed.setBroodCockWB(txtBroodCockWB.getText().toString());
                    MainActivity.selectedBreed.setBroodHenWB(txtBroodHenWB.getText().toString());
                    MainActivity.selectedBreed.setBreedYear(CommonFunc.getYear(txtdateHatch.getText().toString()));
                    MainActivity.myBreed.set(position, MainActivity.selectedBreed);
                    MainActivity.mDataBase.getReference("Breed").child(MainActivity.user.getMobile()).child(MainActivity.selectedBreed.getUniqueID()).setValue(MainActivity.selectedBreed);
                }//.child(MainActivity.selectedBreed.getBreedYear())

                Log.d("Size", "" + MainActivity.myBreed.size());
                setPreferenceObject(mcontext, MainActivity.myBreed, "Breed");

                // MainActivity.mDataBase.getReference("Breed").child(MainActivity.user.getMobile()).child("Breeds").setValue(MainActivity.myBreed);


                MainActivity.selectedBreed = null;
                breedDialog.dismiss();
                SectionFragment.adapter.notifyDataSetChanged();

                clearEditText(new EditText[]{txtdateHatch, txtBroodCock, txtBroodHen, txtNumberHeads, txtMarkings, txtBroodCockWB, txtBroodHenWB});

            }
        });


        breedDialog.show();

    }


    public static void showFightDialog(final Context mcontext, final int position) {

        fightDialog = new Dialog(mcontext);
        fightDialog.setCancelable(false);
        //fightDialog.setTitle("Fight");
        fightDialog.setContentView(R.layout.pop__fight);
        fightDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        final TextInputEditText txtdatefight = (TextInputEditText) fightDialog.findViewById(R.id.txtFightDate);
        final TextInputEditText txteventname = (TextInputEditText) fightDialog.findViewById(R.id.txteventname);
        final TextInputEditText txtentryname = (TextInputEditText) fightDialog.findViewById(R.id.txtEntryName);
        final TextInputEditText txtprize = (TextInputEditText) fightDialog.findViewById(R.id.txtprize);
        final TextInputEditText txtlocation = (TextInputEditText) fightDialog.findViewById(R.id.txtLocation);
        final TextInputEditText txtscore = fightDialog.findViewById(R.id.txtScore);
        final TextInputEditText txtheads = fightDialog.findViewById(R.id.txtFightCount);
        final TextInputEditText txtResult = fightDialog.findViewById(R.id.txtResult);
        final TextInputEditText txtamountwin = fightDialog.findViewById(R.id.txtamountwin);
        final TableLayout tblFight = fightDialog.findViewById(R.id.tblFight);

        if (MainActivity.selectedFight != null) {
            txtdatefight.setText(MainActivity.selectedFight.getFightDate());
            txteventname.setText(MainActivity.selectedFight.getFightName());
            txtentryname.setText(MainActivity.selectedFight.getEntryName());
            txtprize.setText(MainActivity.selectedFight.getPrize());
            txtlocation.setText(MainActivity.selectedFight.getFightLocation());
            txtscore.setText(MainActivity.selectedFight.getFightScore());
            txtheads.setText(MainActivity.selectedFight.getNumberOfFights());
            txtResult.setText(MainActivity.selectedFight.getResult());
            txtamountwin.setText(MainActivity.selectedFight.getAmountWin());
        }


        txtdatefight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonFunc.showDatePicker(mcontext, txtdatefight);
            }
        });

        txtheads.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try {
                    tblFight.removeAllViews();
                    int num = Integer.parseInt(txtheads.getText().toString());
                    append(mcontext, tblFight, Integer.parseInt(txtheads.getText().toString()));
                    Log.i("", num + " is a number");
                } catch (NumberFormatException e) {
                    Log.i("", txtheads.getText().toString() + " is not a number");
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        Button btnAdd = (Button) fightDialog.findViewById(R.id.btnSave);
        Button btnCancel = (Button) fightDialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fightDialog.dismiss();
                MainActivity.selectedFight = null;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.selectedFight == null) {
                    Fight fight = new Fight();

                    fight.setUniqueID(CommonFunc.getTimeLong());
                    fight.setFightDate(txtdatefight.getText().toString());
                    fight.setFightName(txteventname.getText().toString());
                    fight.setEntryName(txtentryname.getText().toString());
                    fight.setPrize(txtprize.getText().toString());
                    fight.setFightLocation(txtlocation.getText().toString());
                    fight.setFightScore(txtscore.getText().toString());
                    fight.setNumberOfFights(txtheads.getText().toString());
                    fight.setFightYear(CommonFunc.getYear(txtdatefight.getText().toString()));
                    fight.setResult(txtResult.getText().toString());
                    fight.setAmountWin(txtamountwin.getText().toString());

                    MainActivity.myFights.add(fight);

                    Log.d("Fight", fight.getFightYear());

                    MainActivity.mDataBase.getReference("Fights").child(MainActivity.user.getMobile()).child(fight.getUniqueID()).setValue(fight);

                    //.child(fight.getFightYear())
                    Log.d("Size", "" + MainActivity.myFights.size());
                } else {
                    if (MainActivity.selectedFight.getUniqueID() == null) {
                        MainActivity.selectedFight.setUniqueID(CommonFunc.getTimeLong());
                    }

                    MainActivity.selectedFight.setFightDate(txtdatefight.getText().toString());
                    MainActivity.selectedFight.setFightName(txteventname.getText().toString());
                    MainActivity.selectedFight.setEntryName(txtentryname.getText().toString());
                    MainActivity.selectedFight.setPrize(txtprize.getText().toString());
                    MainActivity.selectedFight.setFightLocation(txtlocation.getText().toString());
                    MainActivity.selectedFight.setFightScore(txtscore.getText().toString());
                    MainActivity.selectedFight.setNumberOfFights(txtheads.getText().toString());
                    MainActivity.selectedFight.setResult(txtResult.getText().toString());
                    MainActivity.selectedFight.setAmountWin(txtamountwin.getText().toString());
                    MainActivity.selectedFight.setFightYear(CommonFunc.getYear(txtdatefight.getText().toString()));
                    MainActivity.myFights.set(position, MainActivity.selectedFight);

                    Log.d("Fight", MainActivity.selectedFight.getFightYear());
                    Log.d("Fight", MainActivity.selectedFight.getUniqueID());
                    MainActivity.mDataBase.getReference("Fights").child(MainActivity.user.getMobile()).child(MainActivity.selectedFight.getUniqueID()).setValue(MainActivity.selectedFight);


                }//.child(MainActivity.selectedFight.getFightYear())

                setPreferenceObject(mcontext, MainActivity.myFights, "Fight");
                fightDialog.dismiss();
                MainActivity.selectedFight = null;
                SectionFragment.fightAdapter.notifyDataSetChanged();
            }
        });

        fightDialog.show();

    }


    public static void showAddCock(final Context mcontext, final int position) {

        final GameCock gameCock = new GameCock();

        stagDialog = new Dialog(mcontext);
        stagDialog.setCancelable(false);
        //stagDialog.setTitle("Add Stag/Cock");
        stagDialog.setContentView(R.layout.pop_stag);
        stagDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextInputEditText txtwingband = stagDialog.findViewById(R.id.txtwingband);
        final TextInputEditText txtbloodline = stagDialog.findViewById(R.id.txtBloodline);
        final TextInputEditText txtsirewb = stagDialog.findViewById(R.id.txtsirewb);
        final TextInputEditText txtdamwb = stagDialog.findViewById(R.id.txtdamwb);
        final TextInputEditText txtDateHatch = stagDialog.findViewById(R.id.txtDateHatch);


        if (MainActivity.selectedCock != null) {
            txtwingband.setText(MainActivity.selectedCock.getWingBand());
            txtbloodline.setText(MainActivity.selectedCock.getBloodline());
            txtsirewb.setText(MainActivity.selectedCock.getSireWB());
            txtdamwb.setText(MainActivity.selectedCock.getDamWB());
            txtDateHatch.setText(MainActivity.selectedCock.getDateHatch());
        }

        txtDateHatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonFunc.showDatePicker(mcontext, txtDateHatch);
            }
        });

        Button btnAdd = (Button) stagDialog.findViewById(R.id.btnSave);
        Button btnCancel = (Button) stagDialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedCock = null;
                stagDialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.selectedCock == null) {
                    gameCock.setWingBand(txtwingband.getText().toString());
                    gameCock.setBloodline(txtbloodline.getText().toString());
                    gameCock.setSireWB(txtsirewb.getText().toString());
                    gameCock.setDamWB(txtdamwb.getText().toString());
                    gameCock.setDateHatch(txtDateHatch.getText().toString());
                    gameCock.setYearBorn(CommonFunc.getYear(txtDateHatch.getText().toString()));
                    MainActivity.myGameCock.add(gameCock);
                    MainActivity.mDataBase.getReference("GameCocks").child(MainActivity.user.getMobile()).child(gameCock.getWingBand()).setValue(gameCock);
                } else {//.child(gameCock.getYearBorn())
                    MainActivity.selectedCock.setWingBand(txtwingband.getText().toString());
                    MainActivity.selectedCock.setBloodline(txtbloodline.getText().toString());
                    MainActivity.selectedCock.setSireWB(txtsirewb.getText().toString());
                    MainActivity.selectedCock.setDamWB(txtdamwb.getText().toString());
                    MainActivity.selectedCock.setDateHatch(txtDateHatch.getText().toString());
                    MainActivity.selectedCock.setYearBorn(CommonFunc.getYear(txtDateHatch.getText().toString()));
                    MainActivity.myGameCock.set(position, MainActivity.selectedCock);
                    MainActivity.mDataBase.getReference("GameCocks").child(MainActivity.user.getMobile()).child(MainActivity.selectedCock.getWingBand()).setValue(MainActivity.selectedCock);
                }//.child(MainActivity.selectedCock.getYearBorn())

                MainActivity.setCockList();

                setPreferenceObject(mcontext, MainActivity.myGameCock, "Cock");
                stagDialog.dismiss();
                MainActivity.selectedCock = null;
                SectionFragment.warriorsAdapter.notifyDataSetChanged();

            }
        });

        stagDialog.show();

    }


    public static void append(Context mcontext, TableLayout tblFight, int count) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mcontext,
                android.R.layout.simple_list_item_1, MainActivity.GameCockList);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);

        for (int i = 0; i <= count - 1; i++) {

            TableRow tblrow = new TableRow(mcontext);

            tblrow.setPadding(10, 10, 10, 10);

            AutoCompleteTextView meditText = new AutoCompleteTextView(mcontext);
            Switch mswitch = new Switch(mcontext);

            meditText.setId(i);
            meditText.setHint("Cock WB");
            meditText.setPadding(20, 20, 20, 20);
            meditText.setGravity(Gravity.LEFT);
            meditText.setHintTextColor(mcontext.getResources().getColor(R.color.colorAccent));
            meditText.setTextColor(mcontext.getResources().getColor(R.color.colorAccent));
            meditText.setBackgroundColor(mcontext.getResources().getColor(R.color.colorPrimaryDark));
            meditText.setLayoutParams(params);

            meditText.setAdapter(adapter);

            mswitch.setId(10 + i);
            mswitch.setGravity(Gravity.RIGHT);
            mswitch.setPadding(10, 20, 10, 20);
            //mswitch.setTextOff("Lose");
            mswitch.setMinimumWidth(60);
            //mswitch.setTextOn("Win");
            mswitch.setText("Result");
            //mswitch.setShowText(true);
            mswitch.setTextColor(mcontext.getResources().getColor(R.color.colorAccent));
            mswitch.setLayoutParams(params);

            tblrow.addView(meditText);
            tblrow.addView(mswitch);
            tblFight.addView(tblrow);
        }

    }

    public static void setPreferenceObject(Context c, Object modal, String key) {
        /**** storing object in preferences  ****/
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(
                c.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String jsonObject = gson.toJson(modal);
        Log.d("UserPre", "" + jsonObject.toString());
        prefsEditor.putString(key, jsonObject.toString());
        prefsEditor.commit();

    }

    //public static <GenericClass> GenericClass getPreferenceObjectJson(Context c, String key, Class<GenericClass> classType) {

    public static Object getPreferenceObjectJson(Context c, String key, Type classType) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        if (sharedPreferences.contains(key)) {
            Gson gson = new Gson();
            Log.d("UserObj", sharedPreferences.getString(key, "").toString());
            return gson.fromJson(sharedPreferences.getString(key, ""), classType);
        }
        return null;

    }


    public static String getTimeLong() {
        long time = System.currentTimeMillis();

        return String.valueOf(time);
    }


    public static void clearEditText(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            currentField.setText("");
//            if (currentField.getText().toString().length() <= 0) {
//                return false;
//            }
        }

    }

    public static boolean isNumberValid(String number) {
        return number.matches("\\+63[0-9]{10}");
    }

    public static String getYear(String date) {
        return date.split("-")[0];
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


    public static String getDateOnly(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }


    public static Calendar setCalendar(String mdate) {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            cal.setTime(sdf.parse(mdate));// all done


        } catch (Exception e) {
            e.printStackTrace();
        }

        return cal;

    }

}
