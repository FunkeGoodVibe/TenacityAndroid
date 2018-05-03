package kingscollegelondon.segmajorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class CreateChatRoomActivity extends AppCompatActivity {

    private Button createRoom;
    private EditText nameOfChatRoom;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfChatRooms = new ArrayList<>();
    private String nameOfParent;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("User groups");
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String uID = currentUser.getUid();
    private DatabaseReference mUserDatabse = FirebaseDatabase.getInstance().getReference().child("Users").child(uID).child("Group");

    /**
     * This method returns the name of the user group the user belongs to.
     * @return String
     */
    public String getUserGroupFromFirebase() {
        return userGroupFromFirebase;
    }

    /**
     * This method sets the value of the user group the user belongs to.
     * @param userGroupFromFirebase
     */
    public void setUserGroupFromFirebase(String userGroupFromFirebase) {
        this.userGroupFromFirebase = userGroupFromFirebase;
    }

    private String userGroupFromFirebase;
    private Boolean redirectUserBoolean = false;
    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;

    /**
     * This method returns the entry code parents need to join a chat room.
     * @return String
     */
    public String getEntryCodeForParents() {
        return entryCodeForParents;
    }

    /**
     * This method sets the entry code parents need to join a chat room.
     * @param entryCodeForParents
     */
    public void setEntryCodeForParents(String entryCodeForParents) {
        this.entryCodeForParents = entryCodeForParents;
    }

    private String entryCodeForParents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat_room);

        mAuth = FirebaseAuth.getInstance();
        createRoom = (Button) findViewById(R.id.buttonCreateRoom);
        nameOfChatRoom = (EditText) findViewById(R.id.editTextCreateRoom);
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfChatRooms);
        listView.setAdapter(arrayAdapter);
        nameOfParent = getUsername();


        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBoxCreateRoom();
            }
        });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                   dialogBox(view);



           }
       });

        updateChatRoomView();


        txtHome = (TextView) findViewById(R.id.txt_home);
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateChatRoomActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        txtChat = (TextView) findViewById(R.id.txt_chat);
        txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CreateChatRoomActivity.this, CreateChatRoomActivity.class);
                startActivity(intent1);
            }
        });

        txtCourse = (TextView) findViewById(R.id.txt_course);
        txtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(CreateChatRoomActivity.this, CourseWorkActivity.class);
                startActivity(intent2);
            }
        });

        txtProfile = (TextView) findViewById(R.id.txt_profile);
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(CreateChatRoomActivity.this, ProfileActivity.class);
                startActivity(intent3);
            }
        });


    }

    /**
     * This method returns the username of the current user.
     * @return String
     */
    public String getUsername() {
        if (mAuth.getCurrentUser().getDisplayName() != null) {
            return mAuth.getCurrentUser().getDisplayName().toString();
        } else {
            return mAuth.getCurrentUser().getEmail().toString();
        }
    }

    /**
     * This method empties the EditText displaying the name of the chat room.
     */
    public void setEditTextNameOfChatRoom() {
        nameOfChatRoom.setText("");
    }

    public void dialogBox(final View view123) {
        final View view987 = view123;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreateChatRoomActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog, null);
            final EditText textCode = (EditText) mView.findViewById(R.id.editTextGroupCodeDialog);
            Button buttonTextCode = (Button) mView.findViewById(R.id.buttonTextCode);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            checkIfUserIsAllowedToAccessTheGroup(((TextView) view987).getText().toString(),view987);
                dialog.show();
                buttonTextCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        if (!textCode.getText().toString().isEmpty()) {

                            String firebaseCode = getTextCode(((TextView) view987).getText().toString());
                            if (firebaseCode != null){

                                if (textCode.getText().toString().equals(firebaseCode)) {
                                    uploadToFirebase(((TextView) view987).getText().toString());
                                    redirectUserToChatRoom(view987);
                                }
                                dialog.dismiss();

                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Please enter a code", Toast.LENGTH_LONG).show();
                        }
                    }
                });




    }

    /**
     * This method returns a boolean value indicating wether or not the user is allowed to access
     * a given group.
     * @param groupName
     * @param view
     * @return boolean
     */
    public boolean checkIfUserIsAllowedToAccessTheGroup(final String groupName, final View view){
        boolean result = false;
        setUserGroupFromFirebase("");

        mUserDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("userGroup").getValue() !=null){
                    String userGroup = (String)(dataSnapshot.child("userGroup").getValue());

                        setUserGroupFromFirebase(userGroup);

                    if (getUserGroupFromFirebase().contentEquals(groupName)) {
                        redirectUserToChatRoom(view);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
            if (getUserGroupFromFirebase().contentEquals(groupName)) {
              return true;
            }else{
                return false;
            }

    }

    /**
     * This method adds a new user group to the database.
     * @param userGroup
     */
    private void uploadToFirebase(String usergGroup){
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("userGroup", usergGroup);
        mUserDatabse.setValue(userMap);

    }

    /**
     * This method redirects the user to the chat room.
     * @param view
     */
    public void redirectUserToChatRoom(View view){
                Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                intent.putExtra("room_name", ((TextView)view).getText().toString());
                intent.putExtra("parent_name", nameOfParent);
                startActivity(intent);
            }




    public void dialogBoxCreateRoom(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreateChatRoomActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_room_creation, null);
        final EditText textCode = (EditText) mView.findViewById(R.id.editTextGroupCodeDialogRoomCreation);
        Button buttonTextCode = (Button) mView.findViewById(R.id.buttonTextCodeRoomCreation);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        buttonTextCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textCode.getText().toString().isEmpty()){
                    setEntryCodeForParents(textCode.getText().toString());
                    addRoom();
                    dialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter a code", Toast.LENGTH_LONG).show();
                }

            }
        } );
    }

    public String getTextCodeFromFirebase() {
        return textCodeFromFirebase;
    }

    public void setTextCodeFromFirebase(String textCodeFromFirebase) {
        this.textCodeFromFirebase = textCodeFromFirebase;
    }

    private String textCodeFromFirebase;


    public String getTextCode(final String string){
         String result = "";
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(string).getValue() !=null){
                    String textCode = (String)(dataSnapshot.child(string).getValue());
                    setTextCodeFromFirebase(textCode);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        result = getTextCodeFromFirebase();
        setTextCodeFromFirebase("");
        return result;
    }

    /**
     * This method adds a new chat room to the database. To be added the chat must have a
     * a non-empty entry code to join this chat.
     */
    public void addRoom(){
                if(getEntryCodeForParents() !=null && getEntryCodeForParents() !=""){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(nameOfChatRoom.getText().toString(), getEntryCodeForParents());
                    root.updateChildren(map);
                    setEntryCodeForParents("");
                }

            }

    /**
     * This method updates the server by adding parent name and their reply.
     */
    public void updateChatRoomView(){
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                listOfChatRooms.clear();
                listOfChatRooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
                setEditTextNameOfChatRoom();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
