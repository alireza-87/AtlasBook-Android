package com.midnightgeek.atlasbook.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.ktx.Firebase
import com.midnightgeek.atlasbook.MainActivity
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.databinding.FragmentLoginBinding
import com.midnightgeek.atlasbook.utils.PrefHandler
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {
    private val TAG = "LOGINFRAGMENT"
    lateinit var binder: FragmentLoginBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var prefHandler: PrefHandler

    private lateinit var viewModel: LoginViewModel

    private lateinit var gso : GoogleSignInOptions
    private lateinit var mGoogleSignInClient:GoogleSignInClient

    companion object {
        val TAG = "LoginFragment"
        fun newInstance() = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val view = binder.root
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binder.viewmodel = viewModel
        binder.prgBar.visibility=View.GONE

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso);

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initObserve() {
        viewModel.onClick.observe(this, Observer {
            if (it == 1) {
                prefHandler.setPreference(prefHandler.TAG_IS_GUEST,true)
                fragmentManager?.popBackStack()
                (activity as MainActivity).initMainPage()
            } else if (it == 2) {
                fireBaseAuth()
            }
        })
    }

    private fun fireBaseAuth() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1212)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1212) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                Log.e("TESTTT","done")
                fragmentManager?.popBackStack()
                (activity as MainActivity).initMainPage()
            }
        }
    }

}