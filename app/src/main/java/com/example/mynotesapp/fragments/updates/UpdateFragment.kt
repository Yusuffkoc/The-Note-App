package com.example.mynotesapp.fragments.updates

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.mynotesapp.R
import com.example.mynotesapp.base.BaseApplication
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.model.NoteState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    val args: UpdateFragmentArgs by navArgs()
    private lateinit var radioGroup: RadioGroup
    private lateinit var titleEditText: EditText
    private lateinit var subjectEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var changeImageButton: Button
    private lateinit var updateImageView: ImageView

    private lateinit var viewModel: UpdateViewModel


    private var selectedImagePath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        radioGroup = view.filterRGUpdate
        titleEditText = view.updateTitle_et
        subjectEditText = view.updateSubject_et
        updateButton = view.update_btn
        changeImageButton = view.changeImage
        updateImageView = view.selectedImageUpdatePage

        loadCurrentItem()

        val launcher = registerImagePicker {
            val selectedImage = it.getOrNull(0)

            if (selectedImage != null) {
                selectedImagePath = selectedImage.uri.toString()
                Picasso.get().load(selectedImagePath.toUri()).fit().into(updateImageView)
            }

        }

        changeImageButton.setOnClickListener {
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

        updateButton.setOnClickListener {
            updateItem()
        }

        //Add Menu
        setHasOptionsMenu(true)
        return view
    }

    private fun loadCurrentItem() {
        val note = args.clickedNote
        titleEditText.setText(note.title)
        subjectEditText.setText(note.subject)

        radioGroup.check(
            when (note.state) {
                NoteState.WillBeDone -> R.id.tbdRB
                NoteState.Doing -> R.id.ongoingRB
                NoteState.Done -> R.id.doneRB
            }
        )

        Picasso.get().load(note.imagePath.toUri()).fit().into(updateImageView)
    }

    private fun updateItem() {
        val title = titleEditText.text.toString()
        val subject = subjectEditText.text.toString()
        val state: NoteState = when (radioGroup.checkedRadioButtonId) {
            R.id.tbdRB -> NoteState.WillBeDone
            R.id.ongoingRB -> NoteState.Doing
            R.id.doneRB -> NoteState.Done
            else -> NoteState.WillBeDone
        }

        val currentNote = args.clickedNote
        val newNote = Note(
            id = currentNote.id,
            userId = BaseApplication.userName,
            title = title,
            subject = subject,
            state = state,
            if (selectedImagePath.isNotEmpty()) selectedImagePath else currentNote.imagePath
        )

        viewModel.updateNote(newNote)
        Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNoteOptionMenuClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNoteOptionMenuClicked() {

        val currentNote = args.clickedNote
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteNote(note = currentNote)
            Toast.makeText(
                requireContext(),
                "Successfully removed note with id ${args.clickedNote.id}",
                Toast.LENGTH_SHORT
            ).show()
            activity?.onBackPressed()
        }
        builder.setNegativeButton("No") { _, _ ->
            //DoNothing
        }
        builder.setTitle("Delete ?")
        builder.setMessage("Are you sure you want to delete this note? ")
        builder.create().show()
    }

}