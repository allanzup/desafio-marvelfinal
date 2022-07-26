package br.com.zup.marvel.ui.Login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.R
import br.com.zup.marvel.USER_KEY
import br.com.zup.marvel.data.model.User
import br.com.zup.marvel.databinding.ActivityLoginBinding
import br.com.zup.marvel.ui.Login.viewmodel.LoginViewModel
import br.com.zup.marvel.ui.Register.view.RegiterActivity
import br.com.zup.marvel.ui.home.view.HomeActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvRegisterNow?.setOnClickListener {
            initRegister()
        }
        binding.bvLogin.setOnClickListener {
            val user = getUserData()
            viewModel.validateUserData(user)
        }
        initObserve()
    }
    private fun initRegister() {
        startActivity(Intent(this, RegiterActivity::class.java))
    }
    private fun getUserData(): User {
        return User(
            email = binding.etUserEmail?.text.toString(),
            password = binding.etPassword?.text.toString())
    }
    private fun homeGoTo(user: User) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(USER_KEY, user)
        }
        startActivity(intent)
    }
    private fun initObserve() {
        viewModel.userLoginTo.observe(this) {
            homeGoTo(it)
        }
        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }
}