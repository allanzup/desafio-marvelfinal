package br.com.zup.marvel.ui.Register.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.R
import br.com.zup.marvel.USER_KEY
import br.com.zup.marvel.data.model.User
import br.com.zup.marvel.databinding.ActivityRegiterBinding
import br.com.zup.marvel.ui.Register.viewmodel.RegisterViewModel
import br.com.zup.marvel.ui.home.view.HomeActivity
import com.google.android.material.snackbar.Snackbar

class RegiterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegiterBinding
    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegiterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btRegister.setOnClickListener {
            val user = getUserData()
            viewModel.validateDataUser(user)
        }
        initObservers()
    }
    fun getUserData(): User {
        return User(
            name = binding.etNameRegister.text.toString(),
            email = binding.etEmailRegister.text.toString(),
            password = binding.etPasswordRegister.text.toString()
        )
    }
    private fun initObservers() {
        viewModel.registerState.observe(this) {
            goToHome(it)
        }

        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }
    private fun goToHome(user: User) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(USER_KEY, user)
        }
        startActivity(intent)
    }
}