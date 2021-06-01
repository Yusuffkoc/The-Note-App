package com.example.mynotesapp.fragments.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private var isShowPassw = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        view.pssw_img_id.setOnClickListener {
            isShowPassw = !isShowPassw
            showPassword(isShowPassw, view)
        }

        showPassword(isShowPassw, view)

        view.login_button.setOnClickListener {
            login()
        }

        view.newUser_id.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.canUserLogIn.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(R.id.action_loginFragment_to_listFragment)
            }
            else{
                Toast.makeText(requireContext(), "UserName or Password is invalid", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        return view
    }

    private fun login() {

        val userName = LoginUserName_id.text.toString()
        val password = LoginPassword_id.text.toString()


        viewModel.checkUserIsExist(userName,password)

    }

    //Lock and Unlocked
    private fun showPassword(isShow: Boolean, view: View) {
        if (isShow) {
            view.LoginPassword_id.transformationMethod = HideReturnsTransformationMethod.getInstance()
            view.pssw_img_id.setImageResource(R.drawable.ic_lock_open)
        } else {
            view.LoginPassword_id.transformationMethod = PasswordTransformationMethod.getInstance()
            view.pssw_img_id.setImageResource(R.drawable.ic_lock)
        }

    }

}