package com.example.detapp.viewmodel

import androidx.lifecycle.ViewModel
import java.io.Closeable

class AuthViewModel(vararg closeables: Closeable?) : ViewModel(*closeables) {
}