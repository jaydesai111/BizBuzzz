package com.challenge.bizbuzzz.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.challenge.bizbuzzz.Adapter.SavePhotoAdapter;
import com.challenge.bizbuzzz.CallBack.InsertItemCallBack;
import com.challenge.bizbuzzz.CallBack.RemoveItemCallBack;
import com.challenge.bizbuzzz.DialogFragment.SavePhotoDialogFragment;
import com.challenge.bizbuzzz.Pojo.Upload;
import com.challenge.bizbuzzz.R;
import com.challenge.bizbuzzz.Utility.BizBuzzzUtility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Guidezie on 14-09-2017.
 */

public class SavePhotoListFragment extends Fragment implements InsertItemCallBack,RemoveItemCallBack {
    View view;
    private String TAG = "SAVEPHOTOLISTFRAGMENT";
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;
    int tempPosition;
    StorageReference desertRef;

    private StorageReference storageReference;
    public static final int SAVEPHOTOCALLBACK = 1;

    //list to hold all the uploaded images
    public ArrayList<Upload> uploadsList;
    int position;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_save_photo_list, container, false);
        recyclerView = view.findViewById(R.id.rv_imageList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        uploadsList = new ArrayList<>();
        updateList();

        return view;
    }

    public void updateList()
    {
        progressDialog = new ProgressDialog(getActivity());



        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(BizBuzzzUtility.DATABASE_PATH_UPLOADS);

        //adding an event listener to fetch values
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();
                uploadsList.clear();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    uploadsList.add(upload);
                }
                if(uploadsList.size()==0)
                {
                    Toast.makeText(getActivity(),"No data, please add from navbar",Toast.LENGTH_LONG).show();
                }
                //creating adapter
                adapter = new SavePhotoAdapter(getActivity(), uploadsList,SavePhotoListFragment.this,SavePhotoListFragment.this);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onItemInserted(int position) {
        openSavePhotoDialog(position);

    }

    @Override
    public void onItemRemove(int position) {
        if(!progressDialog.isShowing() && progressDialog !=null) {
            progressDialog.show();
        }
        tempPosition = position;

        mDatabase.child(uploadsList.get(position).getKey()).setValue(null);
        removeFromStorage();

    }


    public void removeFromStorage()
    {

        desertRef = FirebaseStorage.getInstance().getReferenceFromUrl(uploadsList.get(tempPosition).getUrl());
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                uploadsList.remove(tempPosition);
                adapter.notifyDataSetChanged();
                if(progressDialog!=null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == SAVEPHOTOCALLBACK)
        {
            uploadsList.add(data.getBundleExtra("data").getInt("position"), (Upload) data.getBundleExtra("data").getSerializable("uploads"));
            adapter.notifyDataSetChanged();
        }
    }

    public void openSavePhotoDialog(int position)
    {
        SavePhotoDialogFragment savePhotoDialogFragment = new SavePhotoDialogFragment();
        savePhotoDialogFragment.setTargetFragment(this,SAVEPHOTOCALLBACK);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        savePhotoDialogFragment.setArguments(bundle);
        savePhotoDialogFragment.show(getActivity().getSupportFragmentManager(),"SavePhotoDialogFragment");
    }

}
