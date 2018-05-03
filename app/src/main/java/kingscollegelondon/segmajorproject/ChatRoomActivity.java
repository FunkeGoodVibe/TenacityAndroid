package kingscollegelondon.segmajorproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ChatRoomActivity extends AppCompatActivity{

    private Button buttonReply;
    private EditText editTextReplyMessage;
    private TextView textViewChat;
    private String parentName;
    private String nameOfChatRoom;
    private DatabaseReference root;
    private String key;
    private String message;
    private String chatRoomUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        buttonReply = (Button) findViewById(R.id.buttonReply);
        editTextReplyMessage = (EditText) findViewById(R.id.editTextReplyMessage);
        textViewChat = (TextView) findViewById(R.id.textView);
        //get fields from layout

        parentName = getIntent().getExtras().get("parent_name").toString();
        nameOfChatRoom = getIntent().getExtras().get("room_name").toString();
        setTitle("Group name: "+ nameOfChatRoom);
        root = FirebaseDatabase.getInstance().getReference().child("Global chat").child(nameOfChatRoom);
        //get database reference

        addMessageToDatabase();
        updateChildrenFirebase();



    }

    /**
     * This method updates the server by adding parent name and their reply.
     */
    public void addMessageToDatabase(){
        buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name", parentName);
                map2.put("msg", editTextReplyMessage.getText().toString());

                message_root.updateChildren(map2);
            }
        });
    }
    /**
     * This method listens for new additions.
     */
    public void updateChildrenFirebase(){

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                updateChatRoomView(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                updateChatRoomView(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method updates the groups by adding parent username and their reply.
     * @param dataSnapshot
     */
    private void updateChatRoomView(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            message = (String) ((DataSnapshot)i.next()).getValue();
            chatRoomUsername = (String) ((DataSnapshot)i.next()).getValue();

            textViewChat.append(chatRoomUsername +" : "+ message +" \n");
        }


    }
}
