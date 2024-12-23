package com.stark.dogboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stark.dogboard.model.LoginRequest
import com.stark.dogboard.model.LoginUserResponse
import com.stark.dogboard.repository.LoginRepository
class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    private val _editTextUser = MutableLiveData<String>("")
    val editTextUser: LiveData<String> get() = _editTextUser

    private val _editTextPass = MutableLiveData<String>("")
    val editTextPass: LiveData<String> get() = _editTextPass

    val isBtnEnabled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(_editTextUser) { value = validateFields() }
        addSource(_editTextPass) { value = validateFields() }
    }

    private fun validateFields() =
        !_editTextUser.value.isNullOrEmpty() && !_editTextPass.value.isNullOrEmpty()

    fun textUserChange(newTxt: String) {
        _editTextUser.value = newTxt
    }

    fun textPassChange(newTxt: String) {
        _editTextPass.value = newTxt
    }

    fun login(username: String, email: String, password: String): LiveData<LoginUserResponse?> {
        val request = LoginRequest(username, email, password)
        return repository.login(request)
    }
}