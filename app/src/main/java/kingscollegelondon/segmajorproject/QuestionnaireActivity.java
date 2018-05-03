package kingscollegelondon.segmajorproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;

import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class QuestionnaireActivity extends AppCompatActivity {
    private String firstName;
    private String surname;
    private String parentDateOfBirth;
    private RadioGroup radioGroupGender;
    private String gender;
    private String houseAddress;
    private String postCode;
    private int homeTelephoneNumber;
    private int mobileTelephoneNumber;
    private String email;
    private String howDidYouFindOutAboutTheCourse;
    private RadioGroup radioGroupParentingGroups;
    private String haveYouAttentedOtherGroups;
    private RadioGroup loneParentRadioGroup;
    private String loneParent;
    private RadioGroup secondLangaugeRadioGroup;
    private String secondLanguage;
    private String typeOfSecondLanguage;
    private RadioGroup disabledRadioGroup;
    private String areYouDisabled;
    private RadioGroup ethnicityRadioGroup;
    private String ethnicity;
    private RadioGroup levelOfEducationRadioGroup;
    private String levelOfEducation;
    private RadioGroup workStatusRadioGroup;
    private String workStatus;
    private RadioGroup typeOfHousingRadioGroup;
    private String typeOfHousing;
    private int numberOfChildren;
    private String nameOfFirstChild;
    private String dateOfFirstChild;
    private String nameOfSecondChild;
    private String dateOfSecondChild;
    private String nameOfThirdChild;
    private String dateOfThirdChild;
    private String nameOfFourthChild;
    private String dateOfFourthChild;
    private String dateOfBirthOfFocusedChild;
    private RadioGroup focusedChildGenderRadioGroup;
    private String focusedChildGender;
    private String relationshipOfFocusedChild;
    private RadioGroup relationshipOfFocusedChildRadioGroup;
    private String disabilityOfFocusedChild;
    private RadioGroup disabilityOfFocusedChildRadioGroup;
    private Button saveInfoButton;
    private static final String CSV_HEADER[] = {"First Name", "Surname", "Date of Birth", "Gender", "House Address", "Postcode", "Telephone number – home"
            , "Telephone number – mobile", "Email address", "How did you find out about this parenting group?", "Have you attended a parenting group before?", "Are you a lone parent?", "Is English your second language", "If yes, what languages are spoken at home?,",
             "Do you consider yourself disabled?", "Ethnicity", "Level of education", "Work status", "Type of Housing", "Number of children", "Child's name", "Child's date of birth",
            "Child 2's name", "Child 2's date of birth", "Child 3's name", "Child 3's date of birth", "Child 4's name", "Child 4's date of birth",
            "Focused child's date of birth", "Child's gender", "Your relationship to your child", "Child's disability"
    };

    private HashMap<String, Object> ANSWER;
    boolean isAlreadyExist = false;
    private File desFile = null;
    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire_layout);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {

            } else {
                requestPermission();
            }
        }


        ANSWER = new HashMap<String, Object>();


        saveInfoButton = (Button) findViewById(R.id.button_finish);
        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkForEmptyFields()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveInfoButton = (Button) findViewById(R.id.button_finish);
                firstName = ((EditText) findViewById(R.id.editTextFirstName)).getText().toString();
                surname = ((EditText) findViewById(R.id.editTextSurname)).getText().toString();
                int month = ((DatePicker) findViewById(R.id.datePickerDateOfBirth)).getMonth();
                int day = ((DatePicker) findViewById(R.id.datePickerDateOfBirth)).getDayOfMonth();
                int year = ((DatePicker) findViewById(R.id.datePickerDateOfBirth)).getYear();
                parentDateOfBirth = day + "/" + month + "/" + year;
                radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
                gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
                houseAddress = ((EditText) findViewById(R.id.editTextHouseAddress)).getText().toString();
                postCode = ((EditText) findViewById(R.id.editTextPostCode)).getText().toString();
                try {
                    homeTelephoneNumber = Integer.valueOf(((EditText) findViewById(R.id.editTextHomeTelephoneNumber)).getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Telephone number must only contain numbers!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    mobileTelephoneNumber = Integer.valueOf(((EditText) findViewById(R.id.editTextMobileTelephoneNumber)).getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Telephone number must only contain numbers!", Toast.LENGTH_SHORT).show();
                    return;
                }

                email = ((EditText) findViewById(R.id.editTextProfileActivityEmail)).getText().toString();
                howDidYouFindOutAboutTheCourse = ((EditText) findViewById(R.id.editTextHowDidYouFindOut)).getText().toString();
                radioGroupParentingGroups = (RadioGroup) findViewById(R.id.radioGroupParentingGroups);
                haveYouAttentedOtherGroups = ((RadioButton) findViewById(radioGroupParentingGroups.getCheckedRadioButtonId())).getText().toString();
                loneParentRadioGroup = (RadioGroup) findViewById(R.id.radioGroupLoneParent);
                loneParent = ((RadioButton) findViewById(loneParentRadioGroup.getCheckedRadioButtonId())).getText().toString();

                secondLangaugeRadioGroup = (RadioGroup) findViewById(R.id.radioGroupSecondLanguage);
                secondLanguage = ((RadioButton) findViewById(secondLangaugeRadioGroup.getCheckedRadioButtonId())).getText().toString();
                if (secondLanguage.equals("Yes")) {
                    typeOfSecondLanguage = ((EditText) findViewById(R.id.editTextSecondLangauge)).getText().toString();
                } else {
                    typeOfSecondLanguage = "";
                }


                disabledRadioGroup = (RadioGroup) findViewById(R.id.radioGroupAreYouDisabled);
                areYouDisabled = ((RadioButton) findViewById(disabledRadioGroup.getCheckedRadioButtonId())).getText().toString();
                ethnicityRadioGroup = (RadioGroup) findViewById(R.id.radioGroupEthnicity);
                ethnicity = ((RadioButton) findViewById(ethnicityRadioGroup.getCheckedRadioButtonId())).getText().toString();
                if (ethnicity.equals("Other")) {
                    ethnicity = ((EditText) findViewById(R.id.editTextOtherEthnicity)).getText().toString();
                }
                levelOfEducationRadioGroup = (RadioGroup) findViewById(R.id.radioGroupLevelOfEducation);
                levelOfEducation = ((RadioButton) findViewById(levelOfEducationRadioGroup.getCheckedRadioButtonId())).getText().toString();
                workStatusRadioGroup = (RadioGroup) findViewById(R.id.radioGroupWorkStatus);
                workStatus = ((RadioButton) findViewById(workStatusRadioGroup.getCheckedRadioButtonId())).getText().toString();
                if (workStatus.equals("Other, please write")) {
                    workStatus = ((EditText) findViewById(R.id.editTextOtherWorkStatus)).getText().toString();
                }
                typeOfHousingRadioGroup = (RadioGroup) findViewById(R.id.radioGroupTypeOfHousing);
                typeOfHousing = ((RadioButton) findViewById(typeOfHousingRadioGroup.getCheckedRadioButtonId())).getText().toString();

                nameOfSecondChild = "";
                dateOfSecondChild = "";

                nameOfThirdChild = "";
                dateOfThirdChild = "";

                nameOfFourthChild = "";
                dateOfFourthChild = "";


                try {
                    numberOfChildren = Integer.valueOf(((EditText) findViewById(R.id.editTextNumberOfChildren)).getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Number of children must only contain numbers!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (numberOfChildren == 1) {
                    setChildData1();
                } else if (numberOfChildren == 2) {
                    setChildData1();
                    setChildData2();
                } else if (numberOfChildren == 3) {
                    setChildData1();
                    setChildData2();
                    setChildData3();
                } else {
                    setChildData1();
                    setChildData2();
                    setChildData3();
                    setChildData4();
                }

                int childMonth = ((DatePicker) findViewById(R.id.datePickerThirdChildDOB)).getMonth();
                int childDay = ((DatePicker) findViewById(R.id.datePickerThirdChildDOB)).getDayOfMonth();
                int childYear = ((DatePicker) findViewById(R.id.datePickerThirdChildDOB)).getYear();
                dateOfBirthOfFocusedChild = childDay + "/" + childMonth + "/" + childYear;

                focusedChildGenderRadioGroup = (RadioGroup) findViewById(R.id.radioGroupChildGender);
                focusedChildGender = ((RadioButton) findViewById(focusedChildGenderRadioGroup.getCheckedRadioButtonId())).getText().toString();

                relationshipOfFocusedChildRadioGroup = (RadioGroup) findViewById(R.id.radioGroupChildRelationship);
                relationshipOfFocusedChild = ((RadioButton) findViewById(relationshipOfFocusedChildRadioGroup.getCheckedRadioButtonId())).getText().toString();

                disabilityOfFocusedChildRadioGroup = (RadioGroup) findViewById(R.id.radioGroupChildDisability);
                disabilityOfFocusedChild = ((RadioButton) findViewById(disabilityOfFocusedChildRadioGroup.getCheckedRadioButtonId())).getText().toString();


                //get user input and store it in appropriate variables

                addDataToCSV();
                handleSDcard();
                writeDataToCSV();
                sendEmail();


            }
        });
    }
    
    /**
     * These 4 methods set child data
     * their only called depending on the ammount of children they have
     */
    public void setChildData1() {
        nameOfFirstChild = ((EditText) findViewById(R.id.editTextNameOfChild1)).getText().toString();
        int monthChild1 = ((DatePicker) findViewById(R.id.datePickerFirstChildDOB)).getMonth();
        int dayChild1 = ((DatePicker) findViewById(R.id.datePickerFirstChildDOB)).getDayOfMonth();
        int yearChild1 = ((DatePicker) findViewById(R.id.datePickerFirstChildDOB)).getYear();
        dateOfFirstChild = dayChild1 + "/" + monthChild1 + "/" + yearChild1;
    }


    public void setChildData2() {
        nameOfSecondChild = ((EditText) findViewById(R.id.editTextNameOfChild2)).getText().toString();
        int monthChild2 = ((DatePicker) findViewById(R.id.datePickerSecondChildDOB)).getMonth();
        int dayChild2 = ((DatePicker) findViewById(R.id.datePickerSecondChildDOB)).getDayOfMonth();
        int yearChild2 = ((DatePicker) findViewById(R.id.datePickerSecondChildDOB)).getYear();
        dateOfSecondChild = dayChild2 + "/" + monthChild2 + "/" + yearChild2;
    }

    public void setChildData3() {
        nameOfThirdChild = ((EditText) findViewById(R.id.editTextNameOfChild3)).getText().toString();
        int monthChild3 = ((DatePicker) findViewById(R.id.datePickerThirdChildDOB)).getMonth();
        int dayChild3 = ((DatePicker) findViewById(R.id.datePickerThirdChildDOB)).getDayOfMonth();
        int yearChild3 = ((DatePicker) findViewById(R.id.datePickerThirdChildDOB)).getYear();
        dateOfThirdChild = dayChild3 + "/" + monthChild3 + "/" + yearChild3;

    }

    public void setChildData4() {
        nameOfFourthChild = ((EditText) findViewById(R.id.editTextNameOfChild4)).getText().toString();
        int monthChild4 = ((DatePicker) findViewById(R.id.datePickerForthChildDOB)).getMonth();
        int dayChild4 = ((DatePicker) findViewById(R.id.datePickerForthChildDOB)).getDayOfMonth();
        int yearChild4 = ((DatePicker) findViewById(R.id.datePickerForthChildDOB)).getYear();
        dateOfFourthChild = dayChild4 + "/" + monthChild4 + "/" + yearChild4;

    }

    /**
     * This method takes an Array of EditTexts and make sure that all of them hold some text.
     * @param fields
     */
    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method returns true if the mandatory fields in the questionnaire are not empty, and false
     * otherwise.
     * @return boolean
     */
    public boolean checkForEmptyFields() {
        EditText firstName = findViewById(R.id.editTextFirstName);
        EditText surname = findViewById(R.id.editTextSurname);
        EditText houseAddress = findViewById(R.id.editTextHouseAddress);
        EditText postCode = findViewById(R.id.editTextPostCode);
        EditText homeTelephoneNumber = findViewById(R.id.editTextHomeTelephoneNumber);
        EditText mobileTelephoneNumber = findViewById(R.id.editTextMobileTelephoneNumber);
        EditText email = findViewById(R.id.editTextProfileActivityEmail);
        EditText howDidYouFindOutAboutTheCourse = findViewById(R.id.editTextHowDidYouFindOut);
        EditText nameOfFirstChild = findViewById(R.id.editTextNameOfChild1);
        boolean fieldsOK = validate(new EditText[]{firstName, surname, houseAddress, postCode, homeTelephoneNumber, mobileTelephoneNumber, email, howDidYouFindOutAboutTheCourse, nameOfFirstChild});
        return fieldsOK;
    }

    /**
     * This method inserts the header and the corresponding user response
     */
    private void addDataToCSV() {
        ANSWER.put(CSV_HEADER[0], firstName);
        ANSWER.put(CSV_HEADER[1], surname);
        ANSWER.put(CSV_HEADER[2], parentDateOfBirth);
        ANSWER.put(CSV_HEADER[3], gender);
        ANSWER.put(CSV_HEADER[4], houseAddress);
        ANSWER.put(CSV_HEADER[5], postCode);
        ANSWER.put(CSV_HEADER[6], homeTelephoneNumber);
        ANSWER.put(CSV_HEADER[7], mobileTelephoneNumber);
        ANSWER.put(CSV_HEADER[8], email);
        ANSWER.put(CSV_HEADER[9], howDidYouFindOutAboutTheCourse);
        ANSWER.put(CSV_HEADER[10], haveYouAttentedOtherGroups);
        ANSWER.put(CSV_HEADER[11], loneParent);
        ANSWER.put(CSV_HEADER[12], secondLanguage);
        ANSWER.put(CSV_HEADER[13], typeOfSecondLanguage);
        ANSWER.put(CSV_HEADER[14], ethnicity);
        ANSWER.put(CSV_HEADER[15], areYouDisabled);
        ANSWER.put(CSV_HEADER[16], levelOfEducation);
        ANSWER.put(CSV_HEADER[17], workStatus);
        ANSWER.put(CSV_HEADER[18], typeOfHousing);
        ANSWER.put(CSV_HEADER[19], numberOfChildren);
        ANSWER.put(CSV_HEADER[20], nameOfFirstChild);
        ANSWER.put(CSV_HEADER[21], dateOfFirstChild);
        ANSWER.put(CSV_HEADER[22], nameOfSecondChild);
        ANSWER.put(CSV_HEADER[23], dateOfSecondChild);
        ANSWER.put(CSV_HEADER[24], nameOfThirdChild);
        ANSWER.put(CSV_HEADER[25], dateOfThirdChild);
        ANSWER.put(CSV_HEADER[26], nameOfFourthChild);
        ANSWER.put(CSV_HEADER[27], dateOfFourthChild);
        ANSWER.put(CSV_HEADER[28], dateOfBirthOfFocusedChild);
        ANSWER.put(CSV_HEADER[29], focusedChildGender);
        ANSWER.put(CSV_HEADER[30], relationshipOfFocusedChild);
        ANSWER.put(CSV_HEADER[31], disabilityOfFocusedChild);

    }

    
    private void handleSDcard() {
        // SD Card path
        File mainDirect = new File(Environment.getExternalStorageDirectory()
                + "/" + "Android/data/" + getPackageName().toString());

        // If Directory not exist then create
        if (!mainDirect.exists())
            if (mainDirect.mkdir())
                ;

        // Here we are creating CSV file on SD Card
        desFile = new File(mainDirect + "/" + "Questionnaire.csv");

        if (!desFile.exists()) {
            // Here only i check if the file is already exist than we not write
            // header of CSV vice versa we write CSV Header
            isAlreadyExist = true;
        }

    }

    /**
     * This method inserts data into a CSV file.
     */
    private void writeDataToCSV() {
        ICsvMapWriter mapWriter = null;
        try {
            mapWriter = new CsvMapWriter(new FileWriter(desFile, false),
                    CsvPreference.STANDARD_PREFERENCE);

            final CellProcessor[] processors = getProcessors();

            // Write the header.
            if (isAlreadyExist)
                mapWriter.writeHeader(CSV_HEADER);

            mapWriter.writeHeader(CSV_HEADER);
            mapWriter.write(ANSWER, CSV_HEADER, processors);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mapWriter != null) {
                try {
                    mapWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method returns CSV file headers.
     * @return CellProcessor[]
     */
    private CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[]{new NotNull(), // Cell
                // 1
                // constraint
                // (Firstname)
                new NotNull(), // surname
                new NotNull(), // DOB
                new NotNull(), //4 gender
                new NotNull(), //houseAddress
                new NotNull(),//6 //postCode
                new NotNull(), //homeTelNumber
                new NotNull(),//8 mobileNumber
                new NotNull(), //email
                new NotNull(),//10 findingOut
                new NotNull(), //otherGroups
                new NotNull(),//12 //loneParent
                new NotNull(), //secondLangauge
                new NotNull(),//14
                new NotNull(),
                new NotNull(), //16
                new NotNull(),
                new NotNull(),//18
                new NotNull(),
                new NotNull(),//20
                new NotNull(),
                new NotNull(),//22
                new NotNull(),
                new NotNull(),//24
                new NotNull(),
                new NotNull(),//26
                new NotNull(),
                new NotNull(),//28
                new NotNull(),
                new NotNull(),//30
                new Optional(),
                new Optional(),



        };
        return processors;
    }

    /**
     *  Attach the CSV file to the email subject
     *  Open email intent
     */
    private void sendEmail() {
        try {
            String strFile = Environment.getExternalStorageDirectory() + "/"
                    + "Android/data/" + getPackageName().toString()
                    + "/Questionnaire.csv";

            File file = new File(strFile);
            if (!file.exists()) {
                Toast.makeText(this, "No data available", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            //
            final Intent emailIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            //
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"k1631141@kcl.ac.uk"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Questionnaire answers");

            emailIntent.putExtra(Intent.EXTRA_STREAM,
                    Uri.parse("file://" + strFile));

            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Temp");

            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Check if you can write to external storage.
     */
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(QuestionnaireActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * If we don't have the permission, request it from user.
     */
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(QuestionnaireActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(QuestionnaireActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(QuestionnaireActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * This method updates the permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }




}
