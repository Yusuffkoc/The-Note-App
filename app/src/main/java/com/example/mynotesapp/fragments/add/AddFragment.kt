package com.example.mynotesapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.mynotesapp.R
import com.example.mynotesapp.base.BaseApplication
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.model.NoteState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var viewModel: AddViewModel
    private var selectedImagePath: String = ""
    private lateinit var imageView: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        view.addbutton.setOnClickListener {
            insertDataToDatabase()
        }

        imageView = view.selectedImageAddPage

        val launcher = registerImagePicker {
            val selectedImage = it.getOrNull(0)

            if (selectedImage != null) {
                selectedImagePath = selectedImage.uri.toString()
                Picasso.get().load(selectedImagePath.toUri()).fit().into(imageView)
            }

        }

        view.addImagebutton.setOnClickListener {
            launcher.launch(
                ImagePickerConfig {
                    mode = ImagePickerMode.SINGLE
                    returnMode =
                        ReturnMode.ALL // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                    isIncludeVideo = false
                    isIncludeAnimation = false
                    limit = 1
                }
            )
        }

        return view
    }


    private fun insertDataToDatabase() {
        val title = addTitle_ed.text.toString()
        val subject = addSubject_ed.text.toString()

        if (inputCheck(title, subject)) {
            //Create User Object
            val note = Note(
                0,
                BaseApplication.userName,
                title,
                subject,
                NoteState.WillBeDone,
                selectedImagePath
            )

            //Add Data to Database
            viewModel.addNote(note)

            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            //Navigate Back
            activity?.onBackPressed()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(title: String, subject: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(subject))
    }

}



