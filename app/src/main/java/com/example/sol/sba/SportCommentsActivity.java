package com.example.sol.sba;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.DocumentChange;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.EventListener;
        import com.google.firebase.firestore.FieldValue;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.FirebaseFirestoreException;
        import com.google.firebase.firestore.QuerySnapshot;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class SportCommentsActivity extends AppCompatActivity {
    private Toolbar commentToolbar;

    private EditText comment_field;
    private ImageView comment_post_btn;


    private RecyclerView comment_list;
    private CommentsRecyclerAdapter commentsRecyclerAdapter;
    private List<Comments> commentsList;
    private List<User> user_list;

    private String blog_post_id;
    private String current_user_id;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_comments);


        commentToolbar = findViewById(R.id.comment_toolbar);
        setSupportActionBar(commentToolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();
        blog_post_id = getIntent().getStringExtra("blog_post_id");

        comment_field = findViewById(R.id.comment_field);
        comment_post_btn = findViewById(R.id.comment_post_btn);
        comment_list = findViewById(R.id.comment_list);

        commentsList = new ArrayList<>();
        user_list = new ArrayList<>();
        commentsRecyclerAdapter = new CommentsRecyclerAdapter(commentsList,user_list);
        comment_list.setHasFixedSize(true);
        comment_list.setLayoutManager(new LinearLayoutManager(this));
        comment_list.setAdapter(commentsRecyclerAdapter);

        firebaseFirestore.collection("Sports/" + blog_post_id + "/Comments")
                .addSnapshotListener(SportCommentsActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (!documentSnapshots.isEmpty()) {

                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                                if (doc.getType() == DocumentChange.Type.ADDED) {

//                                            String commentId = doc.getDocument().getId();
//                                            Comments comments = doc.getDocument().toObject(Comments.class);
//                                            commentsList.add(comments);
//                                            commentsRecyclerAdapter.notifyDataSetChanged();


                                    String commentId = doc.getDocument().getId();
                                    final Comments comments = doc.getDocument().toObject(Comments.class);

                                    String commentUserId = doc.getDocument().getString("user_id");
                                    firebaseFirestore.collection("Users").document(commentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                            if(task.isSuccessful()) {

                                                User user = task.getResult().toObject(User.class);
                                                user_list.add(user);
                                                commentsList.add(comments);
                                                commentsRecyclerAdapter.notifyDataSetChanged();

                                            }
                                        }
                                    });

//                                            String commentId = doc.getDocument().getId();
//                                            Comments comments = doc.getDocument().toObject(Comments.class);
//                                            commentsList.add(comments);
//                                            commentsRecyclerAdapter.notifyDataSetChanged();

                                }
                            }
                        }


                    }
                });

        comment_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment_message = comment_field.getText().toString();

                if(!comment_message.isEmpty()){

                    Map<String ,Object> commentsMap = new HashMap<>();
                    commentsMap.put("message",comment_message);
                    commentsMap.put("user_id",current_user_id);
                    commentsMap.put("timestamp", FieldValue.serverTimestamp());

                    firebaseFirestore.collection("Sports/" + blog_post_id + "/Comments").add(commentsMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if(!task.isSuccessful()){

                                Toast.makeText(SportCommentsActivity.this,"Error Posting Comment : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }else{

                                comment_field.setText("");

                                // by me
                                Intent intent = new Intent(SportCommentsActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        }
                    });

                }
            }
        });
    }
}


