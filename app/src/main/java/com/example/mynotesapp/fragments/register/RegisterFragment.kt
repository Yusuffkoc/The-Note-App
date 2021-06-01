package com.example.mynotesapp.fragments.register

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.R
import com.example.mynotesapp.model.User
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        view.NewUserButton_id.setOnClickListener {
            insertDataToDatabase()
        }

        observers()

        return view
    }

    private fun observers() {
        viewModel.canUserRegister.observe(viewLifecycleOwner, Observer {
            if (it) {
                val userName = NewUserName_id.text.toString()
                val password = NewUserPassword_id.text.toString()
                //Create User Object
                val user = User(
                    0,
                    userName,
                    password
                )

                //Add Data to Database
                viewModel.createNewUser(user)

                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

                //Navigate Back
                activity?.onBackPressed()
            } else {
                Toast.makeText(
                    requireContext(),
                    "User Invalid! Check user details again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun insertDataToDatabase() {
        val userName = NewUserName_id.text.toString()
        val password = NewUserPassword_id.text.toString()

        if (inputCheck(userName, password)) {
            viewModel.checkUserCanRegister(userName, password)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun inputCheck(userName: String, password: String): Boolean {
        return !(TextUtils.isEmpty(userName) && password.isEmpty())
    }
}