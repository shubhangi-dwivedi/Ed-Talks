package com.example.ed_talk.QNA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ed_talk.Modals.Post;
import com.example.ed_talk.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;

public class EditQuestionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner spinner;
    private EditText questionEdt;
    private ImageView imageView;
    private Button cancelBtn, updateBtn;
    private String postID;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        initView();
        getData();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionPost();
            }
        });
    }

    private void initView() {
        spinner = findViewById(R.id.edit_topic_spiner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.topics));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        questionEdt = findViewById(R.id.edit_question_text);
        cancelBtn = findViewById(R.id.edit_cancelBtn);
        updateBtn = findViewById(R.id.edit_postBtn);
        postID = getIntent().getStringExtra("postid");
    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("questions_posts").child(postID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                if (post != null) {
                    int spinerPos = adapter.getPosition(post.getTopic());
                    spinner.setSelection(spinerPos);
                    questionEdt.setText(post.getQuestion());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });}


    private void updateQuestionPost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("questions_posts")
                .child(postID);
        String newQuestion = questionEdt.getText().toString().trim();
        String newTopic = spinner.getSelectedItem().toString();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("question", newQuestion);
        hashMap.put("topic", newTopic);
        reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditQuestionActivity.this, "Update image success", Toast.LENGTH_SHORT).show();
            }
        });
        finish();
    }

}